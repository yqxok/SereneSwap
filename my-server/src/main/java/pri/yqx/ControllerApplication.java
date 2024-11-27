package pri.yqx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
public class ControllerApplication {
    public ControllerApplication() {
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ControllerApplication.class, args);
    }
}
