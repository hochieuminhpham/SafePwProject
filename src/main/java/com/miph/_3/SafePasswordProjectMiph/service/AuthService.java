package com.miph._3.SafePasswordProjectMiph.service;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.Identity;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import com.miph._3.SafePasswordProjectMiph.model.repository.IdentityRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    protected final AccountRepository accountRepository;
    protected final HashingService hashingService;
    private final IdentityRepository identityRepository;

    public AuthService(AccountRepository accountRepository, HashingService hashingService, IdentityRepository identityRepository){
        this.accountRepository = accountRepository;
        this.hashingService = hashingService;
        this.identityRepository = identityRepository;
    }

    public String authenticateUser(String name, String pw){

        Optional<Identity> optionalIdentity = identityRepository.findByUsername(name);
        if (optionalIdentity.isEmpty()){
            return null;
        }

        Identity identity = optionalIdentity.get();
        String pwHashed = hashingService.hashPw(pw);
        if (!Objects.equals(identity.getPasswordHash(), pwHashed)){
            return null;
        }

        return identity.getUserUuid();
    }
}
