package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
//@Slf4j
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    public static final String OBJECT_TYPE = "user";

//    private final JdbcTemplate jdbcTemplate;
//    private final UserRepository userRepository;
//    private final UsersLastPasswordRepository usersLastPasswordRepository;
//    private final IndividualPersonRepository individualPersonRepository;
//    private final SessionRepository sessionRepository;
    private final RoleService roleService;
    private final SecurityObjectService securityObjectService;
//    private final ActionRepository actionRepository;
//    private final UserValidator userValidator;
//    private final AttributeValueService attributeValueService;
//    private final SettingsPasswordService settingsPasswordService;
//    private final SettingsAccessControlService settingsAccessControlService;
//    private final DeletedUsernameService deletedUsernameService;
//    private final UserLockService userLockService;
//    private final UserLogicalDeleteService userLogicalDeleteService;
//    private final GroupsJdbcRepository groupsJdbcRepository;
//
//    private UserMapperDto userMapperDto;
//
//    @Autowired
//    public void setUserMapperDto(UserMapperDto userMapperDto) {
//        this.userMapperDto = userMapperDto;
//    }
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Transactional
//    @Override
//    public User add(User user) {
//        try {
//            log.debug("Создание пользователя");
//            beforeAdd(user);
//            user = userRepository.save(user);
//            log.debug("Пользователь ({}) успешно создан", user.getId());
//
//        } catch (ResourceIllegalArgumentException exception) {
//            log.error(exception.toString());
//            throw exception;
//        }
//        return user;
//    }
//
//    @Override
//    public void beforeAdd(User user) {
//        if (user == null) {
//            throw new ResourceIsNullException(ResourceName.USER);
//        }
//
//
//        boolean hasRolesWrite = true;
//        boolean hasPermissionWrite = true;
//
//        User currentUser = SecurityUtils.getCurrentUser();
//        if (currentUser != null) {
//            hasRolesWrite = hasPermission(currentUser, SecurityObjectConstants.USER_ROLE.getId(), ActionConstants.WRITE.getId());
//            hasPermissionWrite = hasPermission(currentUser, SecurityObjectConstants.USER_PERMISSION.getId(), ActionConstants.WRITE.getId());
//        }
//
//        if (!StringUtils.hasText(user.getId())) {
//            user.setId(generateCn(user));
//        }
//
//        userValidator.validateBeforeAdd(user);
//        user.setCreateDate(new Date());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setChangePasswordDate(ZonedDateTime.now());
//
//        if (!hasRolesWrite) {
//            user.getRoles().clear();
//        }
//
//        if (!hasPermissionWrite) {
//            user.getPermissions().clear();
//        }
//    }
//
//    @Override
//    public User addSysUser(SysUser user) {
//        log.debug("Создание системного пользователя");
//        userValidator.validateBeforeAddSysUser(user);
//        user.setCreateDate(new Date());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setChangePasswordDate(ZonedDateTime.now());
//        User createdUser = userRepository.save(user);
//        log.debug("Системный пользователь ({}) успешно создан", createdUser.getId());
//        return createdUser;
//    }
//
//    @Override
//    public void remove(String key) {
//        log.debug("Удаление пользователя ({})", key);
//        userValidator.validateBeforeRemove(key);
//
//        log.debug("Удаление самого пользователя");
//        userRepository.deleteById(key);
//        log.debug("Пользователь ({}) успешно удален", key);
//    }
//
//    @Override
//    public List<User> findAll() {
//        log.debug("Получение списка пользователей");
//        List<User> users = userRepository.findAll();
//        log.debug("Список пользователей получен");
//        return users;
//    }
//
//    @Override
//    public User findByKey(String userId) {
//        log.debug("Получение информации о пользователе ({})", userId);
//        if (!hasText(userId)) {
//            ResourceEmptyIdException exception = new ResourceEmptyIdException(ResourceName.USER);
//            log.error(exception.toString());
//            throw exception;
//        }
//        User user = userRepository.findById(userId).orElse(null);
//        if (user == null) {
//            ResourceNotFoundException exception = new ResourceNotFoundException(ResourceName.USER, userId);
//            log.error(exception.toString());
//            throw exception;
//        }
//        log.debug("Информация о пользователе ({}) получена", userId);
//        return user;
//    }

    @Override
    public User findByKey(String key) {
        throw new RuntimeException("Метод не используется");
    }

