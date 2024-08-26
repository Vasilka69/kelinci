package src.unigate;


import lombok.*;

import java.time.ZonedDateTime;
import java.util.Objects;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class DictionaryAttribute implements Entity<Long> {

    private Long id;

    private String name;

    private String description;

    private DictionaryKind kind;

    private String tableName;

    private DictionaryIdAttributeType idAttributeType;

    private String idAttribute;

    private String nameAttribute;

    private String parentIdAttribute;

    private ZonedDateTime deleteDate;

    private ZonedDateTime lastModified;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryAttribute dictionaryAttribute = (DictionaryAttribute) o;
        return Objects.equals(id, dictionaryAttribute.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
