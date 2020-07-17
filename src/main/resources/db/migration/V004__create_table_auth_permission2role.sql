DROP TABLE IF EXISTS "auth"."permission2role";

DROP SEQUENCE IF EXISTS "auth"."permission2role_seq_id";

CREATE SEQUENCE "auth"."permission2role_seq_id";

CREATE TABLE "auth"."permission2role" (
    "id"              BIGINT                      NOT NULL    DEFAULT nextval('auth.permission2role_seq_id')
    ,"permissionid"   BIGINT                      NOT NULL    REFERENCES auth.permission (id)
    ,"roleid"         BIGINT                      NOT NULL    REFERENCES auth.role (id)
    ,"createdat"      TIMESTAMP WITH TIME ZONE    NOT NULL    DEFAULT now()
    ,"deletedat"      TIMESTAMP WITH TIME ZONE
);

ALTER TABLE "auth"."permission2role" ADD CONSTRAINT "permission2role_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX auth_permission2role_iq_unique
  ON "auth"."permission2role" (id);


