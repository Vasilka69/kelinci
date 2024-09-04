package src.unigate;

/**
 * Сервис настройки контроля доступа
 */
public interface SettingsAccessControlService extends SettingsService<SettingAccessControl> {

//    SettingAccessControl getInterruptingTime();
//
//    SettingAccessControl getUserSessionMaxCount();

    SettingAccessControl getTwoFactorAuth();

//    SettingAccessControl getBlockNotActiveUser();
//
//    SettingAccessControl getBlockRepeatedUserId();
}
