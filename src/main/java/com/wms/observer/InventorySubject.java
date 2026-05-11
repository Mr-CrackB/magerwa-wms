package com.wms.observer;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * DESIGN PATTERN: Observer Pattern - Subject
 * 
 * Maintains a list of observers and notifies them when
 * inventory stock levels change. Decouples inventory update
 * logic from alert/notification logic.
 */
@Component
public class InventorySubject {

    private final List<InventoryObserver> observers = new ArrayList<>();

    /** Registers a new observer */
    public void addObserver(InventoryObserver observer) {
        observers.add(observer);
        System.out.println("[Observer] Registered: " + observer.getClass().getSimpleName());
    }

    /** Removes an observer */
    public void removeObserver(InventoryObserver observer) {
        observers.remove(observer);
    }

    /** Notifies all registered observers about a stock change */
    public void notifyObservers(String productName, int oldQty, int newQty) {
        for (InventoryObserver observer : observers) {
            observer.update(productName, oldQty, newQty);
        }
    }
}
