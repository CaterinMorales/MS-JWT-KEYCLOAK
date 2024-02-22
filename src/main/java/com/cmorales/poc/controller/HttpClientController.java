package com.cmorales.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cmorales.poc.service.HttpClientService;

@RestController
public class HttpClientController {
    @Autowired
    HttpClientService httpClientService;

    @GetMapping("/public/call-productos")
    public String protectedEndpoint() throws Exception {

        return httpClientService.callToprotectedRequest("http://localhost:8083/protected");
        
    }
}
