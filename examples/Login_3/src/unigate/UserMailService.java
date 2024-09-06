package src.unigate;

/**
 * Сервис отправки сообщений пользователю
 */
public interface UserMailService {
    void sendTwoFactorCode(User user);
//    boolean existsTwoFactorCode(User user);
//    void refreshTwoFactorCode(User user);
//    void checkTwoFactorCode(User user, String code);
//    void deleteTwoFactorCode(User user);
}
