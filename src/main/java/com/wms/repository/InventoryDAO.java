package com.wms.repository;

import com.wms.model.InventoryItem;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DAO Pattern implementation for InventoryItem.
 * Accesses data through the Singleton DatabaseConnection.
 */
@Repository
public class InventoryDAO implements GenericDAO<InventoryItem> {

    private final List<InventoryItem> table;

    public InventoryDAO() {
        this.table = DatabaseConnection.getInstance().getInventoryTable();
    }

    @Override
    public List<InventoryItem> findAll() { return table; }

    @Override
    public Optional<InventoryItem> findById(String id) {
        return table.stream().filter(i -> i.getItemId().equals(id)).findFirst();
    }

    @Override
    public void save(InventoryItem item) { table.add(item); }

    @Override
    public void update(InventoryItem item) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getItemId().equals(item.getItemId())) {
                table.set(i, item);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        table.removeIf(i -> i.getItemId().equals(id));
    }

    /** Custom query: find items with low or zero stock */
    public List<InventoryItem> findLowStockItems() {
        return table.stream()
                .filter(i -> i.getStatus().equals("Low Stock") || i.getStatus().equals("Out of Stock"))
                .collect(Collectors.toList());
    }

    /** Search by product name (case-insensitive) */
    public List<InventoryItem> searchByName(String keyword) {
        return table.stream()
                .filter(i -> i.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
