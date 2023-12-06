package com.aditiya.simple.applicationuser;

import java.util.UUID;

import com.aditiya.simple.authentication.model.dto.RegisterResponseDTO;
import com.aditiya.simple.common.base.model.AuditModel;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicationUser extends AuditModel{
    private UUID id;
    private String name;
    private String email;
    private String username;
    private String password;

    public RegisterResponseDTO convertToResponse() {
        return RegisterResponseDTO.builder().id(this.id).name(this.name).email(this.email).username(this.username).password(this.password).build();
    }
}
