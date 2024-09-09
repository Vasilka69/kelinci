package src.unigate;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;

public interface IAuthenticationStrategy {

    UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request, String[] allowRedirectUrls) throws IOException;

}
