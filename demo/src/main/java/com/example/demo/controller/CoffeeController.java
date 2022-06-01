package com.example.demo.controller;

import com.example.demo.entity.Coffee;
import com.example.demo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    // SELECT ONE - findOne
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Coffee findOneById(@PathVariable int id) throws Exception {
        return coffeeService.findOneById(id - 1);
    }
}