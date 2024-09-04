package src.unigate;

import java.io.Serializable;

public interface ProtocolAndAuditEntity<K extends Serializable> extends ProtocolableEntity<K>, AuditEntity {
}
