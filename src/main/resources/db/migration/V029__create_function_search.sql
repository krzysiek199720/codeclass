CREATE OR REPLACE FUNCTION fn_search(
    searchQueryI    TEXT,
    complexitiesI   VARCHAR(20)[],
    userIdI         BIGINT
)
RETURNS TABLE(
    "courseGroupId"     BIGINT,
    "courseGroupName"   VARCHAR(50),
    "courseCount"       BIGINT,
    "lastAddedDate"     TIMESTAMP WITH TIME ZONE,
    "complexities"      VARCHAR(20)[],
    "languageId"        BIGINT,
    "languageName"      VARCHAR(50),
    "categoryId"        BIGINT,
    "categoryName"      VARCHAR(50),
    "commentCount"      BIGINT,
    "authorFirstName"   VARCHAR(30),
    "authorLastName"    VARCHAR(40),
    "authorId"          BIGINT
)
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        WITH cou AS (
            SELECT c.coursegroupid, COUNT(c.id) as coucount, MAX(c.createdat) as latest, array_agg(c.complexity) as compl
            FROM course.course c
            GROUP BY c.coursegroupid
        ),
         comm AS (
             SELECT cg.id, COUNT(c.id) as comcount
             FROM course.comment c
             RIGHT JOIN course.course c2 on c2.id = c.courseid
             INNER JOIN course.coursegroup cg on cg.id = c2.coursegroupid
             GROUP BY cg.id
         )
    SELECT DISTINCT cg.id, cg.name, cou.coucount, cou.latest, cou.compl, l.id, l.name, cat.id, cat.name, comm.comcount, u.firstname, u.lastname, u.id
    FROM course.coursegroup cg
    LEFT JOIN course.course c on cg.id = c.coursegroupid
    INNER JOIN cou ON cg.id = cou.coursegroupid
    INNER JOIN course.language l on l.id = c.languageid
    INNER JOIN course.category cat on cat.id = c.categoryid
    INNER JOIN comm ON cg.id = comm.id
    INNER JOIN auth."user" u ON cg.userid = u.id

    WHERE
        (
             cg.name SIMILAR TO CONCAT('%', searchQueryI, '%') OR
             l.name SIMILAR TO CONCAT('%', searchQueryI, '%') OR
             cat.name SIMILAR TO CONCAT('%', searchQueryI, '%') OR
             u.firstname SIMILAR TO CONCAT('%', searchQueryI, '%') OR
             u.lastname SIMILAR TO CONCAT('%', searchQueryI, '%')
        )
        AND
        (
            c.complexity = ANY (complexitiesI)
        )
        AND
        (
            cg.userid = userIdI
            OR
            true = CASE WHEN c.ispublished IS NOT NULL THEN true ELSE false END
        )



    ;

END;$$
