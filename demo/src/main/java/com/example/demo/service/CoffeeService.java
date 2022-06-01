package com.example.demo.service;

import com.example.demo.entity.Coffee;

public interface CoffeeService {
    public Coffee findOneById(int id) throws Exception;
}
