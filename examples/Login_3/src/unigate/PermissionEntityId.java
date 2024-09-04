package src.unigate;

import java.io.Serializable;

public interface PermissionEntityId extends Serializable {
    String getSecurityObjectId();
    String getAction();
}
