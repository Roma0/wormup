package com.ascending.repository;

import com.ascending.model.User;

import java.util.List;

public interface UserDao {
    User save(User user);
    User findById(Long id);
    User getUserByEmail(String email);
    User getUserByCredentials(String email, String password);
    Void delete(User user);
}
