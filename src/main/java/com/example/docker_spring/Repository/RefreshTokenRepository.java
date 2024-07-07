package com.example.docker_spring.Repository;

import com.example.docker_spring.Entity.RefreshToken;
import com.example.docker_spring.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Integer userId);

    @Modifying
    int deleteByUser(User user);

}
