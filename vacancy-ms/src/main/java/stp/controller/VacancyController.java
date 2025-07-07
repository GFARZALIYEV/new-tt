package stp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import stp.dto.RequestVacancy;
import stp.service.VacancyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacancy")
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping("/apply")
    public String vacancy(@RequestBody RequestVacancy requestVacancy) {
        return vacancyService.apply(requestVacancy);
    }

}
