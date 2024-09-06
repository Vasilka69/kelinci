package src.unigate;

import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис отправки email сообщений
 */
public interface MailService {

    /**
     * Отправка email
     * @param from адрес отправителя
     * @param to адрес получателя
     * @param subject заголовок письма
     * @param text текст письма
     * @param file вложение
     */
    void send(String from, String to, String subject, String text, MultipartFile... file);

    /**
     * Отправка email
     * @param to адрес получателя
     * @param subject заголовок письма
     * @param text текст письма
     * @param file вложение
     */
    void send(String to, String subject, String text, MultipartFile... file);
}
