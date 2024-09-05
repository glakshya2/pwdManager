package com.glakshya2.pwdManager;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PasswordsDto implements Serializable {
    private String website;
    private String username;
    private String password;
}
