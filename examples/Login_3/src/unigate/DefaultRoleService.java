package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

import static org.springframework.util.StringUtils.hasText;

@Primary
@Service
//@Slf4j
@RequiredArgsConstructor
public class DefaultRoleService implements RoleService {

    public static final String OBJECT_TYPE = "role";

//    private final RoleRepository roleRepository;
//    private final RoleValidator roleValidator;
//    private final UserRepository userRepository;
//    private final RoleLogicalDeleteService roleLogicalDeleteService;
//
//    @Override
//    @Transactional
//    public Role add(Role role) {
//        log.debug("Создание роли {}", role);
//        roleValidator.validateBeforeAdd(role);
//        Role createdRole = roleRepository.save(role);
//        log.debug("Роль успешно создан. Результат: {}", createdRole);
//        return createdRole;
//    }
//
//    @Override
//    @Transactional
//    public Role update(Role role) {
//        log.debug("Обновление роли {}", role);
//        roleValidator.validateBeforeUpdate(role);
//        Role updatedRole = roleRepository.save(role);
//        log.debug("Роль успешно обновлена. Результат: {}", updatedRole);
//        return updatedRole;
//    }
//
//    @Override
//    @Transactional
//    public void remove(String key) {
//        log.debug("Удаление роли ({})", key);
//        remove(findByKey(key), false);
//        log.debug("Роль ({}) успешно удалена", key);
//    }
//
//    @Override
//    public DeleteInfo<String> remove(String key, boolean force) {
//        log.trace(String.format("%s роли (%s)", force ? "Принудительное удаление" : "Удаление", key));
//        DeleteInfo<String> result = new DeleteInfo<>();
//        result.setId(key);
//        try {
//            Role role = findByKey(key);
//            if (!force) {
//                result.setLinks(roleValidator.getLinks(role));
//            }
//            remove(role, force);
//            result.setDeleted(true);
//        } catch (Exception e) {
//            result.setDeleted(false);
//            result.setMessage(e.getMessage());
//        }
//        log.trace(String.format("Роль (%s) %s удалена", key, result.isDeleted() ? "успешно" : "не"));
//        return result;
//    }
//
//    private void remove(Role role, boolean force) {
//        roleValidator.validateBeforeRemove(role, force);
//
//        deleteLinkedUsers(Collections.singleton(role.getId()));
//
//        roleRepository.delete(role);
//    }
//
//    @Override
//    public List<Role> findAll() {
//        log.debug("Получение списка ролей");
//        List<Role> roles = roleRepository.findAll();
//        log.debug("Список ролей получен");
//        return roles;
//    }

    @Override
    public Role findByKey(String key) {
//        log.trace("Получение информации о роли ({})", key);
        System.out.printf("Получение информации о роли (%s)%n", key);
        Role role;
        try {
            if (!hasText(key)) {
                throw new RuntimeException(ResourceName.ROLE);
            }
//            role = roleRepository.findById(key)
            role = findById(key)
                    .orElseThrow(() -> new RuntimeException(String.format("%s с ключом %s не найден", ResourceName.ROLE, key)));
        } catch (RuntimeException exception) {
//            log.error(exception.toString());
            System.out.printf(exception.toString());
            throw exception;
        }
//        log.trace("Информация о роли ({}) получена", key);
        System.out.printf("Информация о роли (%s) получена%n", key);
        return role;
    }

