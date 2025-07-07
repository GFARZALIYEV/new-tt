package stp;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
public class LoginMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginMsApplication.class, args);
    }
}