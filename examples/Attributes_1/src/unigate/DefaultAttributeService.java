package src.unigate;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class DefaultAttributeService implements AttributeService {

    private boolean validateId(String id) {
        return AvailableSymbolsPattern.validateId(id) && id.startsWith(IdPrefix.ATTRIBUTE.getPrefix() + ".");
    }

    @Override
    public Attribute validateBeforeAdd(Attribute attribute) {

        if (attribute.getId() == null) {
            throw new RuntimeException("ОШИБКА: идентификатор атрибута должен быть указан!");
        }

        if (attribute.getId().length() > 255) {
            throw new RuntimeException("ОШИБКА: длина идентификатора атрибута не может превышать 255 символов");
        }

        if (!validateId(attribute.getId())) {
            throw new RuntimeException("ОШИБКА: введённый идентификатор атрибута не соответствует установленному формату!");
        }

        if (attribute.getAttributeType() == null) {
            throw new RuntimeException(String.format(
                    "ОШИБКА: тип атрибута должен быть указан и соответствовать одному из следующих: %s!",
                    Arrays.toString(AttributeType.values())));
        }

        if (attribute.getMultiple() == null) {
            throw new RuntimeException("ОШИБКА: свойство множественности атрибута должно быть определено!");
        }

        if (attribute.getIsGlobal() == null) {
            throw new RuntimeException("ОШИБКА: свойство глобальности атрибута должно быть определено!");
        }

        if (!hasText(attribute.getName())) {
            throw new RuntimeException("ОШИБКА: имя атрибута должно быть указано!");
        }

        if (attribute.getName().length() > 1000) {
            throw new RuntimeException("ОШИБКА: имя атрибута не может превышать 1000 символов!");
        }

        // если тип атрибута boolean
        validateAttributeTypeBoolean(attribute);

        // если тип атрибута dictionary
        validateAttributeTypeDictionary(attribute);

        return attribute;
    }

    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty());
    }

    private void validateAttributeTypeBoolean(Attribute attribute) {
        if (!AttributeType.BOOLEAN.equals(attribute.getAttributeType())) {
            return;
        }

        // атрибут может быть только однозначным
        if (Boolean.TRUE.equals(attribute.getMultiple())) {
            throw new RuntimeException("ОШИБКА: атрибут не может быть мультизначным, если он имеет тип boolean!");
        }
    }

    private void validateAttributeTypeDictionary(Attribute attribute) {
        if (!AttributeType.DICTIONARY.equals(attribute.getAttributeType())) {
            return;
        }

        DictionaryAttribute dictionaryAttribute = attribute.getDictionaryAttribute();

        // должен быть указан справочник
        if (dictionaryAttribute == null) {
            throw new RuntimeException("ОШИБКА: справочный атрибут должен быть указан, если он имеет тип dictionary!");
        }


//        // справочник должен быть корректно настроен и содержать данные
//        String errorDictionaryAttribute = String.format("ОШИБКА: справочный атрибут %s настроен некорректно!", dictionaryAttribute.getId());
//        try {
//            DictionaryAttributeElementFilter filter = new DictionaryAttributeElementFilter();
//            if (!filter.exists(jdbcTemplate, dictionaryAttribute)) {
//                throw new RuntimeException(errorDictionaryAttribute);
//            } else {
//                List<IdNameParentIdProjection> items = filter.findAll(jdbcTemplate, dictionaryAttribute);
//                if (CollectionUtils.isEmpty(items)) {
//                    throw new RuntimeException(String.format("ОШИБКА: справочный атрибут %s не содержит данных!", dictionaryAttribute.getId()));
//                }
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException(errorDictionaryAttribute);
//        }
    }

    @Override
    public Attribute add(Attribute attribute) {
        Attribute validatedAttribute = validateBeforeAdd(attribute);
        System.out.println(validatedAttribute + "сохранён в БД");
        return validatedAttribute;
//        return attributeRepository.save(validateBeforeAdd(attribute));
    }

}
