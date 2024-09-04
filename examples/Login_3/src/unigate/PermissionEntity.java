package src.unigate;

import java.io.Serializable;

public interface PermissionEntity extends Serializable {
    PermissionEntityId getId();
    Boolean getProhibitive();
}
