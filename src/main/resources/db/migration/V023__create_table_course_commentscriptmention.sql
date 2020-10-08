DROP TABLE IF EXISTS "course"."commentscriptmention";

DROP SEQUENCE IF EXISTS "course"."commentscriptmention_seq_id";

CREATE SEQUENCE "course"."commentscriptmention_seq_id";

CREATE TABLE "course"."commentscriptmention"
(
    "id"            BIGINT        NOT NULL   DEFAULT nextval('course.commentscriptmention_seq_id')
    ,"linesFrom"    INTEGER       NOT NULL
    ,"linesTo"      INTEGER       NOT NULL
    ,"coursedataid" BIGINT        NOT NULL   REFERENCES course.coursedata (id) DEFAULT 1
    ,"commentid"    BIGINT        NOT NULL   REFERENCES course.comment (id) DEFAULT 1
);

ALTER TABLE "course"."commentscriptmention" ADD CONSTRAINT "commentscriptmention_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_commentscriptmention_id_unique
    ON "course"."commentscriptmention" (id);
