package com.poly.repository;

import com.poly.entity.Role;
import com.poly.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName name);

    @Query("select r from Role r where r.id = :id")
    List<Role> getRoleByUser(@Param(value = "id") Long id);
}
