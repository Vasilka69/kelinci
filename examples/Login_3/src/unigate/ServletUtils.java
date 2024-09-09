package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServletUtils {
    public static final String ANONYMOUS_USER = "anonymousUser";

    public static String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public static String trimToken(String token) {
        if (token == null) return null;
        return token.replace(TokenContext.PREFIX, "").trim();
    }

    public static String bearerToken(String token) {
        if (token == null) return null;
        return TokenContext.PREFIX + " " + trimToken(token);
    }

    public static String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) return null;
        String token = (String) authentication.getPrincipal();
        return !StringUtils.startsWithIgnoreCase(token, ANONYMOUS_USER) ? token : null;
    }

    public static String getToken(HttpServletRequest request) {
        return getToken(request, null);
    }

    public static String getToken(HttpServletRequest request, String tokenParam) {
        if (request.getHeader(AUTHORIZATION) != null) {
            return getTokenFromHeader(request);
        } else if (getCookie(request) != null) {
            return getTokenFromCookie(request);
        } else if (tokenParam != null) {
            return getTokenFromParameter(request, tokenParam);
        }

        return null;
    }

    public static void setToken(HttpServletResponse response, String token) {
        setTokenToHeader(response, token);
        setTokenToCookie(response, token);
    }

    public static void removeToken(HttpServletRequest request, HttpServletResponse response) {
        removeTokenFromHeader(request, response);
        removeTokenFromCookie(request, response);
    }

    public static String getTokenFromParameter(HttpServletRequest request, String parameter) {
        if (parameter != null) {
            return trimToken(request.getParameter(parameter));
        }
        return null;
    }

    public static String getTokenFromHeader(HttpServletRequest request) {
        if (request.getHeader(AUTHORIZATION) != null) {
            return trimToken(request.getHeader(AUTHORIZATION));
        }
        return null;
    }

    public static void setTokenToHeader(HttpServletResponse response, String token) {
        if (token == null) return;
        response.addHeader(AUTHORIZATION, bearerToken(token));
    }

    public static void removeTokenFromHeader(HttpServletRequest request, HttpServletResponse response) {
        if (getTokenFromHeader(request) != null) {
            response.addHeader(AUTHORIZATION, "");
        }
    }

    public static String getTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = getCookie(request);
        if (cookie != null) {
            return trimToken(cookie.getValue());
        }
        return null;
    }

    public static void setTokenToCookie(HttpServletResponse response, String token) {
        if (token == null) return;
        response.addCookie(createCookie(token, null));
    }

    public static void removeTokenFromCookie(HttpServletRequest request, HttpServletResponse response) {
        if (getTokenFromCookie(request) != null) {
            response.addCookie(createCookie(null, 0));
        }
    }

    public static String getToken(HttpHeaders headers) {
        if (headers.containsKey(AUTHORIZATION)) {
            return trimToken(headers.getFirst(AUTHORIZATION));
        }
        return null;
    }

    public static String getLocation(HttpHeaders headers) {
        if (headers.containsKey(LOCATION)) {
            return headers.getFirst(LOCATION);
        }
        return null;
    }

    private static Cookie getCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Stream.of(request.getCookies())
                    .filter(cookie -> Objects.equals(cookie.getName(), AUTHORIZATION))
                    .findFirst().orElse(null);
        }
        return null;
    }

    private static Cookie createCookie(String token, Integer maxAge) {
        Cookie cookie = new Cookie(AUTHORIZATION, trimToken(token));
        cookie.setPath("/");
        if (maxAge != null) {
            cookie.setMaxAge(maxAge);
        }
        return cookie;
    }
}
