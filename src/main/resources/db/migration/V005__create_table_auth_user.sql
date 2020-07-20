DROP TABLE IF EXISTS "auth"."user";

DROP SEQUENCE IF EXISTS "auth"."user_seq_id";

CREATE SEQUENCE "auth"."user_seq_id";

CREATE TABLE "auth"."user"
(
    "id"                  BIGINT                      NOT NULL    DEFAULT nextval('auth.user_seq_id')
    ,"email"              VARCHAR(100)                NOT NULL
    ,"firstname"           VARCHAR(30)                 NOT NULL
    ,"lastname"           VARCHAR(40)                 NOT NULL
    ,"password"           TEXT                        NOT NULL
    ,"roleid"             BIGINT                      NOT NULL    REFERENCES auth.role (id) DEFAULT defaultRole()
    ,"createdat"          TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"modifiedat"         TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"deletedat"          TIMESTAMP WITH TIME ZONE

    ,UNIQUE(email)
);

ALTER TABLE "auth"."user" ADD CONSTRAINT "user_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX auth_user_id_unique
    ON "auth"."user" (id);
