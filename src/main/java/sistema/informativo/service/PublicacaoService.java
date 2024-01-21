package sistema.informativo.service;

import sistema.informativo.dto.EditarPublicacaoRequestDTO;
import sistema.informativo.dto.PublicacaoRequestDTO;
import sistema.informativo.dto.PublicacaoResponseDTO;
import sistema.informativo.model.Publicacao;

import java.util.List;
import java.util.Optional;

public interface PublicacaoService {

    PublicacaoResponseDTO salvarPublicacao(PublicacaoRequestDTO publicacaoRequestDto);
    List<PublicacaoResponseDTO> buscarTodasPublicacoes();
    Optional<Publicacao> buscarPublicacaoPorId(Long id);
    void deletarPublicacaoPorId(Long id, Optional<Publicacao> publicacao);
    Publicacao atualizarPublicacaoPorId(Optional<Publicacao> publicacao, EditarPublicacaoRequestDTO publicacaoRequestDTO);

}
