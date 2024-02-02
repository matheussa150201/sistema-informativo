package sistema.informativo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.informativo.model.Imagem;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicacaoResponseDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private List<Imagem> imagens;

    private Boolean criador;

    private String usuario;

    private LocalDateTime data;

}
