package src.unigate;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

//@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {
//    private static final String ERROR_TOKEN = "Ошибка обработки токена: {}";
    private static final String ERROR_TOKEN = "Ошибка обработки токена: %s%n";
    private static final String SECRET = "SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET0SECRET";
    private static final String CHANGE_PASS_SALT = "CHANGE0PASS0SALT0CHANGE0PASS0SALT0CHANGE0PASS0SALT0CHANGE0PASS0SALT0CHANGE0PASS0SALT0CHANGE0PASS0SALT";
    private static final String TWO_FACTOR_AUTH_SALT = "TWO0FACTOR0AUTH0SALT0TWO0FACTOR0AUTH0SALT0TWO0FACTOR0AUTH0SALT0TWO0FACTOR0AUTH0SALT0TWO0FACTOR0AUTH0SALT0TWO0FACTOR0AUTH0SALT";
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
//            log.info(ERROR_TOKEN, token);
            System.out.printf(ERROR_TOKEN, token);
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
//            log.info(ERROR_TOKEN, token);
            System.out.printf(ERROR_TOKEN, token);
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
//            log.info("Ошибка обработки токена - токен просрочен: {}", token);
            System.out.printf("Ошибка обработки токена - токен просрочен: %s%n", token);
            return null;
        } catch (JwtException e) {
//            log.info(ERROR_TOKEN, token);
            System.out.printf(ERROR_TOKEN, token);
            return null;
        }
    }
}
