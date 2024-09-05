package com.glakshya2.pwdManager;

import org.springframework.stereotype.Service;


@Service
public class MainServiceImpl implements MainService {
    Network network;
    private ECCEncryption eccEncryption;

    public MainServiceImpl(Network network) throws Exception {
        this.network = network;
        this.eccEncryption = new ECCEncryption();
    }

    void printPasswords(PasswordsDto[] passwordsDtos) {
        try {
            for (PasswordsDto passwordsDto: passwordsDtos) {
                String decryptedPassword = eccEncryption.decrypt(passwordsDto.getPassword());
                System.out.println("Website: " + passwordsDto.getWebsite());
                System.out.println("Username: " + passwordsDto.getUsername());
                System.out.println("Encrypted Password: " + passwordsDto.getPassword());
                System.out.println("Decrypted Password: " + decryptedPassword);
            }
        } catch (Exception e) {
            System.out.println("Error during decryption: " + e.getMessage());
        }
    }

    public void getAll() {
        PasswordsDto[] passwordsDtos = network.getRequest("all");
        printPasswords(passwordsDtos);
    }

    public void getByWebsite(String website) {
            PasswordsDto[] passwordsDtos = network.getRequest("website/" + website);
            printPasswords(passwordsDtos);
    }

    public void getByWebsiteAndUsername(String website, String username) {
            PasswordsDto[] passwordsDtos = network.getRequest("website/" + website + "/username/" + username);
            printPasswords(passwordsDtos);
    }

    public void deletePassword(String website, String username) {
            network.deleteRequest("website/" + website + "/username/" + username);
    }

    public void savePassword(String website, String username, String password) {
        try {
            String encryptedPassword = eccEncryption.encrypt(password);
            PasswordsDto passwordsDto = new PasswordsDto(website, username, encryptedPassword);
            if (network.postRequest("add", passwordsDto)) {
                System.out.println("Saved!");
                System.out.println("Encrypted password: " + encryptedPassword);
            } else {
                System.out.println("Failed to save");
            }
        } catch (Exception e) {
            System.out.println("Error during encryption: " + e.getMessage());
        }
    }
}
