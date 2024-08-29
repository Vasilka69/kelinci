package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProxyRule implements Entity<Integer>{

    private Integer id;

    private String name;

    private String urlPattern;

    private List<ProxyRuleRequestParam> requestParams = new ArrayList<>();

    private RequestMethod httpMethod;

    private String condition;

    private List<ProxyRuleAppendHeader> appendHeaders = new ArrayList<>();

}

