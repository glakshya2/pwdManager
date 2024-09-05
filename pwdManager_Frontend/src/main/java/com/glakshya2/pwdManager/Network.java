package com.glakshya2.pwdManager;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Network {

    private final RestTemplate restTemplate;

    public Network(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    String uri = "https://pwdmanager-pwd-manager-instance.azuremicroservices.io/";

    public PasswordsDto[] getRequest(String mapping) {
        ResponseEntity<PasswordsDto[]> response = this.restTemplate.getForEntity(uri + mapping, PasswordsDto[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.out.println("Error: " + response.getStatusCode());
            return null;
        }
    }

    public void deleteRequest(String mapping) {
        this.restTemplate.delete(uri + mapping);
    }

    public boolean postRequest(String mapping, PasswordsDto passwordsDto) {
        ResponseEntity<ResponseDto> response = this.restTemplate.postForEntity(uri + mapping, passwordsDto, ResponseDto.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return true;
        } else {
            System.out.println("Error: " + response.getStatusCode());
            return false;
        }
    }
}
