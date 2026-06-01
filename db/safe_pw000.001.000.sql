
CREATE DATABASE safe_pw;
USE safe_pw;

create table version (
    major int,
    minor int,
    patch int
);

CREATE TABLE account (
    account_uuid VARCHAR(36) NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (account_uuid)
);