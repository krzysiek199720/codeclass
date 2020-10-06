DROP TABLE IF EXISTS "course"."quizscore";

DROP SEQUENCE IF EXISTS "course"."quizscore_seq_id";

CREATE SEQUENCE "course"."quizscore_seq_id";

CREATE TABLE "course"."quizscore"
(
    "id"          BIGINT        NOT NULL   DEFAULT nextval('course.quizscore_seq_id')
    ,"score"      INTEGER       NOT NULL   DEFAULT 0
    ,"quizid"     BIGINT        NOT NULL   REFERENCES course.quiz (id) DEFAULT 1
    ,"userid"     BIGINT        NOT NULL   REFERENCES auth.user (id) DEFAULT 1
);

ALTER TABLE "course"."quizscore" ADD CONSTRAINT "quizscore_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_quizscore_id_unique
    ON "course"."quizscore" (id);
