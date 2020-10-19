DROP TABLE IF EXISTS "course"."coursedata";

DROP SEQUENCE IF EXISTS "course"."coursedata_seq_id";

CREATE SEQUENCE "course"."coursedata_seq_id";

CREATE TABLE "course"."coursedata"
(
    "id"                  BIGINT                      NOT NULL    DEFAULT nextval('course.coursedata_seq_id')
    ,"createdat"          TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"type"               VARCHAR(20)                 NOT NULL
    ,"theorder"              INTEGER                     NOT NULL    DEFAULT 0
    ,"courseid"           BIGINT                      NOT NULL    REFERENCES course.course (id) DEFAULT 1
);

ALTER TABLE "course"."coursedata" ADD CONSTRAINT "coursedata_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_coursedata_id_unique
    ON "course"."coursedata" (id);
