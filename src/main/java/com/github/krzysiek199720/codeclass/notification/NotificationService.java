package com.github.krzysiek199720.codeclass.notification;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationDAO notificationDAO;

    @Autowired
    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }


    @Transactional
    public List<Notification> getAll(User user) {
        return notificationDAO.getAllNotifications(user.getId());
    }

    @Transactional
    public void markRead(Long id, User user) {
        Notification notification = notificationDAO.fetchById(id);
        if(notification == null)
            throw new NotFoundException("notification.notfound");

        if(!user.equals(notification.getUser()))
            throw new NotFoundException("notification.unauthorized");

        notification.setIsread(true);
        notificationDAO.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id, User user) {
        Notification notification = notificationDAO.fetchById(id);
        if(notification == null)
            throw new NotFoundException("notification.notfound");

        if(!user.equals(notification.getUser()))
            throw new NotFoundException("notification.unauthorized");

        notificationDAO.delete(notification);
    }

}
