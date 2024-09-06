package src.unigate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.LOCATION;
import static src.unigate.ProtocolEventType.LOGIN_EVENT;

@Service
@Slf4j
public class AuthenticationService {
    private static final String CHANGE_PASS_HEADER = "ChangePassword";
    public static final String TWO_FACTOR_AUTH_HEADER = "TwoFactorAuth";

//    private SecurityUserDetailsManager securityUserDetailsManager;
    private SessionService sessionService;
    private MessageService messageService;
    private ChangePasswordService changePasswordService;
    private TwoFactorAuthService twoFactorAuthService;
//    private AuthenticationStrategyRepository authenticationStrategyRepository;
    private EsiaServiceProperties esiaServiceProperties;
    private MenuService menuService;
//
//    @Autowired
//    public void setUserDetailsManager(
//            SecurityUserDetailsManager securityUserDetailsManager,
//            SessionService sessionService,
//            MessageService messageService,
//            ChangePasswordService changePasswordService,
//            MenuService menuService,
//            AuthenticationStrategyRepository authenticationStrategyRepository,
//            EsiaServiceProperties esiaServiceProperties
//    ) {
//        this.securityUserDetailsManager = securityUserDetailsManager;
//        this.sessionService = sessionService;
//        this.messageService = messageService;
//        this.changePasswordService = changePasswordService;
//        this.menuService = menuService;
//        this.authenticationStrategyRepository = authenticationStrategyRepository;
//        this.esiaServiceProperties = esiaServiceProperties;
//    }
//
//    @Autowired
//    private void setTwoFactorAuthService(TwoFactorAuthService twoFactorAuthService) {
//        this.twoFactorAuthService = twoFactorAuthService;
//    }

    public String addChangePasswordToken(HttpServletRequest request, HttpServletResponse response, String username) {
        log.info("Смена пароля пользователем {} IP адрес: {}", username, getRemoteIp(request));
        String token = TokenUtils.generateChangePasswordToken(username);
        changePasswordService.add(token);
        response.addHeader(CHANGE_PASS_HEADER, token);
        log.info("Для пользователя {} был создан токен {} для смены пароля", username, token);
        return token;
    }

    public String addTwoFactorToken(HttpServletRequest request, HttpServletResponse response, User user) {
        log.info("Добавление токена для второго фактора авторизации {} IP адрес: {}", user.getUsername(), getRemoteIp(request));
        String token = TokenUtils.generateTwoFactorToken(user.getUsername());
        twoFactorAuthService.add(user, token);
        response.addHeader(TWO_FACTOR_AUTH_HEADER, token);
        log.info("Для пользователя {} был создан токен {} для второго фактора авторизации", user.getUsername(), token);
        return token;
    }

    public String addAuthentication(
            HttpServletRequest request, HttpServletResponse response, User user, String redirectUri
    ) throws IOException {
        String remoteIp = getRemoteIp(request);
        AuthenticationSystem authenticationSystem = JWTLoginFilter.isEsiaAuthenticationStrategy(request)
                ? AuthenticationSystem.ESIA
                : AuthenticationSystem.UNIGATE;
        String[] links = getUserMenuLinks(user);
        log.info("Добавление авторизации {} IP адрес: {}", user.getUsername(), remoteIp);

        String token;
        if (request.getParameter(WebConstants.TOKEN_PARAMETER) != null) {
            token = updateAuthentication(request, true, remoteIp);
        } else {
            token = addAuthenticationByUser(request, user, true, remoteIp, authenticationSystem);
        }
        ServletUtils.setToken(response, token);
        log.info("Пользователь {} авторизовался", user.getUsername());

        String redirectUrl = JWTLoginFilter.getRedirectUrl(request, user, redirectUri, links, token, esiaServiceProperties.getUrl());

        if (StringUtils.isNotEmpty(redirectUrl)) {
            if (JWTLoginFilter.isEsiaAuthenticationStrategy(request)) {
                response.sendRedirect(redirectUrl);
            } else if (JWTLoginFilter.isRegularAuthenticationStrategy(request)) {
                response.addHeader(LOCATION, redirectUrl);
            }
        }

        return token;
    }

