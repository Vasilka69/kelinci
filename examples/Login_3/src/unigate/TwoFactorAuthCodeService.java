package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TwoFactorAuthCodeService {

//    @Value("${ru.kamatech.unigate.security.two_factor_auth.code_length}")
    private int codeLength = 6;
//    @Value("${ru.kamatech.unigate.security.two_factor_auth.check_attempts}")
//    private int checkAttempts;
//    @Value("${ru.kamatech.unigate.security.two_factor_auth.code_interrupting_time}")
//    private long codeInterruptingTime;

    public static final String ERROR_NOT_EXISTS_CODE = "Код подтверждения не найден!";
    public static final String ERROR_NOT_VALID_CODE = "Код подтверждения не верный!";
    public static final String ERROR_NOT_VALID_CODE_RECREATE = ERROR_NOT_VALID_CODE + " Сгенерирован новый код.";

    private final Map<String, TwoFactorAuthCode> storage = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String addCode(String userId) {
        TwoFactorAuthCode model = new TwoFactorAuthCode();
        model.setCode(createCode(codeLength));
        model.setCreateDate(ZonedDateTime.now());
        model.setCheckCount(0);
        storage.put(userId, model);
        return model.getCode();
    }

//    public boolean existsCode(String userId) {
//        return storage.containsKey(userId);
//    }
//
//    public String getCode(String userId) {
//        TwoFactorAuthCode model = getTwoFactorAuthCode(userId);
//        return model.getCode();
//    }
//
//    public TwoFactorAuthCodeStatus checkCode(String userId, String code) {
//        TwoFactorAuthCode model = storage.get(userId);
//        if (model == null) {
//            return TwoFactorAuthCodeStatus.NOT_EXISTS;
//        }
//        if (!isValid(model)) {
//            return TwoFactorAuthCodeStatus.RECREATE;
//        }
//
//        boolean validateCode = Objects.equals(code, model.getCode());
//        if (!validateCode) {
//            model.setCheckCount(model.getCheckCount() + 1);
//            if (isValidCheckAttempts(model)) {
//                storage.put(userId, model);
//                return TwoFactorAuthCodeStatus.INVALID;
//            } else {
//                return TwoFactorAuthCodeStatus.RECREATE;
//            }
//        }
//        return TwoFactorAuthCodeStatus.VALID;
//    }
//
//    public void deleteCode(String userId) {
//        getTwoFactorAuthCode(userId);
//        storage.remove(userId);
//    }
//
//    @Scheduled(cron = "${ru.kamatech.unigate.security.two_factor_auth.code_interrupting_cron}")
//    public void removeNotActualCodes() {
//        storage.entrySet().stream()
//                .filter(es -> !isValid(es.getValue()))
//                .map(Map.Entry::getKey)
//                .forEach(storage::remove);
//    }

    private String createCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

//    private boolean isValid(TwoFactorAuthCode model) {
//        return isValidCreateDate(model) && isValidCheckAttempts(model);
//    }
//
//    private boolean isValidCreateDate(TwoFactorAuthCode model) {
//        return  !model.getCreateDate().plus(codeInterruptingTime, ChronoUnit.MILLIS).isBefore(ZonedDateTime.now());
//    }
//
//    private boolean isValidCheckAttempts(TwoFactorAuthCode model) {
//        return model.getCheckCount() < checkAttempts;
//    }
//
//    private TwoFactorAuthCode getTwoFactorAuthCode(String userId) {
//        if (!storage.containsKey(userId)) {
//            throw new ResourceNotFoundException(ERROR_NOT_EXISTS_CODE);
//        }
//        return storage.get(userId);
//    }
}
