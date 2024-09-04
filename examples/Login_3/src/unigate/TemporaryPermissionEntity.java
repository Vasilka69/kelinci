package src.unigate;

import java.time.LocalDate;

public interface TemporaryPermissionEntity {
    LocalDate getDateBegin();
    LocalDate getDateEnd();
    String getComment();
}
