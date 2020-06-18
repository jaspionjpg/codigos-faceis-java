package springutils.security.models;

public enum StatusUsuario {

    ATIVO("Ativo", "active"),
    INATIVO("Inativo", "inactive");

    private String nome;
    private String color;

    StatusUsuario(String nome, String color) {
        this.nome = nome;
        this.color = color;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

