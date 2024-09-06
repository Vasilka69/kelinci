package src.unigate;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class TwoFactorAuthCode {
    private String code;
    private ZonedDateTime createDate;
    private int checkCount;
}
