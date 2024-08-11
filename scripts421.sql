--Возраст студента не может быть меньше 16 лет.
alter table student add constraint age_constraint check (age > 15);
--Имена студентов должны быть уникальными и не равны нулю.
alter table student alter column name set not null;
alter table student add constraint name_unique unique (name);
--Пара “значение названия” - “цвет факультета” должна быть уникальной.
alter table faculty add constraint color_unique unique (name, color);
--При создании студента без возраста ему автоматически должно присваиваться 20 лет.
alter table student alter age set default 20;
