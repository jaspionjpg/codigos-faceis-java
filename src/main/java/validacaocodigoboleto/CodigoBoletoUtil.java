package validacaocodigoboleto;

import java.util.Arrays;

public class CodigoBoletoUtil {

    public static boolean convenio(String codigoBarras) {
        codigoBarras = codigoBarras.replace("-", "").replace(".", "").replace(" ", "");
        if (codigoBarras.length() != 48) {
            return false;
        }

        String[] blocos = new String[4];
        blocos[0] = codigoBarras.substring(0, 12);
        blocos[1] = codigoBarras.substring(12, 24);
        blocos[2] = codigoBarras.substring(24, 36);
        blocos[3] = codigoBarras.substring(36, 48);

        Boolean isModulo10 = Arrays.asList('6', '7').contains(codigoBarras.charAt(2));
        Integer valido = 0;

        for (int i = 0; i < blocos.length; i++) {
            if (isModulo10) {
                if (modulo10(blocos[i])) {
                    valido++;
                }
            } else {
                if (modulo11(blocos[i])) {
                    valido++;
                }
            }


        }
        return valido == 4;
    }

    public static Boolean boleto(String linhaDigitavel) {
        linhaDigitavel = linhaDigitavel.replace("-", "").replace(".", "").replace(" ", "");

        if (linhaDigitavel.length() != 47) {
            return false;
        }

        String[] blocos = new String[3];

        blocos[0] = linhaDigitavel.substring(0, 10);
        blocos[1] = linhaDigitavel.substring(10, 21);
        blocos[2] = linhaDigitavel.substring(21, 32);

        Integer valido = 0;
        for (int i = 0; i < blocos.length; i++) {
            if (modulo10(blocos[i])) {
                valido++;
            }
        }

        return valido == 3;
    }

    private static Boolean modulo10(String bloco) {
        Integer tamanhoBloco = bloco.length() - 1;
        Integer digitoVerificador = Integer.valueOf(String.valueOf(bloco.charAt(tamanhoBloco)));

        String codigo = bloco.substring(0, tamanhoBloco);
        codigo = new StringBuilder(codigo).reverse().toString();

        Integer somatorio = 0;
        for (int i = 0; i < codigo.length(); i++) {
            Integer soma = Integer.valueOf(String.valueOf(codigo.charAt(i))) * (i % 2 == 0 ? 2 : 1);
            if (soma > 9) {
                String[] split = soma.toString().split("");
                for (int j = 0; j < split.length; j++) {
                    somatorio += Integer.valueOf(split[j]);
                }

            } else {
                somatorio += soma;
            }
        }

        Double ceil = Math.ceil((double) somatorio / 10);
        Integer dezenaSuperiorSomatorioMenosSomatorio = (ceil.intValue() * 10) - somatorio;
        return dezenaSuperiorSomatorioMenosSomatorio == digitoVerificador;
    }

    private static Boolean modulo11(String bloco) {
        Integer tamanhoBloco = bloco.length() - 1;
        Integer digitoVerificador = Integer.valueOf(String.valueOf(bloco.charAt(tamanhoBloco)));
        Integer dezenaSuperiorSomatorioMenosSomatorio = 0;

        String codigo = bloco.substring(0, tamanhoBloco);
        codigo = new StringBuilder(codigo).reverse().toString();

        Integer somatorio = 0;
        for (int i = 0; i < codigo.length(); i++) {
            somatorio += Integer.valueOf(String.valueOf(codigo.charAt(i))) * (2 + (i >= 8 ? i - 8 : i));
        }

        Integer restoDivisao = somatorio % 11;

        if (restoDivisao == 0 || restoDivisao == 1) {
            dezenaSuperiorSomatorioMenosSomatorio = 0;

        } else if (restoDivisao == 10) {
            dezenaSuperiorSomatorioMenosSomatorio = 1;
        } else {
            Double ceil = (Math.ceil((double) somatorio / 11) * 11) - somatorio;
            dezenaSuperiorSomatorioMenosSomatorio = ceil.intValue();
        }

        return dezenaSuperiorSomatorioMenosSomatorio == digitoVerificador;
    }
}
