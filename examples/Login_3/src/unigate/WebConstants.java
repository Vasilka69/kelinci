package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WebConstants {

    public static final String REDIRECT_URI_PARAMETER = "redirect_uri";
    public static final String REDIRECT_URL_PARAMETER = "redirect_url";
    public static final String ROLES_PARAMETER = "roles";
    public static final String ROLE_PARAMETER = "role";
    public static final String TOKEN_PARAMETER = "token";
    public static final String SYSTEM_TOKEN_PARAMETER = "system_token";
    public static final String STRATEGY_PARAMETER = "strategy";
    public static final String PARAMETERS_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final String PARAMETERS_START_SYMBOL = "?";
    public static final String RIGHT_SLASH = "/";
}
