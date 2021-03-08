insert into clinics (name, location, phone, type, has_insurance, doctors)
values ('clinic 1', 'address 1', '044-123-13-2225',1,false,20),
       ('clinic 2', 'address 2', '044-123-13-2225',1,true,23),
       ('clinic 3', 'address 3', '044-123-13-2225',0,false,20),
       ('clinic 4', 'address 4', '044-123-13-2225',1,false,40),
       ('clinic 5', 'address 5', '044-123-13-2225',0,true,20),
       ('clinic 6', 'address 6', '044-123-13-2225',0,false,20);

insert into services (name, fee, coverage, time, clinic_id)
values ('service 1', 12, 0,'09',1),
       ('service 2', 18.4456, 0,'12:00',2),
       ('service 3', 33.5, 4,'09:00',1),
       ('service 1', 45, 0,'08:00',1),
       ('service 4', 11, 10,'16:00',3),
       ('service 1', 44.25, 0,'09:00',1),
       ('service 3', 123, 12,'07:00',3),
       ('service 8', 34, 0,'07:00',4),
       ('service 1', 0.50, 5,'10:00',4),
       ('service 1', 12.75, 0,'11:00',4);

update services set fee = fee + 10 where name like '%1%';

update clinics set type = 1;

update clinics c set type = 0 from services s where c.id = s.clinic_id and s.coverage > 0;

delete from services s using clinics c where s.clinic_id = c.id and  s.fee < 30;

delete  from clinics c where c.id not in (select distinct  s.clinic_id from services s);

select * from clinics c join services s on c.id = s.clinic_id where coverage = 0;

select * from clinics c left join  services s on c.id = s.clinic_id;

select * from clinics c left join  services s on c.id = s.clinic_id where clinic_id is null ;

