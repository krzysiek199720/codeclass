package com.github.krzysiek199720.codeclass.course.comment.DAO;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.comment.CommentScriptMention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
public class CommentScriptMentionDAOImpl extends GenericDAO<CommentScriptMention> implements CommentScriptMentionDAO {


    //----
    @Autowired
    public CommentScriptMentionDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public CommentScriptMention getById(Long id) {
        return getCurrentSession().get(CommentScriptMention.class, id);
    }

    @Override
    public CommentScriptMention save(CommentScriptMention object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(CommentScriptMention object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
