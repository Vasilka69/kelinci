package src.unigate;

import javax.servlet.http.HttpServletRequest;

/**
 * Сервис сессий
 */
public interface SessionService {
//    /**
//     * Экспорт сессий
//     * @param filter фильтр
//     * @return страница сессий
//     */
//    RegResponse<SessionExportInfo> getSessionExportInfoByFilter(SessionFilter filter);
//
//    /**
//     * Получение информации о сессии по токену
//     * @param token токен
//     * @return see {@link SessionInfo}
//     */
//    SessionInfo getSession(String token);
//
//    /**
//     * Удаление сессии по идентификатору сессии
//     * @param id идентификатор сессии
//     */
//    void deleteSession(String id);
//
//    /**
//     * Удаление всех сессий
//     */
//    void deleteAll();

    /**
     * Добавление сессии
     * @param request http-запрос
     * @param token уникальный идентификатор пользователя
     * @param user пользователь
     * @param ip адрес с которого была произведена авторизация
     * @param authenticationSystem сервис, аутентифицировавший пользователя
     * @return see {@link Session}
     */
    Session addSession(HttpServletRequest request, String token, User user, String ip, AuthenticationSystem authenticationSystem);

    /**
     * Обновление активности сессии
     * @param token токен
     */
    void update(String token);

//    /**
//     * Проверка актуальности токена
//     * @param token токен
//     * @return true - токен присутсвует в хранилище и сессия актуальна, иначе false
//     */
//    boolean isActual(String token);
//
//    /**
//     * Получение статуса актуальности токена и времени оставшейся жизни сессии
//     * (время заполняется если включена настройка "Уведомление о прерывании за" и время уведомления наступило
//     *
//     * @param token токен
//     */
//    SessionStatus getSessionStatus(String token);
//
//    /**
//     * Проверка токена на наличие в хранилище
//     * @param token токен
//     * @return true - токен присутсвует в хранилище, иначе false
//     */
//    boolean exists(String token);
//
//    /**
//     * Проверка на наличие сессии в хранилище по ID
//     * @param id идентификатор сессии
//     * @return true - сессия присутствует в хранилище, иначе false
//     */
//    boolean existsByKey(UUID id);
//
//    /**
//     * Проверить токены на актуальность
//     * @param tokens    Токены для проверки
//     * @return          Неактуальные токены
//     */
//    Set<String> getNotActualTokens(Set<String> tokens);
//
//    /**
//     * Получение информации о пользователе и его привилегиях по токену
//     * @param token токен
//     * @return see {@link UserPermissionsInfo}
//     */
//    UserPermissionsInfo getUserPermissionsInfo(String token);
//
//    /**
//     * Получение разрешений по токену
//     * @param token токен
//     */
//    Map<String, Set<String>> getPermissions(String token);
//
//    /**
//     * Получение списка ролей сессии по токену
//     * @param token токен
//     */
//    List<SessionRoleInfo> getSessionRoles(String token);

    /**
     * Выбрать роль сессии
     * @param token     Токен сессии
     * @param roleId    Идентификатор роли
     */
    void changeSessionRole(String token, String roleId);

//    /**
//     * Проверка наличие привилегии
//     * @param token                 Токен сессии
//     * @param securityObjectAction  Проверяемая привилегия
//     * @return                      Привилегия есть?
//     */
//    boolean hasPermission(String token, PermissionsInfo.SecurityObjectActionInfo securityObjectAction);
//
//    /**
//     * Проверка наличие привилегий
//     * @param token                 Токен сессии
//     * @param securityObjectActions Список проверяемых привилегий
//     * @return                      Список наличия привилегий
//     */
//    List<Boolean> hasPermissions(String token, List<PermissionsInfo.SecurityObjectActionInfo> securityObjectActions);
//
//    /**
//     * Проверка наличие роли
//     * @param token     Токен сессии
//     * @param roleId    Идентификатор роли
//     * @return          Роль присутствует?
//     */
//    Boolean hasRole(String token, String roleId);
//
//    /**
//     * Проверка наличие ролей
//     * @param token     Токен сессии
//     * @param roleIds   Проверяемые роли
//     * @return          Список наличия ролей
//     */
//    List<Boolean> hasRoles(String token, List<String> roleIds);

    /**
     * @param username  Логин пользователя
     * @return          Количество сессий пользователя
     */
    long getUserSessionCount(String username);
}
