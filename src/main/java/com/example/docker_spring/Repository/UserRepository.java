package com.example.docker_spring.Repository;

import com.example.docker_spring.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer userId);

    Optional<User> findByVerifyCode(String verifyCode);

    @Modifying
    void deleteById(Integer userId);

    @Query("select count(u) from User u where u.email = ?1 and (u.status = 1 or u.status = 2)")
    Long findActiveUserByEmail(String email);
    Boolean existsByEmail(String email);
    @Modifying
    User save(User user);
}
