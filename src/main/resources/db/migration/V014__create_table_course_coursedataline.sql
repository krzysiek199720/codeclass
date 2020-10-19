DROP TABLE IF EXISTS "course"."coursedataline";

DROP SEQUENCE IF EXISTS "course"."coursedataline_seq_id";

CREATE SEQUENCE "course"."coursedataline_seq_id";

CREATE TABLE "course"."coursedataline"
(
    "id"                BIGINT      NOT NULL    DEFAULT nextval('course.coursedataline_seq_id')
    ,"theorder"            INTEGER     NOT NULL    DEFAULT 0
    ,"indent"           INTEGER     NOT NULL    DEFAULT 0
    ,"coursedataid"     BIGINT      NOT NULL    REFERENCES course.coursedata (id) DEFAULT 1
);

ALTER TABLE "course"."coursedataline" ADD CONSTRAINT "coursedataline_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_coursedataline_id_unique
    ON "course"."coursedataline" (id);
