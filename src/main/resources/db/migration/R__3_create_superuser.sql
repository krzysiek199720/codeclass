INSERT INTO auth.user ("email", "firstname", "lastname", "password", "roleid")
VALUES (
        'superuser@superuser.superuser'
        ,'superuser'
        ,'superuser'
        , '$2a$10$8i0btZTdgL7/1FNx7fm2FemZfWfso.h.wjfm9UohlyoNSs1XvOFfS' --> 'superuser'
        , (SELECT "id" FROM "auth"."role" WHERE "name" LIKE 'superuser')
        ) ON CONFLICT DO NOTHING;


