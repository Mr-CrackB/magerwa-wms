package com.wms.controller;

import com.wms.model.Client;
import com.wms.repository.ClientDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/** MVC Controller for Client Management. */
@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientDAO clientDAO;

    public ClientController(ClientDAO clientDAO) { this.clientDAO = clientDAO; }

    @GetMapping
    public String listClients(HttpSession session, Model model) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        model.addAttribute("user", session.getAttribute("currentUser"));
        model.addAttribute("clients", clientDAO.findAll());
        return "clients";
    }

    @PostMapping("/add")
    public String addClient(@RequestParam String companyName, @RequestParam String contactPerson,
                            @RequestParam String email, @RequestParam String phone,
                            @RequestParam String address, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        String id = "C" + UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        clientDAO.save(new Client(id, companyName, contactPerson, email, phone, address));
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("currentUser") == null) return "redirect:/";
        clientDAO.delete(id);
        return "redirect:/clients";
    }
}
