CREATE SCHEMA IF NOT EXISTS user_mgmt;
CREATE TABLE IF NOT EXISTS user_mgmt.users
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(35) NOT NULL UNIQUE,
    phone VARCHAR(15),
    role VARCHAR(14) NOT NULL,
    password VARCHAR(150) NOT NULL
);

INSERT INTO user_mgmt.users (id, name, email, phone, role, password) VALUES (1, 'Jon Doe', 'Jon_Doe@Gmail.com', '1122334455', 'city_planner', '$2b$10$XUXHk414IT/Rd2/uJkXbiejTDPFc4iAbiXn/MlwOtz79luXhUJKIe');
INSERT INTO user_mgmt.users (id, name, email, phone, role, password) VALUES (2, 'Jon Rose', 'jon_rose@test.com', '1234567798', 'citizen', '$2b$10$D3T0O0EQiQCcTgpRDOU4V.AaMhqrcaBp28N5HWSgr8i16Ji1mboBq');
INSERT INTO user_mgmt.users (id, name, email, phone, role, password) VALUES (3, 'Jeffar', 'jeffar21@test.com', '1234512345', 'city_planner', '$2b$10$AUjJxx.um0C52udiLLDtmOm0l7i4L5uKS0y6up7I7W29E7g/ww5T.');
INSERT INTO user_mgmt.users (id, name, email, phone, role, password) VALUES (4, 'Jonson', 'jonson@test.com', '1223344515', 'citizen', '$2b$10$.2udKLg5ccvQ91iMEkESpeDgzMAmqFToD0LSOVwKqb7L49r0u/ixW');
INSERT INTO user_mgmt.users (id, name, email, phone, role, password) VALUES (5, 'Rahul Kumar', 'rahul_new@test.com', '2233445566', 'citizen', '$2b$10$0Ow2T6WfAUnLWPgA9JbwtuSK25bnUodXfvXw6A5bt4iym8p9R7Ily');
INSERT INTO user_mgmt.users (id, name, email, phone, role, password) VALUES (6, 'Bhanu Pratap Das', 'bhanu@test.com', '12345654321', 'city_planner', '$2b$10$1TExJ7OiGcuRRePnIJBB9ulSowmeGkyrtJJH2J..c2tL.u.URTtVC');
COMMIT;
