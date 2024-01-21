package sistema.informativo.service;

import org.springframework.web.multipart.MultipartFile;
import sistema.informativo.dto.FileResponseDTO;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<FileResponseDTO> salvarImagemNaClaud(List<MultipartFile> files) throws IOException;
}
