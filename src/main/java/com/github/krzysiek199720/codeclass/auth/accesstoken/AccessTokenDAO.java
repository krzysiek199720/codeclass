package com.github.krzysiek199720.codeclass.auth.accesstoken;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface AccessTokenDAO extends DAO<AccessToken> {

    public AccessToken findByToken(String token);

    public void deleteByUser(User user);

    public List<AccessToken> getAll();

}
