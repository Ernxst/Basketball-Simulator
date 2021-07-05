package com.example.services.user;

import com.example.entities.user.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found.", username)));
    }

    public User register(User user) throws UsernameTakenException {
        String username = user.getUsername();
        if (!usernameExists(username)) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        }
        throw new UsernameTakenException(MessageFormat.format("The username {0} is already taken.", username));
    }

    @Override
    public User login(User user) throws UsernameNotFoundException {
        return null;
    }

    public User changePassword(User user, String newPassword) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean passwordMatches(User user, String enteredPassword) {
        return passwordEncoder.matches(enteredPassword, user.getPassword());
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
