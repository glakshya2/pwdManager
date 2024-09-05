package com.glakshya2.pwdManager;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Scanner;

@ShellComponent
public class Commands {

    MainServiceImpl mainService;

    public Commands(MainServiceImpl mainService) {
        this.mainService = mainService;
    }
    @ShellMethod(key = "save")
    public void save() {
        String website, username, password, technique;
        Scanner sc = new Scanner(System.in);
        System.out.print("Website: ");
        website = sc.nextLine();
        System.out.print("Username: ");
        username = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();
        mainService.savePassword(website, username, password);
    }

    @ShellMethod(key = "all")
    public void all() {
        mainService.getAll();
    }

    @ShellMethod(key = "find")
    public void find(@ShellOption() String website, @ShellOption(defaultValue = "") String username) {
        if (!username.isEmpty()) {
            mainService.getByWebsiteAndUsername(website, username);
        } else {
            mainService.getByWebsite(website);
        }
    }

    @ShellMethod(key = "delete")
    public void deltePasword(@ShellOption(defaultValue = "") String website, @ShellOption(defaultValue = "") String username) {
        if (website.isEmpty() || username.isEmpty()) {
            System.out.println("Please provide both the website and the username for the delete operation");
            return;
        }
        mainService.deletePassword(website, username);
        System.out.println("Deleted!");
    }

    @ShellMethod(key = "quit")
    public void quit() {
        System.exit(0);
    }
}
