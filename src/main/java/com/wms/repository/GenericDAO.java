package com.wms.repository;

import java.util.List;
import java.util.Optional;

/**
 * DESIGN PATTERN: DAO (Data Access Object) Pattern
 * 
 * Defines standard CRUD operations for all data entities.
 * Separates data access logic from business logic (Single Responsibility).
 * Makes it easy to switch databases without changing business code.
 * 
 * @param <T> the entity type this DAO manages
 */
public interface GenericDAO<T> {
    List<T> findAll();
    Optional<T> findById(String id);
    void save(T entity);
    void update(T entity);
    void delete(String id);
}
