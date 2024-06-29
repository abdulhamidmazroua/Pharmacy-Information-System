INSERT INTO UNIT_OF_MEASURES (code, full_name, created_by, last_update_by) VALUES
                                                                               ('MG', 'Milligram', 'system', 'system'),
                                                                               ('ML', 'Milliliter', 'system', 'system'),
                                                                               ('TAB', 'Tablet', 'system', 'system');
INSERT INTO CATEGORY (code, full_name, description, super_category_code, created_by, last_update_by) VALUES
                                                                                                         ('ANT', 'Antibiotics', 'Medications used to treat bacterial infections.', NULL, 'system', 'system'),
                                                                                                         ('ANALG', 'Analgesics', 'Pain relievers.', NULL, 'system', 'system'),
                                                                                                         ('VIT', 'Vitamins', 'Supplements for vitamin deficiencies.', NULL, 'system', 'system');
INSERT INTO MEDICATION (name, description, primary_uom_code, category_code, exp_date, price, quantity, dosage_strength, created_by, last_update_by) VALUES
                                                                                                                                         ('Amoxicillin', 'Antibiotic used to treat a wide variety of bacterial infections.', 'MG', 'ANT', '2024-12-31', 5.00, 100, 500, 'system', 'system'),
                                                                                                                                         ('Ibuprofen', 'Nonsteroidal anti-inflammatory drug used for pain relief.', 'TAB', 'VIT', '2024-12-31', 0.20, 200, 200, 'system', 'system'),
                                                                                                                                         ('Vitamin C', 'Supplement used to prevent or treat low levels of vitamin C.', 'MG','ANT', '2025-01-31', 0.10, 300, 1000, 'system', 'system');
INSERT INTO SALES_HEADER (customer_name, date, total_amount, username, created_by, last_update_by) VALUES
                                                                                                       ('John Doe', '2024-06-01', 25.00, 'hameed', 'hameed', 'hameed'),
                                                                                                       ('Jane Smith', '2024-06-02', 15.00, 'hameed', 'hameed', 'hameed');
INSERT INTO SALES_ITEM (sales_id, medication_id, quantity, unit_price, created_by, last_update_by) VALUES
                                                                                                       (1, 1, 2, 5.00, 'hameed', 'hameed'),  -- John Doe bought 2 Amoxicillin
                                                                                                       (1, 2, 5, 0.20, 'hameed', 'hameed'),  -- John Doe bought 5 Ibuprofen
                                                                                                       (2, 3, 10, 0.10, 'hameed', 'hameed'); -- Jane Smith bought 10 Vitamin C
