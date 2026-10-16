package cool.tch.websitehealthmonitor.net;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static cool.tch.websitehealthmonitor.constant.Constants.*;

@Service
public class HealthCheckService {

    private final HttpClient httpClient;

    public HealthCheckService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 执行监测
     *
     * @return 监测结果
     */
    public String websiteCheck(String websiteUrl) {
        HttpRequest httpRequest = buildRequest(websiteUrl);

        int attempt = 0;
        while(attempt < REQUEST_MAX_COUNT) {
            try {

                HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                return String.valueOf(httpResponse.statusCode());
            } catch (Exception e) {

                // 仅对特定异常进行重试
                if (isRetryable(e)) {
                    attempt ++;

                    // 等待一会再重试
                    try {
                        Thread.sleep(THREAD_SLEEP_TIME);
                    } catch (InterruptedException ex) {
                        return "Retry interrupted: " + e.getMessage();
                    }

                    // 达到最大次数，直接返回
                    if (attempt == REQUEST_MAX_COUNT) {
                        return e.getMessage();
                    }
                } else {
                    // 不可重试异常，直接返回
                    return "Non-retryable error: " + e.getMessage();
                }
            }
        }

        return null;
    }

    private HttpRequest buildRequest(String websiteUrl) {
        // 去除末尾多余的斜杠
        String baseUrl = websiteUrl.endsWith("/") ? websiteUrl.substring(0, websiteUrl.length() - 1) : websiteUrl;

        return HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .timeout(Duration.ofSeconds(HTTP_REQUEST_TIMEOUT))   // 整体请求超时 10 秒
                // 模拟浏览器请求头
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Cache-Control", "max-age=0")
                .GET()
                .build();
    }

    /**
     * 判断异常是否可重试
     */
    private boolean isRetryable(Exception e) {
        String msg = e.getMessage();
        return msg != null &&
                (msg.contains("connect timed out") ||
                        msg.contains("Network is unreachable") ||
                        msg.contains("Connection refused"));
    }
}
