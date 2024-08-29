package src.unigate;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;

public class ProxyRuleMapperDto {

    public ProxyRule mapProxyRule(ProxyRuleInfo proxyRuleInfo){
        var proxyRule = new ProxyRule();
        proxyRule.setId(proxyRuleInfo.getId());
        proxyRule.setName(StringUtils.trim(proxyRuleInfo.getName()));
        proxyRule.setUrlPattern(StringUtils.trim(proxyRuleInfo.getUrlPattern()));
        if(proxyRuleInfo.getRequestParams() != null){
            proxyRule.setRequestParams(proxyRuleInfo.getRequestParams().stream()
                    .map(requestParamInfo -> {
                        ProxyRuleRequestParam requestParam = new ProxyRuleRequestParam();
                        requestParam.setId(requestParamInfo.getId());
                        requestParam.setProxyRule(proxyRule);
                        requestParam.setParamName(StringUtils.trim(requestParamInfo.getParamName()));
                        requestParam.setParamValue(StringUtils.defaultIfEmpty(StringUtils.trim(requestParamInfo.getParamValue()),null));
                        return requestParam;
                    }).collect(Collectors.toList()));
        }
        proxyRule.setHttpMethod(proxyRuleInfo.getHttpMethod());
        proxyRule.setCondition(StringUtils.trim(proxyRuleInfo.getCondition()));
        if(proxyRuleInfo.getAppendHeaders() != null){
            proxyRule.setAppendHeaders(proxyRuleInfo.getAppendHeaders().stream()
                    .map(appendHeaderInfo -> {
                        ProxyRuleAppendHeader appendHeader = new ProxyRuleAppendHeader();
                        appendHeader.setId(appendHeaderInfo.getId());
                        appendHeader.setName(StringUtils.trim(appendHeaderInfo.getName()));
                        appendHeader.setExpression(StringUtils.defaultIfEmpty(StringUtils.trim(appendHeaderInfo.getExpression()),null));
                        appendHeader.setProxyRule(proxyRule);
                        return appendHeader;
                    }).collect(Collectors.toList()));
        }

        return proxyRule;
    }
}
