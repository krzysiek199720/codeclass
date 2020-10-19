DROP TABLE IF EXISTS "course"."coursedataelement";

DROP SEQUENCE IF EXISTS "course"."coursedataelement_seq_id";

CREATE SEQUENCE "course"."coursedataelement_seq_id";

CREATE TABLE "course"."coursedataelement"
(
    "id"                    BIGINT      NOT NULL    DEFAULT nextval('course.coursedataelement_seq_id')
    ,"theorder"                INTEGER     NOT NULL    DEFAULT 0
    ,"depth"                INTEGER     NOT NULL    DEFAULT 0
    ,"data"                 TEXT                    DEFAULT NULL
    ,"description"          TEXT                    DEFAULT NULL
    ,"coursedatalineid"     BIGINT      NOT NULL    REFERENCES course.coursedataline (id) DEFAULT 1
);

ALTER TABLE "course"."coursedataelement" ADD CONSTRAINT "coursedataelement_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_coursedataelement_id_unique
    ON "course"."coursedataelement" (id);
