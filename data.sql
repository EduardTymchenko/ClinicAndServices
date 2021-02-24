insert into clinics (name, location, phone, public, insurance, doctors)
values ('clinic 1', 'address 1', '044-123-13-2225',true,false,20),
       ('clinic 2', 'address 2', '044-123-13-2225',true,false,20),
       ('clinic 3', 'address 3', '044-123-13-2225',true,false,20),
       ('clinic 4', 'address 4', '044-123-13-2225',true,false,20),
       ('clinic 5', 'address 5', '044-123-13-2225',true,false,20),
       ('clinic 6', 'address 6', '044-123-13-2225',true,false,20);

insert into services (name, fee, coverage, time, clinic_id)
values ('service 1', 12, 0,'2021-02-23 09:00',1),
       ('service 2', 18.4456, 0,'2021-02-23 10:00',2),
       ('service 3', 33.5, 4,'2021-02-23 08:00',1),
       ('service 1', 45, 0,'2021-02-22 11:00',1),
       ('service 4', 11, 10,'2021-02-20 17:00',3),
       ('service 1', 44.25, 0,'2021-02-23 11:00',1),
       ('service 3', 123, 12,'2021-02-23 13:00',3),
       ('service 8', 34, 0,'2021-01-23 09:00',4),
       ('service 1', 0.50, 5,'2021-02-26 11:00',4),
       ('service 1', 12.75, 0,'2021-02-27 11:00',4);

update services set fee = fee + 10 where name like '%1%';

update clinics set public = true;

update clinics c set public = false from services s where c.id = s.clinic_id and s.coverage > 0;

delete from services s using clinics c where s.clinic_id = c.id and  s.fee < 30;

delete  from clinics c  using services s where c.id not in (select distinct  s.clinic_id from s);

select * from clinics c join services s on c.id = s.clinic_id where coverage = 0;

select * from clinics c left join  services s on c.id = s.clinic_id;

select * from clinics c left join  services s on c.id = s.clinic_id where clinic_id is null ;

