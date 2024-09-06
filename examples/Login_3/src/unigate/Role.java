package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority, ProtocolAndAuditEntity<String> {
    
    private String id;
    
    private String name;

    private String description;

    private boolean archived = false;

    private ZonedDateTime deleteDate;
    
    private Set<String> types = new HashSet<>();

    private List<RolePermission> permissions = new ArrayList<>();

    private Integer sessionMaxCount;

    private ZonedDateTime lastModified;
    
    /**
     * Конструктор для использрования в user repository - для ролей заполняется только id
     */
    public Role(String id) {
        this.id = id;
    }

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String getProtocolObjectName() {
        return getName();
    }

    @Override
    public String getProtocolObjectType() {
        return DefaultRoleService.OBJECT_TYPE;
    }

    public Role(String id, String name, Set<String> types, String description,
                boolean archived, ZonedDateTime deleteDate, Integer sessionMaxCount) {
        this.id = id;
        this.name = name;
        this.types = types;
        this.description = description;
        this.archived = archived;
        this.deleteDate = deleteDate;
        this.sessionMaxCount = sessionMaxCount;
    }
}
