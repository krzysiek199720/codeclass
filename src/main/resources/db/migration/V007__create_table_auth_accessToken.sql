DROP TABLE IF EXISTS "auth"."accesstoken";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "auth"."accesstoken"
(
    "token"           UUID                        NOT NULL    DEFAULT uuid_generate_v4()
    ,"userid"         BIGINT                      NOT NULL    REFERENCES auth.user (id)
    ,"createdat"      TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"lastaccess"     TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"expires"        TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT (now() + INTERVAL '3 days')

    ,UNIQUE(token)
);

ALTER TABLE "auth"."accesstoken" ADD CONSTRAINT "accessToken_id_pk" PRIMARY KEY (token);

CREATE UNIQUE INDEX auth_accessToken_id_unique
  ON "auth"."accesstoken" (token);
