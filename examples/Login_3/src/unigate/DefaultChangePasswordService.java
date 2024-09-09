package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultChangePasswordService implements ChangePasswordService {

//    private final ChangePasswordTokenRepository changePasswordTokenRepository;

    @Override
    public void add(String token) {
//        changePasswordTokenRepository.add(token);
        System.out.printf("Токен %s записан в БД%n", token);
    }

//    @Override
//    public void delete(String token) {
//        changePasswordTokenRepository.delete(token);
//    }
//
//    @Override
//    public boolean exists(String token) {
//        return changePasswordTokenRepository.exists(token);
//    }

}
