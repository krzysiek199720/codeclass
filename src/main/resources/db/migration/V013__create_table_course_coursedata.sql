DROP TABLE IF EXISTS "course"."coursedata";

DROP SEQUENCE IF EXISTS "course"."coursedata_seq_id";

CREATE SEQUENCE "course"."coursedata_seq_id";

CREATE TABLE "course"."coursedata"
(
    "id"                  BIGINT                      NOT NULL    DEFAULT nextval('course.coursedata_seq_id')
    ,"createdat"          TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"deletedat"          TIMESTAMP WITH TIME ZONE
    ,"type"               SMALLINT                    NOT NULL
    ,"order"              INTEGER                     NOT NULL DEFAULT 0
    ,"sourcepath"         VARCHAR(120)                NOT NULL 
    ,"courseid"           BIGINT                      NOT NULL    REFERENCES course.course (id) DEFAULT 1
);

ALTER TABLE "course"."coursedata" ADD CONSTRAINT "coursedata_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_coursedata_id_unique
    ON "course"."coursedata" (id);