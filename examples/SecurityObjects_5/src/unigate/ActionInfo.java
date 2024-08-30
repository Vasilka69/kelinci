package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInfo {
    private String id;
    private String name;
    private String description;
    private ZonedDateTime deleteDate;
    private ZonedDateTime lastModified;
}
