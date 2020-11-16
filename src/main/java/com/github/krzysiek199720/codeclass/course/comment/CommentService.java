package com.github.krzysiek199720.codeclass.course.comment;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.course.comment.DAO.CommentDAO;
import com.github.krzysiek199720.codeclass.course.comment.DAO.CommentScriptMentionDAO;
import com.github.krzysiek199720.codeclass.course.comment.response.CommentResponse;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseData;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseDataDAO;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupDAO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private final CommentDAO commentDAO;
    private final CommentScriptMentionDAO commentScriptMentionDAO;
    private final CourseDAO courseDAO;
    private final CourseGroupDAO courseGroupDAO;
    private final CourseDataDAO courseDataDAO;

    @Autowired
    public CommentService(CommentDAO commentDAO, CommentScriptMentionDAO commentScriptMentionDAO, CourseDAO courseDAO, CourseGroupDAO courseGroupDAO, CourseDataDAO courseDataDAO) {
        this.commentDAO = commentDAO;
        this.commentScriptMentionDAO = commentScriptMentionDAO;
        this.courseDAO = courseDAO;
        this.courseGroupDAO = courseGroupDAO;
        this.courseDataDAO = courseDataDAO;
    }

    @Transactional
    public List<CommentResponse> getAllComments(Long courseId, User user) {
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());
        if(!author.equals(user))
            if(course.getIsPublished() == null)
                throw new UnauthorizedException("course.comment.unauthorized");


        return commentDAO.getCommentResultByCourseId(courseId);
    }

    @Transactional
    public CommentResponse saveComment(Long courseId, CommentSaveApi api, User user) {
        Course course = courseDAO.getById(courseId);
        if(course == null)
            throw new NotFoundException("course.notfound");

        User author = courseGroupDAO.getUserByCourseId(course.getId());
        if(!author.equals(user))
            if(course.getIsPublished() == null)
                throw new UnauthorizedException("course.comment.unauthorized");

        Comment root = null;
        if(api.getRoot() != null){
            root = commentDAO.getById(api.getRoot());
            if(root == null)
                throw new UnauthorizedException("course.comment.notfound");

//            sprawdzanie poziomu zagniezdzenia (max 1)
            if(root.getRoot() != null){
                Comment newRoot = root.getRoot();
                Hibernate.initialize(newRoot);
                root = newRoot;
            }
        }



        Comment comment = new Comment();
        comment.setId(null);
        comment.setData(api.getData());
        comment.setRoot(root);
        comment.setCourse(course);
        comment.setUser(user);

        comment.setMention(null);
        commentDAO.save(comment);

        CourseData cd = null;
        List<String> lines = null;

        if(api.getScriptId() != null){
            cd = courseDataDAO.getById(api.getScriptId());
            if(cd == null)
                throw new NotFoundException("course.coursedata.notfound");
            CommentScriptMention csm = new CommentScriptMention();
            csm.setId(null);
            csm.setLinesFrom(api.getLinesFrom());
            csm.setLinesTo(api.getLinesTo());
            csm.setComment(comment);
            csm.setCourseData(cd);

            commentScriptMentionDAO.save(csm);
            comment.setMention(csm);
            commentDAO.save(comment);

            lines = courseDataDAO.getLines(cd.getId(), csm.getLinesFrom(), csm.getLinesTo());
        }

        return new CommentResponse(
                comment.getId(),
                comment.getData(),
                comment.getRoot() == null ? null : comment.getRoot().getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getId(),
                cd == null ? null : cd.getId(),
                lines == null ? null : api.getLinesFrom(),
                lines == null ? null : api.getLinesTo(),
                lines
        );
    }
}
