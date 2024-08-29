package src.unigate;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class ProxyRulesController{
    private final ProxyRuleService proxyRuleService;
    private final ProxyRuleMapperDto mapperDto;
    private final SpelValidationService spelValidationService;

//    @PostMapping("/card")
    public IdResponse <Integer> createCard(ProxyRuleInfo proxyRuleInfo){
        validate(proxyRuleInfo);
        ProxyRule proxyRule = mapperDto.mapProxyRule(proxyRuleInfo);
        proxyRule = proxyRuleService.add(proxyRule);
        return new IdResponse <>(proxyRule.getId());
    }

    private void validate(ProxyRuleInfo proxyRuleInfo){
        if(StringUtils.isNoneEmpty(proxyRuleInfo.getCondition()) && ! spelValidationService.validateExpression(proxyRuleInfo.getCondition())){
            throw new RuntimeException("Не валидное условие");
        }

        if(proxyRuleInfo.getAppendHeaders() != null){
            proxyRuleInfo.getAppendHeaders().stream()
                    .filter(appendHeader -> ! spelValidationService.validateExpression(appendHeader.getExpression()))
                    .findAny().ifPresent(appendHeader -> {
                        throw new RuntimeException("Не валидное значение дополнительного заголовка: " + appendHeader.getExpression());
                    });
        }
    }
}