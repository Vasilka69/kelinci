package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SecurityObject implements ProtocolableEntity<String> {

    private String id;
    
    private String name;

    private SecurityObjectType securityObjectType;

    private String parentId;

    private ZonedDateTime deleteDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityObject securityObject = (SecurityObject) o;
        return Objects.equals(id, securityObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String getProtocolObjectName() {
        return this.getName();
    }
}
