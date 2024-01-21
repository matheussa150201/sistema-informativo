package sistema.informativo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sistema.informativo.Utils.Utils;
import sistema.informativo.domain.User;
import sistema.informativo.dto.EditarPublicacaoRequestDTO;
import sistema.informativo.dto.PublicacaoRequestDTO;
import sistema.informativo.dto.PublicacaoResponseDTO;
import sistema.informativo.exception.ErrorResponse;
import sistema.informativo.exception.ValidacaoException;
import sistema.informativo.model.Publicacao;
import sistema.informativo.service.PublicacaoService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/publicacao")
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    @Autowired
    private final MessageSource messageSource;

    @Autowired
    private Utils utils;

    public List<SseEmitter> sseEmitters = new CopyOnWriteArrayList<>();

    public PublicacaoController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Endpoint para salvar uma nova publicação.
     *
     * @param publicacaoRequestDto Objeto contendo os dados da nova publicação a ser salva.
     * @return ResponseEntity com status 200 (OK) e corpo contendo a publicação salva, ou
     *         ResponseEntity com status 400 (Bad Request) e corpo contendo informações sobre o erro de validação,
     *         ou ResponseEntity com status 500 (Internal Server Error) e corpo contendo informações sobre o erro interno no servidor.
     */
    @PostMapping
    public ResponseEntity<?> salvarPublicacao(@RequestBody PublicacaoRequestDTO publicacaoRequestDto){

        try{

            PublicacaoResponseDTO publicacao = publicacaoService.salvarPublicacao(publicacaoRequestDto);

            for (SseEmitter sseEmitter :sseEmitters){
                try{
                    sseEmitter.send(SseEmitter.event().name("new-post").data(publicacao));
                }catch (IOException e){
                    sseEmitters.remove(sseEmitter);
                }
            }

            return ResponseEntity.ok().body(publicacao);

        } catch (ValidacaoException e) {
            ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request", "/sistema-informativo/publicacao", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "/sistema-informativo/publicacao",  messageSource.getMessage("validation.error.erroInesperado", new Object[]{}, null));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @CrossOrigin
    @RequestMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscriber() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try{
            sseEmitter.send(SseEmitter.event().name("INIT"));
        }catch (IOException e){
            ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "/sistema-informativo/publicacao",  messageSource.getMessage("validation.error.erroInesperado", new Object[]{}, null));
            e.printStackTrace();
        }
        sseEmitter.onCompletion(() -> sseEmitters.remove(sseEmitter));
        sseEmitters.add(sseEmitter);
        return sseEmitter;
    }

    /**
     * Endpoint para buscar todas as publicações.
     *
     * @return ResponseEntity com status 200 (OK) e corpo contendo a lista de todas as publicações, ou
     *         ResponseEntity com status 500 (Internal Server Error) em caso de erro interno no servidor.
     */
    @GetMapping
    public ResponseEntity<List<?>> buscarTodasPublicacoes(){
        try{
            return ResponseEntity.ok().body(publicacaoService.buscarTodasPublicacoes());
        }catch (Exception e){
            throw e;
        }

    }

    /**
     * Endpoint para buscar uma publicação pelo ID.
     *
     * @param id O ID da publicação a ser buscada.
     * @return ResponseEntity com status 200 (OK) e corpo contendo a publicação encontrada, ou
     *         ResponseEntity com status 404 (Not Found) e corpo contendo uma mensagem de erro em caso de
     *         publicação não encontrada, ou
     *         ResponseEntity com status 500 (Internal Server Error) em caso de erro interno no servidor.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPublicacaoPorId(@PathVariable("id") Long id){

        Optional<Publicacao> publicacao = publicacaoService.buscarPublicacaoPorId(id);

        if (publicacao.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(404, "Not Found", "/sistema-informativo/publicacao",  messageSource.getMessage("validation.alerta.notFound", new Object[]{id}, null))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(publicacao);
    }

    /**
     * Endpoint para deletar uma publicação pelo ID.
     *
     * @param id O ID da publicação a ser deletada.
     * @return ResponseEntity com status 200 (OK) em caso de sucesso, ou
     *         ResponseEntity com status 404 (Not Found) e corpo contendo uma mensagem de erro em caso de
     *         publicação não encontrada, ou
     *         ResponseEntity com status 500 (Internal Server Error) em caso de erro interno no servidor.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPublicacaoPorId(@PathVariable("id") Long id){

        Optional<Publicacao> publicacaoModelOptional = publicacaoService.buscarPublicacaoPorId(id);

        User usuarioAtual = utils.obterUsuarioAtual();

        if (publicacaoModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(404, "Not Found", "/sistema-informativo/publicacao",  messageSource.getMessage("validation.alerta.notFound", new Object[]{id}, null))
            );
        }

        if (!publicacaoModelOptional.get().getUsuario().equals(usuarioAtual)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ErrorResponse(403, "Forbidden", "/sistema-informativo/publicacao", "Usuário não autorizado a excluir esta publicação.")
            );
        }
        publicacaoService.deletarPublicacaoPorId(publicacaoModelOptional.get().getIdPublicacao(), publicacaoModelOptional);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * Endpoint para atualizar uma publicação pelo ID.
     *
     * @param publicacaoRequestDTO Objeto contendo os dados para atualização da publicação.
     * @return ResponseEntity com status 200 (OK) e corpo contendo a publicação atualizada em caso de sucesso, ou
     *         ResponseEntity com status 404 (Not Found) e corpo contendo uma mensagem de erro em caso de
     *         publicação não encontrada, ou
     *         ResponseEntity com status 400 (Bad Request) e corpo contendo uma mensagem de erro em caso de
     *         requisição inválida, ou
     *         ResponseEntity com status 500 (Internal Server Error) e corpo contendo uma mensagem de erro em caso de
     *         erro interno no servidor.
     */
    @PutMapping()
    public ResponseEntity<?> atualizarPublicacaoPorId(@RequestBody EditarPublicacaoRequestDTO publicacaoRequestDTO) {

        try {
            Optional<Publicacao> publicacaoModelOptional = publicacaoService.buscarPublicacaoPorId(publicacaoRequestDTO.getId());

            if (publicacaoModelOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ErrorResponse(404, "Not Found", "/sistema-informativo/publicacao", messageSource.getMessage("validation.alerta.notFound", new Object[]{publicacaoRequestDTO.getId()}, null))
                );
            }

            User usuarioAtual = utils.obterUsuarioAtual();

            if (!publicacaoModelOptional.get().getUsuario().equals(usuarioAtual)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        new ErrorResponse(403, "Forbidden", "/sistema-informativo/publicacao", "Usuário não autorizado a excluir esta publicação.")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(publicacaoService.atualizarPublicacaoPorId(publicacaoModelOptional, publicacaoRequestDTO));
        } catch (ValidacaoException e) {
            ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request", "/sistema-informativo/publicacao", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "/sistema-informativo/publicacao",  messageSource.getMessage("validation.error.erroInesperado", new Object[]{}, null));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
