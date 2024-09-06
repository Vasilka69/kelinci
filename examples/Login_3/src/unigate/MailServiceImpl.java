package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
//    private final MailProperties properties;

    @Override
    public void send(String from, String to, String subject, String text, MultipartFile... file) {
        if (!StringUtils.hasText(from)) throw new RuntimeException("Не указан отправитель сообщения");
        if (!StringUtils.hasText(to)) throw new RuntimeException("Не указан получатель сообщения");
        if (!StringUtils.hasText(subject)) throw new RuntimeException("Не указан заголовок сообщения");

        File attachments = null;
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            if (StringUtils.hasText(text)) {
                helper.setText(text, true);
            }
            if (!isEmpty(file)) {
                //put file in attachments.zip
                attachments = zip(file);
                if (attachments != null) {
                    String zipName = "attachments.zip";
                    helper.addAttachment(zipName, attachments);
                }
            }
//            javaMailSender.send(mail);
            System.out.printf("Письмо %s отправлено", mail);
        } catch (MessagingException | MailException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // delete attachments
            delete(attachments);
        }
    }

    public static boolean isEmpty(final Object[] target) {
        return target == null || target.length <= 0;
    }

    @Override
    public void send(String to, String subject, String text, MultipartFile... file) {
//        send(properties.getFrom(), to, subject, text, file);
        send("test@test.ru", to, subject, text, file);
    }

    private File zip(MultipartFile... file) {
        if (isEmpty(file)) return null;
        if (Arrays.stream(file).noneMatch(f -> f.getSize() > 0)) return null;

        File zip = new File(UUID.randomUUID().toString());
        try (FileOutputStream fos = new FileOutputStream(zip);
             ZipOutputStream zos = new ZipOutputStream(fos, StandardCharsets.UTF_8)) {
            Arrays.stream(file).forEach(f -> addToZipFile(f, zos));
            return zip;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private boolean addToZipFile(MultipartFile file, ZipOutputStream zos) {
        if (file == null || zos == null) return false;
        if (file.getSize() <= 0) return false;
        try (InputStream fis = file.getInputStream()) {
            ZipEntry zipEntry = new ZipEntry(StringUtils.hasText(file.getOriginalFilename()) ? file.getOriginalFilename() : file.getName());
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
            return true;
        } catch (IOException ignore) {
            return false;
        }
    }

    private void delete(File file) {
        if (file == null) return;
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException ignore) {
            // ignore
        }
    }
}
