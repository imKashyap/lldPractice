package carRentalSystem.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import carRentalSystem.models.Customer;
import carRentalSystem.repository.CustomerRepository;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<String, Customer> customersById = new ConcurrentHashMap<>();

    @Override
    public void save(Customer customer) {
        customersById.put(customer.getId(), customer);
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(customersById.get(id));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customersById.values());
    }
}
