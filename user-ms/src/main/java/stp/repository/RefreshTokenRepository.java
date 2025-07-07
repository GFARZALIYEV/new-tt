package stp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stp.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);

}
