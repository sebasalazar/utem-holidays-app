package cl.utem.cpyd.holiday.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sebastián Salazar Molina <sebasalazar@gmail.com>
 */
public class RutUtils implements Serializable {

    private static final long serialVersionUID = 5154475413864581120L;

    // Patrón del rut
    public static final String DEFAULT_RUT_PATTERN = "##.###.###-X";
    private static final Pattern OUT_RUT_RE = Pattern.compile("([0\\#\\.\\,]+)([^X^x^\\,^\\.^9]*)([Xx]*)");
    private static final Pattern IN_RUT_RE = Pattern.compile("([0-9\\.]+)\\-([a-zA-Z0-9]+)");
    private static final Logger LOGGER = LoggerFactory.getLogger(RutUtils.class);

    /**
     * Clase utilitaria, no debería instanciarse nunca.
     */
    private RutUtils() {
        throw new AssertionError();
    }

    /**
     * Recibe un rut como string y entrega su parte numérica.
     *
     * @param value Rut con dígito verificador
     * @return Retorna la parte numérica del rut o null en cualquier otro caso.
     */
    public static Integer parseRut(final String value) {
        Integer result = null;
        try {
            if (StringUtils.isNotBlank(value)) {
                // Borramos los caracteres esperables
                String rut = StringUtils.remove(StringUtils.trimToEmpty(value), ".");
                rut = StringUtils.remove(rut, ",");
                rut = StringUtils.remove(rut, "-");
                // Separamos el numero y el digito verificador

                String numRut = StringUtils.substring(rut, 0, StringUtils.length(rut) - 1);
                String dv = StringUtils.upperCase(StringUtils.substring(rut, StringUtils.length(rut) - 1));
                if (NumberUtils.isDigits(numRut)) {
                    int longRut = Integer.parseInt(numRut);
                    if (dv.charAt(0) == getDV(longRut)) {
                        result = longRut;
                    }
                }

            }
        } catch (Exception e) {
            result = null;
            LOGGER.error("Error al parsear Rut: {}", e.toString());
            LOGGER.debug("Error al parsear Rut: {}", e.toString(), e);
        }
        return result;
    }

    /**
     *
     * @param rut Número de rut
     * @return Formatea el número del rut, según el patrón por defecto.
     */
    public static String formatRut(final Number rut) {
        return formatRut(rut, DEFAULT_RUT_PATTERN);
    }

    /**
     *
     * @param o Número con el rut
     * @param pattern Patrón a usar para el rut
     * @return Formatea el número del rut, según el patrón entregado.
     */
    public static String formatRut(final Number o, final String pattern) {
        String formatedCheck = StringUtils.EMPTY;
        try {
            Number rut = (Number) o;
            if (rut != null) {
                String value = rut + "-" + getDV(rut.longValue());
                Matcher outMatcher = OUT_RUT_RE.matcher(pattern);
                if (!(outMatcher.matches())) {
                    LOGGER.debug("Pattern incorrecto : " + pattern);
                }

                Matcher inMatcher = IN_RUT_RE.matcher(value);
                if ((inMatcher.matches())) {
                    String checkFmt = outMatcher.group(1);
                    String valueCheck = inMatcher.group(1);
                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    int indexGroupChar = checkFmt.contains(".") ? checkFmt.indexOf(".") : checkFmt.indexOf(",");
                    if (indexGroupChar >= 0) {
                        dfs.setGroupingSeparator(checkFmt.charAt(indexGroupChar));
                    }
                    DecimalFormat df = null;
                    if (indexGroupChar >= 0) {
                        df = new DecimalFormat(checkFmt.replace(
                                checkFmt.charAt(indexGroupChar), ','), dfs);
                    } else {
                        df = new DecimalFormat(checkFmt, dfs);
                    }
                    df.setGroupingUsed(indexGroupChar >= 0);
                    formatedCheck = df.format(Long.parseLong(valueCheck));
                    String separator = outMatcher.group(2);
                    if (!StringUtils.isEmpty(separator)) {
                        formatedCheck = formatedCheck + separator;
                    }
                    String dv = outMatcher.group(3);
                    if (!StringUtils.isEmpty(dv)) {
                        formatedCheck = formatedCheck + inMatcher.group(2);
                    }
                }
            }
        } catch (Exception e) {
            formatedCheck = StringUtils.EMPTY;
            LOGGER.error("Error al formatear Rut: {}", e.toString());
            LOGGER.debug("Error al formatear Rut: {}", e.toString(), e);
        }
        return formatedCheck;
    }

    /**
     *
     * @param rut Número del rut
     * @return Retorna el dígito verificador del rut del parámetro
     */
    public static char getDV(final long rut) {
        long M = 0, S = 1, T = rut;
        for (; T != 0; T /= 10) {
            S = (S + T % 10 * (9 - M++ % 6)) % 11;
        }
        return (char) (S != 0 ? S + 47 : 75);
    }

    /**
     *
     * @param rut Parte numérica del rut.
     * @return El string del dígito verificador.
     */
    public static String getCheckDigit(final Number rut) {
        String check = StringUtils.EMPTY;
        if (rut != null) {
            check = StringUtils.upperCase(StringUtils.trimToEmpty(String.format("%s", getDV(rut.longValue()))));
        }
        return check;
    }

    /**
     *
     * @param rut Rut en formateado
     * @return Retorna verdadero si el rut es válido y falso en cualquier otro
     * caso.
     */
    public static boolean isRut(final String rut) {
        boolean ok = false;
        try {
            if (StringUtils.isNotBlank(rut)) {
                String value = StringUtils.remove(StringUtils.trimToEmpty(rut), ".");
                value = StringUtils.remove(value, ",");
                value = StringUtils.remove(value, "-");
                value = StringUtils.upperCase(value);

                String numberStr = StringUtils.substring(value, 0, StringUtils.length(value) - 1);
                char digit = value.charAt(StringUtils.length(value) - 1);

                Long number = Long.parseLong(numberStr);
                if (digit == getDV(number)) {
                    ok = true;
                }
            }
        } catch (Exception e) {
            ok = false;
            LOGGER.error("Error al validar rut: {}", e.toString());
            LOGGER.debug("Error al validar rut: {}", e.toString(), e);
        }

        return ok;
    }
}
