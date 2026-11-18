-- Group 4: exactly the seven tables and column names in the supplied ERD.
-- MySQL 8.0+; import ONCE into a new empty database. Never drops existing data.
-- Implementation assumptions (not specified in the image): integer auto-generated
-- primary keys; required FKs; nullable business fields; InnoDB; restrictive deletes.
-- Confirm these constraints with the group before building CRUD operations.
-- Status values, billing formulas and policy defaults are deliberately not invented.
CREATE DATABASE IF NOT EXISTS laundry_db CHARACTER SET utf8mb4;
USE laundry_db;

CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    contact_number VARCHAR(20),
    address VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE shop_policies (
    policy_id INT AUTO_INCREMENT PRIMARY KEY,
    free_storage_days INT,
    reminder_after_days INT,
    storage_fee_per_day DECIMAL(10,2),
    review_after_days INT
) ENGINE=InnoDB;

CREATE TABLE services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100),
    price_per_kg DECIMAL(10,2),
    max_kg_per_load DECIMAL(10,2)
) ENGINE=InnoDB;

CREATE TABLE add_ons (
    add_on_id INT AUTO_INCREMENT PRIMARY KEY,
    add_on_name VARCHAR(100),
    unit_label VARCHAR(20),
    price_per_unit DECIMAL(10,2),
    is_active BOOLEAN
) ENGINE=InnoDB;

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    policy_id INT NOT NULL,
    order_date DATETIME,
    status VARCHAR(20),
    ready_at DATETIME,
    claimed_at DATETIME,
    free_days_of_ready INT,
    fee_per_day_if_ready DECIMAL(10,2),
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_orders_policy FOREIGN KEY (policy_id) REFERENCES shop_policies(policy_id)
) ENGINE=InnoDB;

CREATE TABLE order_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    service_id INT NOT NULL,
    weight_kg DECIMAL(10,3),
    unit_price DECIMAL(10,2),
    load_capacity_kg DECIMAL(10,2),
    CONSTRAINT fk_details_order FOREIGN KEY (order_id) REFERENCES orders(order_id),
    CONSTRAINT fk_details_service FOREIGN KEY (service_id) REFERENCES services(service_id)
) ENGINE=InnoDB;

CREATE TABLE order_detail_add_ons (
    selection_id INT AUTO_INCREMENT PRIMARY KEY,
    detail_id INT NOT NULL,
    add_on_id INT NOT NULL,
    quantity INT,
    unit_price DECIMAL(10,2),
    CONSTRAINT fk_selection_detail FOREIGN KEY (detail_id) REFERENCES order_details(detail_id),
    CONSTRAINT fk_selection_add_on FOREIGN KEY (add_on_id) REFERENCES add_ons(add_on_id)
) ENGINE=InnoDB;
