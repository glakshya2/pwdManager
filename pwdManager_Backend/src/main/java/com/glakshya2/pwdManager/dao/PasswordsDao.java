package com.glakshya2.pwdManager.dao;

import com.glakshya2.pwdManager.entity.Passwords;
import com.glakshya2.pwdManager.repository.PasswordsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PasswordsDao {
    private final PasswordsRepository passwordsRepository;

    public PasswordsDao(PasswordsRepository passwordsRepository) {
        this.passwordsRepository = passwordsRepository;
    }

    public void save(Passwords passwords) {
        passwordsRepository.save(passwords);
    }

    public List<Passwords> findByWebsite(String website) {
        return passwordsRepository.findByWebsite(website);
    }

    public Optional<Passwords> findByWebsiteAndUsername(String website, String username) {
        return passwordsRepository.findByWebsiteAndUsername(website, username);
    }

    public List<Passwords> findAll() {
        return passwordsRepository.findAll();
    }

    public void deleteByEntity(Passwords passwords) {
        passwordsRepository.delete(passwords);
    }
}
