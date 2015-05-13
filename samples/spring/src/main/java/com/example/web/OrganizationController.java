package com.example.web;

import com.example.constants.Abilities;
import com.example.domain.User;
import com.example.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        if (!user.can(Abilities.READ_ORGANIZATION)) {
            return "redirect:/";
        }
        model.addAttribute("organizationList", organizationService.findAll());
        return "organization/index";
    }
}
