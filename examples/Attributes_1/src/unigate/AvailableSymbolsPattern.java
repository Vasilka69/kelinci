package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableSymbolsPattern {

    private static final Pattern ID_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9]+[a-zA-Z0-9_.]*[a-zA-Z0-9]+$");
    public static boolean validateId(String id) {
        return ID_PATTERN.matcher(id).matches();
    }

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^([A-Z0-9_.])+$");

    private static final Pattern SNILS_PATTERN = Pattern.compile("^\\d{3}-\\d{3}-\\d{3}[(\\s-)]\\d{2}$");

    public static boolean validateUsername(String id) {
        return USERNAME_PATTERN.matcher(id).matches();
    }

    private static final Pattern SYS_USERNAME_PATTERN = Pattern.compile("^([A-Za-z0-9_.,])+$");
    public static boolean validateSysUsername(String id) {
        return SYS_USERNAME_PATTERN.matcher(id).matches();
    }

    private static final Pattern FIO_PATTERN = Pattern.compile("^([а-яА-ЯёЁ\\-])+$");
    public static boolean validateAnthroponym(String anthroponym) {
        return FIO_PATTERN.matcher(anthroponym).matches();
    }

    public static boolean validateContainsOnStartOrEnd(String symbol, String value) {
        Pattern pattern = Pattern.compile("^" + Pattern.quote(symbol) + "+|" + Pattern.quote(symbol) + "+$");
        return pattern.matcher(value).matches();
    }

    public static boolean validateContainsOnEnd(String symbol, String value) {
        Pattern pattern = Pattern.compile(Pattern.quote(symbol) + "$");
        return pattern.matcher(value).matches();
    }

    private static final Pattern HEX_PATTERN = Pattern.compile("^([A-F0-9])+$");
    public static boolean validateHex(String value) {
        return HEX_PATTERN.matcher(value).matches();
    }

    public static boolean validateSnils(String snils) {
        return SNILS_PATTERN.matcher(snils).matches();
    }
}
