package src.unigate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class User implements ProtocolAndAuditEntity<String> {

    private String id;

    @JsonIgnore
    private String password;

    protected String username;

    private Boolean accountNonLocked;

    private Set<String> roles;

    private List<UserPermission> permissions = new ArrayList<>();

    private boolean changePassword;

    private ZonedDateTime deleteDate;

    private Date createDate;

    private Integer sessionMaxCount;

    private ZonedDateTime changePasswordDate;

    private BlockReason blockReason;

    private String blockComment;

    private ZonedDateTime lastModified;

    private ZonedDateTime lastAuthDate;

    protected User(String id,
                   String username,
                   String password,
                   boolean accountNonLocked,
                   String blockComment,
                   boolean changePassword) {
        this.username = username;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.blockComment = blockComment;
        this.id = id;
        this.changePassword = changePassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User raUser = (User) o;
        return Objects.equals(id, raUser.id)
                && CollectionUtils.equalContentIgnoreOrd(permissions, raUser.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String getProtocolObjectName() {
        return UserUtils.getName(this);
    }

    @Override
    public String getProtocolObjectType() {
        return DefaultUserService.OBJECT_TYPE;
    }

}
