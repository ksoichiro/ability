package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {
    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
