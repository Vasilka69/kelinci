package src.unigate;

import java.util.Collections;
import java.util.List;

public class SecurityObjectRepository {
    public boolean existsById(String id) {
        return false;
    }

    public boolean existsByParentId(String parentId) {
        return true;
    }

    public List<String> findAllDescendantIdsByParentId(String id) {
        return Collections.emptyList();
    }

    public List<String> findIdByParentIdAndName(String s, String name) {
        return Collections.emptyList();
    }
}
