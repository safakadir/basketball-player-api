package com.safakadir.basketballplayerapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa.jwt")
public record RsaJwtKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {

}
