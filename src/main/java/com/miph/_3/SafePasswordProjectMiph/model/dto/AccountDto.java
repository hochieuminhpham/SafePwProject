package com.miph._3.SafePasswordProjectMiph.model.dto;

import com.miph._3.SafePasswordProjectMiph.model.Account;

import java.time.LocalDateTime;

public class AccountDto {
    private String accountUuid;
    private String username;
    private String path;
    private String email;
    private String passwordEncoded;
    private String userUuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AccountDto(Account account) {
        this.accountUuid = account.getAccountUuid();
        this.username = account.getUsername();
        this.path = account.getPath();
        this.email = account.getEmail();
        this.passwordEncoded = account.getPasswordEncoded();
        this.userUuid = account.getUserUuid();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
    }

    public String getAccountUuid() { return accountUuid; }
    public String getUsername() { return username; }
    public String getPath() { return path; }
    public String getEmail() { return email; }
    public String getPasswordEncoded() { return passwordEncoded; }
    public String getUserUuid() { return userUuid; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}

