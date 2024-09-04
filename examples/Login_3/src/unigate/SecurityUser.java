package src.unigate;

import lombok.Getter;

import java.util.Collection;
import java.util.Objects;

@Getter
public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private final User userData;

    public SecurityUser(User user, Collection<Role> authorities) {
        super(user.getUsername(), user.getPassword(), true, true, true, user.getAccountNonLocked(), authorities);
        this.userData = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SecurityUser) || !super.equals(o)) {
            return false;
        }

        SecurityUser that = (SecurityUser) o;
        return Objects.equals(userData, that.userData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userData);
    }
}
