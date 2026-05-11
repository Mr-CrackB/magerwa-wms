package com.wms.controller;

import com.wms.model.User;
import com.wms.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * MVC Controller for user authentication.
 * Manages the login page, authentication, and logout.
 * User Journey: Login → Dashboard → Modules → Logout
 */
@Controller
public class LoginController {

    private final AuthenticationService authService;

    public LoginController(AuthenticationService authService) {
        this.authService = authService;
    }

    /** Displays the login page */
    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    /** Processes login form submission */
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session, Model model) {
        Optional<User> user = authService.login(username, password);

        if (user.isPresent()) {
            if (!user.get().isApproved()) {
                model.addAttribute("error", "Your account is pending approval by an Admin or Manager.");
                return "login";
            }
            session.setAttribute("currentUser", user.get());
            if (user.get().getRole() == User.UserRole.CLIENT) {
                return "redirect:/client/orders";
            }
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    /** Logs out and redirects to login */
    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user != null) {
            System.out.println("[Auth] Logged out: " + user.getFullName());
        }
        session.invalidate();
        return "redirect:/";
    }
}
