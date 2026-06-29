package com.miph._3.SafePasswordProjectMiph.service;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    protected final AccountRepository accountRepository;
    protected final EncryptionService encryptionService;

    public AccountService(AccountRepository accountRepository, EncryptionService encryptionService) {
        this.accountRepository = accountRepository;
        this.encryptionService = encryptionService;
    }

    public void createAccount(String username, String path, String email, String rawPassword, String userUuid) {

        Account newAccount = new Account();
        newAccount.setUsername(username);
        newAccount.setPath(path);
        newAccount.setEmail(email);
        newAccount.setUserUuid(userUuid);
        String encodedPassword = encryptionService.encrypt(rawPassword);
        newAccount.setPasswordEncoded(encodedPassword);
        accountRepository.save(newAccount);
    }

    public boolean editAccount(String uuid, String username, String path, String email, String rawPassword, String userUuid){
        Optional<Account> optionalAccount = getAccount(uuid);
        if (optionalAccount.isEmpty()){
            return false;
        }

        Account newAccount = optionalAccount.get();
        newAccount.setUsername(username);
        newAccount.setPath(path);
        newAccount.setEmail(email);
        newAccount.setUserUuid(userUuid);
        String encodedPassword = encryptionService.encrypt(rawPassword);
        newAccount.setPasswordEncoded(encodedPassword);
        accountRepository.save(newAccount);

        return true;
    }

    public Optional<Account> getAccount(String uuid){
        return accountRepository.findByAccountUuid(uuid);
    }

    public boolean deleteAccount(String uuid){
        Optional<Account> optionalAccount = accountRepository.findByAccountUuid(uuid);
        if (!optionalAccount.isPresent()){
            return false;
        }

        accountRepository.delete(optionalAccount.get());
        return true;
    }
}
