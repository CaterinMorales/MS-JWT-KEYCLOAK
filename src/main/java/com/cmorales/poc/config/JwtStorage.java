package com.cmorales.poc.config;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JwtStorage {
    private String jwtToken;
    private Instant expiryTime;

    @Autowired
    private ApplicationContext context;

    public synchronized String getJwtToken() {
        if (isTokenExpired()) {
            // Obtener el bean SecurityConfigKeycloak y llamar a run
            SecurityConfigKeycloak securityConfig = context.getBean(SecurityConfigKeycloak.class);
            securityConfig.run();
        }
        return jwtToken;
    }

    public synchronized void setJwtToken(String jwtToken, long expiresIn) {
        this.jwtToken = jwtToken;
        this.expiryTime = Instant.now().plusSeconds(expiresIn);
    }

    public synchronized boolean isTokenExpired() {
        if(Instant.now().isAfter(expiryTime)){
            return true;
        }
        return false;
    }
}