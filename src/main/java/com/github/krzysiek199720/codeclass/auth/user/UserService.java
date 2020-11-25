package com.github.krzysiek199720.codeclass.auth.user;

import com.github.krzysiek199720.codeclass.auth.user.api.ChangeEmailApi;
import com.github.krzysiek199720.codeclass.auth.user.api.ChangePasswordApi;
import com.github.krzysiek199720.codeclass.auth.user.api.SignUpApi;

import java.util.List;

public interface UserService {

    public List<User> getAll(String searchQuery, Long roleId);

    public User getById(Long id);

    public User signUp(SignUpApi api);

    public User changeEmail(Long id, ChangeEmailApi api);

    public void changePassword(Long id, ChangePasswordApi api);

    public User changeRole(Long userId, Long roleId);

    public void delete(Long id);
}
