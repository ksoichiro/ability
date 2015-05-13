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
        model.addAttribute("reportList", reportService.findByCreatorId(user.getId()));
        return "report/index";
    }

    @RequestMapping("/new")
    public String newForm(ReportForm reportForm) {
        return "report/new";
    }

    @RequestMapping(value = "/confirmNew", method = RequestMethod.POST)
    public String confirmNew(@Validated ReportForm reportForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return newForm(reportForm);
        }
        return "report/confirmNew";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@AuthenticationPrincipal User user, ReportForm reportForm) {
        reportService.create(user, reportForm);
        return "redirect:/report";
    }
}
