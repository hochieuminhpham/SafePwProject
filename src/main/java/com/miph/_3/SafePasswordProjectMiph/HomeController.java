package com.miph._3.SafePasswordProjectMiph;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String testConnection() {
        return "Spring Boot application is securely connected to your MySQL database!";
    }
}
