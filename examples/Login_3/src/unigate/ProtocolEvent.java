package src.unigate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtocolEvent {
    // типы событий
    public static final String CREATE_EVENT = "create";
    public static final String EDIT_EVENT = "edit";
    public static final String DELETE_EVENT = "delete";
    public static final String PUBLISH_EVENT = "publish";
    public static final String LOGIN_EVENT = "login";
    public static final String LOGOUT_EVENT = "logout";
    public static final String CHECK = "check";

    //типы объектов
    public static final String PERMISSION = "permission";
    public static final String USER = "user";

    private String type;
    private String objectType;
    private String object;
    private String objectId;
    private String userId;
    private String userName;
    private String comment;
    private String url;
    private String ip;
    @JsonSerialize(using = ZonedDateTimeJsonSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeJsonDeserializer.class)
    private ZonedDateTime date;
    private Integer rev;

    public ProtocolEvent(String eventType, String userId, String username, String ip) {
        this.type = eventType;
        this.objectType = USER;
        this.userId = userId;
        this.userName = username;
        this.objectId = userId;
        this.object = username;
        this.date = ZonedDateTime.now();
        this.ip = ip;
    }
}
