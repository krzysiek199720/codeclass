package com.github.krzysiek199720.codeclass.course.link;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.language.Language;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class LinkDAOImpl extends GenericDAO<Link> implements LinkDAO {

    @Override
    public List<Link> getAllByCourse(Long courseId){

        return getCurrentSession().createQuery("select l from Link l inner join l.course c where c.id = :courseid", Link.class)
                .setParameter("courseid", courseId).getResultList();
    }

    @Override
    public User getUserByLinkId(Long id) {
        Query<User> query= getCurrentSession()
                .createQuery("select cg.user from Link l inner join l.course c inner join c.courseGroup cg where l.id = :linkid", User.class)
                .setParameter("linkid", id);

        User res;
        try{
            res = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return res;
    }

    //----
    @Autowired
    public LinkDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Link getById(Long id) {
        return getCurrentSession().get(Link.class, id);
    }

    @Override
    public Link save(Link object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Link object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
