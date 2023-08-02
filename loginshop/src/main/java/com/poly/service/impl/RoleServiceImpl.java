package com.poly.service.impl;

import com.poly.entity.Role;
import com.poly.entity.RoleName;
import com.poly.repository.IRoleRepository;
import com.poly.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public List<Role> getRoleByUser(Long id) {
        return roleRepository.getRoleByUser(id);
    }
}
