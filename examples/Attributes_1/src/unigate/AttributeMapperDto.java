package src.unigate;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.ZoneId;

@RequiredArgsConstructor
public class AttributeMapperDto {

    public Attribute mapAttribute(AttributeInfo attributeInfo) {
        return new Attribute(
                StringUtils.trim(attributeInfo.getId()),
                StringUtils.trim(attributeInfo.getName()),
                attributeInfo.getMultiple(),
                attributeInfo.getAttributeType(),
                attributeInfo.getIsGlobal(),
                attributeInfo.getOrder(),
                attributeInfo.getDictionaryAttributeId() != null
                        ? getDictionaryAttribute(attributeInfo.getDictionaryAttributeId())
                        : null
        );
    }

    private DictionaryAttribute getDictionaryAttribute(Long id) {
        return new DictionaryAttribute(
                id,
                "name",
                "description",
                DictionaryKind.LINEAR,
                "tableName",
                DictionaryIdAttributeType.STRING,
                "idAttribute",
                "nameAttribute",
                "parentIdAttribute",
                Instant.now().atZone(ZoneId.systemDefault()),
                Instant.now().atZone(ZoneId.systemDefault())
        );
    }
}
