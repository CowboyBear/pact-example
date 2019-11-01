package com.xom.orderprovider.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class OrderController {

    @RequestMapping("/orders/{id}")
    public HashMap<String, String> get(@PathVariable(required = false) String id){
        HashMap<String,String> response = new HashMap<>();

        response.put("id", id);

        return response ;
    }
    @RequestMapping("/orders")
    public HashMap<String, String> getByParameters(@RequestParam(required = false) String parameters){
        return  new HashMap<>();
    }
}
