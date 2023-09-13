package kr.co.fastcampus.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringbootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProjectApplication.class, args);
    }

}
