package src.unigate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RolePermissionId implements PermissionEntityId {

    private String roleId;

    private String securityObjectId;

    private String action;
}
