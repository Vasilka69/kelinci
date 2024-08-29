package src.unigate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultProxyRuleService implements ProxyRuleService{


    @Override
    public ProxyRule add(ProxyRule entity){
//        return proxyRuleRepository.save(entity);
        System.out.println(entity + " сохранён в БД");
        return entity;
    }
}
