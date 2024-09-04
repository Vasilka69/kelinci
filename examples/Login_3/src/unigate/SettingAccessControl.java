package src.unigate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor
public class SettingAccessControl implements Setting {

    private String id;

    private SecuritySettings parentSettings;

    private Boolean enable;

    private Integer value;
}
