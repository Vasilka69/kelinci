package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.TemplateEngine;
import src.unigate.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

public class LoginMain
{
    private static final String LOGIN_URL = "/login";
    public static SystemCredentials systemCredentials = null;

    public static void main(final String[] args) {
        System.out.println("Current args:" + Arrays.toString(args));

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

            SecurityUserDetailsManager securityUserDetailsManager = new SecurityUserDetailsManager(userService);
            SessionService sessionService = new DefaultSessionService(
                    settingsAccessControlService
            );
            MessageService messageService = new MessageServiceImpl();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            MessageServiceImpl.setPasswordEncoder(passwordEncoder);

            ChangePasswordService changePasswordService = new DefaultChangePasswordService();
            TwoFactorAuthService twoFactorAuthService = new DefaultTwoFactorAuthService(
                    userMailService
            );
            EsiaServiceProperties esiaServiceProperties = new EsiaServiceProperties();
            MenuService menuService = new MenuServiceImpl(
                    userService
            );

            AuthenticationService authenticationService = new AuthenticationService(
                    securityUserDetailsManager,
                    sessionService,
                    messageService,
                    changePasswordService,
                    twoFactorAuthService,
                    esiaServiceProperties,
                    menuService
            );

            final String redirectUrl = "/test_redirect";
            String[] allowRedirectUrls = new String[] {redirectUrl};

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
            unigateAuthenticationProvider.setPasswordEncoder(passwordEncoder);
            unigateAuthenticationProvider.setUserDetailsService(securityUserDetailsManager);

            ProviderManager providerManager = new ProviderManager(Collections.singletonList(unigateAuthenticationProvider));

            JWTLoginFilter jwtLoginFilter = new JWTLoginFilter(
                    LOGIN_URL,
                    providerManager,
                    authenticationService,
                    settingsPasswordService,
                    settingsAccessControlService,
                    allowRedirectUrls
            );

            systemCredentials = parseJson(path, SystemCredentials.class);

            ServletRequest request = new CustomHttpServletRequest(
                    systemCredentials.getUsername(),
                    systemCredentials.getPassword()
            );
            // HeaderWriterRequest
            CustomHttpServletResponse response = new CustomHttpServletResponse();
            // HeaderWriterResponse
            FilterChain chain = (request1, response1) -> {

            };
            // VirtualFilterChain

            jwtLoginFilter.doFilter(request, response, chain);

            if (response.getStatus() != 200) {
                throw new RuntimeException(String.format("Ошибка аутентификации: %s%n", response.getOutput()));
            }

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

    static class CustomHttpServletRequest implements HttpServletRequest {
        private final String username;
        private final String password;
        private final String jsonPayload;

        public CustomHttpServletRequest(String username, String password) {
            this.username = username;
            this.password = password;
            this.jsonPayload = String.format("{\n" +
                    "    \"username\": \"%s\",\n" +
                    "    \"password\": \"%s\"\n" +
                    "}", username, password);
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new StringReader(jsonPayload));
        }

        @Override
        public String getRemoteAddr() {
            return null;
        }

        @Override
        public String getRemoteHost() {
            return null;
        }

        @Override
        public void setAttribute(String name, Object o) {

        }

        @Override
        public void removeAttribute(String name) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public Enumeration<Locale> getLocales() {
            return null;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String path) {
            return null;
        }

        @Override
        public String getRealPath(String path) {
            return null;
        }

        @Override
        public int getRemotePort() {
            return 0;
        }

        @Override
        public String getLocalName() {
            return null;
        }

        @Override
        public String getLocalAddr() {
            return null;
        }

        @Override
        public int getLocalPort() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            return null;
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            return null;
        }

        @Override
        public boolean isAsyncStarted() {
            return false;
        }

        @Override
        public boolean isAsyncSupported() {
            return false;
        }

        @Override
        public AsyncContext getAsyncContext() {
            return null;
        }

        @Override
        public DispatcherType getDispatcherType() {
            return null;
        }

        @Override
        public String getMethod() {
            return "POST";
        }

        @Override
        public String getPathInfo() {
            return LOGIN_URL;
        }

        @Override
        public String getPathTranslated() {
            return null;
        }

        @Override
        public String getContextPath() {
            return null;
        }

        @Override
        public String getQueryString() {
            return null;
        }

        @Override
        public String getRemoteUser() {
            return null;
        }

