package cool.tch.websitehealthmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WebsiteHealthMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteHealthMonitorApplication.class, args);
    }

}
