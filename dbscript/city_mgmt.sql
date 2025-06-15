CREATE SCHEMA IF NOT EXISTS city_mgmt;
CREATE TABLE IF NOT EXISTS city_mgmt.requests
(
    id SERIAL PRIMARY KEY,
    app VARCHAR(15),
    request VARCHAR(3000),
    response VARCHAR(3000)
);
COMMIT;
