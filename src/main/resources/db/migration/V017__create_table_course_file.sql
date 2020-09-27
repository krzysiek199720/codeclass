DROP TABLE IF EXISTS "course"."file";

DROP SEQUENCE IF EXISTS "course"."file_seq_id";

CREATE SEQUENCE "course"."file_seq_id";

CREATE TABLE "course"."file"
(
    "id"            BIGINT          NOT NULL    DEFAULT nextval('course.file_seq_id')
    ,"display"      VARCHAR(50)     NOT NULL    DEFAULT 0
    ,"path"         VARCHAR(120)    NOT NULL    DEFAULT 0
    ,"courseid"     BIGINT          NOT NULL    REFERENCES course.course (id) DEFAULT 1
);

ALTER TABLE "course"."file" ADD CONSTRAINT "file_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_file_id_unique
    ON "course"."file" (id);
