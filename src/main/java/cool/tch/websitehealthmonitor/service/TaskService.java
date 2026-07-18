package cool.tch.websitehealthmonitor.service;

import cool.tch.websitehealthmonitor.message.DingTalkRobotGroupMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TaskService {

    /**
     * 机器人应用的access_token的值
     */
    @Value("${dingtalk.access_token}")
    private String accessToken;

    /**
     * 安全设置，加签密钥
     */
    @Value("${dingtalk.secret}")
    private String secret;

    /**
     * 要监测网站的url
     */
    @Value("${dingtalk.website_url}")
    private String websiteUrl;

    /**
     * 要监测网站的title
     */
    @Value("${dingtalk.website_title}")
    private String websiteTitle;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 每日存活检查
     */
    public void dailyHealthCheck() {

        String title = "⚙️网站健康监测服务 · 每日存活检查";
        String text = String.format(
                """
                #### %s
                ---
                🕗 早上好！
                网站健康监测服务 正常运行中 ☀️
                """,
                title
        );
        DingTalkRobotGroupMessage.sendMessage(accessToken, secret, title, text);
    }

    /**
     * 周期性健康检查
     */
    public void periodicHealthCheck() {
        String currentTime = LocalDateTime.now().format(FORMATTER);
        try {
            int responseStatus = websiteCheck();
            if (responseStatus == HttpStatus.OK.value()) {
                return;
            }

            String title = "⚙️网站健康监测 · 异常告警";
            String monitorResult = "网站不可用";
            String text = String.format(
                    """
                    #### %s
                    ---
                    🌐监测网站：  *[%s](%s)*
                    ⏰监测时间：   *%s*
                    🛠️监测结果：  🚨*%s*
                    📢响应状态：  🔴*%d*
                    
                    ---
                    ⚠️ 请立即关注，尽快排查网站服务状态
                    """,
                    title, websiteTitle, websiteUrl, currentTime, monitorResult, responseStatus
            );
            DingTalkRobotGroupMessage.sendMessage(accessToken, secret, title, text);
        } catch (Exception e) {
            System.out.println("网站健康监测失败。。。");
            e.printStackTrace();
        }

    }

    /**
     * 执行监测
     *
     * @return 监测结果
     */
    private int websiteCheck() throws Exception {
        // 去除末尾多余的斜杠
        String baseUrl = websiteUrl.endsWith("/") ? websiteUrl.substring(0, websiteUrl.length() - 1) : websiteUrl;

        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

        // 模拟用户访问首页（全链路检测）
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .timeout(Duration.ofSeconds(10))
                .header("User-Agent", "Website-Health-Monitor/1.0")
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.statusCode();
    }

}
