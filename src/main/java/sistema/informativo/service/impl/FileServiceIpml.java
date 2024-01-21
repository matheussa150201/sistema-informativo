package sistema.informativo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sistema.informativo.Utils.ValidacaoUtils;
import sistema.informativo.dto.FileResponseDTO;
import sistema.informativo.exception.ValidacaoException;
import sistema.informativo.service.FileService;
import sistema.informativo.service.StorageCloudinaryService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceIpml implements FileService {

    @Autowired
    private ValidacaoUtils validacaoUtils;

    @Autowired
    private StorageCloudinaryService storageCloudinaryService;

    /**
     * Método para salvar imagens na nuvem (Cloudinary) e retornar informações sobre os arquivos salvos.
     *
     * @param files Lista de arquivos a serem salvos.
     * @return Lista de DTOs contendo informações sobre os arquivos salvos.
     * @throws IOException              Exceção de E/S ao lidar com os arquivos.
     * @throws ValidacaoException       Exceção lançada se houver falha na validação das informações.
     * @throws RuntimeException         Exceção geral lançada para outros erros inesperados.
     */
    @Override
    public List<FileResponseDTO> salvarImagemNaClaud(List<MultipartFile> files) throws IOException {
        List<FileResponseDTO> fileResponseDTOList = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                if (bufferedImage == null) {
                    throw new RuntimeException("Imagem inválida!!");
                }

                Map<String, String> map = storageCloudinaryService.upload(file); // Substitua isso pelo método correto

                FileResponseDTO fileResponseDTO = new FileResponseDTO();
                fileResponseDTO.setPublicId(map.get("public_id"));
                fileResponseDTO.setUrl(map.get("url"));
                fileResponseDTO.setNome(map.get("original_filename"));
                fileResponseDTO.setFormato(map.get("format"));

                validacaoUtils.validarInformacoes(fileResponseDTO.getPublicId(), "Id da Foto");
                validacaoUtils.validarInformacoes(fileResponseDTO.getUrl(), "URL");

                fileResponseDTOList.add(fileResponseDTO);
            }
            return fileResponseDTOList;
        } catch (ValidacaoException e) {
            throw e;
        } catch (Exception ex) {
            throw new RuntimeException("validation.error.erroInesperado", ex);
        }
    }
}
