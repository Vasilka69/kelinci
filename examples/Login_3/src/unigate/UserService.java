package src.unigate;

import java.util.Map;
import java.util.Set;

/**
 * Сервис пользователей
 */
public interface UserService extends BaseProtocolableService<User, String> {

//    void beforeAdd(User user);
//
//    void beforeUpdate(User user);
//
//    User addSysUser(SysUser user);

    /**
     * Получение пользователя по username
     *
     * @return Найденный пользователь
     */
    User findByUsername(String username);

//    UsernameAvailability checkUsernameExists(String username);
//
//    /**
//     * Поиск пользователя по пользователю из ЕСИА. Если пользователь не найден, будет создан новый.
//     * Метод применяется для кейса авторизации через ЕСИА.
//     * @param esiaUser информация о пользователе из ЕСИА
//     * @return информация о существующем или новом пользователе
//     */
//    UserInfo findOrCreateByEsiaUser(EsiaUserInfo esiaUser);
//
//    /**
//     * Поиск последнего способа входа пользователя по СНИЛСу. Если пользователь не найден, метод вернет null.
//     * Метод применяется для кейса авторизации через ЕСИА.
//     * @param snils СНИЛС
//     * @return идентификатор организации пользователя ЕСИА
//     */
//    String findLastUserSessionRoleId(String snils);
//
//    /**
//     * Установка последнего способа входа пользователя по СНИЛСу. Если пользователь не найден, метод вернет исключение.
//     * Метод применяется для кейса авторизации через ЕСИА.
//     * @param snils СНИЛС
//     * @param roleId идентификатор роли
//     */
//    void setLastUserSessionRoleId(String snils, String roleId);
//
//    /**
//     * Получение пользователя по username с прогрузкой атрибутов ролей<br>
//     * В отличие от других методов в нем прогружаются все атрибуты ролей, а не только идентификаторы
//     * @return Найденный пользователь
//     */
//    User findByUsernameFull(String username);
//
//    /**
//     * Сброс привилегий пользователя
//     *
//     * @param userId идентификатор пользователя
//     */
//    void clearPermissions(String userId);

    /**
     * Привилегии пользователя: привязанные напрямую и входящие в роли.
     * @param user пользователь
     * @return набор привилегий пользователя
     */
    Map<String, Set<String>> getPermissions(User user);

//    /**
//     * Обновление карточки пользователя
//     *
//     * @param user  пользователь
//     * @return Обновленный пользователь
//     */
//    User update(User user);
//
//    User updateSysUserCard(SysUser sysUser);

    boolean hasPermission(User user, String resource, String action);

//    String generateCn(User user);
//
//    Boolean lockUnlockUser(String userId, String lockComment);
//    void groupLockUnlockIndividualPerson(List<String> idsForLock, List<String> idsForUnlock, String lockComment);
//    void groupLockUnlockSysUsers(List<String> userNamesForLock, List<String> userNamesForUnlock, String lockComment);
//
//    void assignPermissions(String srcUserId, List<RecipientTemporaryPermissionInfo> recipientTempPermInfo, List<UserPermissionShortInfo> permissionIds);
//
//    void delete(List<String> ids);
//    void deleteLogical(List<String> ids);
//    void deleteSysUsers(List<String> userNames);
//    void deleteLogicalSysUsers(List<String> userNames);
//
//    Page<ShortUserListInfo> findByFullUserNameShort(String fullUserName, Pageable pageable);
//
//    long count();
}

