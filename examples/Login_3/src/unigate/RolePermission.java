package src.unigate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RolePermission implements PermissionEntity {
    
    @EqualsAndHashCode.Include
    private RolePermissionId id;

    private Role role;
    
    private Boolean prohibitive = false;

    public RolePermission(Role role, String securityObjectId, String action, Boolean prohibitive) {
        this.id = new RolePermissionId(role.getId(), securityObjectId, action);
        this.prohibitive = prohibitive;
        this.role = role;
    }
}
