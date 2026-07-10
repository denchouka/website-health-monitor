package cool.tch.websitehealthmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@SpringBootApplication
public class WebsiteHealthMonitorApplication {

    /**
     * 每日存活检查
     */
    private static final String DAILY_HEALTH_CHECK = "daily_health_check";

    /**
     * 周期性健康检查
     */
    private static final String PERIODIC_HEALTH_CHECK = "periodic_health_check";

    /**
     * 触发消息将会作为 event 中的参数 payload
     */
    private static final String PARAM_KEY_PAYLOAD = "payload";

    public static void main(String[] args) {
        SpringApplication.run(WebsiteHealthMonitorApplication.class, args);
    }

    @RequestMapping("/*")
    public String hello(@RequestHeader Map<String, String> headers,
                        @RequestParam(required = false) Map<String, String> params, @RequestBody(required = false) String body) {


        if (params != null && params.containsKey(PARAM_KEY_PAYLOAD)) {

            String payload = params.get(PARAM_KEY_PAYLOAD);
            if (DAILY_HEALTH_CHECK.equals(payload)) {
                // do something
                return "触发 每日存活检查";
            } else if (PERIODIC_HEALTH_CHECK.equals(payload)) {
                // do something
                return "触发 周期性健康检查";
            } else {
                return "触发消息的自定义参数中【payload】不合法";
            }
        } else {
            return "触发消息的自定义参数中【payload】未指定";
        }
    }

}
