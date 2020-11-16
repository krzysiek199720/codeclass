CREATE OR REPLACE FUNCTION get_comments(
    courseIdI BIGINT
)
RETURNS TABLE(
    "id"            BIGINT,
    "data"          VARCHAR(255),
    "rootId"        BIGINT,
    "userFirstName" VARCHAR(30),
    "userLastName"  VARCHAR(40),
    "userId"        BIGINT,
    "courseDataId"  BIGINT,
    "linesFrom"     INT,
    "linesTo"       INT,
    "lines"         TEXT[]
)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        WITH scrpts AS (
            SELECT csm.commentid, csm.coursedataid, csm.linesfrom, csm.linesto, array(select string_agg(cde.data, '')
                        from course.coursedata cd
                            inner join course.coursedataline cdl on cdl.coursedataid = cd.id
                            inner join course.coursedataelement cde on cde.coursedatalineid = cdl.id
                        where cd.id = csm.coursedataid and cdl.theorder between csm.linesfrom and csm.linesto
                        group by cdl.id) as lns
            FROM course.commentscriptmention csm
        )
        SELECT c.id, c.data, c.root, u.firstname, u.lastname, u.id, sc.coursedataid, sc.linesfrom, sc.linesto, sc.lns
        FROM course.comment c
                 LEFT JOIN scrpts sc ON c.id = sc.commentid
                 INNER JOIN auth."user" u ON c.userid = u.id
        WHERE c.courseid = courseIdI

    ;

END;$$
