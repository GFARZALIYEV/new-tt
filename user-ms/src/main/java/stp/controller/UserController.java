package stp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stp.client.VacancyClient;
import stp.dto.userdto.LoginDto;
import stp.dto.RefreshTokenDto;
import stp.dto.RequestVacancy;
import stp.dto.userdto.UserRegistrationDto;
import stp.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final VacancyClient vacancyClient;
    private final UserService userService;

    @PostMapping("/apply")
    public ResponseEntity<String> applyToVacancy(@RequestBody RequestVacancy requestVacancy) {
        return userService.applyToVacancy(requestVacancy);
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok(userService.addUser(userRegistrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<RefreshTokenDto> login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello World");
    }


}
