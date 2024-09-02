package src.unigate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestTemplateUtils {
    private static RestTemplate restTemplate;

    public static synchronized RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = buildRestTemplate();
        }

        return restTemplate;
    }

    private static RestTemplate buildRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        var messageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverter.setWriteAcceptCharset(false);
        restTemplate.getMessageConverters().add(0, messageConverter);

        try {
            SSLContexts sslContext = SSLContexts
                    .custom()
                    .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                    .build();

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

            //TODO: пока сделано так для поднятия версии
            HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.ofMinutes(5)).build())
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .disableRedirectHandling()
                            .build();

            var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setConnectTimeout(300 * 1000);

            restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));

        } catch (Exception e) {
            // do nothing
        }
        return restTemplate;
    }
}
