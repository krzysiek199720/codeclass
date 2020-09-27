DROP TABLE IF EXISTS "course"."link";

DROP SEQUENCE IF EXISTS "course"."link_seq_id";

CREATE SEQUENCE "course"."link_seq_id";

CREATE TABLE "course"."link"
(
    "id"            BIGINT          NOT NULL    DEFAULT nextval('course.link_seq_id')
    ,"display"      VARCHAR(50)     NOT NULL    DEFAULT 0
    ,"link"         VARCHAR(2048)   NOT NULL    DEFAULT 0
    ,"courseid"     BIGINT          NOT NULL    REFERENCES course.course (id) DEFAULT 1
);

ALTER TABLE "course"."link" ADD CONSTRAINT "link_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_link_id_unique
    ON "course"."link" (id);
