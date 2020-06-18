package querybuildbyannotation;

import com.google.api.client.util.Value;

public class PaginacaoVO {
    @Value("20")
    private int limite = 20;

    @Value("0")
    private int pagina = 0;


    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }
}
