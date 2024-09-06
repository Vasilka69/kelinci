package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static src.unigate.PageableUtils.POSTGRES_MAX_PARAMS;

@Primary
@Service
//@Slf4j
@RequiredArgsConstructor
public class DefaultSecurityObjectService implements SecurityObjectService {
    public static final String OBJECT_TYPE = "security_object";
//    private static final String ERROR_NULL_FILTER = "ОШИБКА: фильтр обязателен";
//
//    private final SecurityObjectRepository securityObjectRepository;
//    private final SecurityObjectValidator securityObjectValidator;
//    private final SecurityObjectMapperDto securityObjectMapperDto;
//    private final SecurityObjectLogicalDeleteService securityObjectLogicalDeleteService;
//    private final RoleRepository roleRepository;
//    private final UserRepository userRepository;
//    private final ProxyRuleRepository proxyRuleRepository;
//
//    @Override
//    public SecurityObject add(SecurityObject securityObject) {
//        log.trace("Создание объекта безопасности {}", securityObject);
//        securityObjectValidator.validateBeforeAdd(securityObject);
//        SecurityObject created = securityObjectRepository.save(securityObject);
//        log.trace("Объект безопасности успешно создан. Результат: {}", created);
//        return created;
//    }
//
//    @Override
//    @Transactional
//    public SecurityObject update(SecurityObject securityObject) {
//        log.trace("Обновление объекта безопасности {}", securityObject);
//        securityObjectValidator.validateBeforeUpdate(securityObject);
//        SecurityObject updated = securityObjectRepository.save(securityObject);
//        log.trace("Объект безопасности успешно обновлен. Результат: {}", updated);
//        return updated;
//    }
//
//    @Override
//    @Transactional
//    public void remove(String key) {
//        log.trace("Удаление объекта безопасности ({})", key);
//        remove(findByKey(key), false);
//        log.trace("Объект безопасности ({}) успешно удален", key);
//    }
//
//    @Override
//    public DeleteInfo<String> remove(String key, boolean force) {
//        log.trace(String.format("%s объекта безопасности (%s)", force ? "Принудительное удаление" : "Удаление", key));
//        DeleteInfo<String> result = new DeleteInfo<>();
//        result.setId(key);
//        try {
//            SecurityObject securityObject = findByKey(key);
//            if (!force) {
//                result.setLinks(securityObjectValidator.getLinks(securityObject));
//            }
//            remove(securityObject, force);
//            result.setDeleted(true);
//        } catch (Exception e) {
//            result.setDeleted(false);
//            result.setMessage(e.getMessage());
//        }
//        log.trace(String.format("Объект безопасности (%s) %s удален", key, result.isDeleted() ? "успешно" : "не"));
//        return result;
//    }
//
//    private void remove(SecurityObject securityObject, boolean force) {
//        securityObjectValidator.validateBeforeRemove(securityObject, force);
//
//        List<SecurityObjectTreeNodeProjection> objects = securityObjectRepository.findAllDescendantByParentIds(Collections.singleton(securityObject.getId()));
//
//        Set<String> objectIds = objects.stream()
//                .map(SecurityObjectTreeNodeProjection::getId)
//                .collect(Collectors.toSet());
//
//        objectIds.add(securityObject.getId());
//
//        deleteLinkedUserPermissions(objectIds);
//        deleteLinkedRolePermissions(objectIds);
//        deleteLinkedProxyRules(objectIds);
//
//        // Чтобы не возникали ошибки по кострейнту на parentId
//        while (!objects.isEmpty()) {
//            Set<String> parentIds = objects.stream()
//                    .map(SecurityObjectTreeNodeProjection::getParentId)
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toSet());
//
//            List<String> idsToDelete = objects.stream()
//                    .map(SecurityObjectTreeNodeProjection::getId)
//                    .filter(id -> !parentIds.contains(id))
//                    .collect(Collectors.toList());
//
//            idsToDelete.forEach(securityObjectRepository::deleteById);
//
//            objects = objects.stream().filter(o -> !idsToDelete.contains(o.getId())).collect(Collectors.toList());
//        }
//
//        if (securityObjectRepository.existsById(securityObject.getId())) {
//            securityObjectRepository.delete(securityObject);
//        }
//    }
//
//    @Override
//    public List<SecurityObject> findAll() {
//        log.trace("Получение списка объектов безопасности");
//        List<SecurityObject> securityObjects = securityObjectRepository.findAll();
//        log.trace("Список объектов безопасности получен");
//        return securityObjects;
//    }
//
//    @Transactional(readOnly = true)
//    public List<SecurityObject> findAll(SecurityObjectFilter filter, Pageable pageable) {
//        return securityObjectRepository.findAll(filter, pageable).getContent();
//    }
//
//    @Override
//    public SecurityObject findByKey(String key) {
//        log.trace("Получение информации об объекте безопасности ({})", key);
//        if (!hasText(key)) {
//            ResourceEmptyIdException exception = new ResourceEmptyIdException(ResourceName.SECURITY_OBJECT);
//            log.error(exception.toString());
//            throw exception;
//        }
//        SecurityObject securityObject = securityObjectRepository.findById(key)
//                .orElseThrow(() -> {
//                    ResourceNotFoundException exception = new ResourceNotFoundException(ResourceName.SECURITY_OBJECT, key);
//                    log.error(exception.toString());
//                    return exception;
//                });
//        log.trace("Информация об объекте безопасности ({}) получена", key);
//        return securityObject;
//    }
//
//    @Override
//    public boolean exists(String key) {
//        log.trace("Проверка наличия объекта безопасности ({})", key);
//        if (!hasText(key)) {
//            ResourceEmptyIdException exception = new ResourceEmptyIdException(ResourceName.SECURITY_OBJECT);
//            log.error(exception.toString());
//            throw exception;
//        }
//        boolean exists = securityObjectRepository.existsById(key);
//        log.trace("Результат проверки наличия объекта безопасности ({}) = {}", key, exists);
//        return exists;
//    }

    @Override
    public SecurityObject findByKey(String key) {
        throw new RuntimeException("Метод не используется");
    }

    @Override
    public String getProtocolObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    public Set<String> findAnnulledIdByIds(Collection<String> ids) {

        Set<String> result = new HashSet<>();

        IntStream.range(0, PageableUtils.getPagesCount(ids.size(), POSTGRES_MAX_PARAMS))
//                .forEach(page -> result.addAll(securityObjectRepository.findAnnulledIdByIds(ids.stream()
                .forEach(page -> result.addAll(mockRepositoryFindAnnulledIdByIds(ids.stream()
                        .skip((long) page * POSTGRES_MAX_PARAMS).limit(POSTGRES_MAX_PARAMS).collect(Collectors.toSet()))));

        return result;
    }

    private Set<String> mockRepositoryFindAnnulledIdByIds(Set<String> collect) {
        return collect;
    }

