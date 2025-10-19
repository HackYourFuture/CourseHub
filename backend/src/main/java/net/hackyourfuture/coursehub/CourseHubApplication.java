package net.hackyourfuture.coursehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
public class CourseHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseHubApplication.class, args);
    }

}
