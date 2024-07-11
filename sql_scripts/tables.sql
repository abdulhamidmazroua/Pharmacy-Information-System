-- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

-- Drop tables if they exist
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS AUTHORITIES;
DROP TABLE IF EXISTS UNIT_OF_MEASURES;
DROP TABLE IF EXISTS CATEGORY;
DROP TABLE IF EXISTS MEDICATION;
DROP TABLE IF EXISTS SALES_HEADER;
DROP TABLE IF EXISTS SALES_ITEM;
DROP TABLE IF EXISTS MEDICATIONS_CATEGORIES;

-- Enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Create USERS table
CREATE TABLE USERS (
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       enabled TINYINT NOT NULL,
                       PRIMARY KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create AUTHORITIES table
CREATE TABLE AUTHORITIES (
                             username VARCHAR(50) NOT NULL,
                             authority VARCHAR(50) NOT NULL,
                             UNIQUE KEY authorities_idx_1 (username, authority),
                             CONSTRAINT authorities_ibfk_1 FOREIGN KEY (username) REFERENCES USERS(username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create UNIT_OF_MEASURES table
CREATE TABLE UNIT_OF_MEASURES (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  code VARCHAR(10) NOT NULL UNIQUE,
                                  full_name VARCHAR(255) NOT NULL,
                                  creation_date timestamp DEFAULT current_timestamp,
                                  created_by VARCHAR(50),
                                  last_update_by VARCHAR(50),
                                  last_update_date timestamp DEFAULT current_timestamp ON UPDATE current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create CATEGORY table
CREATE TABLE CATEGORY (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          code VARCHAR(10) NOT NULL UNIQUE,
                          full_name VARCHAR(255) NOT NULL,
                          description TEXT,
                          super_category_code VARCHAR(10),
                          FOREIGN KEY (super_category_code) REFERENCES CATEGORY(code),
                          creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          created_by VARCHAR(50),
                          last_update_by VARCHAR(50),
                          last_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create MEDICATION table
CREATE TABLE MEDICATION (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            primary_uom_code VARCHAR(10),
                            category_code VARCHAR(10),
                            exp_date DATE,
                            price DOUBLE NOT NULL,
                            quantity INT NOT NULL,
                            dosage_strength INT,
                            FOREIGN KEY (primary_uom_code) REFERENCES UNIT_OF_MEASURES(code),
                            FOREIGN KEY (category_code) REFERENCES CATEGORY(code),
                            creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            created_by VARCHAR(50),
                            last_update_by VARCHAR(50),
                            last_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create SALES_HEADER table
CREATE TABLE SALES_HEADER (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              customer_name VARCHAR(255) NOT NULL,
                              total_amount DOUBLE NOT NULL,
                              username VARCHAR(50),
                              creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              created_by VARCHAR(50),
                              last_update_by VARCHAR(50),
                              last_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create SALES_ITEM table
CREATE TABLE SALES_ITEM (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            sales_id BIGINT NOT NULL,
                            medication_id BIGINT NOT NULL,
                            quantity INT NOT NULL,
                            unit_price DOUBLE NOT NULL,
                            FOREIGN KEY (sales_id) REFERENCES SALES_HEADER(id),
                            FOREIGN KEY (medication_id) REFERENCES MEDICATION(id),
                            creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            created_by VARCHAR(50),
                            last_update_by VARCHAR(50),
                            last_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Some Dummy insertions
INSERT INTO USERS VALUES('hameed', '{noop}admin', 1);
INSERT INTO AUTHORITIES VALUES('hameed', 'ROLE_ADMIN'), ('hameed', 'ROLE_EMPLOYEE'), ('hameed', 'ROLE_PHARMACIST');
