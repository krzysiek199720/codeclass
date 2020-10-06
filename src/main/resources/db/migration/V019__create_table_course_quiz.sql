DROP TABLE IF EXISTS "course"."quiz";

DROP SEQUENCE IF EXISTS "course"."quiz_seq_id";

CREATE SEQUENCE "course"."quiz_seq_id";

CREATE TABLE "course"."quiz"
(
    "id"            BIGINT          NOT NULL    DEFAULT nextval('course.quiz_seq_id')
    ,"maxscore"     INTEGER         NOT NULL    DEFAULT 0
    ,"courseid"     BIGINT          NOT NULL    REFERENCES course.course (id) DEFAULT 1
);

ALTER TABLE "course"."quiz" ADD CONSTRAINT "quiz_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_quiz_id_unique
    ON "course"."quiz" (id);
