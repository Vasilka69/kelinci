package src.unigate;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Stream;
public class RedirectUrlUtils {

    public static String getRedirectUri(HttpServletRequest request) {
        if (StringUtils.isNotEmpty(request.getParameter(WebConstants.REDIRECT_URL_PARAMETER))
                && !Objects.equals(request.getParameter(WebConstants.REDIRECT_URL_PARAMETER), "null")) {
            return request.getParameter(WebConstants.REDIRECT_URL_PARAMETER);
        }

        return null;
    }

    public static boolean isAllowRedirectUrl(String redirectUri, String[] allowRedirectUrls) {
        if (ArrayUtils.isEmpty(allowRedirectUrls) || StringUtils.isEmpty(redirectUri)) {
            return true;
        }

        return Stream.of(allowRedirectUrls).anyMatch(allowRedirectUrl -> isSameUrl(redirectUri, allowRedirectUrl, false));
    }
//
//    public static boolean isAllowUserRedirectUrl(String redirectUri, String[] allowRedirectUrls) {
//        if (ArrayUtils.isEmpty(allowRedirectUrls)) {
//            return false;
//        }
//
//        if (StringUtils.isEmpty(redirectUri)) {
//            return true;
//        }
//
//        return Stream.of(allowRedirectUrls).anyMatch(allowRedirectUrl -> isSameUrl(redirectUri, allowRedirectUrl, true));
//    }
//
    private static boolean isSameUrl(String first, String second, boolean checkPath) {
        if (StringUtils.isEmpty(first) || StringUtils.isEmpty(second)) {
            return false;
        }
        URI firstURI = getURI(first);
        URI secondURI = getURI(second);

        if (Objects.nonNull(firstURI) && Objects.nonNull(secondURI)) {
            ArrayList<BiFunction<URI, URI, Boolean>> filters = new ArrayList<BiFunction<URI, URI, Boolean>>();
            filters.add((a, b) -> Objects.equals(a.getHost(), b.getHost()));
            filters.add((a, b) -> Objects.equals(a.getScheme(), b.getScheme()));
            filters.add((a, b) -> Objects.equals(a.getPort(), b.getPort()));
            if (checkPath) {
                filters.add((a, b) -> Objects.equals(a.getPath(), b.getPath()) || StringUtils.contains(a.getPath(), b.getPath()));
            }

            return filters.stream().allMatch(it -> it.apply(firstURI, secondURI));
        } else if (!checkPath) {
            return false;
        } else if (Objects.nonNull(firstURI)) {
            return StringUtils.contains(firstURI.getPath(), second);
        } else if (Objects.nonNull(secondURI)) {
            return StringUtils.contains(secondURI.getPath(), first);
        } else {
            return StringUtils.contains(first, second) || StringUtils.contains(second, first);
        }
    }

    private static URI getURI(String url) {
        try {
            return new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return null;
        }
    }
}
