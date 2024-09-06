package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import ru.kamatech.unigate.security.service.mail.MailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserMailServiceImpl implements UserMailService {
    private final TwoFactorAuthCodeService twoFactorAuthCodeService;
    private final MailService mailService;
//    private final TemplateEngine templateEngine;

    @Override
    public void sendTwoFactorCode(User user) {
        checkUser(user);
        String fio = UserUtils.getName(user);
        String email = null;
        if (user instanceof IndividualPerson) {
            IndividualPerson ip = (IndividualPerson) user;
            email = ip.getEmail();
        }
        if (!StringUtils.hasText(email)) {
            throw new RuntimeException("Email пользователя не заполнен");
        }
        String code = twoFactorAuthCodeService.addCode(user.getId());
        String subject = "Двухфакторная авторизация";
        String text = processTemplateTwoFactorAuth(fio, email, code);
        mailService.send(email, subject, text);
    }

//    @Override
//    public boolean existsTwoFactorCode(User user) {
//        checkUser(user);
//        return twoFactorAuthCodeService.existsCode(user.getId());
//    }
//
//    @Override
//    public void refreshTwoFactorCode(User user) {
//        checkUser(user);
//        if (twoFactorAuthCodeService.existsCode(user.getId())) {
//            twoFactorAuthCodeService.deleteCode(user.getId());
//        }
//        sendTwoFactorCode(user);
//    }
//
//    @Override
//    public void checkTwoFactorCode(User user, String code) {
//        checkUser(user);
//        TwoFactorAuthCodeStatus status = twoFactorAuthCodeService.checkCode(user.getId(), code);
//        switch (status) {
//            case NOT_EXISTS: {
//                throw new ResourceNotFoundException(TwoFactorAuthCodeService.ERROR_NOT_EXISTS_CODE);
//            }
//            case VALID: {
//                twoFactorAuthCodeService.deleteCode(user.getId());
//                return;
//            }
//            case RECREATE: {
//                twoFactorAuthCodeService.deleteCode(user.getId());
//                sendTwoFactorCode(user);
//                throw new ResourceIllegalArgumentException(TwoFactorAuthCodeService.ERROR_NOT_VALID_CODE_RECREATE);
//            }
//            case INVALID:
//            default: {
//                throw new ResourceIllegalArgumentException(TwoFactorAuthCodeService.ERROR_NOT_VALID_CODE);
//            }
//        }
//    }
//
//    @Override
//    public void deleteTwoFactorCode(User user) {
//        checkUser(user);
//        if (twoFactorAuthCodeService.existsCode(user.getId())) {
//            twoFactorAuthCodeService.deleteCode(user.getId());
//        }
//    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException(ResourceName.USER);
        }
    }

    private String processTemplateTwoFactorAuth(String fio, String email, String code) {
        if (!StringUtils.hasText(fio) && !StringUtils.hasText(email) && !StringUtils.hasText(code)) return null;
        Context ctx = new Context();
        ctx.setVariable("fio", fio);
        ctx.setVariable("email", email);
        ctx.setVariable("code", code);
        ctx.setVariable("date", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now()));
        return templateEngine.process("two_factor_auth", ctx);
    }
}
