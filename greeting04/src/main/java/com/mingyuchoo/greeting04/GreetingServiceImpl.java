package com.mingyuchoo.greeting04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    @Autowired GreetingRepository greetingRepository;

    @Override
    public String get() {
        return greetingRepository.get();
    }
}
