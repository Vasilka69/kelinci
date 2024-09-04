package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kamatech.unigate.common.exception.ResourceIllegalArgumentException;
import ru.kamatech.unigate.security.model.IndividualPerson;
import ru.kamatech.unigate.security.model.SysUser;
import ru.kamatech.unigate.security.model.settings.SettingPassword;
import ru.kamatech.unigate.security.repository.UsersLastPasswordRepository;
import ru.kamatech.unigate.security.service.settings.SettingsPasswordService;

import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {
    private static final String CHARACTER_PART = "A-Za-z";
    private static final String NUMBER_PART = "0-9";
    private static final String EMAIL_BEGIN_PART = "[" + CHARACTER_PART + NUMBER_PART + "._%+-]";
    private static final String EMAIL_MIDDLE_PART = "[" + CHARACTER_PART + NUMBER_PART + ".-]";
    private static final String EMAIL_END_PART = "[" + CHARACTER_PART + "]{2,6}";
    private static final String EMAIL_REGEX = "^" + EMAIL_BEGIN_PART + "+@" + EMAIL_MIDDLE_PART + "+\\."  + EMAIL_END_PART + "$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PHONE_REGEX = "^\\+7\\d{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "_@#$%*^";

    public static String getName(User user) {
        if (user != null) {
            user = HibernateUtils.initializeAndUnproxy(user);
            if (user instanceof IndividualPerson) {
                return getIndividualUserName((IndividualPerson) user);
            }
            if (user instanceof SysUser) {
                return ((SysUser) user).getName();
            }
        }
        return null;
    }

    public static String getIndividualUserName(IndividualPerson person) {
        return Stream.of(person.getLastName(), person.getFirstName(), person.getPatronymic())
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }
//
//    public static String getStatus(User user) {
//        if (user.getDeleteDate() != null) {
//            return "Аннулирован";
//        } else if (Boolean.TRUE.equals(user.getAccountNonLocked())) {
//            return "Активен";
//        } else {
//            return "Заблокирован";
//        }
//    }
//
//    public static boolean isValidEmail(String email) {
//        return EMAIL_PATTERN.matcher(email).matches();
//    }
//
//    public static boolean isValidPhone(String phone) {
//        return PHONE_PATTERN.matcher(phone).matches();
//    }
//
//    public static boolean isValidLegalInnCheckSum(Long inn) {
//        /*
//            Алгоритм проверки ИНН 10 знаков:
//            1. Вычисляется контрольная сумма со следующими весовыми коэффициентами: (2,4,10,3,5,9,4,6,8,0).
//            2. Вычисляется контрольное число как остаток от деления контрольной суммы на 11.
//            3. Если контрольное число больше 9, то контрольное число вычисляется как остаток от деления контрольного числа на 10.
//            4. Контрольное число проверяется с десятым знаком ИНН. В случае их равенства ИНН считается правильным.
//        */
//
//        // Номер ИНН разбитый по разрядам
//        var d = String.format("%010d", inn).chars().map(digit -> digit - 48).toArray();
//
//        var sum = d[0] * 2 + d[1] * 4 + d[2] * 10 + d[3] * 3 + d[4] * 5 + d[5] * 9 + d[6] * 4 + d[7] * 6 + d[8] * 8;
//        var check = sum % 11 > 9 ? d[9] % 10 : sum % 11;
//        return check == d[9];
//    }
//
//    public static String validatePassword(String newPassword, User user,
//                                          SettingsPasswordService settingsPasswordService,
//                                          UsersLastPasswordRepository usersLastPasswordRepository) {
//        if (user instanceof IndividualPerson
//                && ((IndividualPerson) user).isNameEqualsSnils()) {
//            return null;
//        }
//
//        if (newPassword == null) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: пароль пользователя не может быть пустым!");
//        }
//        if (notComeMinTime(user, settingsPasswordService.getPasswordMinTime(), usersLastPasswordRepository)) {
//            return  "Дата смены пароля еще не наступила";
//        }
//        var minLengthSetting = settingsPasswordService.getPasswordMinLength();
//        if (lessMinLength(newPassword, minLengthSetting)) {
//            return String.format("Пароль должен быть не короче %d символов", minLengthSetting.getValue());
//        }
//        var maxLengthSetting = settingsPasswordService.getPasswordMaxLength();
//        if (moreMaxLength(newPassword, maxLengthSetting)) {
//            return String.format("Пароль должен быть не длиннее %d символов", maxLengthSetting.getValue());
//        }
//        if (equalUsername(newPassword, user.getUsername(), settingsPasswordService.getPasswordNotEqualName())) {
//            return "Пароль не может совпадать с именем пользователя";
//        }
//        if (notHasUniqueChars(newPassword, settingsPasswordService.getPasswordHasUniqueChars())) {
//            return "Пароль должен состоять из большего количества уникальных символов";
//        }
//        if (notHasEqualChars(newPassword, settingsPasswordService.getPasswordHasEqualChars())) {
//            return "Пароль содержит больше максимально установленного количества повторяющихся символов";
//        }
//        if (notHasDifferentChars(newPassword, settingsPasswordService.getPasswordHasDifferentChars())) {
//            return "Пароль не должен состоять из одинаковых символов";
//        }
//        if (notHasDifferentCharsCase(newPassword, settingsPasswordService.getPasswordHasDifferentCharsCase())) {
//            return "Пароль должен содержать буквы разных регистров";
//        }
//        if (notHasDigitsAndLetters(newPassword, settingsPasswordService.getPasswordHasDigitsAndLetters())) {
//            return "Пароль должен содержать буквы и цифры";
//        }
//        if (notHasSpecialChars(newPassword, settingsPasswordService.getPasswordHasSpecialChars())) {
//            return "Пароль должен содержать специальные символы";
//        }
//        if (hasAnyNotPermitPasswords(newPassword, settingsPasswordService.getNotPermitPasswords())) {
//            return "Пароль не может содержать вхождения запрещенных строк";
//        }
//        if (hasInLastPasswords(newPassword, user.getId(), settingsPasswordService)) {
//            return "Пароль не должен совпадать со старым";
//        }
//
//        return null;
//    }
//
//    public static String generatePassword(User user,
//                                          SettingsPasswordService settingsPasswordService,
//                                          UsersLastPasswordRepository usersLastPasswordRepository) {
//        String password;
//        var passwordCharacters = getAvailablePasswordCharacters(settingsPasswordService);
//        var passwordMaxLength = 8;
//        if (isEnableSetting(settingsPasswordService.getPasswordMaxLength())) {
//            passwordMaxLength = settingsPasswordService.getPasswordMaxLength().getValue();
//        }
//        do {
//            password = generatePassword(passwordCharacters, passwordMaxLength);
//        } while (org.springframework.util.StringUtils.hasText(validatePassword(password, user, settingsPasswordService, usersLastPasswordRepository)));
//        return password;
//    }
//
//    private static String generatePassword(char[] characters, int maxLength) {
//        char[] buffer = new char[maxLength];
//        var random = new SecureRandom();
//        for (int index = 0; index < maxLength; index++) {
//            buffer[index] = characters[random.nextInt(characters.length)];
//        }
//        return new String(buffer);
//    }
//
//    private static char[] getAvailablePasswordCharacters(SettingsPasswordService settingsPasswordService) {
//        String characters = LOWER_CASE + UPPER_CASE;
//        if (isEnableSetting(settingsPasswordService.getPasswordHasDigitsAndLetters())) {
//            characters += DIGITS;
//        }
//        if (isEnableSetting(settingsPasswordService.getPasswordHasSpecialChars())) {
//            characters += SPECIAL_CHARS;
//        }
//
//        return characters.toCharArray();
//    }
//
//    private static boolean notComeMinTime(User user, SettingPassword passwordMinTime, UsersLastPasswordRepository usersLastPasswordRepository) {
//        if (!isEnableSettingWithTime(passwordMinTime)) {
//            return false;
//        }
//
//        ZonedDateTime lastPasswordDate = null;
//        if (!user.isChangePassword()) {
//            lastPasswordDate = Optional.ofNullable(usersLastPasswordRepository.findTopByUserIdOrderByCreateDateDesc(user.getId()))
//                    .map(ulp -> ZonedDateTime.ofInstant(ulp.getCreateDate().toInstant(), ZoneId.systemDefault()))
//                    .orElse(user.getChangePasswordDate());
//        }
//        return lastPasswordDate != null
//                && !ZonedDateTime.now().isAfter(UserUtils.getPasswordMinDate(passwordMinTime, lastPasswordDate));
//    }
//
//    private static boolean lessMinLength(String password, SettingPassword minLengthSetting) {
//        if (!isEnableSetting(minLengthSetting)) {
//            return false;
//        }
//
//        return password.length() < minLengthSetting.getValue();
//    }
//
//    private static boolean moreMaxLength(String password, SettingPassword maxLengthSetting) {
//        if (!isEnableSetting(maxLengthSetting)) {
//            return false;
//        }
//
//        return password.length() > maxLengthSetting.getValue();
//    }
//
//    private static boolean equalUsername(String password, String username, SettingPassword notEqualNameSetting) {
//        if (!isEnableSetting(notEqualNameSetting)) {
//            return false;
//        }
//
//        return Objects.equals(password, username);
//    }
//
//    private static boolean notHasUniqueChars(String password, SettingPassword hasUniqueCharsSetting) {
//        if (!isEnableSetting(hasUniqueCharsSetting)) {
//            return false;
//        }
//
//        return !StringUtils.hasUniqueChars(password, hasUniqueCharsSetting.getValue());
//    }
//
//    private static boolean notHasEqualChars(String password, SettingPassword hasEqualCharsSetting) {
//        if (!isEnableSetting(hasEqualCharsSetting)) {
//            return false;
//        }
//
//        return StringUtils.hasEqualChars(password, hasEqualCharsSetting.getValue());
//    }
//
//    private static boolean notHasDifferentChars(String password, SettingPassword hasDifferentCharsSetting) {
//        if (!isEnableSetting(hasDifferentCharsSetting)) {
//            return false;
//        }
//
//        return StringUtils.sameChars(password);
//    }
//
//    private static boolean notHasDifferentCharsCase(String password, SettingPassword hasDifferentCharsCaseSetting) {
//        if (!isEnableSetting(hasDifferentCharsCaseSetting)) {
//            return false;
//        }
//
//        return !(StringUtils.containAnyChars(password, LOWER_CASE) && StringUtils.containAnyChars(password, UPPER_CASE));
//    }
//
//    private static boolean notHasDigitsAndLetters(String password, SettingPassword hasDigitsAndLettersSetting) {
//        if (!isEnableSetting(hasDigitsAndLettersSetting)) {
//            return false;
//        }
//
//        return !((StringUtils.containAnyChars(password, DIGITS) && StringUtils.containAnyChars(password, LOWER_CASE))
//                || (StringUtils.containAnyChars(password, DIGITS) && StringUtils.containAnyChars(password, UPPER_CASE)));
//    }
//
//    private static boolean notHasSpecialChars(String password, SettingPassword hasSpecialCharsSetting) {
//        if (!isEnableSetting(hasSpecialCharsSetting)) {
//            return false;
//        }
//
//        return !StringUtils.containAnyChars(password, SPECIAL_CHARS);
//    }
//
//    private static boolean hasAnyNotPermitPasswords(String password, List<String> notPermitPasswords) {
//        if (CollectionUtils.isEmpty(notPermitPasswords)) {
//            return false;
//        }
//
//        return notPermitPasswords.stream()
//                .anyMatch(password::contains);
//    }
//
//    private static boolean hasInLastPasswords(String password, String userId, SettingsPasswordService settingsPasswordService) {
//        var checkLastCountSetting = settingsPasswordService.getPasswordCheckLastCount();
//        if (!isEnableSettingWithValue(checkLastCountSetting)) {
//            return false;
//        }
//
//        var lastPasswords = settingsPasswordService.getUserLastPasswords(userId, checkLastCountSetting.getValue());
//        if (CollectionUtils.isEmpty(lastPasswords)) {
//            return false;
//        }
//
//        var passwordEncoder = new BCryptPasswordEncoder();
//        return lastPasswords.stream()
//                .anyMatch(p -> passwordEncoder.matches(password, p));
//    }
//
//    public static ZonedDateTime getPasswordMinDate(SettingPassword passwordMinTime, ZonedDateTime date) {
//        var passwordMinDate = date;
//        if (!isEnableSettingWithTime(passwordMinTime)) {
//            return passwordMinDate;
//        }
//
//        var time = passwordMinTime.getTime().split(":"); // 00:00:00
//        if (time.length == 3) {
//            passwordMinDate = passwordMinDate.plusDays(Long.parseLong(time[0]));
//            passwordMinDate = passwordMinDate.plusHours(Long.parseLong(time[1]));
//            passwordMinDate = passwordMinDate.plusMinutes(Long.parseLong(time[2]));
//        } else {
//            log.error("Недопустимый формат для `Минимальный срок действия`");
//        }
//        return passwordMinDate;
//    }
//
//    public static ZonedDateTime getPasswordMaxDate(SettingPassword passwordMaxTime, ZonedDateTime date) {
//        var passwordMaxDate = date;
//        if (!isEnableSettingWithTime(passwordMaxTime)) {
//            return passwordMaxDate;
//        }
//
//        var time = passwordMaxTime.getTime().split(":"); // 00:00:00
//        if (time.length == 3) {
//            passwordMaxDate = passwordMaxDate.plusDays(Long.parseLong(time[0]));
//            passwordMaxDate = passwordMaxDate.plusHours(Long.parseLong(time[1]));
//            passwordMaxDate = passwordMaxDate.plusMinutes(Long.parseLong(time[2]));
//        } else {
//            log.error("Недопустимый формат для `Максимальный срок действия`");
//        }
//        return passwordMaxDate;
//    }
//
//    public static int getPasswordMaxDateInDays(SettingPassword passwordMaxTime) {
//        var hours = 0;
//        if (!isEnableSettingWithTime(passwordMaxTime)) {
//            return hours;
//        }
//
//        var time = passwordMaxTime.getTime().split(":"); // 00:00:00
//        if (time.length == 3) {
//            hours += Integer.parseInt(time[0]);
//        } else {
//            log.error("Недопустимый формат для `Максимальный срок действия`");
//        }
//        return hours;
//    }
//
//    private static boolean isEnableSetting(SettingPassword settingPassword) {
//        return settingPassword != null && settingPassword.isEnable();
//    }
//
//    private static boolean isEnableSettingWithTime(SettingPassword settingPassword) {
//        return settingPassword != null && settingPassword.isEnable() && settingPassword.getTime() != null;
//    }
//
//    private static boolean isEnableSettingWithValue(SettingPassword settingPassword) {
//        return settingPassword != null && settingPassword.isEnable() && settingPassword.getValue() != null;
//    }
}
