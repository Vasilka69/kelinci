package src.unigate;

public interface TwoFactorAuthService {
//    /**
//     * Проверка токена пользователя
//     *
//     * @param user пользователь
//     * @param token токен для второго фактора авторизации
//     * @return true - токен присутствует, иначе false
//     */
//    boolean exists(User user, String token);

    /**
     * Добавление токена пользователя
     *
     * @param user пользователь
     * @param token токен для второго фактора авторизации
     */
    void add(User user, String token);

//    /**
//     * Обработка второго фактора авторизации
//     * @param info данные о втором факторе авторизации
//     * @param request request
//     * @param response response
//     */
//    void process(TwoFactorAuthInfo info, HttpServletRequest request, HttpServletResponse response) throws IOException;
//
//    /**
//     * Удаление токена пользователя
//     *
//     * @param user пользователь
//     * @param token токен для второго фактора авторизации
//     */
//    void delete(User user, String token);
}
