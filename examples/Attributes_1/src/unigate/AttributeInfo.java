package src.unigate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeInfo {
    private String id;
    private String name;
    private Boolean multiple;
    private AttributeType attributeType;
    private Boolean isGlobal;
    private Integer order;
    private Long dictionaryAttributeId;
    private String dictionaryAttributeName;
}
