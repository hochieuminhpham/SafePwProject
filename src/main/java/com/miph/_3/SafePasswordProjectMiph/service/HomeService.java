package com.miph._3.SafePasswordProjectMiph.service;


import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    protected final AccountRepository accountRepository;

    public HomeService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Page<Account> getAccounts(int page, int size){
        PageRequest pageRequest = PageRequest.of(
                page,
                size
        );

        return accountRepository.findAll(pageRequest);
    }
}
