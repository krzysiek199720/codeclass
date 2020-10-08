DROP TABLE IF EXISTS "course"."follow";

DROP SEQUENCE IF EXISTS "course"."follow_seq_id";

CREATE SEQUENCE "course"."follow_seq_id";

CREATE TABLE "course"."follow"
(
    "id"                BIGINT  NOT NULL   DEFAULT nextval('course.follow_seq_id')
    ,"coursegroupid"    BIGINT  NOT NULL   REFERENCES course.coursegroup (id) DEFAULT 1
    ,"userid"           BIGINT  NOT NULL   REFERENCES auth.user (id) DEFAULT 1
);

ALTER TABLE "course"."follow" ADD CONSTRAINT "follow_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_follow_id_unique
    ON "course"."follow" (id);
