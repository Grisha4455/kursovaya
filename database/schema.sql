CREATE DATABASE IF NOT EXISTS autodealer CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE autodealer;

CREATE TABLE IF NOT EXISTS employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    position VARCHAR(50),
    role VARCHAR(20) NOT NULL DEFAULT 'EMPLOYEE',
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS suppliers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS car_models (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    color VARCHAR(50),
    upholstery VARCHAR(50),
    engine_type VARCHAR(50),
    engine_power INT,
    transmission VARCHAR(50),
    doors INT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS car_model_suppliers (
    car_model_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    PRIMARY KEY (car_model_id, supplier_id),
    FOREIGN KEY (car_model_id) REFERENCES car_models(id) ON DELETE CASCADE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS price_list (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    car_model_id BIGINT NOT NULL,
    production_year INT NOT NULL,
    base_price DECIMAL(12, 2) NOT NULL,
    pre_sale_preparation DECIMAL(12, 2) DEFAULT 0,
    transport_costs DECIMAL(12, 2) DEFAULT 0,
    final_price DECIMAL(12, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_price_model FOREIGN KEY (car_model_id) REFERENCES car_models(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sales (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    car_model_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    price_list_id BIGINT NOT NULL,
    final_price DECIMAL(12, 2) NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (car_model_id) REFERENCES car_models(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (price_list_id) REFERENCES price_list(id)
);

CREATE TABLE IF NOT EXISTS customer_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20) NOT NULL,
    car_model_id BIGINT,
    message TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'NEW',
    response TEXT,
    employee_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (car_model_id) REFERENCES car_models(id) ON DELETE SET NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE SET NULL
);

INSERT INTO employees (username, password, full_name, email, phone, position, role) VALUES
('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Administrator', 'admin@autodealer.com', '+1-555-000-0000', 'Administrator', 'ADMIN'),
('manager1', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'John Smith', 'john@autodealer.com', '+1-555-111-1111', 'Sales Manager', 'EMPLOYEE'),
('manager2', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'Mary Johnson', 'mary@autodealer.com', '+1-555-222-2222', 'Sales Manager', 'EMPLOYEE');

INSERT INTO suppliers (name, contact_person, email, phone, address) VALUES
('AutoImport LLC', 'Peter Wilson', 'peter@autoimport.com', '+1-555-100-1000', 'New York, 5th Ave, 100'),
('MotorWorld Inc', 'Anna Davis', 'anna@motorworld.com', '+1-555-200-2000', 'Los Angeles, Main St, 200'),
('CarTrade Corp', 'Alex Brown', 'alex@cartrade.com', '+1-555-300-3000', 'Chicago, Oak St, 300');

INSERT INTO car_models (name, brand, color, upholstery, engine_type, engine_power, transmission, doors, description) VALUES
('Camry 3.5', 'Toyota', 'White', 'Leather', 'Gasoline', 249, 'Automatic', 4, 'Business class sedan'),
('RAV4 2.5', 'Toyota', 'Black', 'Fabric', 'Gasoline', 199, 'Automatic', 5, 'Popular crossover'),
('X5 3.0d', 'BMW', 'Blue', 'Leather', 'Diesel', 265, 'Automatic', 5, 'Premium crossover'),
('A6 2.0 TFSI', 'Audi', 'Gray', 'Leather', 'Gasoline', 252, 'Automatic', 4, 'Elegant sedan'),
('C-Class 200', 'Mercedes-Benz', 'Black', 'Leather', 'Gasoline', 204, 'Automatic', 4, 'Compact premium sedan'),
('Polo 1.6', 'Volkswagen', 'Red', 'Fabric', 'Gasoline', 110, 'Manual', 4, 'Reliable compact sedan');

INSERT INTO car_model_suppliers (car_model_id, supplier_id) VALUES
(1, 1), (1, 2), (2, 1), (2, 2), (3, 2), (3, 3), (4, 2), (4, 3), (5, 3), (6, 1);

INSERT INTO price_list (car_model_id, production_year, base_price, pre_sale_preparation, transport_costs, final_price) VALUES
(1, 2024, 2800000, 50000, 30000, 2880000),
(2, 2024, 2600000, 45000, 28000, 2673000),
(3, 2024, 5200000, 80000, 50000, 5330000),
(4, 2024, 3800000, 60000, 35000, 3895000),
(5, 2024, 3500000, 55000, 32000, 3587000),
(6, 2024, 1200000, 20000, 15000, 1235000);

INSERT INTO customers (full_name, email, phone, address) VALUES
('Robert Anderson', 'robert@email.com', '+1-555-400-4000', 'New York, Park Ave, 15'),
('Jennifer Miller', 'jennifer@email.com', '+1-555-500-5000', 'Los Angeles, Beach Rd, 20'),
('Michael Taylor', 'michael@email.com', '+1-555-600-6000', 'Chicago, Lake St, 8');

INSERT INTO sales (customer_id, car_model_id, employee_id, price_list_id, final_price, sale_date) VALUES
(1, 2, 2, 2, 2673000, '2024-11-15 14:30:00'),
(2, 6, 3, 6, 1235000, '2024-11-20 16:45:00'),
(3, 1, 2, 1, 2880000, '2024-12-01 11:20:00');

INSERT INTO customer_requests (customer_name, email, phone, car_model_id, message, status) VALUES
('David Wilson', 'david@email.com', '+1-555-700-7000', 1, 'Interested in Toyota Camry in white. Is test drive available?', 'NEW'),
('Sarah Johnson', 'sarah@email.com', '+1-555-800-8000', 3, 'Want to know more about BMW X5. Is it available?', 'NEW'),
('James Brown', 'james@email.com', '+1-555-900-9000', NULL, 'Looking for sedans in price range up to $30,000', 'NEW');
