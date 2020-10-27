package com.github.krzysiek199720.codeclass.course.comment.DAO;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.comment.Comment;
import com.github.krzysiek199720.codeclass.course.comment.response.CommentResponse;

import java.util.List;

public interface CommentDAO extends DAO<Comment> {

    List<CommentResponse> getCommentResultByCourseId(Long courseId);
}
