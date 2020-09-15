DROP TABLE IF EXISTS "course"."course";

DROP SEQUENCE IF EXISTS "course"."course_seq_id";

CREATE SEQUENCE "course"."course_seq_id";

CREATE TABLE "course"."course"
(
    "id"                BIGINT                      NOT NULL    DEFAULT nextval('course.course_seq_id')
    ,"title"            VARCHAR(50)                 NOT NULL
    ,"complexity"       VARCHAR(20)                 NOT NULL
    ,"grouporder"       INTEGER                     NOT NULL    DEFAULT 0
    ,"ispublished"      TIMESTAMP WITH TIME ZONE                DEFAULT NULL
    ,"sourcepath"       VARCHAR(120)                NOT NULL

    ,"createdat"        TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"modifiedat"       TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"deletedat"        TIMESTAMP WITH TIME ZONE

    ,"coursegroupid"    BIGINT                      NOT NULL    REFERENCES course.coursegroup (id) DEFAULT 1
    ,"languageid"       BIGINT                      NOT NULL    REFERENCES course.language (id) DEFAULT 1
    ,"categoryid"       BIGINT                      NOT NULL    REFERENCES course.category (id) DEFAULT 1
);

ALTER TABLE "course"."course" ADD CONSTRAINT "course_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX course_course_id_unique
    ON "course"."course" (id);
