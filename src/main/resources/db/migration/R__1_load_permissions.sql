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

         ,('course', 'save_course', 'course.save')
         ,('course', 'publish_course', 'course.publish')
         ,('course', 'delete_course', 'course.delete')

         ,('course', 'save_course_data', 'course.data.save')

         ,('course', 'save_course_group', 'course.group.save')
         ,('course', 'delete_course_group', 'course.group.delete')

         ,('language', 'save_language', 'course.language.create')
         ,('language', 'save_language', 'course.language.update')
         ,('language', 'delete_language', 'course.language.delete')
;

-- create superrole
INSERT INTO auth.permission2role (roleid, permissionid)
SELECT ro.id, pe.id FROM
    auth.permission pe INNER JOIN auth.role ro ON 1=1
WHERE ro.name LIKE 'superuser';