package com.wms.controller;

import com.wms.model.Order;
import com.wms.repository.OrderDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * MVC Controller for Order Management.
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public String listOrders(@RequestParam(required = false) String status,
                             HttpSession session, Model model) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        model.addAttribute("user", session.getAttribute("currentUser"));

        if (status != null && !status.isBlank() && !"All".equals(status)) {
            model.addAttribute("orders", orderDAO.findByStatus(status));
            model.addAttribute("filterStatus", status);
        } else {
            model.addAttribute("orders", orderDAO.findAll());
        }
        return "orders";
    }

    @PostMapping("/add")
    public String addOrder(@RequestParam String clientName,
                           @RequestParam String productName,
                           @RequestParam int quantity,
                           @RequestParam String orderType,
                           HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        Order order = new Order(orderId, clientName, productName, quantity,
                LocalDate.now(), "Pending", orderType);
        orderDAO.save(order);
        return "redirect:/orders";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam String orderId,
                               @RequestParam String newStatus,
                               HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        orderDAO.findById(orderId).ifPresent(order -> {
            order.setStatus(newStatus);
            orderDAO.update(order);
        });
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        orderDAO.delete(id);
        return "redirect:/orders";
    }
}
