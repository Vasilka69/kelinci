package src.unigate;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SecurityObjectController {

    private final SecurityObjectService securityObjectService;
    private final SecurityObjectMapperDto securityObjectMapperDto;
    private final SecurityObjectTypeRepository securityObjectTypeRepository;


//    @PostMapping("/card")
    public IdResponse<String> addSecurityObjectCard(
            SecurityObjectFullInfo securityObjectFullInfo
    ) {
        System.out.println("Попытка создать новый объект безопасности");

        if (securityObjectFullInfo.getSecurityObjectType() == null || securityObjectFullInfo.getSecurityObjectType().getId() == null) {
            throw new RuntimeException("Тип объекта безопасности должен быть указан,");
        }

        SecurityObjectType type = securityObjectTypeRepository.findById(securityObjectFullInfo.getSecurityObjectType().getId())
                .orElseThrow(() -> new RuntimeException("Тип объекта безопасности с идентификатором " +
                        securityObjectFullInfo.getSecurityObjectType().getId() + " не найден"));

        SecurityObject securityObject = securityObjectService.add(securityObjectMapperDto.mapSecurityObject(securityObjectFullInfo, type));
        System.out.println(String.format("Создан новый объект безопасности с идентификатором: %s", securityObject.getId()));
        return new IdResponse<>(securityObject.getId());
    }
}

