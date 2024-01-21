package sistema.informativo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
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

    public Imagem(Long idImagem, String publicId, String nome, String url, String formato, Publicacao publicacao) {
        this.idImagem = idImagem;
        this.publicId = publicId;
        this.nome = nome;
        this.url = url;
        this.formato = formato;
        this.publicacao = publicacao;
    }

    public Imagem() {
    }

    public Long getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(Long idImagem) {
        this.idImagem = idImagem;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Publicacao getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(Publicacao publicacao) {
        this.publicacao = publicacao;
    }
}