package com.github.krzysiek199720.codeclass.course.comment.DAO;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.comment.Comment;
import com.github.krzysiek199720.codeclass.course.comment.response.CommentResponse;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class CommentDAOImpl extends GenericDAO<Comment> implements CommentDAO {

    @Override
    public List<CommentResponse> getCommentResultByCourseId(Long courseId) {

        return (List<CommentResponse>) getCurrentSession()
                .createSQLQuery("SELECT * FROM get_comments(:courseId)")
                .setParameter("courseId", courseId)
                .addScalar("id", LongType.INSTANCE)
                .addScalar("data", StringType.INSTANCE)
                .addScalar("rootId", LongType.INSTANCE)
                .addScalar("userFirstName", StringType.INSTANCE)
                .addScalar("userLastName", StringType.INSTANCE)
                .addScalar("userId", LongType.INSTANCE)
                .addScalar("courseDataId", LongType.INSTANCE)
                .addScalar("linesFrom", IntegerType.INSTANCE)
                .addScalar("linesTo", IntegerType.INSTANCE)
                .addScalar("lines", ListArrayType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(CommentResponse.class))
                .getResultList();
    }

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
