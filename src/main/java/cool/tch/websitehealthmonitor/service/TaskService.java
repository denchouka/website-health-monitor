package cool.tch.websitehealthmonitor.service;

import cool.tch.websitehealthmonitor.message.DingTalkRobotGroupMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Value("${dingtalk.access_token}")
    private String accessToken;

    @Value("${dingtalk.secret}")
    private String secret;

    /**
     * 每日存活检查
     */
    public void dailyHealthCheck() {
        DingTalkRobotGroupMessage.sendMessage(accessToken, secret, "每日存活检查");
    }

    /**
     * 周期性健康检查
     */
    public void periodicHealthCheck() {
        DingTalkRobotGroupMessage.sendMessage(accessToken, secret, "周期性健康检查");
    }
}
