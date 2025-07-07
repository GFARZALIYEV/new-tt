package stp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stp.client.VacancyClient;
import stp.dto.userdto.LoginDto;
import stp.dto.RefreshTokenDto;
import stp.dto.RequestVacancy;
import stp.dto.userdto.UserRegistrationDto;
import stp.mapper.UserMapper;
import stp.model.Authority;
import stp.model.RefreshToken;
import stp.model.User;
import stp.repository.AuthorityRepository;
import stp.repository.RefreshTokenRepository;
import stp.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final VacancyClient vacancyClient;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<String> applyToVacancy(RequestVacancy requestVacancy) {
        User user = userRepository.findById(requestVacancy.getUserId())
                .orElseThrow(() -> new RuntimeException("User with id " + requestVacancy.getUserId() + " not found"));
        try {
            vacancyClient.applyToVacancy(requestVacancy);
            Set<Long> vacancyIds = user.getVacancyIds();
            if (vacancyIds == null) {
                vacancyIds = new HashSet<>();
            }
            vacancyIds.add(requestVacancy.getVacancyId());
            user.setVacancyIds(vacancyIds);
            userRepository.save(user);
            return ResponseEntity.ok("Vacancy applied successfully");
        } catch (Exception e) {
            throw new RuntimeException("Vacancy application failed");
        }

    }

    @Override
    public ResponseEntity<RefreshTokenDto> login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            //todo loginHistorySaver(user, request);
            String accessToken = jwtService.generateToken(user);
            RefreshToken refreshToken = refreshTokenService.createAndSaveRefreshToken(user, accessToken);
            return ResponseEntity.ok(new RefreshTokenDto(accessToken, refreshToken.getRefreshToken()));
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public String addUser(UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        Optional<User> byEmail = userRepository.findByEmail(userRegistrationDto.getEmail());
        if (byEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = userMapper.regDtoToUser(userRegistrationDto);
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        Authority authority = authorityRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("No authority found"));
        user.setAuthorities(Set.of(authority));
        log.info("User: {}", user);

        userRepository.save(user);
//        user.setEmail(userRegistrationDto.getEmail());
//        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
//        user.setUsername(userRegistrationDto.getName());
//        user.setSurname(userRegistrationDto.getSurname());
//        user.setSeriaNumber(userRegistrationDto.getSeriaNumber());
//        user.setPhone(userRegistrationDto.getPhone());
        return "Successfully created user";
    }
}
