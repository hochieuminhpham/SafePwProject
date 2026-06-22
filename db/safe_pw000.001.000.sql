
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
    path VARCHAR(250),
    email VARCHAR(100) NOT NULL UNIQUE,
    password_encoded VARCHAR(255) NOT NULL,
    user_uuid VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (account_uuid)
);

insert version (major, minor, patch) values (0, 1, 0);
commit;