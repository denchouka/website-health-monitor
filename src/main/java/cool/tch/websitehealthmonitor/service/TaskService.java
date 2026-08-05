package cool.tch.websitehealthmonitor.service;

import cool.tch.websitehealthmonitor.message.DingTalkRobotGroupMessage;
import cool.tch.websitehealthmonitor.net.HealthCheckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static cool.tch.websitehealthmonitor.constant.Constants.*;

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

    private final HealthCheckService healthCheckService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    public TaskService(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    /**
     * 每日存活检查
     */
    public void dailyHealthCheck() {

        String text = String.format(
                DAILY_HEALTH_CHECK_TEXT, DAILY_HEALTH_CHECK_TITLE
        );
        DingTalkRobotGroupMessage.sendMessage(accessToken, secret, DAILY_HEALTH_CHECK_TITLE, text);
    }

    /**
     * 周期性健康检查
     */
    public void periodicHealthCheck() {
        String currentTime = LocalDateTime.now().format(FORMATTER);
        String response = healthCheckService.websiteCheck(websiteUrl);
        if (HTTP_CODE_OK.equals(response)) {
            return;
        }


        String text = String.format(
                PERIODIC_HEALTH_CHECK_TEXT, PERIODIC_HEALTH_CHECK_TITLE, websiteTitle, websiteUrl, currentTime, PERIODIC_HEALTH_CHECK_RESULT, response
        );
        DingTalkRobotGroupMessage.sendMessage(accessToken, secret, PERIODIC_HEALTH_CHECK_TITLE, text);
    }

}
