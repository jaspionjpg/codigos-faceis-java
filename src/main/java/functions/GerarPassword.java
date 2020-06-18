package functions;

import java.security.SecureRandom;

public class GerarPassword {
    public static String generatePassword(int len) {
        String MAIUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
        String NUMERICOS = "0123456789";
        String ESPECIAIS = "!@#$%^&*_=+-";
        SecureRandom random = new SecureRandom();

        String dic = MAIUSCULAS + MINUSCULAS + NUMERICOS + ESPECIAIS;
        String result = "";

        len -= 4;
        result += MAIUSCULAS.charAt(random.nextInt(MAIUSCULAS.length()));
        result += NUMERICOS.charAt(random.nextInt(NUMERICOS.length()));
        result += ESPECIAIS.charAt(random.nextInt(ESPECIAIS.length()));
        result += MINUSCULAS.charAt(random.nextInt(MINUSCULAS.length()));

        for (int i = 0; i < len; i++) {
            result += dic.charAt(random.nextInt(dic.length()));
        }

        return result;
    }
}
