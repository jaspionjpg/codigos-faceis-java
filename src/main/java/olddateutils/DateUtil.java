package olddateutils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private DateUtil() {

    }

    public static Date obterFinalDoDia(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date obterInicioDoDia(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static String obterDataFormatada(String formato, Date data) {
        if (StringUtils.isBlank(formato) || data == null) {
            return "";
        }

        return new SimpleDateFormat(formato).format(data);
    }

    public static Date formatarData(String formato, Date data) {
        String dataFormatada = obterDataFormatada(formato, data);
        try {
            return new SimpleDateFormat(formato).parse(dataFormatada);
        } catch (ParseException e) {
            System.out.println("DateUtil.formatarData -->" + DateUtil.class + e);
        }
        return null;
    }

    public static Date criarDataFormatada(String formato) {
        return formatarData(formato, new Date());
    }

    public static int contarDiasUteis(Date dataInicial, Date dataFinal) {
        Calendar calendarInicial = Calendar.getInstance();
        calendarInicial.setTime(dataInicial);

        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTime(dataFinal);

        int diasUteis = 0;

        if (calendarInicial.get(Calendar.YEAR) == calendarFinal.get(Calendar.YEAR) &&
                calendarInicial.get(Calendar.DAY_OF_YEAR) == calendarFinal.get(Calendar.DAY_OF_YEAR)) {
            return 0;
        }

        if (calendarInicial.getTimeInMillis() > calendarFinal.getTimeInMillis()) {
            calendarInicial.setTime(dataFinal);
            calendarFinal.setTime(dataInicial);
        }

        calendarInicial.add(Calendar.DATE, 1);
        do {
            int day = calendarInicial.get(Calendar.DAY_OF_WEEK);
            if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY)) {
                diasUteis++;
            }

            calendarInicial.add(Calendar.DATE, 1);
        } while (!calendarInicial.after(calendarFinal));

        return diasUteis;
    }

    public static Date adicionarDiasUteis(Date data, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);

        return adicionarDiasUteis(calendar, dias).getTime();
    }

    public static Calendar adicionarDiasUteis(Calendar calendar, int dias) {

        if (dias < 1) {
            return calendar;
        }

        int i = 0;

        do {
            calendar.add(Calendar.DATE, 1);

            int dia = calendar.get(Calendar.DAY_OF_WEEK);

            if (dia != Calendar.SATURDAY && dia != Calendar.SUNDAY) {
                i++;
            }
        } while (i < dias);

        return calendar;
    }

    public static Date zerarHoraMesmoDia(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);

        return zerarHoraMesmoDia(calendar).getTime();
    }

    public static Calendar zerarHoraMesmoDia(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar;
    }


}
