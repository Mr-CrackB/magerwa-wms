package com.wms.controller;

import com.wms.model.User;
import com.wms.model.User.UserRole;
import com.wms.repository.UserDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * User Management Controller — accessible only by ADMIN and MANAGER roles.
 * Allows creating and deleting system user accounts.
 */
@Controller
@RequestMapping("/users")
public class UserManagementController {

    private final UserDAO userDAO;

    public UserManagementController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        return user != null &&
               (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.MANAGER);
    }

    @GetMapping
    public String listUsers(HttpSession session, Model model) {
        if (!isAuthorized(session)) return "redirect:/";
        model.addAttribute("user", session.getAttribute("currentUser"));
        model.addAttribute("users", userDAO.findAll());
        model.addAttribute("roles", UserRole.values());
        return "users";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String fullName,
                          @RequestParam String email,
                          @RequestParam String role,
                          HttpSession session, Model model) {
        if (!isAuthorized(session)) return "redirect:/";

        if (userDAO.findByUsername(username).isPresent()) {
            model.addAttribute("user", session.getAttribute("currentUser"));
            model.addAttribute("users", userDAO.findAll());
            model.addAttribute("roles", UserRole.values());
            model.addAttribute("error", "Username \"" + username + "\" is already taken.");
            return "users";
        }

        String id = "U" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        userDAO.save(new User(id, username, password, fullName, email, UserRole.valueOf(role)));
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/";
        User current = (User) session.getAttribute("currentUser");
        if (!current.getUserId().equals(id)) {
            userDAO.delete(id);
        }
        return "redirect:/users";
    }

    @GetMapping("/approve/{id}")
    public String approveUser(@PathVariable String id, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/";
        userDAO.findById(id).ifPresent(u -> {
            u.setApproved(true);
            userDAO.update(u);
        });
        return "redirect:/users";
    }
}
