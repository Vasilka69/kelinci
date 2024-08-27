package src.unigate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletedUsernameInfo {

    private String username;

    private ZonedDateTime reservationDate;

    private ZonedDateTime deleteDate;

    private DeletedUsernameStatus status;

}
