package com.aditiya.simple.authentication.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Something's wrong when try to authenticate user.")
public class ApplicationAuthenticationException extends RuntimeException {
}
