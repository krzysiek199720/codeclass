package com.github.krzysiek199720.codeclass.notification;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class NotificationDAOImpl extends GenericDAO<Notification> implements NotificationDAO {

    @Override
    public List<Notification> getAllNotifications(Long userId) {

        return getCurrentSession()
                .createQuery("select n from Notification n where n.user.id = :userId order by n.isread, n.id", Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Notification fetchById(Long id) {
        Query<Notification> query = getCurrentSession()
                .createQuery("select n from Notification n inner join fetch n.user u where n.id = :id", Notification.class)
                .setParameter("id", id);

        Notification notification;
        try{
            notification = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return notification;
    }

    @Override
    public void createNotification(Long courseId, String text, String slug) {
        NativeQuery query = getCurrentSession()
                .createNativeQuery("select count(*) from create_follow_notification(:course_group_id, :text_val, :slug_val)")
                .setParameter("course_group_id",courseId)
                .setParameter("text_val", text)
                .setParameter("slug_val", slug);
        query.getResultList();
    }

    //----
    @Autowired
    public NotificationDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Notification getById(Long id) {
        return getCurrentSession().get(Notification.class, id);
    }

    @Override
    public Notification save(Notification object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Notification object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
