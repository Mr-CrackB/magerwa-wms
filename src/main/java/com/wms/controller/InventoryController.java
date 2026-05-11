package com.wms.controller;

import com.wms.model.User;
import com.wms.service.InventoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * MVC Controller for Inventory Management.
 * Handles listing, searching, adding, and deleting inventory items.
 */
@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /** Displays inventory list with optional search */
    @GetMapping
    public String listInventory(@RequestParam(required = false) String search,
                                HttpSession session, Model model) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";

        model.addAttribute("user", session.getAttribute("currentUser"));
        if (search != null && !search.isBlank()) {
            model.addAttribute("items", inventoryService.searchItems(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("items", inventoryService.getAllItems());
        }
        return "inventory";
    }

    /** Adds a new inventory item from the form */
    @PostMapping("/add")
    public String addItem(@RequestParam String productName,
                          @RequestParam String category,
                          @RequestParam int quantity,
                          @RequestParam String storageLocation,
                          HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        inventoryService.addItem(productName, category, quantity, storageLocation);
        return "redirect:/inventory";
    }

    /** Deletes an inventory item by ID */
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        inventoryService.deleteItem(id);
        return "redirect:/inventory";
    }
}
