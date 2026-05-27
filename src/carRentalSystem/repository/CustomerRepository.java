package carRentalSystem.repository;

import java.util.List;
import java.util.Optional;

import carRentalSystem.models.Customer;

public interface CustomerRepository {
    void save(Customer customer);

    Optional<Customer> findById(String id);

    List<Customer> findAll();
}
