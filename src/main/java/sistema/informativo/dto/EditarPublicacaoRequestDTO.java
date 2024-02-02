package sistema.informativo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditarPublicacaoRequestDTO {

    private Long id;

    private String titulo;

    private String descricao;

}
