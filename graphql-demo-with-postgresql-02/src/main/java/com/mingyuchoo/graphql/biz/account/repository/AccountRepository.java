package com.mingyuchoo.graphql.biz.account.repository;

import com.mingyuchoo.graphql.biz.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
