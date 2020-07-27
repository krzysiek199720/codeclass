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

--

INSERT INTO auth.role ("id", "name") VALUES (1, 'superuser') ON CONFLICT DO NOTHING;
INSERT INTO auth.role ("id", "name") VALUES (2, 'user') ON CONFLICT DO NOTHING;

CREATE OR REPLACE FUNCTION defaultRole() RETURNS BIGINT LANGUAGE SQL AS
$$ SELECT id FROM auth.role WHERE name like 'user' AND id = 2; $$;

