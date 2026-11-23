
package com.group4.laundry.service;

import com.group4.laundry.dao.ServiceDAO;
import com.group4.laundry.model.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ServiceManager {

    private final ServiceDAO serviceDAO = new ServiceDAO();

    public List<Service> getAllServices() throws SQLException {
        return serviceDAO.getAllServices();
    }

    public void addService(String name, BigDecimal price,
                           BigDecimal capacity) throws SQLException {

        validateService(name, price, capacity);

        Service service = new Service(
            0, name.trim(), price, capacity
        );

        serviceDAO.addService(service);
    }

    public void updateService(int id, String name,
                              BigDecimal price,
                              BigDecimal capacity) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid service ID.");
        }

        validateService(name, price, capacity);

        Service service = new Service(
            id, name.trim(), price, capacity
        );

        serviceDAO.updateService(service);
    }

    public void deleteService(int id) throws SQLException {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid service ID.");
        }

        serviceDAO.deleteService(id);
    }

    private void validateService(String name, BigDecimal price,
                                 BigDecimal capacity) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Service name is required."
            );
        }

        if (name.trim().length() > 100) {
            throw new IllegalArgumentException(
                "Service name must not exceed 100 characters."
            );
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                "Price must be zero or greater."
            );
        }

        if (price.scale() > 2 ||
            price.compareTo(new BigDecimal("99999999.99")) > 0) {
            throw new IllegalArgumentException(
                "Price must fit the database format (10,2)."
            );
        }

        if (capacity == null ||
            capacity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Load capacity must be greater than zero."
            );
        }

        if (capacity.scale() > 2 ||
            capacity.compareTo(new BigDecimal("999.99")) > 0) {
            throw new IllegalArgumentException(
                "Load capacity must fit the database format (5,2)."
            );
        }
    }
}
