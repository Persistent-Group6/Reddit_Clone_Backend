package com.grp6.reddit_clone.service;

import java.time.Instant;
import java.util.UUID;

import com.grp6.reddit_clone.exceptions.SpringRedditException;
import com.grp6.reddit_clone.model.RefreshToken;
import com.grp6.reddit_clone.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository ;
    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken() ;
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String Token){
        refreshTokenRepository.findByToken(Token).orElseThrow(()->new SpringRedditException("Invalid Refresh Token")) ;
    }

    public void deleteRefreshToken(String Token){
        refreshTokenRepository.deleteByToken(Token);
    }
}
