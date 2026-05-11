package com.wms.controller;

import com.wms.model.Order;
import com.wms.model.User;
import com.wms.repository.OrderDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Controller for the client self-service portal.
 * Clients can view only their own orders and place new ones.
 */
@Controller
@RequestMapping("/client")
public class ClientOrderController {

    private final OrderDAO orderDAO;

    public ClientOrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    private User getClient(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null || user.getRole() != User.UserRole.CLIENT) return null;
        return user;
    }

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
        return "client-orders";
    }

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
