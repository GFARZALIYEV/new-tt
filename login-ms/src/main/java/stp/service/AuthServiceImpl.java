package stp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stp.client.UserClient;
import stp.dto.LoginDto;
import stp.dto.RefreshTokenDto;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserClient userClient;

    @Override
    public RefreshTokenDto login(LoginDto loginDto) {
        return null;
    }
}
