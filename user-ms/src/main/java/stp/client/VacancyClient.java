package stp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import stp.dto.RequestVacancy;

@FeignClient(value = "vacancy-ms", url = "http://localhost:8081/vacancy")
public interface VacancyClient {

    @PostMapping("/apply")
    ResponseEntity<String> applyToVacancy(@RequestBody RequestVacancy requestVacancy);


}
