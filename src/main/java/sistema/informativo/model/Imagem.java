package sistema.informativo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "imagem")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idImagem;

    @Column
    private String publicId;

    @Column
    private String nome;

    @Column
    private String url;

    @Column
    private String formato;

    @ManyToOne
    @JoinColumn(name = "id_publicacao")
    @JsonBackReference
    private Publicacao publicacao;

}