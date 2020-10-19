package com.github.krzysiek199720.codeclass.course.comment;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.course.comment.DAO.CommentDAO;
import com.github.krzysiek199720.codeclass.course.comment.DAO.CommentScriptMentionDAO;
import com.github.krzysiek199720.codeclass.course.comment.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentDAO commentDAO;
    private final CommentScriptMentionDAO commentScriptMentionDAO;

    @Autowired
    public CommentService(CommentDAO commentDAO, CommentScriptMentionDAO commentScriptMentionDAO) {
        this.commentDAO = commentDAO;
        this.commentScriptMentionDAO = commentScriptMentionDAO;
    }

    public CommentResponse getAllComments(Long courseId, User user) {


        return null;
    }
}
