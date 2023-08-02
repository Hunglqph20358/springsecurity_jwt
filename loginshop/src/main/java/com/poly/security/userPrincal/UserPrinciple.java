package com.poly.security.userPrincal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {

    private Long id;

    private String name;

    private String userName;
    @JsonIgnore
    private String password;
    private String email;

    private Collection<? extends GrantedAuthority> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public UserPrinciple() {
    }

    public UserPrinciple(Long id, String name, String userName, String password, String email, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public static UserPrinciple buid(Users users){
        List<GrantedAuthority> authorities = users.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        return new UserPrinciple(
                users.getId(),
                users.getName(),
                users.getUserName(),
                users.getPassword(),
                users.getEmail(),
                authorities
        );
    }
    public String getName(){
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
