package com.app.blogging.service;

import com.app.blogging.exception.UserException;
import com.app.blogging.model.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws UserException;

    public User findUserByEmail(String email) throws UserException;

    public User findUserById(Long userId) throws UserException;
}
