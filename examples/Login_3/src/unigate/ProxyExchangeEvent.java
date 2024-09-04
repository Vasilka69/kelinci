package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProxyExchangeEvent {
    public static final String CLEAR_SESSION_CACHE = "clear_session_cache";
    public static final String UPDATE_PROXY_RULE = "update_proxy_rule";
    public static final String DELETE_PROXY_RULE = "delete_proxy_rule";

    private String type;
    private String id;
}
