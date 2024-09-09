package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
//@Slf4j
@Primary
@RequiredArgsConstructor
public class DefaultSessionService implements SessionService {
    public static final String OBJECT_TYPE = "session";

//    private final SessionMapperDto sessionMapperDto;
//    private final SessionRepository sessionRepository;
//    private final UserService userService;
//    private final JdbcTemplate jdbcTemplate;
//    private final UserRepository userRepository;
//    private final IndividualPersonRepository individualPersonRepository;
//    private final AttributeValueService attributeValueService;
//    private final AttributeValueMapperDto attributeValueMapperDto;
    private final SettingsAccessControlService settingsAccessControlService;
//    private final RoleService roleService;
//    private final MessageService messageService;
//    private final EsiaServiceProperties esiaServiceProperties;

//    @Override
//    public RegResponse<SessionExportInfo> getSessionExportInfoByFilter(SessionFilter filter) {
//        log.debug("Поиск сессий. Переданные атрибуты: {}", filter.getAttrs());
//        if (filter.getAttrs() == null) {
//            throw new ResourceIllegalArgumentException("Атрибуты поиска не могут быть равны null!");
//        }
//
//        var items = SessionExportInfo.of(sessionRepository.getSessionsByFilter(filter).stream()
//                .map(sessionMapperDto::mapSessionInfo)
//                .collect(Collectors.toList()));
//        long totalCount = sessionRepository.count();
//        long filteredCount = sessionRepository.getSessionsFilteredCount(filter);
//
//        log.debug("Поиск сессий по атрибутам завершен");
//        return new RegResponse<>(items, totalCount, filteredCount);
//    }
//
//    @Override
//    public SessionInfo getSession(String token) {
//        Session session = sessionRepository.findByToken(token);
//        if (session == null) {
//            throw new ResourceNotFoundException("Сессия по указанному токену не найдена!");
//        }
//        return sessionMapperDto.mapSessionInfo(session);
//    }
//
//    @Override
//    public boolean isActual(String token) {
//        var session = sessionRepository.findByToken(token);
//        if (session != null) {
//            var expired = new Date().after(Objects.requireNonNull(TokenUtils.getExpirationDate(token)));
//
//            var interruptingTime = settingsAccessControlService.getInterruptingTime();
//            if (interruptingTime != null && Boolean.TRUE.equals(interruptingTime.getEnable())) {
//                var expiredBySetting = session.getActiveDate().plusMinutes(interruptingTime.getValue()).isBefore(ZonedDateTime.now());
//                return !expired && !expiredBySetting;
//            } else {
//                return !expired;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public SessionStatus getSessionStatus(String token) {
//        var session = sessionRepository.findByToken(token);
//        if (session == null) {
//            return new SessionStatus(false);
//        }
//
//        var expired = new Date().after(Objects.requireNonNull(TokenUtils.getExpirationDate(token)));
//        if (expired) {
//            return new SessionStatus(false);
//        }
//
//        var interruptingTime = settingsAccessControlService.getInterruptingTime();
//        if (interruptingTime != null && Boolean.TRUE.equals(interruptingTime.getEnable())) {
//            var interruptDate = session.getActiveDate().plusMinutes(interruptingTime.getValue());
//            if (interruptDate.isBefore(ZonedDateTime.now())) {
//                return new SessionStatus(false);
//            }
//        }
//
//        return new SessionStatus(true);
//    }
//
//    @Override
//    public boolean exists(String token) {
//        return sessionRepository.existsByToken(token);
//    }
//
//    @Override
//    public boolean existsByKey(UUID id) {
//        return sessionRepository.existsById(id);
//    }

