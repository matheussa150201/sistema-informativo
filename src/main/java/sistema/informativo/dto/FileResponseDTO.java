package sistema.informativo.dto;

public class FileResponseDTO {

    private String publicId;
    private String nome;
    private String url;
    private String formato;

    public FileResponseDTO(String publicId, String nome, String url, String formato) {
        this.publicId = publicId;
        this.nome = nome;
        this.url = url;
        this.formato = formato;
    }

    public FileResponseDTO() {
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
}
