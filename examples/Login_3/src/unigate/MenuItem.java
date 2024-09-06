package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuItem implements PermissionEntityId {

    private String id;

    private String parentId;
    
    private String name;
    
    private String link;

    private String securityObjectId;

    private String action;
    
    private Integer ord;

    private ZonedDateTime updateDate;
}
