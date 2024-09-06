package src.unigate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Action implements Serializable {

    private String id;

    private String name;

    private String description;

    private ZonedDateTime deleteDate;

    private ZonedDateTime lastModified;

    public Action(String id, String name, String description, ZonedDateTime deleteDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deleteDate = deleteDate;
    }
}
