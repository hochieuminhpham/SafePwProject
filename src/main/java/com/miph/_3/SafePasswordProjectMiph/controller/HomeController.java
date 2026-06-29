package com.miph._3.SafePasswordProjectMiph.controller;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.dto.AccountDto;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import com.miph._3.SafePasswordProjectMiph.service.EncryptionService;
import com.miph._3.SafePasswordProjectMiph.service.HomeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {


    protected final HomeService homeService;
    protected final EncryptionService encryptionService;

    public HomeController(HomeService homeService, AccountRepository accountRepository, EncryptionService encryptionService) {
        this.homeService = homeService;
        this.encryptionService = encryptionService;

    }

    @GetMapping("/")
    public String home(Model model, @CookieValue(name = "user-session", required = false) String userSession) {
        if (userSession == null) {
            return "login";
        }

        return "home";
    }


    @GetMapping("/getAccounts")
    @ResponseBody
    public ResponseEntity<Page<AccountDto>> getAccounts(
            Pageable pageable,
            @RequestParam(value = "search", required = false) String search,
            @CookieValue(name = "user-session", required = false) String sessionToken) {

        if (sessionToken == null) {
            return ResponseEntity.status(401).build();
        }

        Page<Account> accounts = homeService.getAccounts(pageable.getPageNumber(), pageable.getPageSize(), search, sessionToken);

        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Page<AccountDto> dtoPage = accounts.map(AccountDto::new);

        return ResponseEntity.ok(dtoPage);
    }


    @GetMapping("/decryptPw")
    @ResponseBody
    public ResponseEntity<String> decryptPassword(@RequestParam(value = "pw", required = false) String pw,
                                                  @CookieValue(name = "user-session", required = false) String sessionToken){
        if (sessionToken == null) {
            return ResponseEntity.status(401).build();
        }

        String decryptedPw;

        try {
            decryptedPw = encryptionService.decrypt(pw);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(decryptedPw);
    }

}
