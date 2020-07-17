INSERT INTO auth.user ("email", "firstname", "lastname", "password", "roleid")
VALUES (
        'superuser@superuser.superuser'
        ,'superuser'
        ,'superuser'
        , '$2a$10$F4S3nhkxKnyL.DICBrEWj.67dEw3ZuXFldhZ.ONvA65CllfwsMh7C' --> 'superuser'
        , (SELECT "id" FROM "auth"."role" WHERE "name" LIKE 'superuser')
        ) ON CONFLICT DO NOTHING;