//    @Override
//    public boolean exists(String key) {
//        log.debug("Проверка наличия пользователя ({})", key);
//        if (!hasText(key)) {
//            ResourceEmptyIdException exception = new ResourceEmptyIdException(ResourceName.USER);
//            log.error(exception.toString());
//            throw exception;
//        }
//        boolean exists = userRepository.existsById(key);
//        log.debug("Результат проверки наличия пользователя ({}) = {}", key, exists);
//        return exists;
//    }

    @Override
    public User findByUsername(String username) {
//        log.debug("Поиск пользователя по username ({})", username);
        System.out.printf("Поиск пользователя по username (%s)%n", username);
        if (!hasText(username)) {
            throw new RuntimeException("Username не может быть пустым!");
        }

        // для данного запроса включён query cache
//        String userId = userRepository.findIdByUsernameLowerCase(username.toLowerCase())
        String userId = findIdByUsernameLowerCase(username.toLowerCase())
//                .orElseThrow(() -> new RuntimeException(ResourceName.USER, username));
                .orElseThrow(() -> new RuntimeException(String.format("%s с ключом %s не найден", ResourceName.USER, username)));

//        User user = userRepository.findById(userId)
        User user = findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.USER, username));
                .orElseThrow(() -> new RuntimeException(String.format("%s с ключом %s не найден", ResourceName.USER, username)));

//        log.debug("Поиск пользователя по username ({}) завершен", username);
        System.out.printf("Поиск пользователя по username (%s) завершен%n", username);
        return user;
    }

    private Optional<User> findById(String userId) {
        return Optional.of(MessageServiceImpl.findFirstByUsernameIgnoreCase(userId));
    }

    private Optional<String> findIdByUsernameLowerCase(String username) {
        return Optional.of(username);
    }

    public static boolean hasText(String str) {
        return str != null && !str.isEmpty();
    }

