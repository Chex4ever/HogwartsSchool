-- liquibase formatted sql

-- changeset exever:1

CREATE INDEX student_name ON student (name);

-- changeset exever:2

CREATE INDEX faculty_name_color ON faculty (name,color);
