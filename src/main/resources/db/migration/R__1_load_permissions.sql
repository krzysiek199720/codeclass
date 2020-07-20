TRUNCATE "auth"."permission" CASCADE;

ALTER SEQUENCE "auth"."permission_seq_id" RESTART;
ALTER SEQUENCE "auth"."permission2role_seq_id" RESTART;

INSERT INTO "auth"."permission" ("group", "name", "value")
    VALUES
--         ('group', 'name', 'value')
        ('permission', 'get_permission', 'permission.get')
        ;