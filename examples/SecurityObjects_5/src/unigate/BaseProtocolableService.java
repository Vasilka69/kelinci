package src.unigate;

import java.io.Serializable;

/**
 * Интерфейс сервиса базовыми методами.
 * @param <T> класс сущности
 * @param <K> класс идентификатора сущности
 */
public interface BaseProtocolableService<T extends ProtocolableEntity<K>, K extends Serializable> extends BaseService<T, K> {
    String getProtocolObjectType();
}
