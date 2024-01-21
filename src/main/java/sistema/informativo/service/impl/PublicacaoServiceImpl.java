package sistema.informativo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.informativo.Utils.Utils;
import sistema.informativo.Utils.ValidacaoUtils;
import sistema.informativo.domain.User;
import sistema.informativo.dto.EditarPublicacaoRequestDTO;
import sistema.informativo.dto.FileResponseDTO;
import sistema.informativo.dto.PublicacaoRequestDTO;
import sistema.informativo.dto.PublicacaoResponseDTO;
import sistema.informativo.exception.ValidacaoException;
import sistema.informativo.model.Imagem;
import sistema.informativo.model.Publicacao;
import sistema.informativo.repository.PublicacaoRepository;
import sistema.informativo.service.PublicacaoService;
import sistema.informativo.service.StorageCloudinaryService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PublicacaoServiceImpl implements PublicacaoService {

    @Autowired
    private ValidacaoUtils validacaoUtils;

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private StorageCloudinaryService storageCloudinaryService;

    @Autowired
    private Utils utils;

    /**
     * Método para salvar uma nova publicação com base nos dados fornecidos no DTO de solicitação.
     *
     * @param publicacaoRequestDto DTO contendo as informações da nova publicação.
     * @return A publicação salva.
     * @throws ValidacaoException Exceção lançada se houver falha na validação das informações.
     * @throws RuntimeException   Exceção geral lançada para outros erros inesperados.
     */
    @Override
    public PublicacaoResponseDTO salvarPublicacao(PublicacaoRequestDTO publicacaoRequestDto) {
        try {
            validacaoUtils.validarInformacoes(publicacaoRequestDto.getTitulo(), "Titulo");
            validacaoUtils.validarInformacoes(publicacaoRequestDto.getDescricao(), "Descrição");
            //---
            User usuarioAtual = utils.obterUsuarioAtual();
            if (usuarioAtual == null) {
                throw new RuntimeException("validation.error.erroInesperado");
            }
            //---
            Publicacao publicacao = new Publicacao();
            //---
            BeanUtils.copyProperties(publicacaoRequestDto, publicacao);

            publicacao.setImagens(new ArrayList<>());
            publicacao.setUsuario(usuarioAtual);
            publicacao.setDataCriacaoEdicao(LocalDateTime.now());

            if (publicacaoRequestDto.getImagens() != null) {
                for (FileResponseDTO imagemDto : publicacaoRequestDto.getImagens()) {
                    Imagem imagem = new Imagem();
                    BeanUtils.copyProperties(imagemDto, imagem);
                    imagem.setPublicacao(publicacao);
                    publicacao.getImagens().add(imagem);
                }
            }
            Publicacao publicacaoRetorno = publicacaoRepository.save(publicacao);

                Boolean criador = publicacao.getUsuario().equals(usuarioAtual);

                PublicacaoResponseDTO publicacaoDTO = new PublicacaoResponseDTO();
                publicacaoDTO.setId(publicacaoRetorno.getIdPublicacao());
                publicacaoDTO.setTitulo(publicacaoRetorno.getTitulo());
                publicacaoDTO.setDescricao(publicacaoRetorno.getDescricao());
                publicacaoDTO.setCriador(criador);
                publicacaoDTO.setImagens(publicacaoRetorno.getImagens());
                publicacaoDTO.setUsuario("@" + publicacaoRetorno.getUsuario().getLogin());
                publicacaoDTO.setData(publicacaoRetorno.getDataCriacaoEdicao());

            //---
            return publicacaoDTO;
            //---
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception ex) {
            throw new RuntimeException("validation.error.erroInesperado", ex);
        }

    }

    /**
     * Método para buscar todas as publicações cadastradas no sistema.
     *
     * @return Lista de todas as publicações.
     * @throws RuntimeException Exceção geral lançada para erros inesperados durante a busca.
     */
    @Override
    public List<PublicacaoResponseDTO> buscarTodasPublicacoes() {

            try {
                User usuarioAtual = utils.obterUsuarioAtual();

                List<Publicacao> publicacoes = publicacaoRepository.findAllOrderedByDataCriacaoEdicaoAsc();

                List<PublicacaoResponseDTO> publicacoesDTO = new ArrayList<>();

                for (Publicacao publicacao : publicacoes) {
                    Boolean criador = publicacao.getUsuario().equals(usuarioAtual);

                    PublicacaoResponseDTO publicacaoDTO = new PublicacaoResponseDTO();
                    publicacaoDTO.setId(publicacao.getIdPublicacao());
                    publicacaoDTO.setTitulo(publicacao.getTitulo());
                    publicacaoDTO.setDescricao(publicacao.getDescricao());
                    publicacaoDTO.setCriador(criador);
                    publicacaoDTO.setImagens(publicacao.getImagens());
                    publicacaoDTO.setUsuario("@" + publicacao.getUsuario().getLogin());
                    publicacaoDTO.setData(publicacao.getDataCriacaoEdicao());
                    publicacoesDTO.add(publicacaoDTO);
                }

                return publicacoesDTO;
            } catch (Exception e) {
                throw e;
            }
    }

    /**
     * Método para buscar uma publicação pelo seu ID.
     *
     * @param id Identificador único da publicação a ser buscada.
     * @return Um Optional contendo a publicação encontrada ou vazio se não encontrada.
     * @throws ValidacaoException Exceção lançada para erros de validação nos parâmetros.
     * @throws RuntimeException    Exceção geral lançada para erros inesperados durante a busca.
     */
    @Override
    public Optional<Publicacao> buscarPublicacaoPorId(Long id) {
        try {
            validacaoUtils.validarInformacoes(id, "Id");
            return publicacaoRepository.findById(id);
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception ex) {
            throw new RuntimeException("validation.error.erroInesperado", ex);
        }
    }

    /**
     * Método para deletar uma publicação pelo seu ID.
     *
     * @param id Identificador único da publicação a ser deletada.
     */
    @Override
    public void deletarPublicacaoPorId(Long id, Optional<Publicacao> publicacao) {

        publicacao.get().getImagens().forEach(imagem -> {
            try {
                storageCloudinaryService.delete(imagem.getPublicId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        publicacaoRepository.deleteById(id);
    }

    /**
     * Método para atualizar uma publicação pelo seu ID.
     *
     * @param publicacao Entidade da publicação a ser atualizada.
     * @param publicacaoRequestDTO Dados da publicação a serem atualizados.
     * @return A publicação atualizada.
     */
    @Override
    public Publicacao atualizarPublicacaoPorId(Optional<Publicacao> publicacao, EditarPublicacaoRequestDTO publicacaoRequestDTO) {
        try {
            validacaoUtils.validarInformacoes(publicacaoRequestDTO.getId(), "ID");
            validacaoUtils.validarInformacoes(publicacaoRequestDTO.getDescricao(), "Descrição");
            validacaoUtils.validarInformacoes(publicacaoRequestDTO.getTitulo(), "Titulo");


            publicacao.get().setDescricao(publicacaoRequestDTO.getDescricao());
            publicacao.get().setTitulo(publicacaoRequestDTO.getTitulo());

            Publicacao publicacaoEntidade = publicacao.orElse(new Publicacao());
           return publicacaoRepository.save(publicacaoEntidade);
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception ex) {
            throw new RuntimeException("validation.error.erroInesperado", ex);
        }
    }

}

