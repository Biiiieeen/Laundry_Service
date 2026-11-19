
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
