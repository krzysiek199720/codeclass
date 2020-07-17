INSERT INTO auth.role ("name") VALUES ('superuser') ON CONFLICT DO NOTHING;


INSERT INTO auth.permission2role (roleid, permissionid)
    SELECT ro.id, pe.id FROM
        auth.permission pe INNER JOIN auth.role ro ON 1=1
    WHERE ro.name LIKE 'superuser';