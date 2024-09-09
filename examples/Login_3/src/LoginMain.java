package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Request;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.ProviderManager;
import org.thymeleaf.TemplateEngine;
import src.unigate.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

public class LoginMain
{
    public static void main(final String[] args) {
        System.err.println("Current args:" + Arrays.toString(args));

        String path = args[0];

        try {
            JavaMailSender javaMailSender = new JavaMailSenderImpl();

            TwoFactorAuthCodeService twoFactorAuthCodeService = new TwoFactorAuthCodeService();
            MailService mailService = new MailServiceImpl(
                    javaMailSender
            );
            TemplateEngine templateEngine = new TemplateEngine();
            UserMailService userMailService = new UserMailServiceImpl(
                    twoFactorAuthCodeService,
                    mailService,
                    templateEngine
            );

            UsersLocker usersLocker = new UsersLocker();
            RoleService roleService = new DefaultRoleService();
            SecurityObjectService securityObjectService = new DefaultSecurityObjectService();
            UserService userService = new DefaultUserService(
                    roleService,
                    securityObjectService
            );
            SettingsAccessControlService settingsAccessControlService = new SettingsAccessControlServiceImpl();
            SettingsPasswordService settingsPasswordService = new SettingsPasswordServiceImpl();

            SessionService sessionService = new DefaultSessionService(
                    settingsAccessControlService
            );
            MessageService messageService = new MessageServiceImpl();
            ChangePasswordService changePasswordService = new DefaultChangePasswordService();
            TwoFactorAuthService twoFactorAuthService = new DefaultTwoFactorAuthService(
                    userMailService
            );
            EsiaServiceProperties esiaServiceProperties = new EsiaServiceProperties();
            MenuService menuService = new MenuServiceImpl(
                    userService
            );

            AuthenticationService authenticationService = new AuthenticationService(
                    sessionService,
                    messageService,
                    changePasswordService,
                    twoFactorAuthService,
                    esiaServiceProperties,
                    menuService
            );

            String[] allowRedirectUrls = new String[0];

            UnigateAuthenticationProvider unigateAuthenticationProvider = new UnigateAuthenticationProvider(
                    usersLocker,
                    sessionService,
                    userService,
                    roleService,
                    settingsAccessControlService,
                    settingsPasswordService,
                    authenticationService,
                    allowRedirectUrls

            );

            ProviderManager providerManager = new ProviderManager(Collections.singletonList(unigateAuthenticationProvider));

            final String LOGIN_URL = "/login";
            JWTLoginFilter jwtLoginFilter = new JWTLoginFilter(
                    LOGIN_URL,
                    providerManager,
                    authenticationService,
                    settingsPasswordService,
                    settingsAccessControlService,
                    allowRedirectUrls
            );

            SystemCredentials systemCredentials = parseJson(path, SystemCredentials.class);

            ServletRequest request = new ;
            // HeaderWriterRequest
            ServletResponse response = null;
            // HeaderWriterResponse
            FilterChain chain = null;
            // VirtualFilterChain

            jwtLoginFilter.doFilter(request, response, chain);

            System.out.printf("User with username = %s, password = %s was logged in%n", systemCredentials.getUsername(), systemCredentials.getPassword());
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
        }

        System.out.println("Done.");
    }

    public static <T> T parseJson(String jsonPath, Class<T> type) throws Exception {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(jsonPath));
            String json = new String(encoded, StandardCharsets.UTF_8);
            return new ObjectMapper().readValue(json, type);
        } catch (RuntimeException e) {
            throw new Exception(e);
        }
    }
}

