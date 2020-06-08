package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index () {
        return "index";
    }

    @GetMapping("/read")
    public String read () {
        return "read";
    }

    @GetMapping("/em")
    public String em () {
        return "em";
    }
}
