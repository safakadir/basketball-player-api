package com.safakadir.basketballplayerapi.controller;

import com.safakadir.basketballplayerapi.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        System.out.println("/token "+authentication.getName());
        return tokenService.generateToken(authentication);
    }
}
