package src.unigate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeletedUsername implements Serializable {

    private String username;

    @NotNull(message = "Дата резервации не может быть пустой!")
    private ZonedDateTime reservationDate;

    private ZonedDateTime deleteDate;

    private DeletedUsernameStatus status;
    
}
