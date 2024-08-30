package src.unigate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class SecurityObjectFullInfo {
    private String id;
    private String name;
    private ZonedDateTime deleteDate;
    private SecurityObjectTypeInfo securityObjectType;
    private SecurityObjectLinkedInfo parent;
}
