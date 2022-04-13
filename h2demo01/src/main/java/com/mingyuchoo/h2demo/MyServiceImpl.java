package com.mingyuchoo.h2demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {

    @Autowired MyRepository myRepository;

    @Override
    public MyEntity get() {
        int id = 1; // FIRST ITEM
        return myRepository.findById(id);
    }
}