    public String addAuthenticationByUser(
            HttpServletRequest request, User user,
            boolean writeLog, String remoteIp, AuthenticationSystem authenticationSystem
    ) {
        String token = TokenUtils.generateAuthorizationToken(user.getUsername());
        sessionService.addSession(request, token, user, remoteIp, authenticationSystem);
        log.info("Пользователь {} авторизовался и получил токен {}", user.getUsername(), token);
        if (writeLog) {
            messageService.sendUserProtocolEvent(user.getUsername(), LOGIN_EVENT, remoteIp);
        }
        return token;
    }

    public String updateAuthentication(HttpServletRequest request, boolean writeLog, String remoteIp) {
        String role = request.getParameter(WebConstants.ROLE_PARAMETER);
        String token = request.getParameter(WebConstants.TOKEN_PARAMETER);
        String username = TokenUtils.getAuthorizationUsername(token);
        if (StringUtils.isNotEmpty(role)) {
            sessionService.changeSessionRole(token, role);
            log.info("Пользователь {} авторизовался с ролью {} и обновил токен {}", username, role, token);
        } else {
            sessionService.update(token);
            log.info("Пользователь {} авторизовался и получил токен {}", username, token);
        }
        if (writeLog) {
            messageService.sendUserProtocolEvent(username, LOGIN_EVENT, remoteIp);
        }

        return token;
    }

//    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String token = getToken(request);
//        SessionInfo session = sessionService.getSession(token);
//        Authentication authentication = getAuthentication(request);
//
//        if (AuthenticationSystem.ESIA.equals(session.getUserAuthorizedSystem())) {
//            String url = String.format("%s/auth/esia/url/logout", esiaServiceProperties.getUrl());
//            String esiaLogoutUrl = HttpClient.sendGetRequest(HttpClient.createRequest(token), url, String.class).getBody();
//            log.debug("Перенаправляем пользователя на выход из ЕСИА: {}", esiaLogoutUrl);
//            PrintWriter out = response.getWriter();
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            out.print("{\"redirectUrl\":\"".concat(esiaLogoutUrl).concat("\"}"));
//            out.flush();
//        }
//
//        if (authentication != null) {
//            ServletUtils.removeToken(request, response);
//            String username = ((SecurityUser) authentication.getPrincipal()).getUsername();
//            sessionService.deleteSession(session.getId().toString());
//            log.info("Пользователь {} деавторизовался", username);
//            messageService.sendUserProtocolEvent(username, LOGOUT_EVENT, session.getIp());
//        }
//    }

//    public Authentication getAuthentication(HttpServletRequest request) {
//        String token = getToken(request);
//        if (StringUtils.isNotEmpty(token)) {
//            String userName = TokenUtils.getAuthorizationUsername(token);
//            if (userName != null && sessionService.exists(token)) {
//                UserDetails user = securityUserDetailsManager.loadUserByUsername(userName);
//                UnigateAuthenticationToken authentication = new UnigateAuthenticationToken(user, null, user.getAuthorities(), null);
//                authentication.setDetails(new AuthUserDetails(ServletUtils.getClientIP(request), token));
//                sessionService.update(token);
//                return authentication;
//            }
//        }
//        return null;
//    }

    public String[] getUserMenuLinks(User user) {
        return menuService.getMenu(user).stream()
                .map(MenuItemInfo::getLink)
                .filter(StringUtils::isNotEmpty)
                .toArray(String[]::new);
    }

//    public static String getToken(HttpServletRequest request) {
//        return ServletUtils.getToken(request, WebConstants.TOKEN_PARAMETER);
//    }

    private String getRemoteIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("remoteIp"))
                .orElse(ServletUtils.getClientIP(request));
    }

    public String getAuthenticationStrategyUrl(HttpServletRequest request) {
        String strategyId = request.getParameter(WebConstants.STRATEGY_PARAMETER);
        if (strategyId!=null){
            Optional<AuthenticationStrategy> strategy = findAuthenticationStrategyById(strategyId);
            if (strategy.isPresent()){
                return strategy.get().getRedirectUrl();
            }
        }
        return null;
    }

    private Optional<AuthenticationStrategy> findAuthenticationStrategyById(String strategyId) {
        return Optional.of(new AuthenticationStrategy(
                strategyId,
            "description",
            "redirectUrl"
        ));
    }
}
