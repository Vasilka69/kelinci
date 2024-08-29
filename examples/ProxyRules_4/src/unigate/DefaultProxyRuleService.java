package src.unigate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultProxyRuleService implements ProxyRuleService{


    @Override
    public ProxyRule add(ProxyRule entity){
//        return proxyRuleRepository.save(entity);
        System.out.println(entity + " сохранён в БД");
        return entity;
    }
}