    @Override
    public Session addSession(HttpServletRequest request, String token, User user, String ip, AuthenticationSystem authenticationSystem) {
        validateSession(user);

        Date issuedAt = TokenUtils.getIssuedAt(token);
        if (issuedAt == null) {
            throw new RuntimeException(String.format("Отсутствует дата выдачи токена %s", token));
        }
        ZonedDateTime startDate = ZonedDateTime.ofInstant(issuedAt.toInstant(), ZoneId.systemDefault());

        Session session = new Session();
        session.setId(UUID.randomUUID());
        session.setToken(token);
        session.setUserId(user.getId());
        session.setUsername(user.getUsername());
        session.setFullName(UserUtils.getName(user));
        session.setStartDate(startDate);
        session.setActiveDate(startDate);
        session.setIp(ip);
        session.setUserAuthorizedSystem(authenticationSystem);

//        sessionRepository.save(session);
        System.out.printf("Сессия %s сохранена в БД", session);

//        userRepository.updateLastAuthDateById(jdbcTemplate, session.getActiveDate(), user.getId());
        System.out.printf("Время последнего захода пользователя с id = %s изменено на %s", user.getId(), session.getActiveDate());

        return session;
    }

    @Override
    public void update(String token) {
//        Session session = sessionRepository.findByToken(token);
        Session session = findByToken(token);
        session.setActiveDate(ZonedDateTime.now());
//        sessionRepository.save(session);
        System.out.printf("Сессия %s сохранена в БД", session);

        if (session.getUsername() != null) {
            Optional<String> idByUsername = findIdByUsernameLowerCase(session.getUsername().toLowerCase());
//            idByUsername.ifPresent(s -> userRepository.updateLastAuthDateById(jdbcTemplate, session.getActiveDate(), s));
            idByUsername.ifPresent(s ->
                    System.out.printf("Время последнего захода пользователя с id = %s изменено на %s", s, session.getActiveDate()));
        }
    }

