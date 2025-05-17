truncate table sus.users cascade;
insert into sus.users  values (1, 'Ivan'),
                             (2, 'Petya'),
                             (3, 'Dasha'),
                             (4, 'Lena'),
                             (5, 'Oleg');

ALTER SEQUENCE sus.users_id_seq RESTART WITH 6;

insert into sus.user_subs values (1, 1),
                                 (1,2),
                                 (2,1),
                                 (3,3),
                                 (3,1),
                                 (4,3),
                                 (4,4),
                                 (4,2),
                                 (5,2),
                                 (5,1);