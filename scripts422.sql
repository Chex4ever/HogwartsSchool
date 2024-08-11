CREATE TABLE public.human (
    id bigserial NOT NULL,
    CONSTRAINT human_pk PRIMARY KEY (id),
	name varchar NOT NULL,
	age int2 NULL,
	CONSTRAINT human_age_check CHECK ((age > 0) AND (age < 150)),
	can_drive boolean DEFAULT false NULL
);

CREATE TABLE public.car (
	id bigserial NOT NULL,
	brand varchar NULL,
	model varchar NULL,
	price int8 NULL,
	CONSTRAINT car_pk PRIMARY KEY (id)
);

CREATE TABLE public.car_owners (
	car_id bigserial NOT NULL,
	owner_id bigserial NOT NULL,
	CONSTRAINT car_owners_pk PRIMARY KEY (car_id,owner_id),
	CONSTRAINT car_owners_human_fk FOREIGN KEY (owner_id) REFERENCES public.human(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT car_owners_car_fk FOREIGN KEY (car_id) REFERENCES public.car(id) ON DELETE CASCADE ON UPDATE CASCADE
);

