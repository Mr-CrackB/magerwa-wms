package com.wms.repository;

import com.wms.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/** DAO Pattern implementation for User authentication and management. */
@Repository
public class UserDAO implements GenericDAO<User> {

    private final List<User> table;

    public UserDAO() {
        this.table = DatabaseConnection.getInstance().getUsersTable();
    }

    @Override public List<User> findAll() { return table; }
    @Override
    public Optional<User> findById(String id) {
        return table.stream().filter(u -> u.getUserId().equals(id)).findFirst();
    }
    @Override public void save(User user) { table.add(user); }
    @Override public void update(User user) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getUserId().equals(user.getUserId())) { table.set(i, user); return; }
        }
    }
    @Override public void delete(String id) { table.removeIf(u -> u.getUserId().equals(id)); }

    /** Authenticates user by matching username and password */
    public Optional<User> authenticate(String username, String password) {
        return table.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return table.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }
}
