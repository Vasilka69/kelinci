package src.unigate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Пользователь - ФЛ
 */
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndividualPerson extends User implements Entity<String> {

    private String firstName;

    private String lastName;

    private String patronymic;

    private String post;

    private String email;

    private String phone;

    private UUID photo;

    private LocalDate validTo;

    private String snils;

    private String lastSessionRoleId;

    @SuppressWarnings("java:S107")
    @Builder
    private static IndividualPerson createInstance(
            String id, String username, String password,
            boolean accountNonLocked, boolean changePassword,
            ZonedDateTime deleteDate, Date createDate, Integer sessionMaxCount, ZonedDateTime changePasswordDate,
            BlockReason blockReason, String blockComment, ZonedDateTime lastModified, ZonedDateTime lastAuthDate,
            String firstName, String lastName, String patronymic,
            String post, String email, String phone, UUID photo, LocalDate validTo,
            String snils, String lastSessionRoleId
    ) {
        IndividualPerson user = new IndividualPerson();

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setAccountNonLocked(accountNonLocked);
        user.setChangePassword(changePassword);
        user.setDeleteDate(deleteDate);
        user.setCreateDate(createDate);
        user.setSessionMaxCount(sessionMaxCount);
        user.setChangePasswordDate(changePasswordDate);
        user.setBlockReason(blockReason);
        user.setBlockComment(blockComment);
        user.setLastModified(lastModified);
        user.setLastAuthDate(lastAuthDate);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPatronymic(patronymic);
        user.setPost(post);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPhoto(photo);
        user.setValidTo(validTo);
        user.setSnils(snils);
        user.setLastSessionRoleId(lastSessionRoleId);

        return user;
    }

    public boolean isNameEqualsSnils() {
        if (Objects.isNull(username)) {
            return false;
        }

        return username.equals(snils);
    }
}
