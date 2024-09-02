package src.unigate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.kamatech.unigate.common.exception.HttpClientException;
import ru.kamatech.unigate.common.utils.RestTemplateUtils;
import ru.kamatech.unigate.common.utils.ServletUtils;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class HttpClient {
    private static final RestTemplate restTemplate = RestTemplateUtils.getRestTemplate();

    public static <T> HttpEntity<T> createRequest(String token) {
        return createRequest(token, null);
    }

    public static <T> HttpEntity<T> createRequest(String token, T body) {
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            addTokenHeader(headers, token);
        }
        return new HttpEntity<>(body, headers);
    }

    public static void addTokenHeader(HttpHeaders headers, String token) {
        if (StringUtils.hasText(token)) {
            headers.add(HttpHeaders.AUTHORIZATION, ServletUtils.bearerToken(token));
        }
    }

    public static <T> ResponseEntity<T> sendGetRequest(String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(null, null, url, HttpMethod.GET, clazz);
    }

    public static <T> ResponseEntity<T> sendGetRequest(String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(null, null, url, HttpMethod.GET, responseType);
    }

    public static <T> ResponseEntity<T> sendGetRequest(Object request, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(request, null, url, HttpMethod.GET, clazz);
    }

    public static <T> ResponseEntity<T> sendGetRequest(Object request, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(request, null, url, HttpMethod.GET, responseType);
    }

    public static <T> ResponseEntity<T> sendGetRequest(Object request, HttpHeaders headers, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.GET, clazz);
    }

    public static <T> ResponseEntity<T> sendGetRequest(Object request, HttpHeaders headers, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.GET, responseType);
    }

    public static <T> ResponseEntity<T> sendPostRequest(String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(null, null, url, HttpMethod.POST, clazz);
    }

    public static <T> ResponseEntity<T> sendPostRequest(String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(null, null, url, HttpMethod.POST, responseType);
    }

    public static <T> ResponseEntity<T> sendPostRequest(Object request, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(request, null, url, HttpMethod.POST, clazz);
    }

    public static <T> ResponseEntity<T> sendPostRequest(Object request, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(request, null, url, HttpMethod.POST, responseType);
    }

    public static <T> ResponseEntity<T> sendPostRequest(Object request, HttpHeaders headers, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.POST, clazz);
    }

    public static <T> ResponseEntity<T> sendPostRequest(Object request, HttpHeaders headers, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.POST, responseType);
    }

    public static <T> ResponseEntity<T> sendPostRequest(MultiValueMap<String, Object> request, HttpHeaders headers, String url, Class<T> responseType) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.POST, responseType);
    }

    public static <T> ResponseEntity<T> sendPutRequest(Object request, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(request, null, url, HttpMethod.PUT, clazz);
    }

    public static <T> ResponseEntity<T> sendPutRequest(Object request, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(request, null, url, HttpMethod.PUT, responseType);
    }

    public static <T> ResponseEntity<T> sendPutRequest(Object request, HttpHeaders headers, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.PUT, clazz);
    }

    public static <T> ResponseEntity<T> sendPutRequest(Object request, HttpHeaders headers, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.PUT, responseType);
    }

    public static <T> ResponseEntity<T> sendDeleteRequest(String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(null, null, url, HttpMethod.DELETE, clazz);
    }

    public static <T> ResponseEntity<T> sendDeleteRequest(String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(null, null, url, HttpMethod.DELETE, responseType);
    }

    public static <T> ResponseEntity<T> sendDeleteRequest(HttpHeaders headers, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(null, headers, url, HttpMethod.DELETE, clazz);
    }

    public static <T> ResponseEntity<T> sendDeleteRequest(HttpHeaders headers, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(null, headers, url, HttpMethod.DELETE, responseType);
    }

    public static <T> ResponseEntity<T> sendDeleteRequest(Object request, HttpHeaders headers, String url, Class<T> clazz) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.DELETE, clazz);
    }

    public static <T> ResponseEntity<T> sendDeleteRequest(Object request, HttpHeaders headers, String url, ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return sendRequest(request, headers, url, HttpMethod.DELETE, responseType);
    }

    public static <T> ResponseEntity<T> sendRequest(@Nullable Object request, @Nullable HttpHeaders headers, @NonNull String url, @NonNull HttpMethod method, @NonNull Class<T> clazz) throws HttpClientException {
        return executeRequest(request, headers, url, method, clazz, null);
    }

    public static <T> ResponseEntity<T> sendRequest(@Nullable Object request, @Nullable HttpHeaders headers, @NonNull String url, @NonNull HttpMethod method, @NonNull ParameterizedTypeReference<T> responseType) throws HttpClientException {
        return executeRequest(request, headers, url, method, null, responseType);
    }

    private static <T> ResponseEntity<T> executeRequest(@Nullable Object request, @Nullable HttpHeaders headers, @NonNull String url, @NonNull HttpMethod method, @Nullable Class<T> responseClazz, @Nullable ParameterizedTypeReference<T> responseType) throws HttpClientException {
        log.trace(String.format("Выполнение запроса %s %s", method, url));
        var totalWatch = new StopWatch();
        totalWatch.start();
        HttpEntity<?> httpRequest = readRequest(request);
        var requestHeaders = readHeaders(httpRequest, headers);
        var requestEntity = new HttpEntity<>(httpRequest.getBody(), requestHeaders);

        try {
            ResponseEntity<T> responseEntity;
            if (responseClazz != null) {
                responseEntity = restTemplate.exchange(url, method, requestEntity, responseClazz);
            } else if (responseType != null) {
                responseEntity = restTemplate.exchange(url, method, requestEntity, responseType);
            } else {
                log.info(String.format("Запрос сформирован некорректно %s %s: Не определен тип данных для результата запроса", method, url));
                throw httpException("Не определен тип данных для результата запроса");
            }
            if (HttpStatus.SERVICE_UNAVAILABLE.equals(responseEntity.getStatusCode()) || HttpStatus.NOT_FOUND.equals(responseEntity.getStatusCode())) {
                log.info(String.format("Ошибка выполнения запроса %s %s: %s", method, url, responseEntity.getBody()));
                throw httpException(responseEntity.getBody());
            }
            if (HttpStatus.BAD_REQUEST.equals(responseEntity.getStatusCode()) || HttpStatus.INTERNAL_SERVER_ERROR.equals(responseEntity.getStatusCode())) {
                log.info(String.format("Не удалось выполнить запрос %s %s: %s", method, url, responseEntity.getBody()));
                throw httpException(responseEntity.getBody());
            }
            totalWatch.stop();
            log.trace(String.format("Запрос %s %s выполнен за %s ms", method, url, totalWatch.getTotalTimeMillis()));
            return responseEntity;
        } catch (Exception e) {
            if (totalWatch.isRunning()) {
                totalWatch.stop();
            }
            log.trace(String.format("Запрос %s %s выполнен c ошибкой за %s ms: %s", method, url, totalWatch.getTotalTimeMillis(), e.getMessage()));
            if (e instanceof HttpStatusCodeException) {
                throw e;
            }
            throw httpException(e.getMessage(), e);
        }
    }


    private static HttpEntity<?> readRequest(Object request) {
        if (request == null) {
            return new HttpEntity<>(null, new HttpHeaders());
        } else if (request instanceof HttpEntity) {
            return (HttpEntity<?>) request;
        } else {
            return new HttpEntity<>(request, new HttpHeaders());
        }
    }

    private static HttpHeaders readHeaders(HttpEntity<?> request, HttpHeaders headers) {
        var requestHeaders = new HttpHeaders();
        if (request != null && !CollectionUtils.isEmpty(request.getHeaders())) {
            requestHeaders.setAll(request.getHeaders().toSingleValueMap());
        }
        if (!CollectionUtils.isEmpty(headers)) {
            requestHeaders.setAll(headers.toSingleValueMap());
        }
        if (requestHeaders.getContentType() == null) {
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        }
        if (requestHeaders.getAccept().isEmpty()) {
            requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA));
        }
        return requestHeaders;
    }

    private static HttpClientException httpException(Object message) {
        return new HttpClientException(String.format("%s", message));
    }

    private static HttpClientException httpException(Object message, Throwable cause) {
        return new HttpClientException(String.format("%s", message), cause);
    }
}
