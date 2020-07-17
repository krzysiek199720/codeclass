DROP TABLE IF EXISTS "auth"."permission";

DROP SEQUENCE IF EXISTS "auth"."permission_seq_id";

CREATE SEQUENCE "auth"."permission_seq_id";

CREATE TABLE "auth"."permission"
(
  "id"            BIGINT       NOT NULL DEFAULT nextval('auth.permission_seq_id')
  ,"group"        VARCHAR(100) NOT NULL
  ,"name"         VARCHAR(100) NOT NULL
  ,"value"        VARCHAR(50) NOT NULL
);

ALTER TABLE "auth"."permission" ADD CONSTRAINT "permission_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX auth_permission_id_unique
  ON "auth"."permission" (id);
