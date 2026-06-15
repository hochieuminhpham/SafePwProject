package com.miph._3.SafePasswordProjectMiph.controller;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
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

    public HomeController(HomeService homeService, AccountRepository accountRepository){
        this.homeService = homeService;

    }

    @GetMapping("/")
    public String home(Model model, @CookieValue(name = "user-theme", required = false) String theme) {
        //if (theme == null){
          //  return "login";
        //}

        return "home";
    }

    @GetMapping("/getAccounts")
    @ResponseBody
    public ResponseEntity<Page<Account>> getAccounts(Pageable pageable) {
        Page<Account> accounts = homeService.getAccounts(pageable.getPageNumber(), pageable.getPageSize());

        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }
}
