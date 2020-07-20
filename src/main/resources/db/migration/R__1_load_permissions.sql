TRUNCATE "auth"."permission" CASCADE;

ALTER SEQUENCE "auth"."permission_seq_id" RESTART;
ALTER SEQUENCE "auth"."permission2role_seq_id" RESTART;

INSERT INTO "auth"."permission" ("group", "name", "value")
    VALUES
--         ('group', 'name', 'value')
        ('permission', 'get_permission', 'permission.get')

         ,('role', 'get_role', 'role.get')
         ,('role', 'create_role', 'role.create')
         ,('role', 'update_role', 'role.update')
         ,('role', 'delete_role', 'role.delete')

         ,('user', 'get_user', 'user.get')
         ,('user', 'get_user', 'user.passwd')
         ,('user', 'get_user', 'user.email')
         ,('user', 'delete_user', 'user.delete')
         ,('user', 'update_role_user', 'user.role.update')
        ;