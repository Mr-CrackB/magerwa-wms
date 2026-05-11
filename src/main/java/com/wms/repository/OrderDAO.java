package com.wms.repository;

import com.wms.model.Order;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** DAO Pattern implementation for Order entities. */
@Repository
public class OrderDAO implements GenericDAO<Order> {

    private final List<Order> table;

    public OrderDAO() {
        this.table = DatabaseConnection.getInstance().getOrdersTable();
    }

    @Override public List<Order> findAll() { return table; }

    @Override
    public Optional<Order> findById(String id) {
        return table.stream().filter(o -> o.getOrderId().equals(id)).findFirst();
    }

    @Override public void save(Order order) { table.add(order); }

    @Override
    public void update(Order order) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getOrderId().equals(order.getOrderId())) {
                table.set(i, order); return;
            }
        }
    }

    @Override
    public void delete(String id) { table.removeIf(o -> o.getOrderId().equals(id)); }

    public List<Order> findByStatus(String status) {
        return table.stream().filter(o -> o.getStatus().equals(status)).collect(Collectors.toList());
    }

    public long countByStatus(String status) {
        return table.stream().filter(o -> o.getStatus().equals(status)).count();
    }

    public List<Order> findByClientName(String clientName) {
        return table.stream().filter(o -> o.getClientName().equals(clientName)).collect(Collectors.toList());
    }
}
