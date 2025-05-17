CREATE SCHEMA IF NOT EXISTS city_mgmt;
CREATE TABLE IF NOT EXISTS city_mgmt.requests
(
    id SERIAL PRIMARY KEY,
    app VARCHAR(15),
    request VARCHAR(1000),
    response VARCHAR(1000)
);
