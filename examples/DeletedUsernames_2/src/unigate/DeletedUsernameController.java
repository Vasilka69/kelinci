package src.unigate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class DeletedUsernameController {

    private final DeletedUsernameRepository deletedUsernameRepository;
    private final UserRepository userRepository;


    public IdResponse<String> createCard(
            @Valid DeletedUsernameInfo deletedUsernameInfo
    ) {
        if (StringUtils.isEmpty(deletedUsernameInfo.getUsername())) {
            throw new RuntimeException("Не заполнен логин");
        }
        if (deletedUsernameRepository.existsById(deletedUsernameInfo.getUsername())) {
            throw new RuntimeException("Удаляемый логин уже существует");
        }
        if (userRepository.existsByUsernameIgnoreCase(deletedUsernameInfo.getUsername())) {
            throw new RuntimeException("Удаляемый логин уже используется");
        }
        DeletedUsername deletedUsername = new DeletedUsername(
                deletedUsernameInfo.getUsername(),
                deletedUsernameInfo.getReservationDate(),
                deletedUsernameInfo.getDeleteDate(),
                DeletedUsernameStatus.ADDED_MANUALLY
        );

//        deletedUsername = deletedUsernameRepository.save(deletedUsername);
        System.out.println(deletedUsername + " сохранён в БД");

        return new IdResponse<>(deletedUsername.getUsername());
    }
}

