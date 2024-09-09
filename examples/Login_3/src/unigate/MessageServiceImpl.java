package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import src.LoginMain;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService, ProxyMessageService {
//    private final RabbitTemplate rabbitTemplate;
//    private final UserRepository userRepository;
//    private final SessionRepository sessionRepository;

//    @Value("${spring.rabbitmq.exchange.protocol:}")
//    private String protocolExchange;
//    @Value("${spring.rabbitmq.exchange.proxy:proxy-exchange}")
//    private String proxyExchange;
//    @Value("${spring.rabbitmq.queue.protocol:events-queue}")
//    private String protocolQueue;
//    @Value("${spring.rabbitmq.queue.proxy:}")
//    private String proxyQueue;

    private static PasswordEncoder passwordEncoder;

    public static void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        MessageServiceImpl.passwordEncoder = passwordEncoder;
    }

    //    @Value("${spring.rabbitmq.exchange.protocol:}")
    private String protocolExchange = "";
//    @Value("${spring.rabbitmq.exchange.proxy:proxy-exchange}")
    private String proxyExchange = "proxy-exchange";
//    @Value("${spring.rabbitmq.queue.protocol:events-queue}")
    private String protocolQueue = "events-queue";
//    @Value("${spring.rabbitmq.queue.proxy:}")
    private String proxyQueue = "";

//    @Override
//    public void sendProtocolEvent(String eventType, String objectType, String objectId, String object) {
//        var currentUser = SecurityUtils.getCurrentUser();
//        var userId = currentUser == null ? null : currentUser.getId();
//        var protocolEvent = ProtocolEvent.builder()
//                .type(eventType).objectType(objectType)
//                .object(object).objectId(objectId)
//                .userId(userId).userName(UserUtils.getName(currentUser))
//                .comment(eventType + " " + objectType)
//                .ip(SecurityUtils.getCurrentUserIp())
//                .date(ZonedDateTime.now())
//                .build();
//        this.sendProtocolEvent(protocolEvent);
//    }
//
//    @Override
//    public void sendProtocolEvent(String eventType, String objectType, String objectId, String object, Integer rev) {
//        var currentUser = SecurityUtils.getCurrentUser();
//        var userId = currentUser == null ? null : currentUser.getId();
//        var protocolEvent = ProtocolEvent.builder()
//                .type(eventType).objectType(objectType)
//                .object(object).objectId(objectId)
//                .userId(userId).userName(UserUtils.getName(currentUser))
//                .comment(eventType + " " + objectType)
//                .ip(SecurityUtils.getCurrentUserIp())
//                .date(ZonedDateTime.now())
//                .rev(rev)
//                .build();
//        this.sendProtocolEvent(protocolEvent);
//    }

    @Override
    public void sendUserProtocolEvent(String login, String eventCode, String remoteIp) {
//        User user = userRepository.findFirstByUsernameIgnoreCase(login.toUpperCase());
        User user = findFirstByUsernameIgnoreCase(login.toUpperCase());
        String username = UserUtils.getName(user);
        this.sendProtocolEvent(new ProtocolEvent(eventCode, user.getId(), username, remoteIp));
    }

    public static User findNullableFirstByUsernameIgnoreCase(String username) {
        if (new Random().nextBoolean()) {
            return findFirstByUsernameIgnoreCase(username);
        } else {
            return null;
        }
    }

    public static User findFirstByUsernameIgnoreCase(String username) {
        String password = encodePassword(username);
        if (new Random().nextBoolean()) {
            IndividualPerson individualPerson = IndividualPerson.builder()
                .id(username)
                .username(username)
                .password(password)
                .accountNonLocked(true)
                .changePassword(false)
                .deleteDate(null)
                .createDate(Date.from(Instant.now()))
                .sessionMaxCount(999)
                .changePasswordDate(ZonedDateTime.now().plusYears(5))
                .blockReason(null)
                .blockComment("")
                .lastModified(ZonedDateTime.now())
                .lastAuthDate(ZonedDateTime.now())
                .firstName(username)
                .lastName(username)
                .patronymic(username)
                .post(username)
                .email(username)
                .phone("81234567890")
                .photo(UUID.randomUUID())
                .validTo(LocalDate.now().plusYears(5))
                .snils(username)
                .lastSessionRoleId(username)
                .build();

            individualPerson.setRoles(new HashSet<>());
            individualPerson.setPermissions(new ArrayList<>());

            return individualPerson;
        } else {
            SysUser sysUser = new SysUser(
                    username,
                    username,
                    password,
                    true,
                    "",
                    username
            );

            sysUser.setId(username);
            sysUser.setUsername(username);
            sysUser.setPassword(password);
            sysUser.setAccountNonLocked(true);
            sysUser.setChangePassword(false);
            sysUser.setDeleteDate(null);
            sysUser.setCreateDate(Date.from(Instant.now()));
            sysUser.setSessionMaxCount(999);
            sysUser.setChangePasswordDate(ZonedDateTime.now().plusYears(5));
            sysUser.setBlockReason(null);
            sysUser.setBlockComment("");
            sysUser.setLastModified(ZonedDateTime.now());
            sysUser.setLastAuthDate(ZonedDateTime.now());

            sysUser.setRoles(new HashSet<>());
            sysUser.setPermissions(new ArrayList<>());

            return sysUser;
        }
    }

    private static String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static User fillCredentials(User user) {
        user.setUsername(LoginMain.systemCredentials.getUsername());
        user.setPassword(encodePassword(LoginMain.systemCredentials.getPassword()));
        return user;
    }

//    @Override
//    public void sendProxyCacheClearByUserId(String userId) {
//        sessionRepository.findTokensByUserId(userId).forEach(this::sendProxyCacheClearByToken);
//    }

//    @Override
//    public void sendProxyCacheClearByRoleId(String roleId) {
//        userRepository.findAllByRolesIn(Collections.singletonList(roleId))
//                .forEach(user -> this.sendProxyCacheClearByUserId(user.getId()));
//    }

//    @Override
//    public void sendProxyCacheClearByToken(String token) {
//        sendProxyEvent(ProxyExchangeEvent.CLEAR_SESSION_CACHE, token);
//    }

//    @Override
//    public void sendUpdateProxyRule(Integer id) {
//        sendProxyEvent(ProxyExchangeEvent.UPDATE_PROXY_RULE, id.toString());
//    }
//
//    @Override
//    public void sendDeleteProxyRule(Integer id) {
//        sendProxyEvent(ProxyExchangeEvent.DELETE_PROXY_RULE, id.toString());
//    }

    private void sendProtocolEvent(ProtocolEvent protocolEvent) {
        System.out.printf("Сообщение %s отправлено%n", protocolEvent);
//        rabbitTemplate.setExchange(protocolExchange);
//        rabbitTemplate.convertAndSend(protocolQueue, ObjectUtils.toJson(protocolEvent));
    }

    private void sendProxyEvent(String type, String id) {
        ProxyExchangeEvent event = new ProxyExchangeEvent(type, id);
        System.out.printf("Сообщение %s отправлено%n", event);
//        rabbitTemplate.setExchange(proxyExchange);
//        rabbitTemplate.convertAndSend(proxyQueue, ObjectUtils.toJson(event));
    }
}
