package com.wms.controller;

import com.wms.model.Order;
import com.wms.model.User;
import com.wms.repository.OrderDAO;
import com.wms.service.InventoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Controller for the client self-service portal.
 * Clients can view notifications, browse stock, and manage their orders.
 */
@Controller
@RequestMapping("/client")
public class ClientOrderController {

    private final OrderDAO orderDAO;
    private final InventoryService inventoryService;

    public ClientOrderController(OrderDAO orderDAO, InventoryService inventoryService) {
        this.orderDAO = orderDAO;
        this.inventoryService = inventoryService;
    }

    private User getClient(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null || user.getRole() != User.UserRole.CLIENT) return null;
        return user;
    }

    /** Client orders page */
    @GetMapping("/orders")
    public String viewOrders(@RequestParam(required = false) String status,
                             HttpSession session, Model model) {
        User client = getClient(session);
        if (client == null) return "redirect:/";

        var orders = orderDAO.findByClientName(client.getFullName());
        if (status != null && !status.isBlank() && !"All".equals(status)) {
            orders = orders.stream().filter(o -> o.getStatus().equals(status)).toList();
            model.addAttribute("filterStatus", status);
        }

        model.addAttribute("user", client);
        model.addAttribute("orders", orders);
        model.addAttribute("unreadCount", inventoryService.getUnreadNotificationCount());
        return "client-orders";
    }

    /** Client stock browsing page with notifications */
    @GetMapping("/stock")
    public String viewStock(HttpSession session, Model model) {
        User client = getClient(session);
        if (client == null) return "redirect:/";

        model.addAttribute("user", client);
        model.addAttribute("items", inventoryService.getAllItems());
        model.addAttribute("notifications", inventoryService.getClientNotifications());
        inventoryService.markNotificationsRead();
        return "client-stock";
    }

    /** Place order — can be triggered from stock page */
    @PostMapping("/orders/add")
    public String placeOrder(@RequestParam String productName,
                             @RequestParam int quantity,
                             @RequestParam String orderType,
                             HttpSession session) {
        User client = getClient(session);
        if (client == null) return "redirect:/";

        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        orderDAO.save(new Order(orderId, client.getFullName(), productName,
                quantity, LocalDate.now(), "Pending", orderType));
        return "redirect:/client/orders";
    }
}
