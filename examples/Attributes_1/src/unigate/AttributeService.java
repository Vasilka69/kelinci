package src.unigate;

/**
 * Сервис атрибутов
 */
public interface AttributeService extends BaseService<Attribute, String> {
    Attribute validateBeforeAdd(Attribute attribute);
//    void validateBeforeRemove(String id);
//    void validateBeforeRemove(List<String> ids);
//    void remove(List<String> ids);
}
