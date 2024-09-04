package src.unigate;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class SettingsPasswordServiceImpl extends AbstractSettingsService<SettingPassword> implements SettingsPasswordService {

    public static final String OBJECT_TYPE = "setting_password";

//    private final SettingPasswordRepository passwordRepository;
//    private final SecuritySettingsRepository securitySettingsRepository;
//    private final UsersLastPasswordRepository usersLastPasswordRepository;
//
//    private final MessageService messageService;
//
//    @Override
//    public SettingPassword getPasswordAttemptsBeforeLock() {
//        return get("attempts_before_lock");
//    }
//
//    @Override
//    public SettingPassword getPasswordMinTime() {
//        return get("min_time");
//    }

    @Override
    public SettingPassword getPasswordMaxTime() {
        return get("max_time");
    }

//    @Override
//    public SettingPassword getPasswordMinLength() {
//        return get("min_length");
//    }
//
//    @Override
//    public SettingPassword getPasswordMaxLength() {
//        return get("max_length");
//    }
//
//    @Override
//    public SettingPassword getPasswordHasDifferentChars() {
//        return get("has_different_chars");
//    }
//
//    @Override
//    public SettingPassword getPasswordHasDifferentCharsCase() {
//        return get("has_different_chars_case");
//    }
//
//    @Override
//    public SettingPassword getPasswordHasDigitsAndLetters() {
//        return get("has_digits_and_letters");
//    }
//
//    @Override
//    public SettingPassword getPasswordHasSpecialChars() {
//        return get("has_special_chars");
//    }
//
//    @Override
//    public SettingPassword getPasswordHasUniqueChars() {
//        return get("has_unique_chars");
//    }
//
//    @Override
//    public SettingPassword getPasswordHasEqualChars() {
//        return get("has_equal_chars");
//    }
//
//    @Override
//    public SettingPassword getPasswordCheckLastCount() {
//        return get("check_last_count");
//    }
//
//    @Override
//    public SettingPassword getPasswordNotEqualName() {
//        return get("not_equal_name");
//    }
//
//    @Override
//    public List<String> getNotPermitPasswords() {
//        SecuritySettings passwordSecuritySettings = securitySettingsRepository.findById("setting.password")
//                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: не найдена настройка безопасности"));
//        return passwordSecuritySettings.getNotPermitPasswords();
//    }
//
//    @Override
//    public List<String> getUserLastPasswords(String userId, Integer size) {
//        Pageable limit = PageRequest.of(0, size);
//        List<UsersLastPassword> lastPasswords = usersLastPasswordRepository.findAllByUserIdOrderByCreateDateDesc(userId, limit);
//        return lastPasswords.stream().map(UsersLastPassword::getPassword).collect(Collectors.toList());
//    }
//
//    @Override
//    protected String getObjectType() {
//        return OBJECT_TYPE;
//    }
//
//    @Override
//    protected MessageService getMessageService() {
//        return messageService;
//    }
//
    @Override
    protected JpaRepository<SettingPassword, String> getRepository() {
        return new JpaRepository<SettingPassword, String>() {
            @Override
            public List<SettingPassword> findAll() {
                return Arrays.asList(
                        new SettingPassword(
                                "max_time",
                                null,
                                false,
                                99999,
                                99999,
                                "99999"
                        )
                );
            }

            @Override
            public List<SettingPassword> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<SettingPassword> findAllById(Iterable<String> strings) {
                return null;
            }

            @Override
            public <S extends SettingPassword> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends SettingPassword> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends SettingPassword> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<SettingPassword> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<String> strings) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public SettingPassword getOne(String s) {
                return null;
            }

            @Override
            public SettingPassword getById(String s) {
                return null;
            }

            @Override
            public SettingPassword getReferenceById(String s) {
                return null;
            }

            @Override
            public <S extends SettingPassword> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends SettingPassword> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<SettingPassword> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends SettingPassword> S save(S entity) {
                return null;
            }

            @Override
            public Optional<SettingPassword> findById(String s) {
                return Optional.of(
                        new SettingPassword(
                                "max_time",
                                null,
                                false,
                                99999,
                                99999,
                                "99999"
                        )
                );
            }

            @Override
            public boolean existsById(String s) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(String s) {

            }

            @Override
            public void delete(SettingPassword entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends String> strings) {

            }

            @Override
            public void deleteAll(Iterable<? extends SettingPassword> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends SettingPassword> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends SettingPassword> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends SettingPassword> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends SettingPassword> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends SettingPassword, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
    }
}
