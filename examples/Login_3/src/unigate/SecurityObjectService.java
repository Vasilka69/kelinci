package src.unigate;

import java.util.Collection;
import java.util.Set;

/**
 * Сервис объектов безопасности
 */
public interface SecurityObjectService extends BaseProtocolableService<SecurityObject, String>  {

    Set<String> findAnnulledIdByIds(Collection<String> ids);

//    List<SecurityObjectTreeInfo> findTree(SecurityObjectFilterTreeInfo filter);
//
//    RegResponse<PermissionProjection> findPermissions(PermissionFilterInfo filter);
//
//    RegResponse<TemporaryPermissionProjection> findUserPermissions(UserPermissionFilterInfo filter);
//
//    RegResponse<TemporaryPermissionProjection> findActiveUserPermissions(ActiveUserPermissionFilterInfo filter);
//
//    RegResponse<PermissionProjection> findActiveRolePermissions(ActiveRolePermissionFilterInfo filter);
//
//    /**
//     * Физическое удаление объекта безопасности
//     * @param key идентификатор объекта
//     * @param force признак принудительного удаления
//     */
//    DeleteInfo<String> remove(String key, boolean force);
//    /**
//     * Логическое удаление объектов безопасности.
//     * Объекты безопасности помечаются как удаленные.
//     *
//     * @param ids идентификаторы объектов
//     */
//    void deleteLogicalSecurityObjects(Set<String> ids);
//
//    void setSecurityObjectParentForGroup(String id, List<String> groupIds);
}
