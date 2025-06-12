CREATE SCHEMA IF NOT EXISTS rbh_mgmt;
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_type
(
    id SERIAL PRIMARY KEY,
    price_per_unit real,
    name character varying(30),
    provider character varying(30),
    unit character varying(20)
);
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (1, 0.35, 'solar', 'E-ON', 'm3');
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (2, 0.45, 'electricity', 'RWE', 'kwh');
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (3, 0.75, 'gas', 'DEW21', 'm3');
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (4, 0.59, 'heating-pump', 'octapus', 'm3');


CREATE TABLE IF NOT EXISTS rbh_mgmt.building
(
    id SERIAL PRIMARY KEY,
    floor_area integer,
    zip_code integer,
    address character varying(50),
    city character varying(40),
    owner_email character varying(35) UNIQUE
);
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_source
(
    id SERIAL PRIMARY KEY,
    building_id Integer NOT NULL,
    energy_type_id Integer NOT NULL,
    consumption integer,
    start_date timestamp(6) without time zone,
    end_date timestamp(6) without time zone,
    FOREIGN KEY (building_id) REFERENCES rbh_mgmt.building (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (energy_type_id) REFERENCES rbh_mgmt.energy_type (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO rbh_mgmt.building (id, floor_area, zip_code, address, city, owner_email) VALUES (1, 26, 44222, 'ErmlingStr 1', 'Dortmund', 'rahul_new@test.com');
INSERT INTO rbh_mgmt.building (id, floor_area, zip_code, address, city, owner_email) VALUES (2, 32, 44225, 'ErmlingStr 23', 'Dortmund', 'jonson@test.com');
INSERT INTO rbh_mgmt.building (id, floor_area, zip_code, address, city, owner_email) VALUES (3, 45, 44323, 'Stiftstr 2', 'Dortmund', 'jon_rose@test.com');

INSERT INTO rbh_mgmt.energy_source (id, building_id, energy_type_id, consumption, start_date, end_date) VALUES (1, 1, 2, 458, '2024-03-01 00:00:00', '2024-05-31 00:00:00');
INSERT INTO rbh_mgmt.energy_source (id, building_id, energy_type_id, consumption, start_date, end_date) VALUES (3, 3, 4, 532, '2024-02-01 00:00:00', '2024-05-31 00:00:00');
INSERT INTO rbh_mgmt.energy_source (id, building_id, energy_type_id, consumption, start_date, end_date) VALUES (5, 1, 2, 411, '2024-09-01 00:00:00', '2024-11-30 00:00:00');
INSERT INTO rbh_mgmt.energy_source (id, building_id, energy_type_id, consumption, start_date, end_date) VALUES (4, 3, 2, 413, '2024-09-01 00:00:00', '2024-11-30 00:00:00');
INSERT INTO rbh_mgmt.energy_source (id, building_id, energy_type_id, consumption, start_date, end_date) VALUES (6, 2, 4, 444, '2024-08-01 00:00:00', '2024-11-30 00:00:00');
INSERT INTO rbh_mgmt.energy_source (id, building_id, energy_type_id, consumption, start_date, end_date) VALUES (2, 2, 3, 437, '2024-08-01 00:00:00', '2024-12-31 00:00:00');
INSERT INTO rbh_mgmt.energy_source (id, building_id, energy_type_id, consumption, start_date, end_date) VALUES (7, 2, 1, 335, '2025-01-01 00:00:00', '2025-04-30 00:00:00');
SELECT setval(
  pg_get_serial_sequence('rbh_mgmt.energy_source', 'id'),
  (SELECT COALESCE(MAX(id), 1) FROM rbh_mgmt.energy_source),
  true
);
SELECT setval(
  pg_get_serial_sequence('rbh_mgmt.building', 'id'),
  (SELECT COALESCE(MAX(id), 1) FROM rbh_mgmt.building),
  true
);
COMMIT;