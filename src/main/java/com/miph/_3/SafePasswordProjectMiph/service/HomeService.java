package com.miph._3.SafePasswordProjectMiph.service;


import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class HomeService {

    protected final AccountRepository accountRepository;
    protected final EncryptionService encryptionService;

    public HomeService(AccountRepository accountRepository, EncryptionService encryptionService){
        this.accountRepository = accountRepository;
        this.encryptionService = encryptionService;
    }

    public Page<Account> getAccounts(int page, int size, String search, String uuid) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (search == null || search.trim().isEmpty()) {
            return accountRepository.findByUserUuid(uuid, pageRequest);
        }

        List<Account> allAccounts = accountRepository.findByUserUuid(uuid);
        String keyword = search.trim().toLowerCase();


        Predicate<Account> searchLogic = account ->
                (account.getPath() != null && account.getPath().toLowerCase().contains(keyword)) ||
                        (account.getUsername() != null && account.getUsername().toLowerCase().contains(keyword)) ||
                        (account.getEmail() != null && account.getEmail().toLowerCase().contains(keyword));

        List<Account> filteredList = applySearchFilter(allAccounts, searchLogic);


        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), filteredList.size());
        List<Account> pageContent = (start <= end) ? filteredList.subList(start, end) : List.of();

        return new PageImpl<>(pageContent, pageRequest, filteredList.size());
    }



    public Page<Account> findAccounts(int page, int size, String searchText, String uuid){
        PageRequest pageRequest = PageRequest.of(
                page,
                size
        );

        return accountRepository.findAccountsLikeUsername(searchText, uuid, pageRequest);
    }

    protected List<Account> applySearchFilter(List<Account> accounts, Predicate<Account> condition) {
        return accounts.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

}
