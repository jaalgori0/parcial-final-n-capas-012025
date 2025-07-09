package com.uca.parcialfinalncapas.service.impl;

import com.uca.parcialfinalncapas.entities.Token;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.repository.TokenRepository;
import com.uca.parcialfinalncapas.service.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void saveToken(User user, String jwt) {
        Token token = Token.builder()
                .token(jwt)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(1))
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeToken(String jwt) {
        Token token = tokenRepository.findByToken(jwt)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        token.setRevoked(true);
        tokenRepository.save(token);
    }

    @Override
    public boolean isTokenValid(String jwt) {
        return tokenRepository.findByToken(jwt)
                .filter(t -> !t.getRevoked() && t.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();
    }
}
