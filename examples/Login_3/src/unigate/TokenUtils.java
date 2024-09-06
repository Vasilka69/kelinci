package src.unigate;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
    private static final String ERROR_TOKEN = "Ошибка обработки токена: {}";
    private static final String SECRET = "9bEYcfLIieA6PjIw7Wur/gT8JKNsSfz5iqAPOsIDNDThXo3FRPNypFu0KfJ2bSwe+Oqe5rXCXJBw4diE22/ddQ==";//old: Secret
    private static final String CHANGE_PASS_SALT = "ViwTGKjVXtYTadMPwBuoVJGFksLooZn82y+dEhFxfMJkTBAkACw7OxgvZp1LAA+lVnlJv49bA4Hh+hc/4dQ0WcN3+FrNf+8Z4Y/QTJq+k6ul2DjqCz5GKRH1WUazJFW+A51VEOFsJmyf4ihn5lDvDVW2AS8Y8bEdPhSsQ9kj67121oNs9+SuilQTC5uKPc6TzLD/ExLkTjnOSMlv8axowFsaT148CLRQ47YBPw==";//old: Chan3ePa$$w0rd
    private static final String TWO_FACTOR_AUTH_SALT = "n0D1FzkEclkczqbwSdCKwrLCLhFCrHi72ZlxtfSLI23B+XSRncrLJ5A3GcMBNsAO2UG+5p5eSbcvVdh1h9Zkvg=="; //old: Tw0Fact0rAuth
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public static String generateAuthorizationToken(String username) {
        return generateToken(username, SECRET);
    }

    public static String generateChangePasswordToken(String username) {
        return generateToken(username, CHANGE_PASS_SALT);
    }

    public static String generateTwoFactorToken(String username) {
        return generateToken(username, TWO_FACTOR_AUTH_SALT);
    }

    private static String generateToken(String username, String secret) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setIssuer(String.valueOf(UUID.randomUUID()))
                .setSubject(username)
                .setExpiration(new Date(currentTimeMillis + EXPIRATION_TIME))
                .setIssuedAt(new Date(currentTimeMillis))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Date getExpirationDate(String token) {
        try {
            return token != null ? Jwts.parser()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration() : null;
        } catch (MalformedJwtException e) {
            log.info(ERROR_TOKEN, token);
            return null;
        } catch (ExpiredJwtException e) {
            // свалится ошибка, если ExpirationDate уже наступил :)
            return e.getClaims().getExpiration();
        }
    }

    public static Date getIssuedAt(String token) {
        try {
            return token != null ? Jwts.parser()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getIssuedAt() : null;
        } catch (MalformedJwtException e) {
            log.info(ERROR_TOKEN, token);
            return null;
        } catch (ExpiredJwtException e) {
            // свалится ошибка, если ExpirationDate уже наступил :)
            return e.getClaims().getExpiration();
        }
    }

    public static String getAuthorizationUsername(String token) {
        return getUsername(token, SECRET);
    }

    public static String getChangePasswordUsername(String token) {
        return getUsername(token, CHANGE_PASS_SALT);
    }

    public static String getTwoFactorUsername(String token) {
        return getUsername(token, TWO_FACTOR_AUTH_SALT);
    }

    private static String getUsername(String token, String secretKey) {
        try {
            return token != null ? Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject() : null;
        } catch (ExpiredJwtException e) {
            log.info("Ошибка обработки токена - токен просрочен: {}", token);
            return null;
        } catch (JwtException e) {
            log.info(ERROR_TOKEN, token);
            return null;
        }
    }
}
