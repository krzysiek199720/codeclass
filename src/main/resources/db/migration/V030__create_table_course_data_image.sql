DROP TABLE IF EXISTS "course"."dataimage";

DROP SEQUENCE IF EXISTS "course"."dataimage_seq_id";

CREATE SEQUENCE "course"."dataimage_seq_id";

CREATE TABLE "course"."dataimage"
(
    "id"                BIGINT          NOT NULL   DEFAULT nextval('course.dataimage_seq_id')
    ,"courseid"         BIGINT          NOT NULL   REFERENCES course.course (id) DEFAULT 1
    ,"localid"          VARCHAR(20)     NOT NULL   DEFAULT ''
    ,"path"             VARCHAR(120)    NOT NULL   DEFAULT 0
    ,"type"             VARCHAR(4)      NOT NULL   DEFAULT 'jpg'
);

ALTER TABLE "course"."dataimage" ADD CONSTRAINT "dataimage_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_dataimage_id_unique
    ON "course"."dataimage" (id);
