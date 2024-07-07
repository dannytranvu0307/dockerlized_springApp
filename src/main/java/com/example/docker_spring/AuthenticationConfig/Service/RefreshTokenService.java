package com.example.docker_spring.AuthenticationConfig.Service;

import com.example.docker_spring.Entity.RefreshToken;
import com.example.docker_spring.Exception.TokenRefreshException;
import com.example.docker_spring.Repository.RefreshTokenRepository;
import com.example.docker_spring.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${app.jwtRefreshExpiration}")
    private Long refreshTokenDuration;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Integer userId) {
        RefreshToken refreshToken = new RefreshToken();
        if (refreshTokenRepository.findByUserId(userId).isEmpty()) {
            refreshToken.setUser(userRepository.findById(userId).get());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration*1000));
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            refreshToken = refreshTokenRepository.findByUserId(userId).get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration*1000));
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken = refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }

    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(),
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Integer userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}