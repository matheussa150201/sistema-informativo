package sistema.informativo.dto;

import sistema.informativo.model.Imagem;

import java.time.LocalDateTime;
import java.util.List;

public class PublicacaoResponseDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private List<Imagem> imagens;

    private Boolean criador;

    private String usuario;

    private LocalDateTime data;

    public PublicacaoResponseDTO() {
    }

    public PublicacaoResponseDTO(Long id, String titulo, String descricao, List<Imagem> imagens, Boolean criador, String usuario, LocalDateTime data) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagens = imagens;
        this.criador = criador;
        this.usuario = usuario;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getCriador() {
        return criador;
    }

    public void setCriador(Boolean criador) {
        this.criador = criador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
