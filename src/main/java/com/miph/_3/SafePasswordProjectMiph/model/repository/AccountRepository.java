package com.miph._3.SafePasswordProjectMiph.model.repository;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.email LIKE %:domain AND a.path IS NOT NULL")
    List<Account> findAccountsInDomainWithValidPath(@Param("domain") String domain);

    boolean existsByEmail(String email);
}
