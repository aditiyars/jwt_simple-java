package com.aditiya.simple;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aditiya.simple.authentication.model.UserPrincipal;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@SecurityRequirement(name = "bearerAuth")
@RestController
class GreetingController {
    @GetMapping("/greetings")
    public String greet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return "Hello " + userPrincipal.getEmail() +"\n" + "pass : " + userPrincipal.getPassword();
    }
}
