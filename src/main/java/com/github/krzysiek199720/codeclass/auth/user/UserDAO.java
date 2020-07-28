package com.github.krzysiek199720.codeclass.auth.user;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public interface UserDAO extends DAO<User> {

    public User findByEmail(String email);

    public boolean isEmailTaken(String email);

}
