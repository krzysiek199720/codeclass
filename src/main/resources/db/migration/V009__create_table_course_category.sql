DROP TABLE IF EXISTS "course"."category";

DROP SEQUENCE IF EXISTS "course"."category_seq_id";

CREATE SEQUENCE "course"."category_seq_id";

CREATE TABLE "course"."category"
(
    "id"                BIGINT                      NOT NULL    DEFAULT nextval('course.category_seq_id')
    ,"name"             VARCHAR(50)                 NOT NULL
);

ALTER TABLE "course"."category" ADD CONSTRAINT "category_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_category_id_unique
    ON "course"."category" (id);
