package src.unigate;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProxyRuleInfo implements Serializable {
    private Integer id;
    private String name;
    private String urlPattern;
    private List<ProxyRuleRequestParamInfo> requestParams;
    private RequestMethod httpMethod;
    private String condition;
    private List<ProxyRuleAppendHeaderInfo> appendHeaders;
}
