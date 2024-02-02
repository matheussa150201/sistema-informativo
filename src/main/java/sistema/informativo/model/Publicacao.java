package sistema.informativo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.informativo.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "publicacao")
@Entity
@Builder
public class Publicacao implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPublicacao;

    @Column
    private String titulo;

    @Column
    private String descricao;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Imagem> imagens;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao_edicao")
    private LocalDateTime dataCriacaoEdicao;

    public String getUsuarioComArroba() {
        return "@" + usuario;
    }
}


