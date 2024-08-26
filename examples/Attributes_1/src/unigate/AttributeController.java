package src.unigate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttributeController {

    private final AttributeMapperDto attributeMapperDto;
    private final AttributeService attributeService;

    public IdResponse<String> addAttributeCard(AttributeInfo attributeInfo) {
        return new IdResponse<>(attributeService.add(attributeMapperDto.mapAttribute(attributeInfo)).getId());
    }
}
