package com.aditiya.simple.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aditiya.simple.applicationuser.ApplicationUser;
import com.aditiya.simple.applicationuser.ApplicationUserService;
import com.aditiya.simple.authentication.jwt.JwtProvider;
import com.aditiya.simple.authentication.model.dto.RegisterUserRequestDTO;
import com.aditiya.simple.authentication.model.dto.JwtResponseDto;
import com.aditiya.simple.authentication.model.dto.LoginRequestDto;
import com.aditiya.simple.authentication.model.dto.RegisterResponseDTO;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final ApplicationUserService applicationUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/tokens")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        logger.info("Attempt login to system: {}", loginRequestDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponseDto(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterUserRequestDTO applicationUserRequestDTO){
        ApplicationUser newUser = applicationUserRequestDTO.convertToEntitiy();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        ApplicationUser savedUser = this.applicationUserService.createUser(newUser);
        return ResponseEntity.ok().body(savedUser.convertToResponse());
    }
}