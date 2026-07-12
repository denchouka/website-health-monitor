package cool.tch.websitehealthmonitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.tch.websitehealthmonitor.entity.TriggerEventBody;
import cool.tch.websitehealthmonitor.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@SpringBootApplication
public class WebsiteHealthMonitorApplication {

    @Autowired
    private TaskService taskService;

    /**
     * 每日存活检查
     */
    private static final String DAILY_HEALTH_CHECK = "daily_health_check";

    /**
     * 周期性健康检查
     */
    private static final String PERIODIC_HEALTH_CHECK = "periodic_health_check";

    public static void main(String[] args) {
        SpringApplication.run(WebsiteHealthMonitorApplication.class, args);
    }

    @RequestMapping("/*")
    public String hello(@RequestHeader Map<String, String> headers,
                        @RequestParam(required = false) Map<String, String> params, @RequestBody(required = true) String body) throws JsonProcessingException {

        System.out.println("我要执行函数啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");
        System.out.println("body = " + body);

        TriggerEventBody triggerEventBody = new ObjectMapper().readValue(body, TriggerEventBody.class);
        String payload = triggerEventBody.getPayload();

        if (DAILY_HEALTH_CHECK.equals(payload)) {
            taskService.dailyHealthCheck();
            return "触发 每日存活检查";
        } else if (PERIODIC_HEALTH_CHECK.equals(payload)) {
            taskService.periodicHealthCheck();
            return "触发 周期性健康检查";
        } else {
            return "触发消息的自定义参数中【payload】不合法";
        }
    }

}
