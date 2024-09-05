package com.glakshya2.pwdManager.repository;

import com.glakshya2.pwdManager.entity.Passwords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordsRepository extends JpaRepository<Passwords, Integer> {

    List<Passwords> findByWebsite(String website);

    Optional<Passwords> findByWebsiteAndUsername(String website, String username);

    void deleteByWebsiteAndUsername(String website, String username);
}
