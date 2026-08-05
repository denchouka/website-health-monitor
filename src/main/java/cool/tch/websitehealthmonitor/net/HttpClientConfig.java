package cool.tch.websitehealthmonitor.net;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.net.http.HttpClient;
import java.time.Duration;

import static cool.tch.websitehealthmonitor.constant.Constants.HTTP_CLIENT_TIMEOUT;

@Configuration
public class HttpClientConfig {

    @Bean
    @Lazy
    public HttpClient httpClient() {
        return HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(HTTP_CLIENT_TIMEOUT)).build();
    }
}
