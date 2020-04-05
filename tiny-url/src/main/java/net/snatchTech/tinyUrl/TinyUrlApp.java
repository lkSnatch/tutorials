package net.snatchTech.tinyUrl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"net.snatchTech.tinyUrl"})
public class TinyUrlApp {

    public static void main( String[] args ) {
        SpringApplication.run(TinyUrlApp.class, args);
    }
}
