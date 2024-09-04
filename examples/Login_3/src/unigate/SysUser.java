package src.unigate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Системный пользователь
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SysUser extends User implements Entity<String> {

    private String name;

    public SysUser(String id, String username, String password, boolean accountNonLocked, String blockComment, String name) {
        super(id, username, password, accountNonLocked, blockComment, false);
        this.name = name;
    }
}
