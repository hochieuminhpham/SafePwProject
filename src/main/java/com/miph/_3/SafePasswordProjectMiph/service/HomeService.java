package com.miph._3.SafePasswordProjectMiph.service;


import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HomeService {

    protected final AccountRepository accountRepository;
    protected final EncryptionService encryptionService;

    public HomeService(AccountRepository accountRepository, EncryptionService encryptionService){
        this.accountRepository = accountRepository;
        this.encryptionService = encryptionService;
    }

    public Page<Account> getAccounts(int page, int size, String uuid){
        PageRequest pageRequest = PageRequest.of(
                page,
                size
        );

        return accountRepository.findByUserUuid(uuid, pageRequest);
    }

    public Page<Account> findAccounts(int page, int size, String searchText, String uuid){
        PageRequest pageRequest = PageRequest.of(
                page,
                size
        );

        return accountRepository.findAccountsLikeUsername(searchText, uuid, pageRequest);
    }

    public Account createAccount(String username, String path, String email, String rawPassword, String userUuid) {

        Account newAccount = new Account();
        newAccount.setAccountUuid(UUID.randomUUID().toString());
        newAccount.setUsername(username);
        newAccount.setPath(path);
        newAccount.setEmail(email);
        newAccount.setUserUuid(userUuid);
        String encodedPassword = encryptionService.encrypt(rawPassword);
        newAccount.setPasswordEncoded(encodedPassword);
        return accountRepository.save(newAccount);
    }
}
