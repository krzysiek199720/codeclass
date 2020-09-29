DROP TABLE IF EXISTS "course"."assimilation";

DROP SEQUENCE IF EXISTS "course"."assimilation_seq_id";

CREATE SEQUENCE "course"."assimilation_seq_id";

CREATE TABLE "course"."assimilation"
(
    "id"            BIGINT          NOT NULL    DEFAULT nextval('course.assimilation_seq_id')
    ,"value"        VARCHAR(20)     NOT NULL    DEFAULT 'NO'
    ,"courseid"     BIGINT          NOT NULL    REFERENCES course.course (id) DEFAULT 1
    ,"userid"       BIGINT          NOT NULL    REFERENCES auth.user (id) DEFAULT 1
);

ALTER TABLE "course"."assimilation" ADD CONSTRAINT "assimilation_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_assimilation_id_unique
    ON "course"."assimilation" (id);
