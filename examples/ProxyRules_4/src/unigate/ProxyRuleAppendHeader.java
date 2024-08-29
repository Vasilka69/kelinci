package src.unigate;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProxyRuleAppendHeader implements Entity<Integer> {

    private Integer id;

    private String name;

    private String expression;

    private ProxyRule proxyRule;

}