//    @Override
//    public List<SecurityObjectTreeInfo> findTree(SecurityObjectFilterTreeInfo filter) {
//        if (filter.getIds() != null) {
//            filter.setIds(filter.getIds().stream().filter(id -> id != null && !id.isEmpty()).collect(Collectors.toList()));
//
//            if (filter.getIds().isEmpty()) {
//                filter.getIds().add("");
//            }
//        } else {
//            filter.setIds(Collections.singletonList(""));
//        }
//
//        if (filter.getTypeIds() != null) {
//            filter.setTypeIds(filter.getTypeIds().stream().filter(id -> id != null && !id.isEmpty()).collect(Collectors.toList()));
//
//            if (filter.getTypeIds().isEmpty()) {
//                filter.getTypeIds().add("");
//            }
//        } else {
//            filter.setTypeIds(Collections.singletonList(""));
//        }
//
//        if(StringUtils.hasText(filter.getId())){
//            filter.setId(AbstractFilter.wrapByWildcardStatic(AbstractFilter.preFormattingStatic(filter.getId())));
//        } else {
//            filter.setId("");
//        }
//
//        return securityObjectRepository.findRecursive(filter.getName(), filter.getId(), filter.getParentId(), filter.getDeleted(), filter.getIds(), filter.getTypeIds()).stream()
//                .map(securityObjectMapperDto::mapSecurityObjectTreeInfo)
//                .sorted(Comparator.comparing(SecurityObjectTreeInfo::getName))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public RegResponse<PermissionProjection> findPermissions(PermissionFilterInfo filter) {
//        if (filter == null) {
//            filter = new PermissionFilterInfo();
//        }
//
//        filter.validateFilter();
//
//        Page<PermissionProjection> page = securityObjectRepository.findPermissions(
//                filter.getSecurityObjectIds(),
//                filter.getSecurityObjectId(),
//                filter.getSecurityObjectName(),
//                filter.getTypeIds(),
//                filter.getRoleIds(),
//                PageableUtils.toProjectionPageable(filter.getSize(), filter.getPage(), filter.getSort(), PermissionFilterInfo.SECURITY_OBJECT_ID, PermissionFilterInfo.ACTION_ID)
//        );
//        Long totalCount = securityObjectRepository.countPermissions(filter.getSecurityObjectIds());
//        return new RegResponse<>(page.getContent(), totalCount, page.getTotalElements());
//    }
//
//    @Override
//    public RegResponse<TemporaryPermissionProjection> findUserPermissions(UserPermissionFilterInfo filter) {
//        if (filter == null) {
//            throw new ResourceIllegalArgumentException(ERROR_NULL_FILTER);
//        }
//
//        filter.validateFilter();
//
//        Page<TemporaryPermissionProjection> page = securityObjectRepository.findUserPermissions(
//                filter.getSecurityObjectIds(),
//                filter.getUserId(),
//                filter.getFormattedStatuses(),
//                filter.getSecurityObjectId(),
//                filter.getSecurityObjectName(),
//                filter.getTypeIds(),
//                filter.getActions(),
//                filter.getFormattedDateBegin(),
//                filter.getFormattedDateEnd(),
//                filter.getHideExpired(),
//                PageableUtils.toProjectionPageable(filter.getSize(), filter.getPage(), filter.getSort(), PermissionFilterInfo.SECURITY_OBJECT_ID, PermissionFilterInfo.ACTION_ID)
//        );
//
//        Long totalCount = securityObjectRepository.countPermissions(Collections.singletonList(""));
//
//        return new RegResponse<>(page.getContent(), totalCount, page.getTotalElements());
//    }
//
//    @Override
//    public RegResponse<TemporaryPermissionProjection> findActiveUserPermissions(ActiveUserPermissionFilterInfo filter) {
//        if (filter == null) {
//            throw new ResourceIllegalArgumentException(ERROR_NULL_FILTER);
//        }
//
//        filter.validateFilter();
//
//        Page<TemporaryPermissionProjection> page = securityObjectRepository.findActiveUserPermissions(
//                filter.getUserId(),
//                filter.getSecurityObjectId(),
//                filter.getSecurityObjectName(),
//                filter.getTypeIds(),
//                PageableUtils.toProjectionPageable(filter.getSize(), filter.getPage(), filter.getSort(), PermissionFilterInfo.SECURITY_OBJECT_ID, PermissionFilterInfo.ACTION_ID)
//        );
//
//        return new RegResponse<>(page.getContent(), page.getTotalElements(), page.getTotalElements());
//    }
//
//    @Override
//    public RegResponse<PermissionProjection> findActiveRolePermissions(ActiveRolePermissionFilterInfo filter) {
//        if (filter == null) {
//            throw new ResourceIllegalArgumentException(ERROR_NULL_FILTER);
//        }
//
//        filter.validateFilter();
//
//        Page<PermissionProjection> page = securityObjectRepository.findActiveRolePermissions(
//                filter.getRoleId(),
//                filter.getSecurityObjectId(),
//                filter.getSecurityObjectName(),
//                filter.getTypeIds(),
//                PageableUtils.toProjectionPageable(filter.getSize(), filter.getPage(), filter.getSort(), PermissionFilterInfo.SECURITY_OBJECT_ID, PermissionFilterInfo.ACTION_ID)
//        );
//
//        return new RegResponse<>(page.getContent(), page.getTotalElements(), page.getTotalElements());
//    }
//
//    @Transactional
//    @Override
//    public void deleteLogicalSecurityObjects(Set<String> ids) {
//        securityObjectValidator.validateBeforeDelete(ids);
//        final Map<String, List<String>> relations = new HashMap<>();
//        final Set<String> allChildren = new HashSet<>();
//        for (String id : ids) {
//            if (allChildren.contains(id)) {
//                continue;
//            }
//            List<String> children = securityObjectRepository.findAllDescendantIdsByParentId(id);
//            relations.put(id, children);
//            allChildren.addAll(children);
//        }
//
//        securityObjectRepository.findSecurityObjectIdAndDeleteDate(relations.keySet()).forEach(o -> {
//            if (Objects.isNull(o.getDeleteDate()) && !allChildren.contains(o.getSecurityObjectId())) {
//                securityObjectLogicalDeleteService.delete(o.getSecurityObjectId());
//                if (!CollectionUtils.isEmpty(relations.get(o.getSecurityObjectId()))) {
//                    securityObjectRepository.findSecurityObjectIdAndDeleteDate(new HashSet<>(relations.get(o.getSecurityObjectId())))
//                            .forEach(e -> {
//                                if (Objects.isNull(e.getDeleteDate())) {
//                                    securityObjectLogicalDeleteService.delete(e.getSecurityObjectId());
//                                }
//                            });
//                }
//            } else if (Objects.nonNull(o.getDeleteDate())) {
//                securityObjectLogicalDeleteService.restore(o.getSecurityObjectId());
//                List<String> parents = securityObjectRepository.findDeletedParents(o.getSecurityObjectId());
//                parents.forEach(securityObjectLogicalDeleteService::restore);
//            }
//        });
//    }
//
//    @Transactional
//    @Override
//    public void setSecurityObjectParentForGroup(String id, List<String> groupIds) {
//        if (!StringUtils.hasText(id)) {
//            throw new ResourceIllegalArgumentException("ОШИБКА: идентификатор родительского объекта безопасности не указано");
//        }
//
//        if (CollectionUtils.isEmpty(groupIds)) {
//            throw new ResourceIllegalArgumentException(
//                    "ОШИБКА: не указаны идентификаторы объектов безопасности, для которых должен быть определён родитель");
//        }
//
//        SecurityObject donorSecurityObject = securityObjectRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException(String.format("ОШИБКА: родительский объект с идентификатором %s не найден", id)));
//
//        groupIds.stream().distinct().forEach(idRecepientObject -> {
//            if (!StringUtils.hasText(idRecepientObject)) {
//                throw new ResourceIllegalArgumentException(
//                        "ОШИБКА: в перечне имеются пустые идентификаторы объектов, для которых должен быть определён родитель");
//            }
//
//            if (id.equals(idRecepientObject)) {
//                throw new ResourceIllegalArgumentException("ОШИБКА: недопустимо назначение объекта безопасности" +
//                        " в качестве своего же родителя");
//            }
//
//            SecurityObject recepientSecurityObject = securityObjectRepository.findById(idRecepientObject)
//                    .orElseThrow(() -> new ResourceNotFoundException(
//                            String.format("ОШИБКА: по переданному идентификатору %s объект безопасности не найден", idRecepientObject)));
//
//            if (donorSecurityObject.getParentId() != null
//                    && donorSecurityObject.getParentId().equals(recepientSecurityObject.getId())) {
//                throw new ResourceIllegalArgumentException(String.format("ОШИБКА: в результате назначения объекту с идентификатором %s" +
//                        " в качестве родителя объекта с идентификатором %s, образуется цикл",
//                        recepientSecurityObject.getId(), donorSecurityObject.getId()));
//            }
//
//            recepientSecurityObject.setParentId(id);
//            securityObjectRepository.save(recepientSecurityObject);
//        });
//    }
//
//    private List<User> getLinkedUserPermissions(Set<String> objectIds) {
//        if (CollectionUtils.isEmpty(objectIds)) return Collections.emptyList();
//        return userRepository.findBySecurityObjectId(objectIds);
//    }
//
//    private void deleteLinkedUserPermissions(Set<String> objectIds) {
//        getLinkedUserPermissions(objectIds).forEach(user -> {
//            List<UserPermission> toDelete = user.getPermissions().stream()
//                    .filter(p -> objectIds.contains(p.getId().getSecurityObjectId()))
//                    .collect(Collectors.toList());
//
//            user.getPermissions().removeAll(toDelete);
//            userRepository.save(user);
//        });
//    }
//
//    private List<Role> getLinkedRolePermissions(Set<String> objectIds) {
//        if (CollectionUtils.isEmpty(objectIds)) return Collections.emptyList();
//        return roleRepository.findBySecurityObjectId(objectIds);
//    }
//
//    private void deleteLinkedRolePermissions(Set<String> objectIds) {
//        getLinkedRolePermissions(objectIds).forEach(role -> {
//            List<RolePermission> toDelete = role.getPermissions().stream()
//                    .filter(p -> objectIds.contains(p.getId().getSecurityObjectId()))
//                    .collect(Collectors.toList());
//
//            role.getPermissions().removeAll(toDelete);
//            roleRepository.save(role);
//        });
//    }
//
//    private List<ProxyRule> getLinkedProxyRules(Set<String> objectIds) {
//        if (CollectionUtils.isEmpty(objectIds)) return Collections.emptyList();
//        return objectIds.stream()
//                .map(objectId -> {
//                    var filter = ProxyRuleFilter.builder()
//                            .securityObjectId(objectId)
//                            .build();
//
//                    return filter.findAll(proxyRuleRepository).getContent();
//                }).flatMap(List::stream)
//                .collect(Collectors.toList());
//    }
//
//    private void deleteLinkedProxyRules(Set<String> objectIds) {
//        proxyRuleRepository.deleteAll(getLinkedProxyRules(objectIds));
//    }
}
