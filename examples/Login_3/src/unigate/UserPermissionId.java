package src.unigate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserPermissionId implements PermissionEntityId {

    private String userId;

    private String securityObjectId;

    private String action;
}
