CREATE SCHEMA IF NOT EXISTS rbh_mgmt;
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_type
(
    id bigint PRIMARY KEY,
    price_per_unit integer,
    name character varying(30),
    provider character varying(30),
    unit character varying(20)
);
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (1, 100, 'solar', 'E-ON', 'm3');
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (2, 200, 'electricity', 'RWE', 'kwh');
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (3, 300, 'gas', 'DEW21', 'm3');
INSERT INTO rbh_mgmt.energy_type(id, price_per_unit, name, provider, unit) VALUES (4, 400, 'heating-pump', 'octapus', 'm3');


CREATE TABLE IF NOT EXISTS rbh_mgmt.building
(
    id bigint PRIMARY KEY,
    floor_area integer,
    zip_code integer,
    address character varying(50),
    city character varying(40),
    owner_email character varying(35)
);
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_source
(
    id bigint PRIMARY KEY,
    building_id bigint NOT NULL,
    energy_type_id bigint NOT NULL,
    consumption integer,
    start_date timestamp(6) without time zone,
    end_date timestamp(6) without time zone,
    FOREIGN KEY (building_id) REFERENCES rbh_mgmt.building (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (energy_type_id) REFERENCES rbh_mgmt.energy_type (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
COMMIT;