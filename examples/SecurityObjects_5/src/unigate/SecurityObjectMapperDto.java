package src.unigate;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class SecurityObjectMapperDto {

    public SecurityObject mapSecurityObject(SecurityObjectFullInfo securityObjectFullInfo, SecurityObjectType securityObjectType) {
        SecurityObject securityObject = new SecurityObject();
        securityObject.setId(StringUtils.trim(securityObjectFullInfo.getId()));
        securityObject.setName(StringUtils.trim(securityObjectFullInfo.getName()));
        if (securityObjectFullInfo.getParent() != null) {
            securityObject.setParentId(StringUtils.trim(securityObjectFullInfo.getParent().getId()));
        }
        securityObject.setSecurityObjectType(securityObjectType);
        return securityObject;
    }
}
