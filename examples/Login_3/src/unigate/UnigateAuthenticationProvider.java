package src.unigate;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Objects;

//@Slf4j
@AllArgsConstructor
public class UnigateAuthenticationProvider extends DaoAuthenticationProvider {

//    @Autowired
    private UsersLocker usersLocker;

//    @Autowired
    private SessionService sessionService;

//    @Autowired
    private UserService userService;

//    @Autowired
    private RoleService roleService;

//    @Autowired
    private SettingsAccessControlService settingsAccessControlService;

//    @Autowired
    private SettingsPasswordService settingsPasswordService;

//    @Autowired
    private AuthenticationService authenticationService;

//    @Value("${ru.kamatech.unigate.security.allow_redirect_url:}")
    private String[] allowRedirectUrls;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UnigateAuthenticationToken.class, authentication,
                () -> messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.onlySupports",
                        "Only UnigateAuthenticationToken is supported"));
        String username = String.valueOf(authentication.getPrincipal());
        User user = userService.findByUsername(username);

        UserLockCounter lockCounter = usersLocker.getLockCounter(username);
        // user attemptsBeforeLock
        validateAttemptsBeforeLock(lockCounter);
        // user authenticationStrategy
//        validateAuthenticationStrategy(user);

        UnigateAuthenticationToken authenticate = unigateAuthenticate(authentication);

        // user blocked by delete date
        validateBlock(user);
        // validate session count
        validateSessionCount(user);
        // validate redirect path
        checkRedirectUri(user, authenticate);

        lockCounter.resetCount();
        return authenticate;
    }

    private UnigateAuthenticationToken unigateAuthenticate(Authentication authentication) {
        UnigateAuthenticationToken unigateAuthentication = (UnigateAuthenticationToken)authentication;
        Authentication authenticate = super.authenticate(authentication);

        return new UnigateAuthenticationToken(
                authenticate.getPrincipal(),
                authentication.getCredentials(),
                authenticate.getAuthorities(),
                unigateAuthentication.getRedirectUri()
        );
    }

//    private void validateAuthenticationStrategy(User user) {
//        var request = SecurityUtils.getCurrentRequest();
//        var isEsiaUser = user instanceof IndividualPerson && ((IndividualPerson)user).isNameEqualsSnils();
//
//        if ((JWTLoginFilter.isEsiaAuthenticationStrategy(request) && isEsiaUser)
//                || (JWTLoginFilter.isRegularAuthenticationStrategy(request) && !isEsiaUser)
//        ) {
//            return;
//        }
//
//        throw new UnigateAuthenticationException(
//                "Недопустимый способ авторизации",
//                UnigateAuthenticationException.UnigateAuthenticationErrorType.LOGIN);
//    }

    private void validateAttemptsBeforeLock(UserLockCounter lockCounter) {
        SettingPassword attemptsBeforeLock = settingsPasswordService.getPasswordAttemptsBeforeLock();
        if (lockCounter.checkLock(attemptsBeforeLock)) {
            throw new UnigateAuthenticationException(
                    "Превышен лимит попыток авторизации",
                    UnigateAuthenticationException.UnigateAuthenticationErrorType.LOCK);
        }
    }

    private void validateBlock(User user) {
        // user blocked by delete date
        if(user.getDeleteDate() != null) {
            throw new UnigateAuthenticationException(
                    String.format("Доступ пользователя %s к системе был ограничен", user.getUsername()),
                    UnigateAuthenticationException.UnigateAuthenticationErrorType.DELETED);
        }
        // user locked manually
        if (Boolean.FALSE.equals(user.getAccountNonLocked())) {
            throw new UnigateAuthenticationException(
                    "Пользователь заблокирован",
                    UnigateAuthenticationException.UnigateAuthenticationErrorType.BLOCK);
        }
    }

    private void validateSessionCount(User user) {
        if (SecurityUtils.isSysUser(user)) {
            return;
        }

        SettingAccessControl userSessionMaxCount = settingsAccessControlService.getUserSessionMaxCount();
        if (userSessionMaxCount != null && userSessionMaxCount.getEnable()) {
            long userSessionCount = sessionService.getUserSessionCount(user.getUsername());
//            boolean sessionExists = getUserSessionExists(user);
            boolean sessionExists = true;
            Integer maxSessionCount = getUserMaxSessionCount(user, userSessionMaxCount);
            if (!sessionExists) {
                userSessionCount += 1L; // + предполагаемая текущая сессия
            }
            if (maxSessionCount == null || userSessionCount > maxSessionCount) {
                throw new UnigateAuthenticationException(
                        "Превышено максимальное число активных сессий. Для подключения необходимо закрыть предыдущие сессии.",
                        UnigateAuthenticationException.UnigateAuthenticationErrorType.SESSION);
            }
        }
    }

    private Integer getUserMaxSessionCount(User user, SettingAccessControl userSessionMaxCount) {
        Integer maxSessionCount = user.getSessionMaxCount();
        if (maxSessionCount == null) {
            maxSessionCount = user.getRoles().stream()
                    .map(roleService::findByKey)
                    .map(Role::getSessionMaxCount)
                    .filter(Objects::nonNull)
                    .min(ObjectUtils::compare)
                    .orElse(null);
        }
        if (maxSessionCount == null) {
            maxSessionCount = userSessionMaxCount.getValue();
        }

        return maxSessionCount;
    }

//    private boolean getUserSessionExists(User user) {
//        boolean sessionExists = false;
//        HttpServletRequest request = SecurityUtils.getCurrentRequest();
//        if (request != null) {
//            String token = request.getParameter(WebConstants.TOKEN_PARAMETER);
//            if (token != null) {
//                try {
//                    SessionInfo session = sessionService.getSession(token);
//                    if (session != null && Objects.equals(session.getUsername(), user.getUsername())) {
//                        sessionExists = true; // найдена существующая сессия
//                    }
//                } catch (Exception ex) {
//                    // ignore
//                }
//            }
//        }
//
//        return sessionExists;
//    }

    private void checkRedirectUri(User user, UnigateAuthenticationToken auth) {
        String redirectUri = auth.getRedirectUri();
        String[] links = authenticationService.getUserMenuLinks(user);
        String[] userRedirectUrls = ArrayUtils.addAll(links, allowRedirectUrls);
        if (RedirectUrlUtils.isAllowUserRedirectUrl(redirectUri, userRedirectUrls)) {
            return;
        }

        throw  new UnigateAuthenticationException(
                UnigateAuthenticationException.getErrorRedirect(redirectUri),
                UnigateAuthenticationException.UnigateAuthenticationErrorType.REDIRECT
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UnigateAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        if (JWTLoginFilter.isRegularAuthenticationStrategy(SecurityUtils.getCurrentRequest())) {
            super.additionalAuthenticationChecks(userDetails, authentication);
//        }
    }
}
