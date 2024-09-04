package src.unigate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UnigateAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String redirectUri;

    public UnigateAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String redirectUri) {
        super(principal, credentials, authorities);
        this.redirectUri = redirectUri;
    }
}
