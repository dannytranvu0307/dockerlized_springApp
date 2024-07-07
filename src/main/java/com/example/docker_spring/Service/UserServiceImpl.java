package com.example.docker_spring.Service;

import com.example.docker_spring.AuthenticationConfig.jwtConfig.JwtUtils;
import com.example.docker_spring.Entity.User;
import com.example.docker_spring.Exception.AuthenticationFailedException;
import com.example.docker_spring.Exception.FlyException;
import com.example.docker_spring.Exception.UserException;
import com.example.docker_spring.Payload.RegisterPayload;
import com.example.docker_spring.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final Integer USER_CACHE_DURATION = 30;
    private final String RST_PWD_PREFIX = "RST_PWD:";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;



    public User saveUser(RegisterPayload payload) throws FlyException {
        if (isActiveUserAccount(payload.getEmail())) {
            log.info("{} is trying to register ", payload.getEmail());
            throw new UserException("REGIS_ER_01", "This email has already been used");
        }
        if (isExistsUserOnCache(payload.getEmail())) {
            log.info("{} is trying to register", payload.getEmail());
            throw new UserException("API002_ER2", "Active email already sent to " + payload.getEmail());
        }
        User preActiveUser = saveNewUser(payload);
        if (preActiveUser != null) {
            restoreRegisterUser(preActiveUser);
            emailService.sendRegistrationUserConfirm(payload.getEmail(), preActiveUser.getVerifyCode());
        }
        return preActiveUser;
    }

    @Override
    public User getUser()  {
        return null;
    }



    @Override
    public void activeUser(String token) throws AuthenticationFailedException {

        if (!redisTemplate.hasKey(token)) {
            throw new AuthenticationFailedException("02", "Verify code has expired");
        }
        User user = (User) redisTemplate.opsForValue().get(token);
        user.setCreateDt(Instant.now());
        user.setVerifyCode(null);
        user.setStatus((short) 1);
        userRepository.save(user);
        clearCacheAfterActive(user, token);

        log.info("User {} of account is active", user.getFullName());

    }

    @Override
    public User updateUser()  {
        return null;
    }

    @Override
    public void sendResetPasswordViaEmail()  {

    }

    @Override
    public void resetPassword()  {

    }

    public boolean isActiveUserAccount(String email) {
        return userRepository.findActiveUserByEmail(email) > 0;

    }

    private boolean isExistsUserOnCache(String email) {
        return redisTemplate.hasKey(email);
    }
    private User saveNewUser(RegisterPayload payload) {
        User user = new User(payload.getFullName(), payload.getEmail(), encoder.encode(payload.getPassword()));
        user.setStatus((short) 0);
        String tokenActive = jwtUtils.generateTokenToActiveUser(payload.getEmail());
        user.setVerifyCode(tokenActive);
        user.setDeleteFlag(false);
        userRepository.save(user) ;
        log.info("New account {} has been registered", user.getEmail());
        log.info("{} of details has been restored to cache", user.getEmail());
        return user;
    }

    private void restoreRegisterUser(User user) {
        redisTemplate.opsForValue().set(user.getEmail(), null, Duration.ofMinutes(USER_CACHE_DURATION));
        redisTemplate.opsForValue().set(user.getVerifyCode(), user, Duration.ofMinutes(USER_CACHE_DURATION));
    }

    private void clearCacheAfterActive(User user, String token) {
        redisTemplate.delete(user.getEmail());
        redisTemplate.delete(token);
    }

}
