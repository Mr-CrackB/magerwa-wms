package com.wms.service;

import com.wms.model.User;
import com.wms.repository.UserDAO;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Handles user authentication and session management.
 * Single Responsibility: only manages login/logout logic.
 */
@Service
public class AuthenticationService {

    private final UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /** Authenticates user and returns the User if valid */
    public Optional<User> login(String username, String password) {
        Optional<User> user = userDAO.authenticate(username, password);
        user.ifPresent(u -> System.out.println("[Auth] Logged in: " + u.getFullName()));
        return user;
    }
}
