package com.github.krzysiek199720.codeclass.course.comment.DAO;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.comment.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
public class CommentDAOImpl extends GenericDAO<Comment> implements CommentDAO {


    //----
    @Autowired
    public CommentDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Comment getById(Long id) {
        return getCurrentSession().get(Comment.class, id);
    }

    @Override
    public Comment save(Comment object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Comment object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
