package com.miph._3.SafePasswordProjectMiph.model.repository;

import com.miph._3.SafePasswordProjectMiph.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT a FROM Account a WHERE a.username LIKE %:name AND a.userUuid = :userUuid")
    Page<Account> findAccountsLikeUsername(@Param("name") String name, @Param("userUuid") String userUuid, Pageable pageable);

    Page<Account> findByUserUuid(String userUuid, Pageable pageable);

    boolean existsByEmail(String email);
}
