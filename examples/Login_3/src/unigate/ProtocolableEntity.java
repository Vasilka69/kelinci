package src.unigate;

import java.io.Serializable;

public interface ProtocolableEntity<K extends Serializable> extends Entity<K> {
    String getProtocolObjectName();
    default String getProtocolObjectType() {
        throw new UnsupportedOperationException("Необходима реализация в подтипах");
    }
}
