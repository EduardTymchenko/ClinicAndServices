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
    public    boolean,
    insurance boolean,
    doctors   int
);

create table services
(
    id           bigserial
        constraint services_pk primary key,
    name         varchar,
    fee          numeric(7, 2),
    coverage     int,
    start_time   time,
    end_time     time,
    days_of_week int,
    clinic_id    bigint not null
        constraint services_clinics_id_fk
            references clinics (id)
);

ALTER TABLE clinics RENAME COLUMN insurance TO has_insurance;
ALTER TABLE clinics ADD type int;
ALTER TABLE clinics DROP COLUMN public;


ALTER TABLE services ADD time varchar;
ALTER TABLE services DROP COLUMN start_time;
ALTER TABLE services DROP COLUMN end_time;
ALTER TABLE services DROP COLUMN days_of_week;
