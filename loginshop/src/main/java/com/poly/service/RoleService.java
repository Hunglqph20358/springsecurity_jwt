package com.poly.service;

import com.poly.entity.Role;
import com.poly.entity.RoleName;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleName name);
    List<Role> getRoleByUser(Long id);
}
