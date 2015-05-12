package com.example.web;

import com.example.domain.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {
    @RequestMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
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
