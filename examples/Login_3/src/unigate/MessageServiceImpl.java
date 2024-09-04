package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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

    private User findFirstByUsernameIgnoreCase(String username) {
        if (new Random().nextBoolean()) {
            return IndividualPerson.builder()
                    .id(username)
                    .username(username)
                    .password(username)
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
        } else {
            return new SysUser(
                    username,
                    username,
                    username,
                    true,
                    "",
                    username
            );
        }
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
        System.out.printf("Сообщение %s отправлено", protocolEvent);
//        rabbitTemplate.setExchange(protocolExchange);
//        rabbitTemplate.convertAndSend(protocolQueue, ObjectUtils.toJson(protocolEvent));
    }

    private void sendProxyEvent(String type, String id) {
        ProxyExchangeEvent event = new ProxyExchangeEvent(type, id);
        System.out.printf("Сообщение %s отправлено", event);
//        rabbitTemplate.setExchange(proxyExchange);
//        rabbitTemplate.convertAndSend(proxyQueue, ObjectUtils.toJson(event));
    }
}