    private Optional<Role> findById(String key) {
        Optional<Role> result = Optional.empty();
        if (new Random().nextBoolean()) {
            Role role = new Role(
                    "id",
                    "name",
                    "description",
                    false,
                    ZonedDateTime.now(),
                    new HashSet<>(Arrays.asList("type 1", "type 2")),
                    new ArrayList<>(),
                    99999,
                    ZonedDateTime.now()
            );
            role.setPermissions(Arrays.asList(
                    new RolePermission(
                            role,
                            "securityObjectId",
                            "action",
                            false
                    ),
                    new RolePermission(
                            role,
                            "securityObjectId 2",
                            "action 2",
                            false
                    )
            ));

            result = Optional.of(role);
        }
        return result;
    }

//    @Override
//    public List<Role> findByKeys(Collection<String> keys) {
//        log.debug("Получение информации о ролях ({})", keys);
//        if (!keys.stream().allMatch(StringUtils::hasText)) {
//            throw new ResourceEmptyIdException(ResourceName.ROLE);
//        }
//        List<Role> roles = roleRepository.findAllById(keys);
//        keys.stream()
//                .filter(k -> roles.stream().noneMatch(r -> k.equals(r.getId())))
//                .forEach(k -> {
//                    throw new ResourceNotFoundException(ResourceName.ROLE, k);
//                });
//        log.debug("Информация о ролях ({}) получена", keys);
//        return roles;
//    }
//
//    @Override
//    public boolean exists(String key) {
//        log.debug("Проверка наличия роли ({})", key);
//        if (!hasText(key)) {
//            ResourceEmptyIdException exception = new ResourceEmptyIdException(ResourceName.ROLE);
//            log.error(exception.toString());
//            throw exception;
//        }
//        boolean exists = roleRepository.existsById(key);
//        log.debug("Результат проверки наличия роли ({}) = {}", key, exists);
//        return exists;
//    }
//
//    @Transactional
//    @Override
//    public void deleteLogicalRoles(List<String> ids) {
//        roleValidator.validateBeforeDelete(ids);
//
//        roleRepository.findRoleIdAndDeleteDate(ids).forEach(role -> {
//            if (role.getDeleteDate() == null) {
//                roleLogicalDeleteService.delete(role.getRoleId());
//            } else {
//                roleLogicalDeleteService.restore(role.getRoleId());
//            }
//        });
//
//    }
//
//    @Override
//    @Transactional
//    public void assignPermissions(String srcRoleId, List<RecipientRolePermissionInfo> recipients, List<RolePermissionShortInfo> rolePermissions) {
//        if (CollectionUtils.isEmpty(recipients)) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: не переданы идентификаторы получателей прав");
//        }
//
//        if (CollectionUtils.isEmpty(rolePermissions)) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: не переданы идентификаторы передаваемых привилегий");
//        }
//
//        Role scrRole = findByKey(srcRoleId);
//
//        List<String> transferPermissionFullId = rolePermissions.stream().map(perm -> perm.getSecurityObjectId() + "::" + perm.getActionId()).collect(Collectors.toList());
//        Map<String, Set<String>> transferPermissions = new HashMap<>();
//        scrRole.getPermissions().forEach(rp -> {
//            String securityObjectId = rp.getId().getSecurityObjectId();
//            String action = rp.getId().getAction();
//            String rolePermFullName = securityObjectId + "::" + action;
//            if (transferPermissionFullId.contains(rolePermFullName)) {
//                if (!transferPermissions.containsKey(securityObjectId)) {
//                    transferPermissions.put(securityObjectId, new HashSet<>());
//                }
//                transferPermissions.get(securityObjectId).add(action);
//            }
//        });
//        int foundPermissionsSize = transferPermissions.values().stream().mapToInt(Set::size).sum();
//
//        if (foundPermissionsSize < rolePermissions.size()) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: пользователь пытается передать не принадлежащие роли права");
//        }
//
//        recipients.forEach(recipientInfo -> {
//            Role recipient = findByKey(recipientInfo.getId());
//            Map<String, Set<String>> recipientPermissions = new HashMap<>();
//            recipient.getPermissions().forEach(rp -> {
//                String securityObjectId = rp.getId().getSecurityObjectId();
//                String action = rp.getId().getAction();
//                if (!recipientPermissions.containsKey(securityObjectId)) {
//                    recipientPermissions.put(securityObjectId, new HashSet<>());
//                }
//                recipientPermissions.get(securityObjectId).add(action);
//            });
//
//            Set<RolePermission> permissionsToAdd = transferPermissions.entrySet().stream()
//                    .flatMap(e -> e.getValue().stream()
//                            .filter(v -> recipientPermissions.get(e.getKey()) == null || !recipientPermissions.get(e.getKey()).contains(v))
//                            .map(v -> new RolePermission(recipient, e.getKey(), v, false))
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
//                roleRepository.save(recipient);
//            }
//
//        });
//    }


    @Override
    public String getProtocolObjectType() {
        return OBJECT_TYPE;
    }

//    private List<User> getLinkedUsers(Set<String> ids) {
//        if (CollectionUtils.isEmpty(ids)) return Collections.emptyList();
//        return userRepository.findAllByRolesIn(ids);
//    }
//
//    private void deleteLinkedUsers(Set<String> ids) {
//        getLinkedUsers(ids)
//                .forEach(user -> {
//                    user.getRoles().removeAll(ids);
//                    userRepository.save(user);
//                });
//    }
}
