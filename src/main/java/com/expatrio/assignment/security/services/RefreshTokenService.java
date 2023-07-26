package com.expatrio.assignment.security.services;

import com.expatrio.assignment.exception.TokenRefreshException;
import com.expatrio.assignment.models.RefreshToken;
import com.expatrio.assignment.models.User;
import com.expatrio.assignment.repository.RefreshTokenRepository;
import com.expatrio.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${expatrio.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            throw new TokenRefreshException(String.valueOf(userId), "No such user exists");
        }

        refreshToken.setUser(user.get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            throw new TokenRefreshException(String.valueOf(userId), "No such user exists");
        }
        return refreshTokenRepository.deleteByUser(user.get());
    }
}