//    @Override
//    public UsernameAvailability checkUsernameExists(String username) {
//        if (userRepository.existsByUsernameIgnoreCase(username)) {
//            return UsernameAvailability.EXISTS;
//        }
//
//        if (deletedUsernameService.exists(username)) {
//            return UsernameAvailability.RESERVED;
//        }
//
//        return UsernameAvailability.AVAILABLE;
//    }
//
//    @Transactional
//    public UserInfo findOrCreateByEsiaUser(EsiaUserInfo esiaUser) {
//        String snils = esiaUser.getSnils();
//        if (!hasText(snils)) {
//            ResourceIllegalArgumentException exception = new ResourceIllegalArgumentException("СНИЛС пользователя из ЕСИА не может быть пустым!");
//            log.error(exception.toString());
//            throw exception;
//        }
//        if (!individualPersonRepository.existsByUsername(snils) && deletedUsernameService.exists(snils)) {
//            throw new UnigateAuthenticationException("Логин зарезервирован. Введенный логин принадлежит удаленной учетной записи",
//                    UnigateAuthenticationException.UnigateAuthenticationErrorType.RESERVED);
//        }
//
//        log.debug("Поиск пользователя по username ({})", snils);
//        User user = individualPersonRepository.findByUsername(snils);
//        if (user == null) {
//            log.debug("Поиск пользователя по snils ({})", snils);
//            user = individualPersonRepository.findBySnils(snils);
//        }
//        if (user == null) {
//            // create new user with new snils
//            IndividualPerson individualPerson = userMapperDto.mapIndividualPerson(esiaUser);
//            individualPerson.setPassword(passwordEncoder.encode(UserUtils.generatePassword(individualPerson, settingsPasswordService, usersLastPasswordRepository)));
//            individualPerson.setRoles(Collections.singleton(esiaUser.getRole()));
//            user = add(individualPerson);
//            log.debug("Новый пользователь ({}) добавлен", individualPerson);
//        } else {
//            // compare name from esia
//            IndividualPerson individualPerson = (IndividualPerson) user;
//
//            boolean isSnilsUpdated = isSnilsUpdated(individualPerson, esiaUser);
//            boolean isFioUpdated = isFioUpdated(individualPerson, esiaUser);
//            boolean isRoleUpdated = checkAndSetIfRoleUpdated(individualPerson, esiaUser);
//            if (isSnilsUpdated || isFioUpdated || isRoleUpdated) {
//                userRepository.saveAndFlush(user);
//                log.debug("Пользователь ({}) обновлен", user);
//            }
//        }
//
//        return userMapperDto.mapUserInfo(user);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public String findLastUserSessionRoleId(String snils) {
//        log.debug("Поиск последнего способа входа пользователя по snils ({})", snils);
//        if (!hasText(snils)) {
//            ResourceIllegalArgumentException exception = new ResourceIllegalArgumentException("Snils не может быть пустым!");
//            log.error(exception.toString());
//            throw exception;
//        }
//
//        return individualPersonRepository.findLastSessionRoleIdBySnils(snils);
//    }
//
//    @Override
//    @Transactional
//    public void setLastUserSessionRoleId(String snils, String roleId) {
//        log.debug("Установка последнего способа входа ({}) пользователю по snils ({})", roleId, snils);
//        if (!hasText(snils)) {
//            ResourceIllegalArgumentException exception = new ResourceIllegalArgumentException("Snils не может быть пустым!");
//            log.error(exception.toString());
//            throw exception;
//        }
//
//        if (!individualPersonRepository.existsBySnils(snils)) {
//            ResourceIllegalArgumentException exception = new ResourceIllegalArgumentException(String.format("Пользователь по snils '%s' не найден!", snils));
//            log.error(exception.toString());
//            throw exception;
//        }
//
//        individualPersonRepository.updateLastSessionRoleIdBySnils(jdbcTemplate, snils, roleId);
//    }
//
//    private boolean isSnilsUpdated(IndividualPerson individualPerson, EsiaUserInfo esiaUser) {
//        boolean result = false;
//        if (!Objects.equals(individualPerson.getSnils(), esiaUser.getSnils())) {
//            individualPerson.setSnils(esiaUser.getSnils());
//            individualPerson.setPassword(passwordEncoder.encode(UserUtils.generatePassword(individualPerson, settingsPasswordService, usersLastPasswordRepository)));
//            individualPerson.setChangePasswordDate(ZonedDateTime.now());
//            result = true;
//        }
//
//        return result;
//    }
//
//    private boolean isFioUpdated(IndividualPerson individualPerson, EsiaUserInfo esiaUser) {
//        boolean result = false;
//        if (!Objects.equals(individualPerson.getFirstName(), esiaUser.getFirstName())) {
//            individualPerson.setFirstName(esiaUser.getFirstName());
//            result = true;
//        }
//        if (!Objects.equals(individualPerson.getPatronymic(), esiaUser.getPatronymic())) {
//            individualPerson.setPatronymic(esiaUser.getPatronymic());
//            result = true;
//        }
//        if (!Objects.equals(individualPerson.getLastName(), esiaUser.getLastName())) {
//            individualPerson.setLastName(esiaUser.getLastName());
//            result = true;
//        }
//
//        return result;
//    }
//
//    private boolean checkAndSetIfRoleUpdated(IndividualPerson individualPerson, EsiaUserInfo esiaUser) {
//        boolean result;
//        Set<String> userRoles = individualPerson.getRoles();
//        String esiaUserUnigateRoleId = esiaUser.getRole();
//        Set<String> esiaRoleIdsToDelete = Stream.of(RoleConstants.ESIA_USER, RoleConstants.ESIA_PRIVILEGED)
//                .map(RoleConstants::getId)
//                .filter(id -> !Objects.equals(id, esiaUserUnigateRoleId))
//                .collect(Collectors.toSet());
//
//        // У пользователя должна остаться только одна роль от ЕСИА
//        if (userRoles.contains(esiaUserUnigateRoleId)
//                && !CollectionUtils.containsAny(userRoles, esiaRoleIdsToDelete)) {
//            result = false;
//        } else {
//            boolean removed = userRoles.removeIf(esiaRoleIdsToDelete::contains);
//            boolean added = userRoles.add(esiaUserUnigateRoleId);
//            result = removed || added;
//
//        }
//
//        return result;
//    }
//
//    @Override
//    public User findByUsernameFull(String username) {
//        log.debug("Поиск пользователя по username с полной прогрузкой атрибутов ({})", username);
//        User user = findByUsername(username);
//
//        log.debug("Поиск пользователя по username ({}) с полной прогрузкой атрибутов завершен", username);
//        return user;
//    }
//
//    @Transactional
//    @Override
//    public Boolean lockUnlockUser(String userId, String lockComment) {
//        User user = findByKey(userId);
//        if (Boolean.TRUE.equals(user.getAccountNonLocked())) {
//            log.debug("{} пытается заблокировать пользователя ({})", LoggingUtils.getCurrentUserInfo(), userId);
//            lockUser(userId, lockComment);
//            log.debug("{} заблокировал пользователя ({})", LoggingUtils.getCurrentUserInfo(), userId);
//            return true;
//        } else {
//            log.debug("{} пытается разблокировать пользователя ({})", LoggingUtils.getCurrentUserInfo(), userId);
//            unlockUser(userId);
//            log.debug("{} разблокировал пользователя ({})", LoggingUtils.getCurrentUserInfo(), userId);
//            return false;
//        }
//    }
//
//    @Transactional
//    @Override
//    public void groupLockUnlockIndividualPerson(List<String> idsForLock, List<String> idsForUnlock, String lockComment) {
//        if (!CollectionUtils.isEmpty(idsForLock)) {
//            log.debug("{} пытается заблокировать пользователей ({})", LoggingUtils.getCurrentUserInfo(), idsForLock);
//            idsForLock.forEach(idForLock -> lockUser(idForLock, lockComment));
//            log.debug("{} заблокировал пользователей ({})", LoggingUtils.getCurrentUserInfo(), idsForLock);
//        }
//        if (!CollectionUtils.isEmpty(idsForUnlock)) {
//            log.debug("{} пытается разблокировать пользователей ({})", LoggingUtils.getCurrentUserInfo(), idsForUnlock);
//            idsForUnlock.forEach(this::unlockUser);
//            log.debug("{} разблокировал пользователей ({})", LoggingUtils.getCurrentUserInfo(), idsForUnlock);
//        }
//    }
//
//    @Transactional
//    @Override
//    public void groupLockUnlockSysUsers(List<String> userNamesForLock, List<String> userNamesForUnlock, String lockComment) {
//        if (!CollectionUtils.isEmpty(userNamesForLock)) {
//            log.debug("{} пытается заблокировать системных пользователей {}", LoggingUtils.getCurrentUserInfo(), userNamesForLock);
//            userNamesForLock.forEach(userNameForLock -> {
//                String idForLock = userRepository.findIdByUsernameLowerCase(userNameForLock.toLowerCase()).orElseThrow(() ->
//                        new ResourceNotFoundException(String.format("ОШИБКА: пользователь с username %s не найден", userNameForLock)));
//                lockUser(idForLock, lockComment);
//            });
//            log.debug("{} заблокировал системных пользователей {}", LoggingUtils.getCurrentUserInfo(), userNamesForLock);
//        }
//        if (!CollectionUtils.isEmpty(userNamesForUnlock)) {
//            log.debug("{} пытается разблокировать системных пользователей {}", LoggingUtils.getCurrentUserInfo(), userNamesForUnlock);
//            userNamesForUnlock.forEach(userNameForUnlock -> {
//                String idForUnlock = userRepository.findIdByUsernameLowerCase(userNameForUnlock.toLowerCase()).orElseThrow(() ->
//                        new ResourceNotFoundException(String.format("ОШИБКА: пользователь с username %s не найден", userNameForUnlock)));
//                unlockUser(idForUnlock);
//            });
//            log.debug("{} разблокировал системных пользователей {}", LoggingUtils.getCurrentUserInfo(), userNamesForUnlock);
//        }
//    }
//
//    private void lockUser(String idForLock, String lockComment) {
//        if (!StringUtils.hasText(idForLock)) {
//            throw new ResourceEmptyIdException(ResourceName.USER);
//        }
//        if (UserConstants.getById(idForLock) != null) {
//            throw new ResourceIllegalArgumentException(String.format("ОШИБКА: запрещено блокировать служебного пользователя с идентификатором %s", idForLock));
//        }
//        User user = findByKey(idForLock);
//        if (Objects.equals(user.getUsername(), SecurityUtils.getCurrentUserUsername())) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: невозможно заблокировать текущего пользователя");
//        }
//        userLockService.lock(user, lockComment);
//    }
//
//    private void unlockUser(String idForUnlock) {
//        if (!StringUtils.hasText(idForUnlock)) {
//            throw new ResourceEmptyIdException(ResourceName.USER);
//        }
//        User user = findByKey(idForUnlock);
//        if (user instanceof IndividualPerson && Boolean.TRUE.equals(!user.getAccountNonLocked())) {
//            LocalDate validTo = ((IndividualPerson) user).getValidTo();
//            if (validTo != null && LocalDate.now().isAfter(validTo)) {
//                throw new ResourceIllegalArgumentException(String.format("ОШИБКА: невозможно разблокировать пользователя с идентификатором %s", idForUnlock));
//            }
//        }
//        userLockService.unlock(user);
//    }
//
//    @Override
//    public void clearPermissions(String userId) {
//        log.debug("Сброс привилегий пользователя {}", userId);
//        if (!hasText(userId)) {
//            ResourceEmptyIdException exception = new ResourceEmptyIdException(ResourceName.USER);
//            log.error(exception.toString());
//            throw exception;
//        }
//
//        User user = findByKey(userId);
//        user.getPermissions().clear();
//        userRepository.save(user);
//        log.debug("У пользователя {} сброшены привилегии", userId);
//    }
//
//    @Override
//    @Transactional
//    public User update(User user) {
//        try {
//            beforeUpdate(user);
//            user = userRepository.save(user);
//
//        } catch (ResourceIllegalArgumentException | ResourceNotFoundException exception) {
//            log.error(exception.toString());
//            throw exception;
//        }
//        return user;
//    }
//
//    @Override
//    public void beforeUpdate(User user) {
//        if (user == null) {
//            throw new ResourceIsNullException(ResourceName.USER);
//        }
//        if (!hasText(user.getId())) {
//            throw new ResourceEmptyIdException(ResourceName.USER);
//        }
//        User existedUser = userRepository.findById(user.getId()).orElse(null);
//
//        if (existedUser == null) {
//            throw new ResourceNotFoundException(ResourceName.USER, user.getId());
//        }
//
//        if (user instanceof IndividualPerson) {
//            userValidator.validateEmail(((IndividualPerson) user).getEmail());
//        }
//
//        User currentUser = SecurityUtils.getCurrentUser();
//
//        user.setCreateDate(existedUser.getCreateDate());
//
//        // в случае, если при редактировании пользователя пароль не менялся, то с фронта летит null, которое на бэке
//        // в таком случае оставляем старый пароль
//        if (user.getPassword() == null) {
//            user.setPassword(existedUser.getPassword());
//            user.setChangePasswordDate(existedUser.getChangePasswordDate() != null ? existedUser.getChangePasswordDate() : ZonedDateTime.now());
//        } else {
//            userValidator.validatePassword(user.getPassword(), existedUser);
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            user.setChangePasswordDate(ZonedDateTime.now());
//            List<UsersLastPassword> lastPasswords = usersLastPasswordRepository.findAllByUserId(user.getId());
//            boolean addLastPassword = lastPasswords.stream()
//                    .noneMatch(p -> passwordEncoder.matches(existedUser.getPassword(), p.getPassword()));
//            if (addLastPassword) {
//                usersLastPasswordRepository.save(new UsersLastPassword(user.getId(), existedUser.getPassword()));
//            }
//        }
//
//        if (!hasPermission(currentUser, SecurityObjectConstants.USER_ROLE.getId(), ActionConstants.WRITE.getId())) {
//            user.getRoles().clear();
//            user.getRoles().addAll(existedUser.getRoles());
//        }
//
//        if (!existedUser.getUsername().equals(user.getUsername())) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: изменение наименования учётной записи недопустимо!");
//        }
//
//        userValidator.validateRequiredAndLengthConditions(user);
//
//        if (!hasPermission(currentUser, SecurityObjectConstants.USER_PERMISSION.getId(), ActionConstants.WRITE.getId())) {
//            user.setPermissions(existedUser.getPermissions());
//        } else {
//            existedUser.getPermissions().clear();
//            existedUser.getPermissions().addAll(user.getPermissions());
//            user.setPermissions(existedUser.getPermissions());
//        }
//        user.setPermissions(user.getPermissions().stream().distinct().collect(Collectors.toList()));
//
//        restoreOrValidatePhone(user, existedUser);
//        setOrRestorePassword(user, existedUser);
//        setOrRestoreFio(user, existedUser);
//        setOrRestoreSnils(user, existedUser);
//    }
//
//
//    private void setOrRestorePassword(User user, User existedUser) {
//        if (!(user instanceof IndividualPerson)) {
//            return;
//        }
//        if (existedUser instanceof IndividualPerson
//                && ((IndividualPerson) existedUser).isNameEqualsSnils()) {
//            user.setPassword(existedUser.getPassword());
//        }
//    }
//
//    private void setOrRestoreFio(User user, User existedUser) {
//        if (!(user instanceof IndividualPerson)) {
//            return;
//        }
//        if (existedUser instanceof IndividualPerson
//                && ((IndividualPerson) existedUser).isNameEqualsSnils()) {
//            ((IndividualPerson) user).setFirstName(((IndividualPerson) existedUser).getFirstName());
//            ((IndividualPerson) user).setLastName(((IndividualPerson) existedUser).getLastName());
//            ((IndividualPerson) user).setPatronymic(((IndividualPerson) existedUser).getPatronymic());
//        }
//    }
//
//    private void setOrRestoreSnils(User user, User existedUser) {
//        if (!(user instanceof IndividualPerson)) {
//            return;
//        }
//        if (existedUser instanceof IndividualPerson) {
//            ((IndividualPerson) user).setSnils(((IndividualPerson) existedUser).getSnils());
//        } else {
//            ((IndividualPerson) user).setSnils(null);
//        }
//    }
//
//    private void restoreOrValidatePhone(User user, User existedUser) {
//        if (!(user instanceof IndividualPerson)) {
//            return;
//        }
//        if (existedUser instanceof IndividualPerson
//                && ((IndividualPerson) existedUser).isNameEqualsSnils()) {
//            ((IndividualPerson) user).setPhone(((IndividualPerson) existedUser).getPhone());
//        }
//    }
//
//    @Override
//    public User updateSysUserCard(SysUser sysUser) {
//        User user;
//
//        try {
//            if (sysUser == null) {
//                throw new ResourceIsNullException(ResourceName.USER);
//            }
//            if (!hasText(sysUser.getId())) {
//                throw new ResourceEmptyIdException(ResourceName.USER);
//            }
//            User existedUser = userRepository.findById(sysUser.getId()).orElse(null);
//            if (existedUser == null) {
//                throw new ResourceNotFoundException(ResourceName.USER, sysUser.getId());
//            }
//
//            sysUser.setCreateDate(existedUser.getCreateDate());
//
//            // в случае, если при редактировании пользователя пароль не менялся, то с фронта летит null, которое на бэке
//            // в таком случае оставляем старый пароль
//            if (sysUser.getPassword() == null) {
//                sysUser.setPassword(existedUser.getPassword());
//                sysUser.setChangePasswordDate(existedUser.getChangePasswordDate() != null ? existedUser.getChangePasswordDate() : ZonedDateTime.now());
//            } else {
//                userValidator.validateSysUserPassword(sysUser.getPassword());
//                sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
//                sysUser.setChangePasswordDate(ZonedDateTime.now());
//                List<UsersLastPassword> lastPasswords = usersLastPasswordRepository.findAllByUserId(sysUser.getId());
//                boolean addLastPassword = lastPasswords.stream()
//                        .noneMatch(p -> passwordEncoder.matches(existedUser.getPassword(), p.getPassword()));
//                if (addLastPassword) {
//                    usersLastPasswordRepository.save(new UsersLastPassword(sysUser.getId(), existedUser.getPassword()));
//                }
//            }
//
//            if (!existedUser.getUsername().equals(sysUser.getUsername())) {
//                throw new ResourceIllegalArgumentException("ОШИБКА: изменение наименования учётной записи недопустимо!");
//            }
//
//            userValidator.validateRequiredAndLengthConditions(sysUser);
//
//            user = userRepository.save(sysUser);
//        } catch (ResourceIllegalArgumentException | ResourceNotFoundException exception) {
//            log.error(exception.toString());
//            throw exception;
//        }
//        return user;
//    }
//
    @Override
    public Map<String, Set<String>> getPermissions(User user) {
//        log.debug("Получение привилегий пользователя ({})", user.getId());
        System.out.printf("Получение привилегий пользователя (%s)%n", user.getId());
        Map<String, Set<String>> permissions = new HashMap<>();

        Stream.concat(
                user.getRoles().stream()
                        .map(this::loadRole)
                        .filter(Objects::nonNull)
                        .filter(role -> role.getDeleteDate() == null)
                        .map(Role::getPermissions)
                        .flatMap(List::stream),
                user.getPermissions().stream()
                        .filter(permission -> !permission.getProhibitive() && permission.isTemporaryPermissionActive())
        ).forEach(permission -> {
            String securityObjectId = permission.getId().getSecurityObjectId();
            permissions.computeIfAbsent(securityObjectId, key -> new HashSet<>());
            permissions.get(securityObjectId).add(permission.getId().getAction());
        });

        List<GroupPermissionEntity> permissionsFromGroups
//                = groupsJdbcRepository.getPermissionsByUserGroups(user.getId());
                = getPermissionsByUserGroups(user.getId());

        permissionsFromGroups.forEach(p -> {
            permissions.computeIfAbsent(p.getSecurityObjectId(), key -> new HashSet<>());
            permissions.get(p.getSecurityObjectId()).add(p.getActionId());
        });

        user.getPermissions().stream()
                .filter(UserPermission::getProhibitive)
                .filter(permission -> permissions.containsKey(permission.getId().getSecurityObjectId()))
                .forEach(permission -> permissions.get(permission.getId().getSecurityObjectId()).remove(permission.getId().getAction()));

        securityObjectService.findAnnulledIdByIds(permissions.keySet()).forEach(permissions::remove);

        Set<String> actionIds = permissions.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        if (!actionIds.isEmpty()) {
//            Set<String> annulledActions = actionRepository.findAnnulledIdByIds(actionIds);
            Set<String> annulledActions = mockRepositoryFindAnnulledIdByIds(actionIds);
            permissions.values().forEach(actions -> actions.removeAll(annulledActions));
        }

        Set<String> emptySecObj = permissions.entrySet().stream()
                .filter(e -> e.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        emptySecObj.forEach(permissions::remove);

        return permissions;
    }

    private Set<String> mockRepositoryFindAnnulledIdByIds(Set<String> actionIds) {
        return actionIds;
    }

    private List<GroupPermissionEntity> getPermissionsByUserGroups(String id) {
        return Arrays.asList(new GroupPermissionEntity("securityObjectId", "actionId"));
    }

    @Override
    public boolean hasPermission(User user, String resource, String action) {
        return getPermissions(user).getOrDefault(resource, Collections.emptySet()).contains(action);
    }

//    @Override
//    public String generateCn(User user) {
//        return UUID.randomUUID().toString();
//    }
//
//    @Transactional
//    @Override
//    public void assignPermissions(String srcUserId, List<RecipientTemporaryPermissionInfo> recipientTempPermInfo, List<UserPermissionShortInfo> permissionIds) {
//        if (CollectionUtils.isEmpty(recipientTempPermInfo)) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: не переданы id получателей прав");
//        }
//
//        if (CollectionUtils.isEmpty(permissionIds)) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: не переданы id передаваемых привилегий");
//        }
//
//        userValidator.checkTemporaryPermissionCorrectness(recipientTempPermInfo);
//
//        User srcUser = findByKey(srcUserId);
//
//        List<String> transferPermissionFullId = permissionIds.stream().map(perm -> perm.getSecurityObjectId() + "::" + perm.getActionId()).collect(Collectors.toList());
//        Map<String, Set<String>> transferPermissions = new HashMap<>();
//        getPermissions(srcUser).forEach((securityObjectId, actions) -> actions.forEach(action -> {
//            String userPermFullName = securityObjectId + "::" + action;
//            if (transferPermissionFullId.contains(userPermFullName)) {
//                if (!transferPermissions.containsKey(securityObjectId)) {
//                    transferPermissions.put(securityObjectId, new HashSet<>());
//                }
//                transferPermissions.get(securityObjectId).add(action);
//            }
//        }));
//        int foundPermissionsSize = transferPermissions.values().stream().mapToInt(Set::size).sum();
//
//        if (foundPermissionsSize < permissionIds.size()) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: пользователь пытается передать не принадлежащие ему права");
//        }
//
//        recipientTempPermInfo.forEach(recipientInfo -> {
//            User recipient = findByKey(recipientInfo.getId());
//            Map<String, Set<String>> recipientPermissions = getPermissions(recipient);
//
//            Set<UserPermission> permissionsToAdd = transferPermissions.entrySet().stream()
//                    .flatMap(e -> e.getValue().stream()
//                            .filter(v -> recipientPermissions.get(e.getKey()) == null || !recipientPermissions.get(e.getKey()).contains(v))
//                            .map(v -> new UserPermission(recipient, e.getKey(), v, false, recipientInfo.getDateBegin(),
//                                    recipientInfo.getDateEnd(), recipientInfo.getComment()))
//                    ).collect(Collectors.toSet());
//
//            if (!permissionsToAdd.isEmpty()) {
//                Map<String, Set<String>> addPermissions = permissionsToAdd.stream()
//                        .collect(Collectors.groupingBy(
//                                up -> up.getId().getSecurityObjectId(),
//                                Collectors.mapping(up -> up.getId().getAction(), Collectors.toSet())
//                        ));
//                recipient.getPermissions().removeIf(permission ->
//                        addPermissions.get(permission.getId().getSecurityObjectId()) != null
//                                && addPermissions.get(permission.getId().getSecurityObjectId()).contains(permission.getId().getAction())
//                );
//                recipient.getPermissions().addAll(permissionsToAdd);
//                recipient.setLastModified(ZonedDateTime.now());
//                userRepository.save(recipient);
//            }
//
//        });
//    }

    private Role loadRole(String roleId) {
        try {
            return roleService.findByKey(roleId);
        } catch (RuntimeException e) {
//            log.warn("Ошибка при прогрузке роли ({})", roleId);
            System.out.printf("Ошибка при прогрузке роли (%s)%n", roleId);
            return null;
        }
    }

    @Override
    public String getProtocolObjectType() {
        return OBJECT_TYPE;
    }
//
//    @Transactional
//    @Override
//    public void delete(List<String> ids) {
//        userValidator.validateBeforeDelete(ids);
//
//        List<User> users = userRepository.findAllById(ids);
//        if (users.stream().anyMatch(u -> u.getDeleteDate() == null)) {
//            throw new ResourceIllegalArgumentException("Ошибка: удалить можно только аннулированные записи!");
//        }
//        var usernames = users.stream().map(User::getUsername).collect(Collectors.toList());
//        users.forEach(user -> {
//            removeAllLinksFromUser(user);
//            remove(user.getId());
//        });
//        deletedUsernameService.addByUsernames(usernames);
//    }
//
//    @Transactional
//    @Override
//    public void deleteLogical(List<String> ids) {
//        userValidator.validateBeforeDelete(ids);
//
//        userRepository.findUserIdAndDeleteDate(ids).forEach(u -> {
//            if (u.getDeleteDate() == null) {
//                userLogicalDeleteService.delete(u.getUserId());
//            } else {
//                userLogicalDeleteService.restore(u.getUserId());
//            }
//        });
//
//    }
//
//    @Transactional
//    @Override
//    public void deleteSysUsers(List<String> userNames) {
//        if (CollectionUtils.isEmpty(userNames)) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: не указаны логины системных пользователей, подлежащих удалению");
//        }
//        delete(userRepository.findAllIdByUsernameInLowerCase(userNames.stream().map(String::toLowerCase).collect(Collectors.toList())));
//    }
//
//    @Transactional
//    @Override
//    public void deleteLogicalSysUsers(List<String> userNames) {
//        if (CollectionUtils.isEmpty(userNames)) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: не указаны логины системных пользователей, подлежащих удалению");
//        }
//        deleteLogical(userRepository.findAllIdByUsernameInLowerCase(userNames.stream().map(String::toLowerCase).collect(Collectors.toList())));
//    }
//
//    @Override
//    public Page<ShortUserListInfo> findByFullUserNameShort(String fullUserName, Pageable pageable) {
//        return userRepository.findByFullUserNameShort(Objects.isNull(fullUserName) ? "" : "%" + fullUserName + "%", pageable)
//                .map(
//                        shortUserListInfoProjection -> new ShortUserListInfo(shortUserListInfoProjection.getUserId(),
//                                shortUserListInfoProjection.getFullName())
//                );
//    }
//
//    @Override
//    public long count() {
//        return userRepository.count();
//    }
//
//    private void removeAllLinksFromUser(User user) {
//        user.getPermissions().clear();
//        user.getRoles().clear();
//        groupsJdbcRepository.deleteGroupsUsersRefs(List.of(user.getId()));
//        if (user instanceof IndividualPerson) {
//            attributeValueService.removeAllUserAttributeValues(user.getId());
//        }
//        userRepository.save(user);
//
//        usersLastPasswordRepository.deleteByUserId(user.getId());
//        sessionRepository.deleteByUserId(user.getId());
//    }
//
//    /**
//     * Разблокировка пользователей, заблокированных после превышения неудачных попыток авторизации
//     */
//    @Scheduled(cron = "${ru.kamatech.unigate.security.unlock_users.cron}")
//    public void unlockLockedByLoginUsers() {
//        log.info("Запуск процесса автоматической разблокировки пользователей, заблокированных после превышения неудачных попыток авторизации");
//        SettingPassword attemptsBeforeLock = settingsPasswordService.getPasswordAttemptsBeforeLock();
//
//        if (attemptsBeforeLock != null && attemptsBeforeLock.isEnable()) {
//            ZonedDateTime unlockDate = ZonedDateTime.now();
//
//            if (attemptsBeforeLock.getTime() != null) {
//                String[] time = attemptsBeforeLock.getTime().split(":");
//                if (time.length == 3) {
//                    unlockDate = unlockDate.minusDays(Long.parseLong(time[0]));
//                    unlockDate = unlockDate.minusHours(Long.parseLong(time[1]));
//                    unlockDate = unlockDate.minusMinutes(Long.parseLong(time[2]));
//                } else {
//                    log.error("Недопустимый формат для срока блокировки пользователя после указанного числа неудачных попыток входа");
//                }
//            }
//
//            List<User> lockedUsers = userRepository.findByAccountNonLockedFalseAndBlockReasonAndLastModifiedLessThanEqual(BlockReason.LOGIN, unlockDate);
//
//            log.info("Найдено заблокированных учетных записей: {}", lockedUsers.size());
//
//            if (!lockedUsers.isEmpty()) {
//                lockedUsers.forEach(user -> {
//                    user.setBlockReason(null);
//                    user.setBlockComment(null);
//                    user.setAccountNonLocked(true);
//                    userRepository.save(user);
//                });
//
//                log.info("Разблокировано учетных записей: {}", lockedUsers.size());
//            }
//        }
//    }
//
//    /**
//     * Блокирование недействительных пользователей
//     */
//    @Scheduled(cron = "${ru.kamatech.unigate.security.lock_invalid_users.cron}")
//    public void lockInvalidUsers() {
//        log.info("Запуск процесса автоматической блокировки истекших временных учетных записей");
//        List<User> usersToLock = new ArrayList<>(individualPersonRepository.findByValidToBeforeAndAccountNonLockedTrue(LocalDate.now()));
//
//        log.info("Найдено истекших временных учетных записей: {}", usersToLock.size());
//
//        if (!usersToLock.isEmpty()) {
//            usersToLock.forEach(user -> {
//                user.setAccountNonLocked(false);
//                user.setBlockReason(BlockReason.EXPIRED);
//                user.setBlockComment(null);
//                userRepository.save(user);
//            });
//
//            log.info("Заблокировано истекших временных учетных записей: {}", usersToLock.size());
//        }
//
//    }
//
//    /**
//     * Блокировка долго отсутствующих пользователей
//     */
//    @Scheduled(cron = "${ru.kamatech.unigate.security.lock_absent_users.cron}")
//    public void lockAbsentUsers() {
//        SettingAccessControl blockNotActiveUser = settingsAccessControlService.getBlockNotActiveUser();
//        if (Boolean.TRUE.equals(blockNotActiveUser.getEnable()) && blockNotActiveUser.getValue() != null) {
//
//            log.info("Запуск процесса автоматической блокировки неактивных пользователей");
//
//            ZonedDateTime blockBefore = ZonedDateTime.now().minusDays(blockNotActiveUser.getValue());
//            List<String> usersToLock = userRepository.findUsernameByLastAuthDateBefore(blockBefore);
//
//            Set<String> activeSessions = sessionRepository.findUsernamesByUsernames(usersToLock);
//
//            usersToLock.removeAll(activeSessions);
//
//            log.info("Найдено неактивных пользователей: {}", usersToLock.size());
//
//            if (!usersToLock.isEmpty()) {
//                usersToLock.forEach(username -> {
//                    User user = userRepository.findFirstByUsernameIgnoreCase(username.toUpperCase());
//                    user.setAccountNonLocked(false);
//                    user.setBlockReason(BlockReason.ABSENT);
//                    user.setBlockComment(null);
//                    userRepository.save(user);
//                });
//
//                log.info("Заблокировано неактивных пользователей: {}", usersToLock.size());
//            }
//
//        }
//    }
}
