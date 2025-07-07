package stp.service;

import org.springframework.http.ResponseEntity;
import stp.dto.userdto.LoginDto;
import stp.dto.RefreshTokenDto;
import stp.dto.RequestVacancy;
import stp.dto.userdto.UserRegistrationDto;

public interface UserService {
    ResponseEntity<String> applyToVacancy(RequestVacancy requestVacancy);

    ResponseEntity<RefreshTokenDto> login(LoginDto loginDto);

    String addUser(UserRegistrationDto userRegistrationDto);
}
