package com.glakshya2.pwdManager.service;

import com.glakshya2.pwdManager.dto.PasswordsDto;

import java.util.List;

public interface PasswordsService {
    void save(PasswordsDto passwordsDto);

    List<PasswordsDto> findAll();

    List<PasswordsDto> findByWebsite(String website);

    List<PasswordsDto> findByWebsiteAndUsername(String website, String username);

    void deleteByWebsiteAndUsername(String website, String username);
}
