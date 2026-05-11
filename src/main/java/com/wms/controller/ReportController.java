package com.wms.controller;

import com.wms.factory.ReportFactory;
import com.wms.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * MVC Controller for Report Generation.
 * Demonstrates the Factory Method Pattern — the ReportFactory
 * creates the correct report type based on user selection.
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    @GetMapping
    public String showReports(HttpSession session, Model model) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        model.addAttribute("user", session.getAttribute("currentUser"));
        return "reports";
    }

    /** Generates a report using the Factory Pattern */
    @PostMapping("/generate")
    public String generateReport(@RequestParam String reportType,
                                 HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/";

        // Factory Pattern in action
        String reportContent = ReportFactory.createReport(reportType, user.getFullName());

        model.addAttribute("user", user);
        model.addAttribute("reportContent", reportContent);
        model.addAttribute("selectedType", reportType);
        return "reports";
    }
}
