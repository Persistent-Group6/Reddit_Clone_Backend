package com.grp6.reddit_clone.repository;

import java.util.Optional;

import com.grp6.reddit_clone.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
    Optional<RefreshToken> findByToken(String Token) ;

    void deleteByToken(String Token) ;
}
