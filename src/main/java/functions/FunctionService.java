package functions;

import org.springframework.stereotype.Component;
import validacaocodigoboleto.CodigoBoletoUtil;

import java.util.Optional;
import java.util.function.Function;

@Component
public class FunctionService {

    public static final Function<Optional, Boolean> VALIDA_BOLETO = handler -> {
        String valor = (String) handler.get();
        valor = valor.replace("-", "").replace(".", "").replace(" ", "");

        if (valor.length() == 47) {
            return CodigoBoletoUtil.boleto(valor);
        } else if (valor.length() == 48) {
            return CodigoBoletoUtil.convenio(valor);
        } else {
            return false;
        }
    };

    public static final Function<Optional, Boolean> VALIDA_UUID = handler -> {
        String valor = (String) handler.get();
        return valor.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
    };

    public static final Function<Optional, Boolean> VALIDA_CPF = handler -> {
        String valor = (String) handler.get();

        valor = valor.trim().replace(".", "").replace("-", "").replace("_", "");

        if (valor.length() != 11 || valor.equals("00000000000") || valor.equals("99999999999")) {
            return false;
        }
        String digitos = valor.substring(0, 9);
        String dvs = valor.substring(9, 11);

        String dv1 = calculaDigito(digitos);
        String dv2 = calculaDigito(digitos + dv1);

        return dvs.equals(dv1 + dv2);
    };

    public static final Function<Optional, Boolean> VALIDA_CNPJ = handler -> {
        String valor = (String) handler.get();

        String cnpj = valor.trim().replace(".", "").replace("-", "").replace("/", "").replace("_", "");
        if ((cnpj == null) || (cnpj.length() != 14)) {
            return false;
        }

        String digito1 = calculaDigito(cnpj.substring(0, 12));
        String digito2 = calculaDigito(cnpj.substring(0, 12) + digito1);
        return cnpj.equals(cnpj.substring(0, 12) + digito1 + digito2);

    };

    public static String calculaDigito(String value) {
        int dv = 0;
        if (value.length() < 11) {
            int peso = value.length() + 1;
            for (int i = 0; i < value.length(); i++) {
                dv += Integer.parseInt(value.substring(i, i + 1)) * peso;
                peso--;
            }
        } else {
            int[] pesoCnpj = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            for (int indice = value.length() - 1, digito; indice >= 0; indice--) {
                digito = Integer.parseInt(value.substring(indice, indice + 1));
                dv += digito * pesoCnpj[pesoCnpj.length - value.length() + indice];
            }
        }

        dv = 11 - (dv % 11);

        if (dv > 9) {
            return "0";
        }

        return String.valueOf(dv);
    }
}
