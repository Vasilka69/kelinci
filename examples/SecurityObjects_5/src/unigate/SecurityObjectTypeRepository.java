package src.unigate;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;

public class SecurityObjectTypeRepository {
    public boolean existsById(String id) {
        return true;
    }

    public Optional<SecurityObjectType> findById(String id) {
        return Optional.of(new SecurityObjectType(
                "id",
                "name",
                "description",
                Instant.now().atZone(ZoneId.systemDefault()),
                Arrays.asList(new Action(
                        "id",
                        "name",
                        "description",
                        Instant.now().atZone(ZoneId.systemDefault()))
                )
        ));
    }
}
