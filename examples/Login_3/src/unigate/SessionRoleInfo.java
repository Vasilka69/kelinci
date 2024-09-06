package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SessionRoleInfo {
    private UUID id;
    private String roleId;
    private String name;
    private String type;
    private Boolean selected;
}
