package com.wms.controller;

import com.wms.model.User;
import com.wms.repository.OrderDAO;
import com.wms.service.InventoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * MVC Controller for the Dashboard — the main navigation hub.
 * Displays key statistics and recent activity.
 */
@Controller
public class DashboardController {

    private final InventoryService inventoryService;
    private final OrderDAO orderDAO;

    public DashboardController(InventoryService inventoryService, OrderDAO orderDAO) {
        this.inventoryService = inventoryService;
        this.orderDAO = orderDAO;
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/";

        // Pass statistics to the template
        model.addAttribute("user", user);
        model.addAttribute("totalProducts", inventoryService.getTotalItemCount());
        model.addAttribute("totalUnits", inventoryService.getTotalUnitsInStock());
        model.addAttribute("pendingOrders", orderDAO.countByStatus("Pending"));
        model.addAttribute("lowStockCount", inventoryService.getLowStockItems().size());
        model.addAttribute("orders", orderDAO.findAll());
        model.addAttribute("alerts", inventoryService.getAlerts());

        return "dashboard";
    }
}
