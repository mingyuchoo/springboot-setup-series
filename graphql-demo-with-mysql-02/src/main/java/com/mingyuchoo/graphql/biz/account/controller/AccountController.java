package com.mingyuchoo.graphql.biz.account.controller;

import com.mingyuchoo.graphql.biz.account.model.Account;
import com.mingyuchoo.graphql.biz.account.repository.AccountRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryMapping
    public List<Account> findAllAccounts () {
        return accountRepository.findAll();
    }

    @QueryMapping
    public Long countAccounts() {
        return accountRepository.count();
    }
}
