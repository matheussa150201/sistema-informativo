package sistema.informativo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.Data;
import sistema.informativo.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "publicacao")
@Entity
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


    public Publicacao(Long idPublicacao, String titulo, String descricao, List<Imagem> imagens, User usuario, LocalDateTime dataCriacaoEdicao) {
        this.idPublicacao = idPublicacao;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagens = imagens;
        this.usuario = usuario;
        this.dataCriacaoEdicao = dataCriacaoEdicao;
    }

    public Publicacao(String titulo, String descricao, String idFoto) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Publicacao() {
    }

    public Long getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(Long idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataCriacaoEdicao() {
        return dataCriacaoEdicao;
    }

    public void setDataCriacaoEdicao(LocalDateTime dataCriacaoEdicao) {
        this.dataCriacaoEdicao = dataCriacaoEdicao;
    }
}
