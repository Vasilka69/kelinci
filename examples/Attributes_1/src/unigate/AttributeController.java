package src.unigate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttributeController {

    private final AttributeMapperDto attributeMapperDto;
    private final AttributeService attributeService;

//    @PostMapping("/card")
    public IdResponse<String> addAttributeCard(
            /*@RequestBody */AttributeInfo attributeInfo
    ) {
        return new IdResponse<>(attributeService.add(attributeMapperDto.mapAttribute(attributeInfo)).getId());
    }
}
