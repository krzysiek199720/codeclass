DROP TABLE IF EXISTS "course"."coursegroup";

DROP SEQUENCE IF EXISTS "course"."coursegroup_seq_id";

CREATE SEQUENCE "course"."coursegroup_seq_id";

CREATE TABLE "course"."coursegroup"
(
    "id"        BIGINT      NOT NULL    DEFAULT nextval('course.coursegroup_seq_id')
    ,"name"     VARCHAR(50) NOT NULL
    ,"userid"   BIGINT      NOT NULL    REFERENCES auth."user" (id) DEFAULT 1
);

ALTER TABLE "course"."coursegroup" ADD CONSTRAINT "coursegroup_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_coursegroup_id_unique
    ON "course"."coursegroup" (id);
