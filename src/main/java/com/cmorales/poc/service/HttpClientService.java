package com.cmorales.poc.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmorales.poc.config.*;;

@Service
public class HttpClientService {
    @Autowired
    private JwtStorage jwtStorage;

    public String callToprotectedRequest(String url) throws Exception {
        
        String jwtToken = jwtStorage.getJwtToken(); // Recuperar el JWT y de ser necesario genera el token nuevamente

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwtToken) // Incluir el JWT en el encabezado
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
