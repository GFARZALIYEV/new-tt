package stp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stp.model.RefreshToken;
import stp.model.User;
import stp.repository.RefreshTokenRepository;
import stp.util.RandomTokenUtil;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createAndSaveRefreshToken(User user, String accessToken) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(RandomTokenUtil.generateToken())
                .accessToken(accessToken)
                .user(user)
                .expiresAt(new Date(System.currentTimeMillis() + 1000L * 60 * 5))
                .build();

        refreshTokenRepository.save(refreshToken);
        return refreshToken;    }

}
