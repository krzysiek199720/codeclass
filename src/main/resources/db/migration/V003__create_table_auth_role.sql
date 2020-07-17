DROP TABLE IF EXISTS "auth"."role";

DROP SEQUENCE IF EXISTS "auth"."role_seq_id";

CREATE SEQUENCE "auth"."role_seq_id";

CREATE TABLE "auth"."role"
(
    "id"          BIGINT                     NOT NULL    DEFAULT nextval('auth.role_seq_id')
    ,"name"       VARCHAR(50)                NOT NULL
    ,"createdat"  TIMESTAMP WITH TIME ZONE   NOT NULL    DEFAULT now()
    ,"modifiedat" TIMESTAMP WITH TIME ZONE   NOT NULL    DEFAULT now()
    ,"deletedat"  TIMESTAMP WITH TIME ZONE

    ,UNIQUE(name)
);

ALTER TABLE "auth"."role" ADD CONSTRAINT "role_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX auth_role_iq_unique
  ON "auth"."role" (id);

