package com.miph._3.SafePasswordProjectMiph.service;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.Identity;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import com.miph._3.SafePasswordProjectMiph.model.repository.IdentityRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    protected final AccountRepository accountRepository;
    protected final HashingService hashingService;
    protected final IdentityRepository identityRepository;
    protected static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{10,}$";
    protected static final Pattern PATTERN = Pattern.compile(PASSWORD_REGEX);

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

    public void createIdentity(String name, String pw){
        Identity newUser = new Identity();
        newUser.setUsername(name);
        newUser.setPasswordHash(hashingService.hashPw(pw));
        identityRepository.save(newUser);
    };

    public Boolean checkPwComplexityAndLength(String pw){
        Matcher matcher = PATTERN.matcher(pw);
        return matcher.matches();
    }
}
