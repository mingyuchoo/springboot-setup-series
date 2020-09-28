package com.mingyuchoo.greeting05;

import org.springframework.stereotype.Repository;

@Repository
public class GreetingRepositoryImpl implements GreetingRepository {

    @Override
    public String get() {
        return "Hello, World!";
    }

    public Boolean contains(String monContactName) {
        return true;
    }
}
