package sellapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"service","controller"})
@EnableJpaRepositories(basePackages = {"repository"})
@EntityScan(basePackages = {"dataobject"})
public class sellApplication {
    public static void main(String[] args) {
        SpringApplication.run(sellApplication.class,args);
    }
}
