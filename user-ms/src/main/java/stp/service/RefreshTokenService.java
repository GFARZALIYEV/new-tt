package stp.service;

import stp.model.RefreshToken;
import stp.model.User;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken createAndSaveRefreshToken(User user, String accessToken);
}
