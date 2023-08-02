package com.poly.controller;

import com.poly.dto.request.SignInFormRequest;
import com.poly.dto.request.SignUpFormRequest;
import com.poly.dto.response.JwtReponse;
import com.poly.dto.response.ResponeMessage;
import com.poly.entity.Role;
import com.poly.entity.RoleName;
import com.poly.entity.Users;
import com.poly.security.jwt.JwtProvider;
import com.poly.security.userPrincal.UserPrinciple;
import com.poly.service.RoleService;
import com.poly.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpFormRequest signUpFormRequest){
        if(userService.existsByUserName(signUpFormRequest.getUsername())){
            return new ResponseEntity<>(new ResponeMessage("The Username is existed"), HttpStatus.OK);
        }
        if(userService.existsByEmail(signUpFormRequest.getEmail())){
            return new ResponseEntity<>(new ResponeMessage("The email is existed"), HttpStatus.OK);
        }
        Users users = Users.builder().name(signUpFormRequest.getName()).userName(signUpFormRequest.getUsername()).email(signUpFormRequest.getEmail())
                .password(passwordEncoder.encode(signUpFormRequest.getPassword())).build();
        Set<String> strRoles = signUpFormRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        for (String x: strRoles) {
            if(x.equalsIgnoreCase("ADMIN")){
                Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(adminRole);
            } else if (x.equalsIgnoreCase("KH")) {
                Role khRole = roleService.findByName(RoleName.KH).orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(khRole);
            }else {
                Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(userRole);
            }
        }
        users.setRoles(roles);
        userService.createUser(users);
        return new ResponseEntity<>(new ResponeMessage("Create Success"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody SignInFormRequest signInFormRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInFormRequest.getUsername(), signInFormRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtReponse(token,userPrinciple.getName(),userPrinciple.getAuthorities()));
    }
}
