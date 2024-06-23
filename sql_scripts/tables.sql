CREATE TABLE users (
                       username varchar(50) not null ,
                       password varchar(50) not null ,
                       enabled tinyint not null,
                       primary key (username)
) engine=InnoDB Default charset=latin1;

create table authorities (
                             username varchar(50) not null ,
                             authority varchar(50) not null ,
                             unique key authorities_idx_1 (username,authority),
                             constraint authorities_ibfk_1 foreign key (username) references users(username)
) engine=InnoDB Default charset=latin1;

insert into users values('hameed', '{noop}admin', 1);
insert into authorities values('hameed', 'ROLE_ADMIN'),('hameed', 'ROLE_EMPLOYEE'),('hameed', 'ROLE_PHARMACIST');

-- Create UNIT_OF_MEASURES table
CREATE TABLE UNIT_OF_MEASURES (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  code VARCHAR(10) NOT NULL UNIQUE,
                                  full_name VARCHAR(255) NOT NULL
);

-- Create CATEGORY table
CREATE TABLE CATEGORY (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          code VARCHAR(10) NOT NULL UNIQUE,
                          full_name VARCHAR(255) NOT NULL,
                          description TEXT,
                          super_category_code VARCHAR(10),
                          FOREIGN KEY (super_category_code) REFERENCES CATEGORY(code)
);

-- Create MEDICATION table
CREATE TABLE MEDICATION (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            primary_uom_code VARCHAR(10),
                            exp_date DATE,
                            price DOUBLE NOT NULL,
                            quantity INT NOT NULL,
                            dosage_strength INT,
                            FOREIGN KEY (primary_uom_code) REFERENCES UNIT_OF_MEASURES(code)
);

-- Create SALES_HEADER table
CREATE TABLE SALES_HEADER (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              customer_name VARCHAR(255) NOT NULL,
                              date DATE NOT NULL,
                              total_amount DOUBLE NOT NULL,
                              username VARCHAR(50),
                              FOREIGN KEY (username) REFERENCES USERS(username)
);

-- Create SALES_ITEM table
CREATE TABLE SALES_ITEM (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            sales_id BIGINT NOT NULL,
                            medication_id BIGINT NOT NULL,
                            quantity INT NOT NULL,
                            price DOUBLE NOT NULL,
                            FOREIGN KEY (sales_id) REFERENCES SALES_HEADER(id),
                            FOREIGN KEY (medication_id) REFERENCES MEDICATION(id)
);

-- Create MEDICATIONS_CATEGORIES table
CREATE TABLE MEDICATIONS_CATEGORIES (
                                        medication_id BIGINT NOT NULL,
                                        category_id BIGINT NOT NULL,
                                        PRIMARY KEY (medication_id, category_id),
                                        FOREIGN KEY (medication_id) REFERENCES MEDICATION(id),
                                        FOREIGN KEY (category_id) REFERENCES CATEGORY(id)
);
