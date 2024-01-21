package sistema.informativo.dto;

import java.util.List;

public class PublicacaoRequestDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private List<FileResponseDTO> imagens;

    public PublicacaoRequestDTO(String titulo, String descricao, List<FileResponseDTO> imagens) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagens = imagens;
    }

    public PublicacaoRequestDTO(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public PublicacaoRequestDTO(Long id, String titulo, String descricao, List<FileResponseDTO> imagens) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagens = imagens;
    }

    public PublicacaoRequestDTO() {
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

    public List<FileResponseDTO> getImagens() {
        return imagens;
    }

    public void setImagens(List<FileResponseDTO> imagens) {
        this.imagens = imagens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
