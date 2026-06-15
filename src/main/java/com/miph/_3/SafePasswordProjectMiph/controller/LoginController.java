package com.miph._3.SafePasswordProjectMiph.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public ResponseEntity<String> setCookie() {
        ResponseCookie cookie = ResponseCookie.from("user-theme", "dark")
                .httpOnly(true)
                .path("/")
                .maxAge(1800)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Cookie erfolgreich gesetzt!");
    }
}
