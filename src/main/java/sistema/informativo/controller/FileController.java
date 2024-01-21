package sistema.informativo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sistema.informativo.exception.ErrorResponse;
import sistema.informativo.exception.ValidacaoException;
import sistema.informativo.service.FileService;
import sistema.informativo.service.StorageCloudinaryService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/storege")
public class FileController {

    @Autowired
    private StorageCloudinaryService storageCloudinaryService;

    @Autowired
    private FileService fileService;

    /**
     * Endpoint para realizar o upload de imagens para o serviço de armazenamento em nuvem (Cloudinary).
     *
     * @param files Lista de arquivos a serem enviados no upload.
     * @return ResponseEntity com status 200 (OK) e corpo contendo informações sobre as imagens salvas,
     * ou ResponseEntity com status 400 (Bad Request) em caso de validação de dados inválida,
     * ou ResponseEntity com status 500 (Internal Server Error) em caso de erro interno no servidor.
     * @throws IOException Exceção lançada em caso de erro de entrada/saída durante o processo de upload.
     */
    @PostMapping("/upload")
    public ResponseEntity<?> salvarImagemNaClaud(@RequestParam("files") List<MultipartFile> files) throws IOException {
        try {
            return ResponseEntity.ok().body(
                    fileService.salvarImagemNaClaud(files)
            );
        } catch (ValidacaoException e) {
            ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request", "/sistema-informativo/publicacao", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "/sistema-informativo/publicacao", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Endpoint para excluir um arquivo no serviço de armazenamento em nuvem (Cloudinary) com base no ID fornecido.
     *
     * @param id Identificador único do arquivo a ser excluído.
     * @return ResponseEntity com status 200 (OK) e corpo contendo informações sobre a exclusão bem-sucedida,
     * ou ResponseEntity com status 500 (Internal Server Error) em caso de erro interno no servidor.
     * @throws IOException Exceção lançada em caso de erro de entrada/saída durante o processo de exclusão.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> delete(@PathVariable("id") String id) throws IOException {
        try {
            return ResponseEntity.ok().body(storageCloudinaryService.delete(id));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}