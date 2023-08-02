package com.poly.repository;

import com.poly.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByUserName(String username); // tim kiem user co ton tai trong db khong?
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

}
