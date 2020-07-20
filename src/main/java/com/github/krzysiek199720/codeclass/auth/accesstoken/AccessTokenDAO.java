package com.github.krzysiek199720.codeclass.auth.accesstoken;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;

public interface AccessTokenDAO extends DAO<AccessToken> {

    public AccessToken findByToken(String token);

    public void deleteByUser(User user);

}
