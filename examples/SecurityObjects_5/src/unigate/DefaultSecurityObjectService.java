package src.unigate;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DefaultSecurityObjectService implements SecurityObjectService {
    public static final String OBJECT_TYPE = "security_object";

    private final SecurityObjectValidator securityObjectValidator;

    @Override
    public SecurityObject add(SecurityObject securityObject) {
        System.out.printf("Создание объекта безопасности %s%n", securityObject);
//        log.trace("Создание объекта безопасности {}", securityObject);
        securityObjectValidator.validateBeforeAdd(securityObject);
//        SecurityObject created = securityObjectRepository.save(securityObject);
        SecurityObject created = securityObject;
//        log.trace("Объект безопасности успешно создан. Результат: {}", created);
        System.out.printf("Создание объекта безопасности %s%n", securityObject);
        return created;
    }

    @Override
    public String getProtocolObjectType() {
        return OBJECT_TYPE;
    }
}
