package src.unigate;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProxyRuleRequestParam implements Entity<Integer> {

    private Integer id;

    private String paramName;

    private String paramValue;

    private ProxyRule proxyRule;
}
