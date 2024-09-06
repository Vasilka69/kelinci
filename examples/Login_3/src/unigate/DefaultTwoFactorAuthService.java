package src.unigate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.kamatech.unigate.common.exception.ResourceIllegalArgumentException;
import ru.kamatech.unigate.common.exception.ResourceIsNullException;
import ru.kamatech.unigate.common.service.security.impl.ResourceName;
import ru.kamatech.unigate.security.dto.TwoFactorAuthInfo;
import ru.kamatech.unigate.security.repository.TwoFactorAuthTokenRepository;
import ru.kamatech.unigate.security.repository.UserRepository;
import ru.kamatech.unigate.security.service.UserMailService;

import java.io.IOException;

import static ru.kamatech.unigate.security.jwt.AuthenticationService.TWO_FACTOR_AUTH_HEADER;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTwoFactorAuthService implements TwoFactorAuthService {

    private final SettingsAccessControlService settingsAccessControlService;
    private final UserMailService userMailService;
    private final TwoFactorAuthTokenRepository twoFactorAuthTokenRepository;
    private final UserRepository userRepository;
    private AuthenticationService authenticationService;

//    @Lazy
//    @Autowired
//    private void setAuthenticationService(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }

    @Override
    public void add(User user, String token) {
        twoFactorAuthTokenRepository.add(token);
        System.out.printf("Токен %s записан в БД", token);
        userMailService.sendTwoFactorCode(user);
    }

//    @Override
//    public void delete(User user, String token) {
//        twoFactorAuthTokenRepository.delete(token);
//        userMailService.deleteTwoFactorCode(user);
//    }
//
//    @Override
//    public boolean exists(User user, String token) {
//        return twoFactorAuthTokenRepository.exists(token)
//                && userMailService.existsTwoFactorCode(user);
//    }
//
//    public void process(TwoFactorAuthInfo info, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SettingAccessControl twoFactorAuth = settingsAccessControlService.getTwoFactorAuth();
//        if (!Boolean.TRUE.equals(twoFactorAuth.getEnable())) {
//            throw new ResourceIllegalArgumentException("Двухфакторная авторизация отключена!");
//        }
//
//        if (info == null) {
//            throw new ResourceIsNullException("Запрос двухфакторной авторизации");
//        }
//        String twoFactorAuthToken = info.getTwoFactorAuthToken();
//        String username = TokenUtils.getTwoFactorUsername(twoFactorAuthToken);
//        if(username == null) {
//            throw new ResourceIsNullException(ResourceName.USER);
//        }
//        User user = userRepository.findFirstByUsernameIgnoreCase(username.toUpperCase());
//        if (user == null) {
//            throw new ResourceIsNullException(ResourceName.USER);
//        }
//
//        if(!exists(user, twoFactorAuthToken)) {
//            throw new ResourceIllegalArgumentException("Токен отсутствует в хранилище");
//        }
//
//        if (!SecurityUtils.isGeneralUser(user)) {
//            throw new ResourceIllegalArgumentException("Недопустимый способ авторизации");
//        }
//
//        log.info("Попытка двухфакторной авторизации по токену {} у пользователя {}", twoFactorAuthToken, user.getUsername());
//        Pair<Integer, String> result;
//        if (info.isRefreshCode()) {
//            result = refreshTwoFactorCode(user);
//        } else if (!StringUtils.hasText(info.getCode())) {
//            result = sendTwoFactorCode(user);
//        } else {
//            result = checkTwoFactorCode(user, info.getCode());
//        }
//
//        if (result.getLeft() == 200 && result.getRight() == null) {
//            delete(user, info.getTwoFactorAuthToken());
//            log.debug("{} авторизовался по токену двухфакторной авторизации {}", user.getUsername(), twoFactorAuthToken);
//            addAuthenticationToken(request, response, user, info.getRedirectUri());
//        } else {
//            log.debug("{} не авторизовался по токену двухфакторной авторизации {}: {}", user.getUsername(), twoFactorAuthToken, result.getRight());
//            response.addHeader(TWO_FACTOR_AUTH_HEADER, twoFactorAuthToken);
//            response.setContentType("text/plain;charset=UTF-8");
//            response.setStatus(result.getLeft());
//            response.getWriter().write(result.getRight());
//        }
//    }
//
//    private Pair<Integer, String> refreshTwoFactorCode(User user) {
//        int status;
//        String message;
//        try {
//            userMailService.refreshTwoFactorCode(user);
//            status = 200;
//            message = "Сгенерирован новый код";
//        } catch (Exception e) {
//            status = 500;
//            message = "Не удалось отправить код подтверждения";
//        }
//
//        return Pair.of(status, message);
//    }
//
//    private Pair<Integer, String> sendTwoFactorCode(User user) {
//        int status;
//        String message;
//        try {
//            if (!userMailService.existsTwoFactorCode(user)) {
//                userMailService.sendTwoFactorCode(user);
//            }
//            status = 500;
//            message = "Не указан код подтверждения";
//        } catch (Exception e) {
//            userMailService.deleteTwoFactorCode(user);
//            status = 500;
//            message = "Не удалось отправить код подтверждения";
//        }
//
//        return Pair.of(status, message);
//    }
//
//    private Pair<Integer, String> checkTwoFactorCode(User user, String code) {
//        int status;
//        String message;
//        try {
//            userMailService.checkTwoFactorCode(user, code);
//            status = 200;
//            message = null;
//        } catch (Exception e) {
//            status = 500;
//            message = e.getMessage();
//        }
//
//        return Pair.of(status, message);
//    }
//
//    private void addAuthenticationToken(HttpServletRequest request, HttpServletResponse response, User user, String redirectUri) throws IOException {
//        authenticationService.addAuthentication(request, response, user, redirectUri);
//    }

}
