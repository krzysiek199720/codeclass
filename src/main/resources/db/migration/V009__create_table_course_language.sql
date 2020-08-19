DROP TABLE IF EXISTS "course"."language";

DROP SEQUENCE IF EXISTS "course"."language_seq_id";

CREATE SEQUENCE "course"."language_seq_id";

CREATE TABLE "course"."language"
(
    "id"                BIGINT                      NOT NULL    DEFAULT nextval('course.language_seq_id')
    ,"name"             VARCHAR(50)                 NOT NULL
);

ALTER TABLE "course"."language" ADD CONSTRAINT "language_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_language_id_unique
    ON "course"."language" (id);
