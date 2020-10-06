DROP TABLE IF EXISTS "course"."quizquestion";

DROP SEQUENCE IF EXISTS "course"."quizquestion_seq_id";

CREATE SEQUENCE "course"."quizquestion_seq_id";

CREATE TABLE "course"."quizquestion"
(
    "id"          BIGINT          NOT NULL   DEFAULT nextval('course.quizquestion_seq_id')
    ,"question"   VARCHAR(100)    NOT NULL
    ,"answer0"    VARCHAR(100)    NOT NULL
    ,"answer1"    VARCHAR(100)    NOT NULL
    ,"answer2"    VARCHAR(100)    NOT NULL
    ,"answer3"    VARCHAR(100)    NOT NULL
    ,"answer"     INTEGER         NOT NULL   CONSTRAINT answ_min CHECK ( answer > 0 ) CONSTRAINT answ_max CHECK ( answer <= 4 )
    ,"quizid"     BIGINT          NOT NULL   REFERENCES course.quiz (id) DEFAULT 1
);

ALTER TABLE "course"."quizquestion" ADD CONSTRAINT "quizquestion_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_quizquestion_id_unique
    ON "course"."quizquestion" (id);
