package src.unigate;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface IAuthenticationStrategy {

    UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request, String[] allowRedirectUrls) throws IOException;

}
