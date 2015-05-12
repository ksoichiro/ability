package com.example.web;

import com.example.domain.User;
import com.example.form.ReportForm;
import com.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("reportList", reportService.findByCreatorId(user.getId()));
        return "report/index";
    }

    @RequestMapping("/new")
    public String newForm(@AuthenticationPrincipal User user, ReportForm reportForm, Model model) {
        model.addAttribute("user", user);
        return "report/new";
    }

    @RequestMapping(value = "/confirmNew", method = RequestMethod.POST)
    public String confirmNew(@AuthenticationPrincipal User user, @Validated ReportForm reportForm, Model model,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return newForm(user, reportForm, model);
        }
        model.addAttribute("user", user);
        return "report/confirmNew";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@AuthenticationPrincipal User user, ReportForm reportForm, Model model) {
        reportService.create(user, reportForm);
        model.addAttribute("user", user);
        return "redirect:/report";
    }
}
