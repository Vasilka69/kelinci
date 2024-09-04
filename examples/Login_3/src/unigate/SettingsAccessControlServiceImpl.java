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
public class SettingsAccessControlServiceImpl extends AbstractSettingsService<SettingAccessControl> implements SettingsAccessControlService {

//    public static final String OBJECT_TYPE = "setting_access_control";

//    private final SettingAccessControlRepository accessControlRepository;
//    private final MessageService messageService;

//    @Override
//    public SettingAccessControl getInterruptingTime() {
//        return get("interrupting_time");
//    }
//
//    @Override
//    public SettingAccessControl getUserSessionMaxCount() {
//        return get("user_session_max_count");
//    }

    @Override
    public SettingAccessControl getTwoFactorAuth() {
        return get("two_factor_auth");
    }

//    @Override
//    public SettingAccessControl getBlockNotActiveUser() {
//        return get("block_not_active_user");
//    }
//
//    @Override
//    public SettingAccessControl getBlockRepeatedUserId() {
//        return get("block_repeated_user_id");
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

    @Override
    protected JpaRepository<SettingAccessControl, String> getRepository() {
        return new JpaRepository<SettingAccessControl, String>() {
            @Override
            public List<SettingAccessControl> findAll() {
                return Arrays.asList(new SettingAccessControl(
                    "two_factor_auth",
                    null,
                    true,
                    99999
                ));
            }

            @Override
            public List<SettingAccessControl> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<SettingAccessControl> findAllById(Iterable<String> strings) {
                return null;
            }

            @Override
            public <S extends SettingAccessControl> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends SettingAccessControl> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends SettingAccessControl> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<SettingAccessControl> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<String> strings) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public SettingAccessControl getOne(String s) {
                return null;
            }

            @Override
            public SettingAccessControl getById(String s) {
                return null;
            }

            @Override
            public SettingAccessControl getReferenceById(String s) {
                return null;
            }

            @Override
            public <S extends SettingAccessControl> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends SettingAccessControl> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<SettingAccessControl> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends SettingAccessControl> S save(S entity) {
                return null;
            }

            @Override
            public Optional<SettingAccessControl> findById(String s) {
                return Optional.of(new SettingAccessControl(
                        "two_factor_auth",
                        null,
                        true,
                        99999
                ));
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
            public void delete(SettingAccessControl entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends String> strings) {

            }

            @Override
            public void deleteAll(Iterable<? extends SettingAccessControl> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends SettingAccessControl> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends SettingAccessControl> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends SettingAccessControl> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends SettingAccessControl> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends SettingAccessControl, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        };
    }
}
