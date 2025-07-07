package stp.service;

import stp.dto.LoginDto;
import stp.dto.RefreshTokenDto;

public interface AuthService {
    RefreshTokenDto login(LoginDto loginDto);
}
