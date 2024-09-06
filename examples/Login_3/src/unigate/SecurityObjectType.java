package src.unigate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SecurityObjectType implements Entity<String> {

    private String id;

    private String name;

    private String description;

    private ZonedDateTime deleteDate;

    private ZonedDateTime lastModified;

    private List<Action> actions;

    public SecurityObjectType(String id, String name, String description, ZonedDateTime deleteDate, List<Action> actions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleteDate = deleteDate;
        this.actions = actions;
    }
}
