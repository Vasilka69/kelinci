package src.unigate;

public interface ChangePasswordService {
//    /**
//     * Проверка токена на наличие
//     *
//     * @param token токен для смены пароля
//     * @return true - токен присутствует, иначе false
//     */
//    boolean exists(String token);

    /**
     * Добавление токена
     *
     * @param token токен для смены пароля
     */
    void add(String token);

//    /**
//     * Удаление токена из хранилища
//     *
//     * @param token токен для смены пароля
//     */
//    void delete(String token);
}
