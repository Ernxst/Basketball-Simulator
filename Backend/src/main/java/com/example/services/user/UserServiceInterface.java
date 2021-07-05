package com.example.services.user;

import com.example.entities.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceInterface extends UserDetailsService {
    User register(User user) throws UsernameTakenException;

    User login(User user) throws UsernameNotFoundException;

    User changePassword(User user, String newPassword);

    boolean usernameExists(String username);

    boolean passwordMatches(User user, String enteredPassword);

    void deleteUser(User user);
}
