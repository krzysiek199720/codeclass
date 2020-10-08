DROP TABLE IF EXISTS "course"."comment";

DROP SEQUENCE IF EXISTS "course"."comment_seq_id";

CREATE SEQUENCE "course"."comment_seq_id";

CREATE TABLE "course"."comment"
(
    "id"          BIGINT        NOT NULL   PRIMARY KEY DEFAULT nextval('course.comment_seq_id')
    ,"data"       VARCHAR(255)  NOT NULL
    ,"root"       BIGINT        REFERENCES course.comment (id) DEFAULT NULL
    ,"courseid"   BIGINT        NOT NULL   REFERENCES course.course (id) DEFAULT 1
    ,"userid"     BIGINT        NOT NULL   REFERENCES auth.user (id) DEFAULT 1
);

CREATE UNIQUE INDEX course_comment_id_unique
    ON "course"."comment" (id);