    private Optional<String> findIdByUsernameLowerCase(String username) {
        return Optional.of(username);
    }

//    @Override
//    public void deleteSession(String id) {
//        var sessionOpt = sessionRepository.findById(UUID.fromString(id));
//        if (sessionOpt.isEmpty()) {
//            return;
//        }
//
//        var session = sessionOpt.get();
//        messageService.sendProxyCacheClearByToken(session.getToken());
//        messageService.sendProtocolEvent(INTERRUPT_EVENT, OBJECT_TYPE, session.getToken(), "Сессия пользователя " + session.getUsername());
//        var logoutDate = ZonedDateTime.now();
//        if (session.getUsername() != null) {
//            var idByUsernameOpt = userRepository.findIdByUsernameLowerCase(session.getUsername().toLowerCase());
//            idByUsernameOpt.ifPresent(s -> userRepository.updateLastAuthDateById(jdbcTemplate, logoutDate, s));
//        }
//
//        sessionRepository.delete(session);
//    }
//
//    @Override
//    public void deleteAll() {
//        sessionRepository.deleteAll();
//    }
//
//    @Override
//    public Set<String> getNotActualTokens(Set<String> tokens) {
//        if (CollectionUtils.isEmpty(tokens)) return Collections.emptySet();
//        var actualTokens = sessionRepository.findTokensByTokens(tokens);
//        tokens.removeAll(actualTokens);
//
//        return tokens;
//    }
//
//    /**
//     * Очистка неактивных сессий пользователей
//     */
//    @Scheduled(cron = "${ru.kamatech.unigate.security.session_clear.cron}")
//    public void cleaning() {
//        log.debug("Очистка неактивных сессий пользователей");
//        sessionRepository.findAllOwnerGeneralUser().stream()
//                .filter(s -> !isActual(s.getToken()))
//                .forEach(s -> {
//                    sessionRepository.delete(s);
//                    log.info("Удаление неактивной сессии {}, токен {}", s.getId(), s.getToken());
//                });
//    }
//
//    /**
//     * Очистка неактивных сессий системных пользователей
//     */
//    @Scheduled(cron = "${ru.kamatech.unigate.security.sys_users_session_clear.cron}")
//    public void cleaningSysUsersSessions() {
//        log.debug("Очистка неактивных сессий системных пользователей");
//        sessionRepository.findAllOwnerSysUser().stream()
//                .filter(s -> !isActual(s.getToken()))
//                .forEach(s -> {
//                    sessionRepository.delete(s);
//                    log.info("Удаление неактивной сессии {}, токен {}", s.getId(), s.getToken());
//                });
//    }
//
//    @Override
//    public UserPermissionsInfo getUserPermissionsInfo(String token) {
//        var session = sessionRepository.findByToken(token);
//        if (session == null) {
//            throw new ResourceNotFoundException(String.format("Сессия по указанному токену(%s) не найдена!", token));
//        }
//        var userName = TokenUtils.getAuthorizationUsername(token);
//        var user = userService.findByUsernameFull(userName);
//        var userPermissions = getPermissions(token);
//
//        var userInfo = new UserInfo();
//        userInfo.setId(user.getId());
//        userInfo.setUsername(user.getUsername());
//        userInfo.setRoleInfo(user.getRoles().stream().map(roleId -> {
//            var role = roleService.findByKey(roleId);
//            return new RoleInfo(role.getId(), role.getName(), role.getDescription(), new ArrayList<>(role.getTypes()));
//        }).collect(Collectors.toList()));
//        if (user instanceof IndividualPerson) {
//            var person = (IndividualPerson) user;
//            userInfo.setFirstName(person.getFirstName());
//            userInfo.setLastName(person.getLastName());
//            userInfo.setPatronymic(person.getPatronymic());
//            userInfo.setPost(person.getPost());
//            userInfo.setEmail(person.getEmail());
//            userInfo.setPhone(person.getPhone());
//
//            var attributeValuesMap = getAttributes(person);
//            if (!attributeValuesMap.isEmpty()) {
//                userInfo.setAttributes(attributeValuesMap.entrySet().stream()
//                        .collect(Collectors.toMap(
//                                es -> es.getKey().getId(),
//                                es -> es.getValue().stream()
//                                        .map(val -> attributeValueMapperDto.getAttributeShortInfoValue(
//                                                val.getAttribute(),
//                                                val.getValue()))
//                                        .collect(Collectors.toList())
//                        )));
//            }
//
//        } else if (user instanceof SysUser) {
//            userInfo.setFirstName(((SysUser) user).getName());
//        }
//
//        if (StringUtils.hasText(session.getIp())) {
//            userInfo.setRemoteIp(session.getIp());
//        }
//
//        return new UserPermissionsInfo(userInfo, userPermissions);
//    }
//
//    private Map<Attribute, List<AttributeValue>> getAttributes(IndividualPerson individualPerson) {
//        Map<Attribute, List<AttributeValue>> result = new LinkedHashMap<>();
//        var attributeValuesMap = attributeValueService.findAllUserAttributeValues(individualPerson.getId())
//                .stream()
//                .filter(value -> Objects.nonNull(value.getAttribute()))
//                .collect(Collectors.groupingBy(AttributeValue::getAttribute));
//
//        attributeValuesMap.keySet().stream()
//                .filter(Attribute::getIsGlobal)
//                .sorted(Comparator.comparingInt(Attribute::getOrder)
//                        .thenComparing(Attribute::getId))
//                .forEach(attribute -> result.put(attribute, attributeValuesMap.getOrDefault(attribute, Collections.emptyList())));
//        attributeValuesMap.keySet().stream()
//                .filter(attribute -> !attribute.getIsGlobal())
//                .sorted(Comparator.comparing(Attribute::getId))
//                .forEach(attribute -> result.put(attribute, attributeValuesMap.getOrDefault(attribute, Collections.emptyList())));
//
//        return result;
//    }
//
//    @Override
//    public Map<String, Set<String>> getPermissions(String token) {
//        var userName = TokenUtils.getAuthorizationUsername(token);
//        var user = userService.findByUsernameFull(userName);
//
//        return userService.getPermissions(user);
//    }

//    @Override
    public List<SessionRoleInfo> getSessionRoles(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity entity = new HttpEntity<>(headers);
//        String getRolesUrl = String.format("%s/auth/esia/roles", esiaServiceProperties.getUrl());
//        return HttpClient
//                .sendGetRequest(entity, getRolesUrl,new ParameterizedTypeReference<List<SessionRoleInfo>>() {})
//                .getBody();
        return Arrays.asList(new SessionRoleInfo(
                UUID.randomUUID(),
                "roleId",
                "name",
                "type",
                true
        ));
    }

