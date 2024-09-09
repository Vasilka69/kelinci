package src.unigate;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Collections;

//@Service("userDetailsService")
@RequiredArgsConstructor
public class SecurityUserDetailsManager implements UserDetailsManager {
    private static final String ERROR_USER_NOT_FOUND = "Не найден пользователь %s";
    private static final String ERROR_UNSUPPORTED = "Метод не реализован";

    private final UserService userService;

    @Override
    public void createUser(UserDetails user) {
        throw new UnsupportedOperationException(ERROR_UNSUPPORTED);
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException(ERROR_UNSUPPORTED);
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException(ERROR_UNSUPPORTED);
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException(ERROR_UNSUPPORTED);
    }

    public UserDetails loadUserByUsername(String username) {
        try {
            User userData = userService.findByUsername(username);
            if (userData == null) {
                throw new UsernameNotFoundException(String.format(ERROR_USER_NOT_FOUND, username));
            }
            MessageServiceImpl.fillCredentials(userData);

            return new SecurityUser(userData, Collections.emptyList());
        } catch (RuntimeException e) {
            throw new UsernameNotFoundException(String.format(ERROR_USER_NOT_FOUND, username), e);
        }
    }
}
