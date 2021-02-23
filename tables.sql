-- I think the service is unique to each clinic
-- because it has fee and insurance coverage percentages.
-- The tables are linked one to many.

create database  clinic_services;

create table clinics
(
    id        bigserial
        constraint clinics_pk primary key,
    name      varchar,
    location  varchar,
    phone     varchar,
    public      boolean,
    insurance boolean,
    doctors   int
);

create table services
(
    id        bigserial
        constraint services_pk primary key,
    name      varchar,
    fee       numeric(7,2),
    coverage  int,
    time      timestamp,
    clinic_id bigint not null
        constraint services_clinics_id_fk
            references clinics(id)
);