        @Override
        public boolean isUserInRole(String role) {
            return false;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public String getRequestedSessionId() {
            return null;
        }

        @Override
        public String getRequestURI() {
            return LOGIN_URL;
        }

        @Override
        public StringBuffer getRequestURL() {
            return null;
        }

        @Override
        public String getServletPath() {
            return null;
        }

        @Override
        public HttpSession getSession(boolean create) {
            return null;
        }

        @Override
        public HttpSession getSession() {
            return null;
        }

        @Override
        public String changeSessionId() {
            return null;
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromUrl() {
            return false;
        }

        @Override
        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            return false;
        }

        @Override
        public void login(String username, String password) throws ServletException {

        }

        @Override
        public void logout() throws ServletException {

        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            return null;
        }

        @Override
        public Part getPart(String name) throws IOException, ServletException {
            return null;
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String getCharacterEncoding() {
            return null;
        }

        @Override
        public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {

        }

        @Override
        public int getContentLength() {
            return 0;
        }

        @Override
        public long getContentLengthLong() {
            return 0;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public ServletInputStream getInputStream() {
            return new ServletInputStream() {
                private final InputStream inputStream = new ByteArrayInputStream(jsonPayload.getBytes());

                @Override
                public int read() throws IOException {
                    return inputStream.read();
                }

                @Override
                public boolean isFinished() {
                    try {
                        return inputStream.available() == 0;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(javax.servlet.ReadListener readListener) {

                }
            };
        }

        @Override
        public String getParameter(String name) {
            if ("username".equals(name)) {
                return username;
            } else if ("password".equals(name)) {
                return password;
            }
            return null;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return null;
        }

        @Override
        public String[] getParameterValues(String name) {
            return new String[0];
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return null;
        }

        @Override
        public String getProtocol() {
            return null;
        }

        @Override
        public String getScheme() {
            return null;
        }

        @Override
        public String getServerName() {
            return null;
        }

        @Override
        public int getServerPort() {
            return 0;
        }

        @Override
        public String getAuthType() {
            return null;
        }

        @Override
        public Cookie[] getCookies() {
            return new Cookie[0];
        }

        @Override
        public long getDateHeader(String name) {
            return 0;
        }

        @Override
        public String getHeader(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            return null;
        }

        @Override
        public int getIntHeader(String name) {
            return 0;
        }

    }

    static class CustomHttpServletResponse implements HttpServletResponse {
        private final StringWriter stringWriter = new StringWriter();
        private PrintWriter writer = new PrintWriter(stringWriter);
        private int status = 200;

        @Override
        public String getCharacterEncoding() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return null;
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public void setCharacterEncoding(String charset) {

        }

        @Override
        public void setContentLength(int len) {

        }

        @Override
        public void setContentLengthLong(long length) {

        }

        public String getOutput() {
            return stringWriter.toString();
        }

        @Override
        public void addCookie(Cookie cookie) {

        }

        @Override
        public boolean containsHeader(String name) {
            return false;
        }

        @Override
        public String encodeURL(String url) {
            return null;
        }

        @Override
        public String encodeRedirectURL(String url) {
            return null;
        }

        @Override
        public String encodeUrl(String url) {
            return null;
        }

        @Override
        public String encodeRedirectUrl(String url) {
            return null;
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {

        }

        @Override
        public void sendError(int sc) throws IOException {

        }

        @Override
        public void sendRedirect(String location) throws IOException {

        }

        @Override
        public void setDateHeader(String name, long date) {

        }

        @Override
        public void addDateHeader(String name, long date) {

        }

        @Override
        public void setHeader(String name, String value) {

        }

        @Override
        public void addHeader(String name, String value) {

        }

        @Override
        public void setIntHeader(String name, int value) {

        }

        @Override
        public void addIntHeader(String name, int value) {

        }

        @Override
        public void setStatus(int sc) {
            this.status = sc;
        }

        @Override
        public void setStatus(int sc, String sm) {

        }

        public int getStatus() {
            return status;
        }

        @Override
        public String getHeader(String name) {
            return null;
        }

        @Override
        public Collection<String> getHeaders(String name) {
            return null;
        }

        @Override
        public Collection<String> getHeaderNames() {
            return null;
        }

        @Override
        public void setContentType(String type) {

        }

        @Override
        public void setBufferSize(int size) {

        }

        @Override
        public int getBufferSize() {
            return 0;
        }

        @Override
        public void flushBuffer() throws IOException {

        }

        @Override
        public void resetBuffer() {

        }

        @Override
        public boolean isCommitted() {
            return false;
        }

        @Override
        public void reset() {

        }

        @Override
        public void setLocale(Locale loc) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }
    }
}

