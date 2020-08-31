package com.github.krzysiek199720.codeclass.auth.user;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends DAO<User> {

    public User findByEmail(String email);

    public boolean isEmailTaken(String email);

    public List<User> getAll();

}
