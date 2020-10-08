DROP TABLE IF EXISTS "notification"."notification";

DROP SEQUENCE IF EXISTS "notification"."notification_seq_id";

CREATE SEQUENCE "notification"."notification_seq_id";

CREATE TABLE "notification"."notification"
(
    "id"          BIGINT        NOT NULL   DEFAULT nextval('notification.notification_seq_id')
    ,"isread"     BOOLEAN       NOT NULL   DEFAULT FALSE
    ,"text"       VARCHAR(30)   NOT NULL
    ,"slug"       VARCHAR(255)  NOT NULL
    ,"userid"     BIGINT        NOT NULL   REFERENCES auth.user (id) DEFAULT 1
);

ALTER TABLE "notification"."notification" ADD CONSTRAINT "notification_id_pk" PRIMARY KEY (id);

CREATE UNIQUE INDEX notification_notification_id_unique
    ON "notification"."notification" (id);
