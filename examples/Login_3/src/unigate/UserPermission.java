package src.unigate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserPermission implements TemporaryPermissionEntity, PermissionEntity {

    @EqualsAndHashCode.Include
    private UserPermissionId id;

    private User user;

    private Boolean prohibitive = false;

    private LocalDate dateBegin;

    private LocalDate dateEnd;

    private String comment;

    public UserPermission(User user, String securityObjectId, String action, Boolean prohibitive) {
        this.id = new UserPermissionId(user.getId(), securityObjectId, action);
        this.prohibitive = prohibitive;
        this.user = user;
    }

    public UserPermission(User user, UserPermission srcPermission) {
        this.id = new UserPermissionId(user.getId(), srcPermission.getId().getSecurityObjectId(), srcPermission.getId().getAction());
        this.prohibitive = srcPermission.prohibitive;
        this.user = user;
    }

    public UserPermission(User user, String securityObjectId, String action, Boolean prohibitive, LocalDate dateBegin, LocalDate dateEnd, String comment) {
        this.id = new UserPermissionId(user.getId(), securityObjectId, action);
        this.prohibitive = prohibitive;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.comment = comment;
        this.user = user;
    }

    public boolean isTemporaryPermissionActive() {
        LocalDate now = LocalDate.now();
        return (dateBegin == null || now.equals(dateBegin) || now.isAfter(dateBegin)) &&
                (dateEnd == null || now.equals(dateEnd) || now.isBefore(dateEnd));
    }
}
