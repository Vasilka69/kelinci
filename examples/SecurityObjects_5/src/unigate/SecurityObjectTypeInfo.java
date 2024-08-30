package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityObjectTypeInfo {
    private String id;
    private String name;
    private String description;
    private ZonedDateTime deleteDate;
    private ZonedDateTime lastModified;
    private List<ActionInfo> actions;
}
