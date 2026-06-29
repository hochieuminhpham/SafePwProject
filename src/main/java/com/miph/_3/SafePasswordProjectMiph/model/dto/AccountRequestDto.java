package com.miph._3.SafePasswordProjectMiph.model.dto;

public class AccountRequestDto {
    private String path;
    private String username;
    private String password;

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}