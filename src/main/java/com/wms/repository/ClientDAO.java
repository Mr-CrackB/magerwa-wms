package com.wms.repository;

import com.wms.model.Client;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/** DAO Pattern implementation for Client entities. */
@Repository
public class ClientDAO implements GenericDAO<Client> {

    private final List<Client> table;

    public ClientDAO() {
        this.table = DatabaseConnection.getInstance().getClientsTable();
    }

    @Override public List<Client> findAll() { return table; }

    @Override
    public Optional<Client> findById(String id) {
        return table.stream().filter(c -> c.getClientId().equals(id)).findFirst();
    }

    @Override public void save(Client client) { table.add(client); }

    @Override
    public void update(Client client) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getClientId().equals(client.getClientId())) {
                table.set(i, client); return;
            }
        }
    }

    @Override
    public void delete(String id) { table.removeIf(c -> c.getClientId().equals(id)); }
}
