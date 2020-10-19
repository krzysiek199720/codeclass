CREATE OR REPLACE FUNCTION create_follow_notification(
    course_group_id BIGINT,
    text_val VARCHAR(30),
    slug_val VARCHAR(255)
)
    RETURNS VOID
    LANGUAGE plpgsql
AS $$
DECLARE user_id BIGINT;
BEGIN
    FOR user_id IN
        SELECT userid AS user_id FROM course.follow WHERE coursegroupid = course_group_id
        LOOP
            INSERT INTO notification.notification (text, slug, userid) VALUES (text_val, slug_val, user_id);
        END LOOP;
END;$$