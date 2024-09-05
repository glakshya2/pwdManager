package com.glakshya2.pwdManager.service.impl;

import com.glakshya2.pwdManager.dao.PasswordsDao;
import com.glakshya2.pwdManager.dto.PasswordsDto;
import com.glakshya2.pwdManager.entity.Passwords;
import com.glakshya2.pwdManager.exceptions.AccountAlreadyExistsException;
import com.glakshya2.pwdManager.exceptions.PasswordNotFoundException;
import com.glakshya2.pwdManager.mapper.PasswordsMapper;
import com.glakshya2.pwdManager.service.PasswordsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PasswordsServiceImpl implements PasswordsService {
    PasswordsDao passwordsDao;

    PasswordsServiceImpl(PasswordsDao passwordsDao) {
        this.passwordsDao = passwordsDao;
    }

    @Override
    public void save(PasswordsDto passwordsDto) {
        Passwords passwords = PasswordsMapper.mapToPasswords(passwordsDto, new Passwords());
        Optional<Passwords> optionalPasswords = passwordsDao.findByWebsiteAndUsername(passwords.getWebsite(), passwords.getUsername());
        if (optionalPasswords.isPresent()) {
            throw new AccountAlreadyExistsException("There is already an account saved with given username: " + passwords.getUsername() + " for given website: " + passwords.getWebsite());
        }
        passwordsDao.save(passwords);
    }

    List<PasswordsDto> convertToPasswordsDtoList(List<Passwords> passwordsList) {
        List<PasswordsDto> passwordsDtoList = new ArrayList<>();
        for (Passwords passwords: passwordsList) {
            passwordsDtoList.add(PasswordsMapper.mapToPasswordsDto(passwords, new PasswordsDto()));
        }
        return passwordsDtoList;
    }
    @Override
    public List<PasswordsDto> findAll() {
        List<Passwords> passwordsList = passwordsDao.findAll();
        return convertToPasswordsDtoList(passwordsList);
    }

    @Override
    public List<PasswordsDto> findByWebsite(String website) {
        List<Passwords> passwordsList = passwordsDao.findByWebsite(website);
        if (passwordsList.isEmpty()) {
            throw new PasswordNotFoundException("There are no passwords saved for the website: " + website);
        }
        return convertToPasswordsDtoList(passwordsList);
    }

    @Override
    public List<PasswordsDto> findByWebsiteAndUsername(String website, String username) {
        Optional<Passwords> optionalPasswords = passwordsDao.findByWebsiteAndUsername(website, username);
        if (optionalPasswords.isEmpty()) {
            throw new PasswordNotFoundException("There is no password saved for the given website: " + website + " with the given username: " + username);
        }
        List<PasswordsDto> passwordsDtoList = new ArrayList<>();
        passwordsDtoList.add(PasswordsMapper.mapToPasswordsDto(optionalPasswords.get(), new PasswordsDto()));
        return passwordsDtoList;
    }

    @Override
    public void deleteByWebsiteAndUsername(String website, String username) {
        Optional<Passwords> optionalPasswords = passwordsDao.findByWebsiteAndUsername(website, username);
        if (optionalPasswords.isEmpty()) {
            throw new PasswordNotFoundException("There is not password saved for the given website: " + website + " and the given username: " + username);
        }
        passwordsDao.deleteByEntity(optionalPasswords.get());
    }
}
