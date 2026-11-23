
package com.group4.laundry.dao;

import com.group4.laundry.config.DatabaseConnection;
import com.group4.laundry.model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    // Retrieve all laundry services
    public List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();

        String sql = "SELECT service_id, service_name, price_per_kg, "
                   + "max_kg_per_load FROM services ORDER BY service_id";

        try (Connection conn = DatabaseConnection.open();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                services.add(new Service(
                    rs.getInt("service_id"),
                    rs.getString("service_name"),
                    rs.getBigDecimal("price_per_kg"),
                    rs.getBigDecimal("max_kg_per_load")
                ));
            }
        }

        return services;
    }

    // Add a new laundry service
    public void addService(Service service) throws SQLException {
        String sql = "INSERT INTO services "
                   + "(service_name, price_per_kg, max_kg_per_load) "
                   + "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, service.getServiceName());
            stmt.setBigDecimal(2, service.getPricePerKg());
            stmt.setBigDecimal(3, service.getMaxKgPerLoad());

            stmt.executeUpdate();
        }
    }

    // Update an existing laundry service
    public void updateService(Service service) throws SQLException {
        String sql = "UPDATE services SET service_name = ?, "
                   + "price_per_kg = ?, max_kg_per_load = ? "
                   + "WHERE service_id = ?";

        try (Connection conn = DatabaseConnection.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, service.getServiceName());
            stmt.setBigDecimal(2, service.getPricePerKg());
            stmt.setBigDecimal(3, service.getMaxKgPerLoad());
            stmt.setInt(4, service.getServiceId());

            stmt.executeUpdate();
        }
    }

    // Delete a service only if no order references it
    public void deleteService(int serviceId) throws SQLException {
        String sql = "DELETE FROM services WHERE service_id = ?";

        try (Connection conn = DatabaseConnection.open();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
        }
    }
}
