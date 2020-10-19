package com.github.krzysiek199720.codeclass.notification;

import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface NotificationDAO extends DAO<Notification> {
    List<Notification> getAllNotifications(Long userId);
    Notification fetchById(Long userId);

    void createNotification(Long courseId, String text, String slug);
}
