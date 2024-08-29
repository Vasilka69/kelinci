package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyRuleRequestParamInfo implements Serializable {
    private Integer id;
    private String paramName;
    private String paramValue;
}
