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

import static cool.tch.websitehealthmonitor.constant.Constants.DAILY_HEALTH_CHECK;
import static cool.tch.websitehealthmonitor.constant.Constants.PERIODIC_HEALTH_CHECK;

@RestController
@SpringBootApplication
public class WebsiteHealthMonitorApplication {

    @Autowired
    private TaskService taskService;

    public static void main(String[] args) {
        SpringApplication.run(WebsiteHealthMonitorApplication.class, args);
    }

    @RequestMapping("/*")
    public void hello(@RequestHeader Map<String, String> headers,
                        @RequestParam(required = false) Map<String, String> params, @RequestBody(required = true) String body) throws JsonProcessingException {

        TriggerEventBody triggerEventBody = new ObjectMapper().readValue(body, TriggerEventBody.class);
        String payload = triggerEventBody.getPayload();

        if (DAILY_HEALTH_CHECK.equals(payload)) {
            taskService.dailyHealthCheck();
        } else if (PERIODIC_HEALTH_CHECK.equals(payload)) {
            taskService.periodicHealthCheck();
        }
    }

}
