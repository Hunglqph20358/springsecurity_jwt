package com.poly.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpFormRequest implements Serializable {
    private String name;
    private String username;
    private String password;
    private String email;
    private Set<String> roles;

}
