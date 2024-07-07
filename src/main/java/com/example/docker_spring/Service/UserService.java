package com.example.docker_spring.Service;

import com.example.docker_spring.Entity.User;
import com.example.docker_spring.Exception.FlyException;

public interface UserService {

    User getUser() throws FlyException;

    void activeUser(String token) throws FlyException;

    User updateUser() throws FlyException;

    void sendResetPasswordViaEmail() throws FlyException;

    void resetPassword() throws FlyException ;
}

