package cool.tch.websitehealthmonitor.service;

import cool.tch.websitehealthmonitor.message.DingTalkRobotGroupMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

        String currentTime = LocalDateTime.now().format(FORMATTER);
        String status = "200";
        String statusEmoji = "200".equals(status) ? "✅" : "🚨";
        String statusText = "200".equals(status) ? "正常" : "异常 (" + status + ")";

        String content = String.format(
                "【%s】每日存活检查\n\n" +
                        "| **触发器类型** | 每日存活检查 (08:00) |\n" +
                        "| **监测时间** | %s |\n" +
                        "| **网站状态** | %s %s |\n\n" +
                        "> 💡 服务运行正常，请继续保持。",
                websiteTitle, currentTime, statusEmoji, statusText
        );
        DingTalkRobotGroupMessage.sendMessage(accessToken, secret, content);
    }

    /**
     * 周期性健康检查
     */
    public void periodicHealthCheck() {
        String currentTime = LocalDateTime.now().format(FORMATTER);
        String statusEmoji = "🚨";
        String status = "200";

        String content = String.format(
                "【%s】网站健康检查告警\n\n" +
                        "| **触发器类型** | 周期性健康检查 (每5分钟) |\n" +
                        "| **监测时间** | %s |\n" +
                        "| **网站状态** | %s 异常 (HTTP %s) |\n\n" +
                        "> 🔔 请立即检查网站服务是否正常运行！",
                websiteTitle, currentTime, statusEmoji, status
        );
        DingTalkRobotGroupMessage.sendMessage(accessToken, secret, content);
    }

}
