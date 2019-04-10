package com.example.granny;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GrannyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrannyApplication.class, args);
    }

}
