package src.unigate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Session implements Entity<UUID> {

    public static final String OBJECT_TYPE = "session";
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault().getRules().getOffset(Instant.now()).getTotalSeconds() == 0
            ? ZoneId.ofOffset(StringUtils.EMPTY, ZoneOffset.UTC)
            : ZoneId.systemDefault();

    private UUID id;

    /** идентификатор сессии */
    private String token;

    /** Ид пользователя */
    private String userId;

    /** Логин пользователя */
    private String username;

    /** ФИО пользователя */
    private String fullName;

    /** Дата и время начала сессии */
    @JsonSerialize(using = ZonedDateTimeJsonSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeJsonDeserializer.class)
    private ZonedDateTime startDate;

    /** Дата и время последней активности */
    @JsonSerialize(using = ZonedDateTimeJsonSerializer.class)
    @JsonDeserialize(using = ZonedDateTimeJsonDeserializer.class)
    private ZonedDateTime activeDate;

    /** IP адрес */
    private String ip;

    /** Система аутентифицировавшая пользователя*/
    private AuthenticationSystem userAuthorizedSystem;
    public ZonedDateTime getStartDate() {
        return startDate.withZoneSameInstant(DEFAULT_ZONE_ID);
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate.withZoneSameInstant(DEFAULT_ZONE_ID);
    }

    public ZonedDateTime getActiveDate() {
        return activeDate.withZoneSameInstant(DEFAULT_ZONE_ID);
    }

    public void setActiveDate(ZonedDateTime activeDate) {
        this.activeDate = activeDate.withZoneSameInstant(DEFAULT_ZONE_ID);
    }
}
