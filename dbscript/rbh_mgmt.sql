CREATE SCHEMA IF NOT EXISTS rbh_mgmt;
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_type
(
    price_per_unit integer,
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    provider character varying(255) COLLATE pg_catalog."default",
    unit character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT energy_type_pkey PRIMARY KEY (id)
);
INSERT INTO public.energy_type(
    price_per_unit, id, name, provider, unit)
VALUES (100, 1, 'solar', 'E-ON', 'm3');

INSERT INTO public.energy_type(
    price_per_unit, id, name, provider, unit)
VALUES (200, 2, 'electricity', 'RWE', 'kwh');

INSERT INTO public.energy_type(
    price_per_unit, id, name, provider, unit)
VALUES (300, 3, 'gas', 'DEW21', 'm3');

INSERT INTO public.energy_type(
    price_per_unit, id, name, provider, unit)
VALUES (400, 4, 'heating-pump', 'octapus', 'm3');


CREATE TABLE IF NOT EXISTS rbh_mgmt.building
(
    floor_area integer,
    zip_code integer,
    id bigint NOT NULL,
    address character varying(255) COLLATE pg_catalog."default",
    city character varying(255) COLLATE pg_catalog."default",
    owner_email character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT building_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS rbh_mgmt.energy_source
(
    consumption integer,
    building_id bigint NOT NULL,
    end_date timestamp(6) without time zone,
    energy_type_id bigint NOT NULL,
    id bigint NOT NULL,
    start_date timestamp(6) without time zone,
    CONSTRAINT energy_source_pkey PRIMARY KEY (id),
    CONSTRAINT fkclt7a13bgd1o8si3oagl5u4ky FOREIGN KEY (building_id)
    REFERENCES public.building (id) MATCH SIMPLE
                          ON UPDATE NO ACTION
                          ON DELETE NO ACTION,
    CONSTRAINT fkqcdjkhvyi6s26dpjqoknctk2x FOREIGN KEY (energy_type_id)
    REFERENCES public.energy_type (id) MATCH SIMPLE
                          ON UPDATE NO ACTION
                          ON DELETE NO ACTION
);
COMMIT;