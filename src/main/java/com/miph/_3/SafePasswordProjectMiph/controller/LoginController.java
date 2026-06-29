package com.miph._3.SafePasswordProjectMiph.controller;

import com.miph._3.SafePasswordProjectMiph.service.AuthService;
import org.apache.juli.logging.Log;
import org.hibernate.service.spi.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
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

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Void> registerSend( @RequestParam("username") String username,
                                              @RequestParam("password") String password){
        log.info("Register attempt for user: {}", username);
        if (!authService.checkPwComplexityAndLength(password)){
            log.error("pw is invalid");
            return ResponseEntity.badRequest().header(HttpHeaders.LOCATION, "/register").build();
        }

        authService.createIdentity(username, password);

        return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/login").build();
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResponseEntity<Void> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("user-session", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .secure(true)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }



    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Void> handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        log.info("Login attempt for user: {}", username);
        String uuid = authService.authenticateUser(username, password);

        if (uuid == null){
            log.error("Login attempt failed for user: {}", username);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header(HttpHeaders.LOCATION, "/login")
                    .build();
        }



        ResponseCookie cookie = ResponseCookie.from("user-session", uuid)
                .httpOnly(true)
                .path("/")
                .maxAge(1800)
                .build();

        log.info("user {} is logged in - session is {} seconds long", uuid, cookie.getMaxAge());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
