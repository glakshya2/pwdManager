package com.glakshya2.pwdManager.controller;

import com.glakshya2.pwdManager.dto.PasswordsDto;
import com.glakshya2.pwdManager.dto.ResponseDto;
import com.glakshya2.pwdManager.service.impl.PasswordsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Endpoints {
    private final PasswordsServiceImpl passwordsService;

    Endpoints(PasswordsServiceImpl passwordsService) {
        this.passwordsService = passwordsService;
    }

    @GetMapping("/test")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> save(@RequestBody PasswordsDto passwordsDto) {
        passwordsService.save(passwordsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201", "Password saved successfully."));
    }

    @GetMapping("/all")
    public List<PasswordsDto> findAll() {
        return passwordsService.findAll();
    }

    @GetMapping("/website/{website}")
    public List<PasswordsDto> findByWebsite(@PathVariable String website) {
        return passwordsService.findByWebsite(website);
    }

    @GetMapping("/website/{website}/username/{username}")
    public List<PasswordsDto> findByWebsiteAndUsername(@PathVariable String website, @PathVariable String username) {
        return passwordsService.findByWebsiteAndUsername(website, username);
    }

    @DeleteMapping("/website/{website}/username/{username}")
    public ResponseEntity<ResponseDto> deleteByWebsiteAndUsername(@PathVariable String website, @PathVariable String username) {
        passwordsService.deleteByWebsiteAndUsername(website, username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Password deleted Successfully."));
    }
}
