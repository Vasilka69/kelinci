package src.unigate;

import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import src.unigate.strategy.impl.AuthenticationStrategy;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

public class RegularAuthenticationStrategyImpl implements AuthenticationStrategy {

    @Override
    public UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request, String[] allowRedirectUrls) throws IOException {
        byte[] input = IOUtils.toByteArray(request.getInputStream());
        AccountCredentials credentials = input.length == 0
                ? new AccountCredentials(request.getParameter("username"), request.getParameter("password"), request.getParameter("redirect_uri"))
                : ObjectUtils.fromJson(input, AccountCredentials.class);

        String redirectUri = credentials.getRedirectUri();
        if (!RedirectUrlUtils.isAllowRedirectUrl(redirectUri, allowRedirectUrls)) {
            throw new UnigateAuthenticationException(
                    UnigateAuthenticationException.getErrorRedirect(redirectUri),
                    UnigateAuthenticationException.UnigateAuthenticationErrorType.REDIRECT
            );
        }

        System.out.printf("Попытка авторизации username %s IP %s", credentials.getUsername(), ServletUtils.getClientIP(request));

        return new UnigateAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                Collections.emptyList(),
                credentials.getRedirectUri()
        );
    }
}
