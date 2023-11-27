package com.mingyuchoo.graphql.account.repository;

import com.mingyuchoo.graphql.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
