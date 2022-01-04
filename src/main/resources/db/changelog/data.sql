--liquibase formatted sql
--changeset admin_sql:5

INSERT INTO public.table_role(
    name)
VALUES ('ROLE_ADMIN');

INSERT INTO public.table_role(
    name)
VALUES ('ROLE_USER');

INSERT INTO public.table_users(
    firstname, lastname, password, username)
VALUES ('Ivan', 'Ivanov', '$2a$10$cCbrc.ffTdw/C6.ULBTpAeWIdh5O/widd5xyQtV/OG4KSzwB3WYmy', 'Ivanov');

INSERT INTO public.table_users_roles(
    user_id, roles_id)
VALUES (1,2);

INSERT INTO public.table_users(
    firstname, lastname, password, username)
VALUES ('Yulia', 'Sergeeva', '$2a$10$By/hXET6rUlq3I4sKn/ZLe4eHJUKc5.SToZmkT478EMpOKnkhWUwG', 'Sergeeva');

INSERT INTO public.table_users_roles(
    user_id, roles_id)
VALUES (2,2);

INSERT INTO public.table_users(
    firstname, lastname, password, username)
VALUES ('Dmitry', 'Eremin', '$2a$10$l85i9NkeeT79BpqTqhyrVe92ybgdEIPHGe7.nMe/TllDOQuF1nEcy', 'Eremin');

INSERT INTO public.table_users_roles(
    user_id, roles_id)
VALUES (3,2);

INSERT INTO public.table_users(
    firstname, lastname, password, username)
VALUES ('admin', 'admin', '$2a$10$vgALloythrnjh4Gb0/RaSOvaMZKm2t/1FbNqxX1iv6MKHVtf0VIT2', 'admin');

INSERT INTO public.table_users_roles(
    user_id, roles_id)
VALUES (4,1);

INSERT INTO public.table_meetings(
    date, details, length, start, id_user)
VALUES ('2021-12-22', 'Meeting with the company', 120, 600, 1);

INSERT INTO public.table_meetings(
    date, details, length, start, id_user)
VALUES ('2021-12-21', 'Quarterly accounting report', 240, 480, 2);

INSERT INTO public.table_meetings(
    date, details, length, start, id_user)
VALUES ('2021-12-22', 'Discussion of a corporate party for the new year', 180, 1080, 3);

INSERT INTO public.table_meetings(
    date, details, length, start, id_user)
VALUES ('2021-12-17', 'QA engineers meeting', 120, 720, 3);

INSERT INTO public.table_meetings(
    date, details, length, start, id_user)
VALUES ('2021-12-29', 'Report for the past year', 180, 600, 3);

