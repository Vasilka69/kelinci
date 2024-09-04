package src.unigate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecuritySettings implements Entity<String> {
    private String id;

    private Boolean enable;

    private List<String> notPermitPasswords;

    public SecuritySettings(String id) {
        this.id = id;
    }
}
