package com.github.krzysiek199720.codeclass.auth.accesstoken;


import com.github.krzysiek199720.codeclass.auth.accesstoken.api.LogInApi;
import com.github.krzysiek199720.codeclass.auth.accesstoken.response.LogInResponse;
import com.github.krzysiek199720.codeclass.auth.user.User;


public interface AccessTokenService {

    public AccessToken getAccesstokenByToken(String token);

    public boolean isSessionActive(String token);

    public boolean isSessionActive(AccessToken accessToken);

    public void deleteSessionByUser(User user);

    public LogInResponse logIn(LogInApi api);

    public void logOut(String token);

    public void logOutAll(String token);
}
