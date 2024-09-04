package src.unigate;

import java.util.List;

public interface SettingsService<T extends Setting> {

//    List<T> save(List<T> settings);
//
//    boolean existsById(String id);
//
//    List<T> getAll();

    T get(String id);
}
