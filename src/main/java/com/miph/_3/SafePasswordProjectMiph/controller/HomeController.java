package com.miph._3.SafePasswordProjectMiph.controller;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import com.miph._3.SafePasswordProjectMiph.service.HomeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {


    protected final HomeService homeService;

    public HomeController(HomeService homeService, AccountRepository accountRepository){
        this.homeService = homeService;

    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/getAccounts")
    public Page<Account> getAccounts(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size){

        return homeService.getAccounts(page, size);
    }
}
