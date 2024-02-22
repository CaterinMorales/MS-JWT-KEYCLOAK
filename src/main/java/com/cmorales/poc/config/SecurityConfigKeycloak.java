package com.cmorales.poc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
//CommandLineRunner permite la ejecución automatica luego de levantar el proyecto
public class SecurityConfigKeycloak implements CommandLineRunner {

    @Autowired
    private JwtStorage jwtStorage;
    
    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Override
    public void run(String... args) {

        RestTemplate restTemplate = new RestTemplate();
        String tokenEndpoint = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        //Se preparan los client credentials para ser enviados por body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "client_credentials");
        requestParams.add("client_id", clientId);
        requestParams.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);

        ResponseEntity<Map> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, requestEntity, Map.class);
        
        
        if (response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            String accessToken = (String) responseBody.get("access_token");
            int expiresIn = (int) responseBody.get("expires_in");
            
            jwtStorage.setJwtToken(accessToken, expiresIn); // Almacena el JWT y el tiempo de expiración
            System.out.println("JWT Token: " + accessToken);
        }
    }
}
