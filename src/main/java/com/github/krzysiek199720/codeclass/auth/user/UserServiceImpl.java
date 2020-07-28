package com.github.krzysiek199720.codeclass.auth.user;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.role.Role;
import com.github.krzysiek199720.codeclass.auth.role.RoleDAO;
import com.github.krzysiek199720.codeclass.auth.user.api.ChangeEmailApi;
import com.github.krzysiek199720.codeclass.auth.user.api.ChangePasswordApi;
import com.github.krzysiek199720.codeclass.auth.user.api.SignUpApi;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.EmailTakenException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    private final AccessTokenService accessTokenService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, AccessTokenService accessTokenService) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.accessTokenService = accessTokenService;
    }

    @Transactional
    public List<User> getAll(){
        return (List<User>) userDAO.getAll();
    }

    @Transactional
    public User getById(Long id){
        User user = userDAO.getById(id);
        if(user == null)
            throw new NotFoundException("user.notfound");
        return user;
    }

    @Transactional
    public User signUp(SignUpApi api){
        User user = new User();

        if(isEmailTaken(api.getEmail()))
            throw new EmailTakenException();

        user.setId(null);
        user.setRole(roleDAO.getDefaultRole());
        user.setEmail(api.getEmail());
        user.setFirstname(api.getFirstName());
        user.setLastname(api.getLastName());
        user.setCreatedAt(LocalDateTime.now());

        String password = BCrypt.hashpw(api.getPassword(), BCrypt.gensalt());
        user.setPassword(password);

        user.setModifiedAt(user.getCreatedAt());

        user = userDAO.save(user);

        return user;
    }

    @Transactional
    public User changeEmail(Long id, ChangeEmailApi api){
        if(isEmailTaken(api.getEmail()))
            throw new EmailTakenException();

        User user = userDAO.getById(id);
        if(user == null)
            throw new NotFoundException("auth.user.notfound");

        if ( ! BCrypt.checkpw(api.getPassword(), user.getPassword()))
            throw new UnauthorizedException("auth.unauthorized");

        user.setEmail(api.getEmail());

        userDAO.save(user);
        return user;
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordApi api){
        User user = userDAO.getById(id);
        if(user == null)
            throw new NotFoundException("auth.user.notfound");

        if ( ! BCrypt.checkpw(api.getOldPassword(), user.getPassword()))
            throw new UnauthorizedException("auth.user.unauthorized");

        String password = BCrypt.hashpw(api.getNewPassword(), BCrypt.gensalt());
        user.setPassword(password);

        userDAO.save(user);
    }

    @Transactional
    public User changeRole(Long userId, Long roleId){
        User user = userDAO.getById(userId);
        if(user == null)
            throw new NotFoundException("auth.user.notfound");
        Role role = roleDAO.getById(roleId);
        if(role == null)
            throw new NotFoundException("auth.role.notfound");

        user.setRole(role);

        user = userDAO.save(user);

        return user;
    }

    @Transactional
    public void delete(Long id){
        User user = userDAO.getById(id);
        if(user == null)
            throw new NotFoundException("user.notfound");

        accessTokenService.deleteSessionByUser(user);

        userDAO.delete(user);
    }

    private boolean isEmailTaken(String email){
        return userDAO.isEmailTaken(email);
    }
}
