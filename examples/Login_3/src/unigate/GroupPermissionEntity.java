package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GroupPermissionEntity {

    private String securityObjectId;

    private String actionId;
}
