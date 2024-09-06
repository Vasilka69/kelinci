package src.unigate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;
import src.unigate.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationService authenticationService;
    private final SettingsPasswordService settingsPasswordService;
    private final SettingsAccessControlService settingsAccessControlService;
//    private final EsiaServiceProperties esiaServiceProperties = new EsiaServiceProperties("http://10.80.37.15:8081");
    private final String[] allowRedirectUrls;

    public JWTLoginFilter(String url,
                          AuthenticationManager authManager,
                          AuthenticationService authenticationService,
                          SettingsPasswordService settingsPasswordService,
                          SettingsAccessControlService settingsAccessControlService,
//                          EsiaServiceProperties esiaServiceProperties,
                          String[] allowRedirectUrls) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.authenticationService = authenticationService;
        this.settingsPasswordService = settingsPasswordService;
        this.settingsAccessControlService = settingsAccessControlService;
//        this.esiaServiceProperties = esiaServiceProperties;
        this.allowRedirectUrls = allowRedirectUrls;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        try {
            String url = authenticationService.getAuthenticationStrategyUrl(request);
            if (url == null){
                RegularAuthenticationStrategyImpl authenticationStrategy = new RegularAuthenticationStrategyImpl();
                return getAuthenticationManager().authenticate(authenticationStrategy.authenticate(request, allowRedirectUrls));
            }
//            url = String.format("%s/%s?%s", esiaServiceProperties.getUrl(), url, request.getQueryString());
//            ResponseEntity<String> authenticationToken = HttpClient.sendGetRequest(url, String.class);
            ResponseEntity<String> authenticationToken = new ResponseEntity<>("TEST_TOKEN", HttpStatus.OK);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new CoreJackson2Module());
            mapper.addMixIn(UnigateAuthenticationToken.class, UnigateAuthenticationTokenMixin.class);
            UnigateAuthenticationToken token = mapper.readerFor(UnigateAuthenticationToken.class).readValue(authenticationToken.getBody());

            return getAuthenticationManager().authenticate(token);
        } catch (Exception e) {
//            if (isEsiaAuthenticationStrategy(request)) {
//                String redirectUri = RedirectUrlUtils.getRedirectUri(request);
//                String errorCode = getErrorCode(e);
//                String getErrorRedirectUrl = String.format("%s/auth/esia/url/error/%s?%s=%s", esiaServiceProperties.getUrl(),
//                        errorCode, WebConstants.REDIRECT_URI_PARAMETER, redirectUri);
//                String url = HttpClient.sendGetRequest(getErrorRedirectUrl, String.class).getBody();
//                response.sendRedirect(url);
//                return null;
//            } else {
                throw e;
//            }
        }
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response)
                || StringUtils.isNotEmpty(request.getParameter(WebConstants.REDIRECT_URL_PARAMETER));
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        UnigateAuthenticationToken unigateAuth = (UnigateAuthenticationToken)auth;
        User user = ((SecurityUser) auth.getPrincipal()).getUserData();

        if (isAddChangePassword(request, user)) {
            addChangePasswordToken(request, response, user);
        } else if (isAddTwoFactorAuth(request, user)) {
            addTwoFactorToken(request, response, user);
        } else {
            addAuthenticationToken(request, response, user, unigateAuth.getRedirectUri());
        }

    }

    private static String getErrorCode(Exception e) {
        final String defaultCode = "error";

        if (e == null) {
            return defaultCode;
        } else if (e instanceof LockedException) {
            return UnigateAuthenticationException.UnigateAuthenticationErrorType.LOCK.name().toLowerCase();
        } else if (e instanceof UnigateAuthenticationException) {
            UnigateAuthenticationException.UnigateAuthenticationErrorType errorType = ((UnigateAuthenticationException) e).getErrorType();
            if (errorType == null) {
                return defaultCode;
            }

            return errorType.name().toLowerCase();
        } else {
            return defaultCode;
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(failed.getMessage());
    }

    private boolean isPasswordExpired(User user) {
        boolean passwordExpired = false;
        SettingPassword passwordMaxTime = settingsPasswordService.getPasswordMaxTime();
        if (!SecurityUtils.isSysUser(user) && passwordMaxTime != null && passwordMaxTime.isEnable()) {
            if (passwordMaxTime.getTime() != null) {
                passwordExpired = ZonedDateTime.now().isAfter(UserUtils.getPasswordMaxDate(passwordMaxTime, user.getChangePasswordDate()));
            } else {
                System.err.println("Максимальный срок действия пароля не указан!");
            }
        }

        return passwordExpired;
    }

    private boolean isAddChangePassword(HttpServletRequest request, User user) {
        return isRegularAuthenticationStrategy(request)
                && (Boolean.TRUE.equals(user.isChangePassword()) || isPasswordExpired(user));
    }

    private boolean isAddTwoFactorAuth(HttpServletRequest request, User user) {
        if (SecurityUtils.isSysUser(user)
                || !(isRegularAuthenticationStrategy(request))) {
            return false;
        }

        SettingAccessControl twoFactorAuth = settingsAccessControlService.getTwoFactorAuth();
        return Boolean.TRUE.equals(twoFactorAuth.getEnable());
    }

    private void addChangePasswordToken(HttpServletRequest request, HttpServletResponse response, User user) {
        authenticationService.addChangePasswordToken(request, response, user.getUsername());
    }

    private void addTwoFactorToken(HttpServletRequest request, HttpServletResponse response, User user) {
        authenticationService.addTwoFactorToken(request, response, user);
    }

    private void addAuthenticationToken(HttpServletRequest request, HttpServletResponse response, User user, String redirectUri) throws IOException {
        authenticationService.addAuthentication(request, response, user, redirectUri);
    }

    public static boolean isEsiaAuthenticationStrategy(HttpServletRequest request) {
        return Objects.equals(request.getParameter(WebConstants.STRATEGY_PARAMETER), "strategy.esia");
    }

    public static boolean isRegularAuthenticationStrategy(HttpServletRequest request) {
        return Objects.equals(request.getParameter(WebConstants.STRATEGY_PARAMETER), null);
    }

    public static String getRedirectUrl(HttpServletRequest request, User user, String redirectUri, String[] allowRedirectUrls, String token, String esiaUrl) {
        String url;
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity entity = new HttpEntity<>(headers);
//        if (isEsiaAuthenticationStrategy(request)) {
//            String getRolesUrl = String.format("%s/auth/esia/roles", esiaUrl);
//            List roles = HttpClient
//                    .sendGetRequest(entity, getRolesUrl, List.class)
//                    .getBody();
//            if (!CollectionUtils.isEmpty(roles)) {
//                String getRolesRedirectUrl = String.format("%s/auth/esia/url/roles?%s=%s&%s=%s",
//                        esiaUrl, WebConstants.REDIRECT_URI_PARAMETER, redirectUri, WebConstants.TOKEN_PARAMETER, token);
//                url = HttpClient
//                        .sendGetRequest(entity, getRolesRedirectUrl, String.class)
//                        .getBody();
//                return url;
//            } else if (StringUtils.isNotEmpty(redirectUri)) {
//                String getSuccessRedirectUrl = String.format("%s/auth/esia/url/success?%s=%s&%s=%s",
//                        esiaUrl, WebConstants.REDIRECT_URI_PARAMETER, redirectUri, WebConstants.TOKEN_PARAMETER, token);
//                url = HttpClient
//                        .sendGetRequest(entity, getSuccessRedirectUrl, String.class)
//                        .getBody();
//                return url;
//            } else if (!ArrayUtils.isEmpty(allowRedirectUrls) && allowRedirectUrls.length == 1) {
//                String getSuccessRedirectUrl = String.format("%s/auth/esia/url/success?%s=%s&%s=%s",
//                        esiaUrl, WebConstants.REDIRECT_URI_PARAMETER, allowRedirectUrls[0], WebConstants.TOKEN_PARAMETER, token);
//                url = HttpClient
//                        .sendGetRequest(entity, getSuccessRedirectUrl, String.class)
//                        .getBody();
//                return url;
//            }
//
//        } else if (isRegularAuthenticationStrategy(request)) {
            if (StringUtils.isNotEmpty(redirectUri)) {
                return redirectUri;
            } else if (SecurityUtils.isGeneralUser(user) && !ArrayUtils.isEmpty(allowRedirectUrls) && allowRedirectUrls.length == 1) {
                return allowRedirectUrls[0];
            }
//        }

        return null;
    }
}
