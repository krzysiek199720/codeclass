DROP TABLE IF EXISTS "auth"."user";

DROP SEQUENCE IF EXISTS "auth"."user_seq_id";

CREATE SEQUENCE "auth"."user_seq_id";

CREATE TABLE "auth"."user"
(
    "id"                  BIGINT                      NOT NULL    DEFAULT nextval('auth.user_seq_id')
    ,"email"              VARCHAR(100)                NOT NULL
    ,"firstname"          VARCHAR(30)                 NOT NULL
    ,"lastname"           VARCHAR(40)                 NOT NULL
    ,"password"           TEXT                        NOT NULL
    ,"roleid"             BIGINT                      NOT NULL    REFERENCES auth.role (id) DEFAULT 1
    ,"createdat"          TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"modifiedat"         TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"deletedat"          TIMESTAMP WITH TIME ZONE

    ,UNIQUE(email)
);

ALTER TABLE "auth"."user" ADD CONSTRAINT "user_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX auth_user_id_unique
    ON "auth"."user" (id);


-- create superuser
INSERT INTO auth.user ("email", "firstname", "lastname", "password", "roleid")
VALUES (
        'superuser@superuser.superuser'
       ,'superuser'
       ,'superuser'
       , '$2a$10$8i0btZTdgL7/1FNx7fm2FemZfWfso.h.wjfm9UohlyoNSs1XvOFfS' --> 'superuser'
       , 1 -- superuser role -
       ) ON CONFLICT DO NOTHING;