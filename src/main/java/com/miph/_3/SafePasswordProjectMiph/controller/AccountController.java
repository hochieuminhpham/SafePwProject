package com.miph._3.SafePasswordProjectMiph.controller;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.dto.AccountRequestDto;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import com.miph._3.SafePasswordProjectMiph.service.AccountService;
import com.miph._3.SafePasswordProjectMiph.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AccountController {

    protected final LoggingService loggingService;
    protected Logger log = LoggerFactory.getLogger(AccountController.class);
    protected final AccountService accountService;

    public AccountController(LoggingService loggingService, AccountService accountService) {
        this.loggingService = loggingService;
        this.accountService = accountService;
    }

    @PostMapping("/addAccount")
    @ResponseBody
    public ResponseEntity<Void> addAccount(@RequestBody AccountRequestDto request,
                                           @CookieValue(name = "user-session", required = false) String sessionToken) {
        if (sessionToken == null) {
            loggingService.logUnauthorizedSession(log, "/addAccount");
            return ResponseEntity.status(401).build();
        }

        accountService.createAccount(
                request.getUsername(),
                request.getPath(),
                request.getUsername(),
                request.getPassword(),
                sessionToken
        );

        log.info("Account {} created", request.getUsername());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/editAccount")
    @ResponseBody
    public ResponseEntity<Void> editAccount(@RequestParam("id") String id,
                                            @RequestBody AccountRequestDto request,
                                            @CookieValue(name = "user-session", required = false) String sessionToken) {
        if (sessionToken == null) {
            loggingService.logUnauthorizedSession(log, "/editAccount");
            return ResponseEntity.status(401).build();
        }

        boolean isUpdated = accountService.editAccount(
                id,
                request.getUsername(),
                request.getPath(),
                request.getUsername(),
                request.getPassword(),
                sessionToken
        );

        if (isUpdated) {
            log.info("Account {} created", request.getUsername());
            return ResponseEntity.ok().build();
        } else {
            log.info("unable to edit account {}", id);
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/getAccount")
    @ResponseBody
    public ResponseEntity<Account> getAccount(@RequestParam(value = "id", required = false) String id,
                                              @CookieValue(name = "user-session", required = false) String userSession) {

        if (userSession == null) {
            loggingService.logUnauthorizedSession(log, "/account");
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(accountService.getAccount(id).get());
    }

    @GetMapping("/account")
    public String account(@RequestParam(value = "id", required = false) String id,
                          @CookieValue(name = "user-session", required = false) String userSession) {

        if (userSession == null) {
            loggingService.logUnauthorizedSession(log, "/account");
            return "redirect:/login";
        }

        if (id != null) {
            Optional<Account> optionalAccount = accountService.getAccount(id);
            if (optionalAccount.isEmpty()) {
                return "redirect:/";
            }
        }

        return "account";
    }
    @GetMapping("/deleteAccount")
    @ResponseBody
    public ResponseEntity<Void> deleteAccount(@RequestParam(value = "id", required = false) String id,
                                              @CookieValue(name = "user-session", required = false) String sessionToken){

        if (sessionToken == null) {
            loggingService.logUnauthorizedSession(log, "/deleteAccount");
            return ResponseEntity.status(401).build();
        }

        if (accountService.deleteAccount(id)){
            log.info("Account deleted {}", id);
            return ResponseEntity.ok().build();
        } else {
            log.error("Error deleting account {}", id);
            return ResponseEntity.status(400).build();
        }
    }
}
