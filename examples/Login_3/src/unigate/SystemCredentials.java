package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemCredentials {
    private String username;
    private String password;
}
