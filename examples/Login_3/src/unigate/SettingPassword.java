package src.unigate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettingPassword implements Setting {

    private String id;

    @JsonIgnore
    private SecuritySettings parentSettings;

    private Boolean enable;

    private Integer value;

    private Integer duration;

    private String time;

    public SettingPassword(Boolean enable) {
        this.enable = enable;
    }

    public SettingPassword(Boolean enable, Integer value) {
        this.enable = enable;
        this.value = value;
    }

    public SettingPassword(Boolean enable, String time) {
        this.enable = enable;
        this.time = time;
    }

    public boolean isEnable() {
        return Boolean.TRUE.equals(getEnable());
    }
}
