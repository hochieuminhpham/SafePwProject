package com.miph._3.SafePasswordProjectMiph.controller;

import com.miph._3.SafePasswordProjectMiph.service.AuthService;
import org.apache.juli.logging.Log;
import org.hibernate.service.spi.InjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;

@Controller
public class LoginController {

    protected final AuthService authService;

    public LoginController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String register(){return "register";}

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Void> handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {

        String uuid = authService.authenticateUser(username, password);

        if (uuid == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.LOCATION, "/login")
                    .build();
        }

        System.out.println("Login attempt for user: " + username);

        ResponseCookie cookie = ResponseCookie.from("user-session", uuid)
                .httpOnly(true)
                .path("/")
                .maxAge(1800)
                .build();


        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
