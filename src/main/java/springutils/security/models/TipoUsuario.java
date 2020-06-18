package springutils.security.models;


public enum TipoUsuario {

    ADMINISTRADOR("Administrador", "/app", Boolean.FALSE),
    OPERADOR("Operador", "/app", Boolean.FALSE),
    SUPERVISOR("Supervisor", "/app", Boolean.FALSE);

    private String nome;
    private String telaInicial;
    private Boolean limitaClientes;

    TipoUsuario(String nome, String telaInicial, Boolean limitaClientes) {
        this.nome = nome;
        this.telaInicial = telaInicial;
        this.limitaClientes = limitaClientes;
    }

    public String getNome() {
        return nome;
    }

    public String getTelaInicial() {
        return telaInicial;
    }

    public Boolean getLimitaClientes() {
        return limitaClientes;
    }
}