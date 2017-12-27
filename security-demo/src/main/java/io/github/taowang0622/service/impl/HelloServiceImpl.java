package io.github.taowang0622.service.impl;

import io.github.taowang0622.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void greet() {
        System.out.println("Greeting from HelloService!!");
    }
}
