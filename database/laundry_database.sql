-- Laundry Services Management System
-- MySQL schema based on the supplied entity-relationship diagram.
-- Select your target database before running this file (for example, in phpMyAdmin).
-- This creates seven empty tables. It does not drop tables or migrate existing data.
-- If these tables already exist, use a reviewed migration or a fresh database.
-- The diagram does not specify nullability, defaults, or referential actions;
-- the choices below require parent records and preserve historical references.

SET NAMES utf8mb4;

CREATE TABLE customers (
    customer_id INT NOT NULL AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(30) NULL,
    address VARCHAR(255) NULL,
    PRIMARY KEY (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE shop_policies (
    policy_id INT NOT NULL AUTO_INCREMENT,
    free_storage_days INT NOT NULL,
    reminder_after_days INT NOT NULL,
    storage_fee_per_day DECIMAL(10,2) NOT NULL,
    review_after_days INT NOT NULL,
    PRIMARY KEY (policy_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE services (
    service_id INT NOT NULL AUTO_INCREMENT,
    service_name VARCHAR(100) NOT NULL,
    price_per_kg DECIMAL(10,2) NOT NULL,
    max_kg_per_load DECIMAL(5,2) NOT NULL,
    PRIMARY KEY (service_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE add_ons (
    add_on_id INT NOT NULL AUTO_INCREMENT,
    add_on_name VARCHAR(100) NOT NULL,
    unit_label VARCHAR(30) NOT NULL,
    price_per_unit DECIMAL(10,2) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (add_on_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE orders (
    order_id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    policy_id INT NOT NULL,
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    ready_at DATETIME NULL,
    claimed_at DATETIME NULL,
    free_days_at_ready INT NULL,
    fee_per_day_at_ready DECIMAL(10,2) NULL,
    PRIMARY KEY (order_id),
    KEY idx_orders_customer_id (customer_id),
    KEY idx_orders_policy_id (policy_id),
    CONSTRAINT fk_orders_customer
        FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_orders_policy
        FOREIGN KEY (policy_id) REFERENCES shop_policies (policy_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE order_details (
    detail_id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    service_id INT NOT NULL,
    weight_kg DECIMAL(8,2) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    load_capacity_kg DECIMAL(5,2) NOT NULL,
    PRIMARY KEY (detail_id),
    KEY idx_order_details_order_id (order_id),
    KEY idx_order_details_service_id (service_id),
    CONSTRAINT fk_order_details_order
        FOREIGN KEY (order_id) REFERENCES orders (order_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_order_details_service
        FOREIGN KEY (service_id) REFERENCES services (service_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE order_detail_add_ons (
    selection_id INT NOT NULL AUTO_INCREMENT,
    detail_id INT NOT NULL,
    add_on_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (selection_id),
    KEY idx_order_detail_add_ons_detail_id (detail_id),
    KEY idx_order_detail_add_ons_add_on_id (add_on_id),
    CONSTRAINT fk_order_detail_add_ons_detail
        FOREIGN KEY (detail_id) REFERENCES order_details (detail_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_order_detail_add_ons_add_on
        FOREIGN KEY (add_on_id) REFERENCES add_ons (add_on_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Application calculations shown in the diagram:
--   loads = CEIL(weight_kg / load_capacity_kg)
--   add-on charge = quantity * unit_price
--   order total = laundry charges + selected extras + applicable late fees
-- Validate positive weights/capacities/quantities and nonnegative prices in the app.
-- Copy service prices and capacities into order_details when an order is placed.
-- Copy add-on prices into order_detail_add_ons when extras are selected.
-- Copy the policy's free days and daily fee into orders when it becomes ready.
-- Set ready_at and claimed_at as the order moves through the service workflow.
-- Derived totals and load counts are not stored because the diagram omits them.
