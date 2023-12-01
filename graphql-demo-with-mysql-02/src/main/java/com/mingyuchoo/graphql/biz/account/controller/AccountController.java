package com.mingyuchoo.graphql.biz.account.controller;

import com.mingyuchoo.graphql.biz.account.model.Account;
import com.mingyuchoo.graphql.biz.account.repository.AccountRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @SchemaMapping(value = "findAllAccounts", typeName = "Query")
    public Iterable<Account> findAllAccounts () {
        return accountRepository.findAll();
    }

    @SchemaMapping(value = "countAccounts", typeName = "Query")
    public Long countAccounts() {
        return accountRepository.count();
    }
}
