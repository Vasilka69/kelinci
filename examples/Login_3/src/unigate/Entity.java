package src.unigate;

import java.io.Serializable;

public interface Entity<K extends Serializable> extends Serializable {
    K getId();
}
