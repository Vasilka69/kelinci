package src.unigate.strategy.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;

public interface AuthenticationStrategy {

    UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request, String[] allowRedirectUrls) throws IOException;

}
