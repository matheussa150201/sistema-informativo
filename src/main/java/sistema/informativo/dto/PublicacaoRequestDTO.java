package sistema.informativo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicacaoRequestDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private List<FileResponseDTO> imagens;

}
