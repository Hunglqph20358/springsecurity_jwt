package com.poly.service;

import com.poly.entity.Users;

import java.util.Optional;

public interface UserService {
    Optional<Users> findByUsername(String name); // tim kiem user co ton tai trong db khong?
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

    Users createUser(Users users);
}
