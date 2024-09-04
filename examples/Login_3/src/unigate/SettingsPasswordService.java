package src.unigate;

/**
 * Сервис настройки парольной политики
 */
public interface SettingsPasswordService extends SettingsService<SettingPassword> {

//    SettingPassword getPasswordAttemptsBeforeLock();
//
//    SettingPassword getPasswordMinTime();

    SettingPassword getPasswordMaxTime();

//    SettingPassword getPasswordMinLength();
//
//    SettingPassword getPasswordMaxLength();
//
//    SettingPassword getPasswordHasDifferentChars();
//
//    SettingPassword getPasswordHasDifferentCharsCase();
//
//    SettingPassword getPasswordHasDigitsAndLetters();
//
//    SettingPassword getPasswordHasSpecialChars();
//
//    SettingPassword getPasswordHasUniqueChars();
//
//    SettingPassword getPasswordHasEqualChars();
//
//    SettingPassword getPasswordCheckLastCount();
//
//    SettingPassword getPasswordNotEqualName();
//
//    List<String> getNotPermitPasswords();
//
//    List<String> getUserLastPasswords(String userId, Integer size);
}
