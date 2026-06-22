package com.miph._3.SafePasswordProjectMiph.model.repository;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import com.miph._3.SafePasswordProjectMiph.model.Identity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, String> {

    Optional<Identity> findByEmail(String email);

    Optional<Identity> findByUsername(String username);

    //@Query("SELECT a FROM Account a WHERE a.email LIKE %:domain AND a.path IS NOT NULL")
    //List<Account> findAccountsInDomainWithValidPath(@Param("domain") String domain);
}
