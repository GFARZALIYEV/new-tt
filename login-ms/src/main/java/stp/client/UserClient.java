package stp.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "vacancy-ms", url = "http://localhost:8080/users")
public interface UserClient {
}
