package src.unigate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenContext {
    public static final String PREFIX = "Bearer";

    private String systemToken;
}
