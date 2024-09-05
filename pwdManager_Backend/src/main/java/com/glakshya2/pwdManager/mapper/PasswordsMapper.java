package com.glakshya2.pwdManager.mapper;

import com.glakshya2.pwdManager.dto.PasswordsDto;
import com.glakshya2.pwdManager.entity.Passwords;

public class PasswordsMapper {
    public static PasswordsDto mapToPasswordsDto(Passwords passwords,PasswordsDto passwordsDto) {
        passwordsDto.setWebsite(passwords.getWebsite());
        passwordsDto.setPassword(passwords.getPassword());
        passwordsDto.setUsername(passwords.getUsername());
        return passwordsDto;
    }

    public static Passwords mapToPasswords(PasswordsDto passwordsDto, Passwords passwords) {
        passwords.setWebsite(passwordsDto.getWebsite());
        passwords.setUsername(passwordsDto.getUsername());
        passwords.setPassword(passwordsDto.getPassword());
        return passwords;
    }
}
