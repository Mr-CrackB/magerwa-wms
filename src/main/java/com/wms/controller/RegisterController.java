package com.wms.controller;

import com.wms.model.User;
import com.wms.model.User.UserRole;
import com.wms.repository.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * Public registration controller.
 * Anyone can sign up — accounts are saved as unapproved until an Admin/Manager approves them.
 */
@Controller
public class RegisterController {

    private final UserDAO userDAO;

    public RegisterController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String fullName,
                                 @RequestParam String email,
                                 @RequestParam String role,
                                 Model model) {
        if (userDAO.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username \"" + username + "\" is already taken. Please choose another.");
            return "register";
        }

        String id = "U" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        userDAO.save(new User(id, username, password, fullName, email, UserRole.valueOf(role), false));
        model.addAttribute("success", "Account created! Please wait for an Admin or Manager to approve your account before logging in.");
        return "register";
    }
}
