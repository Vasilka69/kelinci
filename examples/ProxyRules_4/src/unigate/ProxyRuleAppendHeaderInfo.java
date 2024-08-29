package src.unigate;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProxyRuleAppendHeaderInfo implements Serializable {
    private Integer id;
    private String name;
    private String expression;
}