    @Override
    public void changeSessionRole(String token, String roleId) {
        Session session = getSessionByToken(token);
        List<SessionRoleInfo> roles = getSessionRoles(token);
        if (CollectionUtils.isEmpty(roles)) {
            throw new RuntimeException("Указанная сессия не содержит роли для входа!");
        }
        SessionRoleInfo role = sessionRoleById(roles, roleId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity entity = new HttpEntity<>(Collections.emptyList(), headers);
//        String getRolesUrl = String.format("%s/auth/esia/roles", esiaServiceProperties.getUrl());
//        HttpClient.sendPostRequest(entity, getRolesUrl, List.class).getBody();
//        individualPersonRepository.updateLastSessionRoleIdBySnils(jdbcTemplate, session.getUsername(), role.getRoleId());
    }

    private SessionRoleInfo sessionRoleById(List<SessionRoleInfo> roles, String id) {
        if (CollectionUtils.isEmpty(roles)) {
            throw new RuntimeException("Указанная сессия не содержит роли для входа!");
        }
        SessionRoleInfo role = roles.stream().filter(r -> Objects.equals(r.getRoleId(), id)).findFirst().orElse(null);
        if (role == null) {
            throw new RuntimeException("Роль входа по указанной сессии не найдена!");
        }

        return role;
    }

//    @Override
//    public boolean hasPermission(String token, PermissionsInfo.SecurityObjectActionInfo securityObjectAction) {
//        return hasPermission(getPermissions(token), securityObjectAction);
//    }
//
//    @Override
//    public List<Boolean> hasPermissions(String token, List<PermissionsInfo.SecurityObjectActionInfo> securityObjectActions) {
//        var permissions = getPermissions(token);
//        return securityObjectActions.stream()
//                .map(action -> hasPermission(permissions, action))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Boolean hasRole(String token, String roleId) {
//        var userName = TokenUtils.getAuthorizationUsername(token);
//        var user = userService.findByUsernameFull(userName);
//        return user.getRoles().contains(roleId);
//    }
//
//    @Override
//    public List<Boolean> hasRoles(String token, List<String> roleIds) {
//        var userName = TokenUtils.getAuthorizationUsername(token);
//        var user = userService.findByUsernameFull(userName);
//
//        return roleIds.stream()
//                .map(roleId -> user.getRoles().contains(roleId))
//                .collect(Collectors.toList());
//    }
//
//    private boolean hasPermission(Map<String, Set<String>> permissions, PermissionsInfo.SecurityObjectActionInfo securityObjectAction) {
//        var actions = permissions.get(securityObjectAction.getSecurityObjectId());
//        return actions != null && actions.contains(securityObjectAction.getAction());
//    }

//    @Override
    public long getUserSessionCount(String username) {
//        return sessionRepository.countByUsername(username);
        return 1;
    }

    private void validateSession(User user) {
        if (SecurityUtils.isSysUser(user)) {
            return;
        }
        SettingAccessControl userSessionMaxCount = settingsAccessControlService.getUserSessionMaxCount();
        if (userSessionMaxCount != null && Boolean.TRUE.equals(userSessionMaxCount.getEnable())) {
            long userSessionCount = getUserSessionCount(user.getUsername()) + 1; // + предполагаемая текущая сессия
            if (userSessionCount > userSessionMaxCount.getValue()) {
                throw new RuntimeException(String.format(
                        "Превышено максимально допустимое количество сессий для пользователя %s", user.getUsername()
                ));
            }
        }
    }

    private Session getSessionByToken(String token) {
//        Session session = sessionRepository.findByToken(token);
        Session session = findByToken(token);
        if (session == null) {
            throw new RuntimeException("Сессия по направленному токену не найдена!");
        }

        return session;
    }

    private Session findByToken(String token) {
        Session session = null;
        if (new Random().nextBoolean()) {
            session = new Session(
                    UUID.randomUUID(),
                    token,
                    token,
                    token,
                    token,
                    ZonedDateTime.now(),
                    ZonedDateTime.now(),
                    "0.0.0.0",
                    AuthenticationSystem.UNIGATE
            );
        }
        return session;
    }
}
