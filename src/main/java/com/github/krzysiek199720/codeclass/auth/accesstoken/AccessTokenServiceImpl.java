package com.github.krzysiek199720.codeclass.auth.accesstoken;

import com.github.krzysiek199720.codeclass.auth.accesstoken.api.LogInApi;
import com.github.krzysiek199720.codeclass.auth.accesstoken.response.LogInResponse;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.auth.user.UserDAO;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AccessTokenServiceImpl implements AccessTokenService{
    private final AccessTokenDAO accessTokenDAO;

    private final UserDAO userDAO;

    @Autowired
    public AccessTokenServiceImpl(AccessTokenDAO accessTokenDAO, UserDAO userDAO) {
        this.accessTokenDAO = accessTokenDAO;
        this.userDAO = userDAO;
    }

    public AccessToken getAccesstokenByToken(String token){
        AccessToken at = accessTokenDAO.findByToken(token);
        if(at == null)
            throw new NotFoundException("auth.token.notfound");
        return at;
    }

    public boolean isSessionActive(String token){
        AccessToken accessToken = accessTokenDAO.findByToken(token);

        if(accessToken == null)
            throw new NotFoundException("auth.token.notfound");

        return isSessionActive(accessToken);
    }

    public boolean isSessionActive(AccessToken accessToken){
        if(accessToken != null)
            return ! accessToken.getExpires().isBefore(LocalDateTime.now());

        return false;
    }

    public void deleteSessionByUser(User user){
        accessTokenDAO.deleteByUser(user);
    }

    @Transactional
    public LogInResponse logIn(LogInApi api){
        User user = userDAO.findByEmail(api.getEmail());
        if(user == null)
            throw new UnauthorizedException("auth.bad_credentials");

        if ( ! BCrypt.checkpw(api.getPassword(), user.getPassword()))
            throw new UnauthorizedException("auth.bad_credentials");

        AccessToken accessToken = new AccessToken();
        accessToken.setUser(user);
        accessToken.setCreatedAt(LocalDateTime.now());
        accessToken.setLastAccess(accessToken.getCreatedAt());
        accessToken.setExpires(LocalDateTime.now().plusDays(2));

        accessToken = accessTokenDAO.save(accessToken);

        return new LogInResponse(
                accessToken.getToken().toString(),
                accessToken.getExpires(),
                user.getId(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole().getId(),
                user.getRole().getName(),
                user.getRole().getPermissions()
        );
    }

    @Transactional
    public void logOut(String token){
        AccessToken accessToken = accessTokenDAO.findByToken(token);

        if(accessToken == null)
            throw new NotFoundException("auth.token.notfound");

        accessTokenDAO.delete(accessToken);
    }

    @Transactional
    public void logOutAll(String token){
        AccessToken accessToken = accessTokenDAO.findByToken(token);

        if(accessToken == null)
            throw new NotFoundException("auth.token.notfound");

        deleteSessionByUser(accessToken.getUser());
    }
}
