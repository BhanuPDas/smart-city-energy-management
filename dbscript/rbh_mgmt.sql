CREATE SCHEMA IF NOT EXISTS rbh_mgmt;
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_source
(
    energy_type VARCHAR(14) PRIMARY KEY,
    provider VARCHAR(15) NOT NULL,
    price_per_unit real NOT NULL
);
INSERT INTO rbh_mgmt.energy_source(energy_type, provider, price_per_unit) VALUES ('renewable', 'DEW21', 0.25);
INSERT INTO rbh_mgmt.energy_source(energy_type, provider, price_per_unit) VALUES ('solar', 'DEW21', 0.30);
INSERT INTO rbh_mgmt.energy_source(energy_type, provider, price_per_unit) VALUES ('electricity', 'DEW21', 0.50);
INSERT INTO rbh_mgmt.energy_source(energy_type, provider, price_per_unit) VALUES ('gas', 'DEW21', 0.65);
CREATE TABLE IF NOT EXISTS rbh_mgmt.building_details
(
    id SERIAL PRIMARY KEY,
    address VARCHAR(50) NOT NULL,
    zipcode integer NOT NULL,
    city VARCHAR(30) NOT NULL,
    owner_email VARCHAR(35)
);
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_details
(
    id SERIAL PRIMARY KEY ,
    building_id integer NOT NULL,
    energy_type character varying(14),
    start_date date,
    end_date date,
    units_consumed integer,
    amount real
);
COMMIT;