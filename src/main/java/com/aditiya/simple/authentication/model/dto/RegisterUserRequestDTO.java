package com.aditiya.simple.authentication.model.dto;

import java.util.UUID;

import com.aditiya.simple.applicationuser.ApplicationUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDTO {
    private UUID id;
    private String name;
    private String email;
    private String username;
    private String password;

    public ApplicationUser convertToEntitiy() {
        return ApplicationUser.builder().id(this.id).name(this.name).username(this.username).email(this.email).password(this.password).build();
    }
}
