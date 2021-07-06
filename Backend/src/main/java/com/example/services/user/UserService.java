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

    /**
     * Load a user from the database by their (unique) username.
     *
     * @param username the username of the user.
     * @return the found user object.
     * @throws UsernameNotFoundException if a user with the provided username could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found.", username)));
    }

    /**
     * Register a new user and store their details in the database, encrypting their password.
     *
     * @param user the user to insert into the database.
     * @return the created user object.
     * @throws UsernameTakenException if the chosen username is already present in the database.
     */
    public User register(User user) throws UsernameTakenException {
        String username = user.getUsername();
        if (!usernameExists(username)) {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        }
        throw new UsernameTakenException(MessageFormat.format("The username {0} is already taken, please try another.", username));
    }

    // TODO - Remove
    @Override
    public User login(User user) throws UsernameNotFoundException {
        return null;
    }

    /**
     * Change a given user's password.
     *
     * @param user        the user whose password to change.
     * @param newPassword the user's desired new password.
     * @return the user with their new password set.
     */
    public User changePassword(User user, String newPassword) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    /**
     * Return whether a given username is already present in the database.
     *
     * @param username the username to test.
     * @return a boolean, representing whether the username already exists.
     */
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Test whether an entered password matches the password for a given user.
     *
     * @param user            the user whose password is being matched to.
     * @param enteredPassword the input password.
     * @return a boolean representing whether the passwords match.
     */
    public boolean passwordMatches(User user, String enteredPassword) {
        return passwordEncoder.matches(enteredPassword, user.getPassword());
    }

    /**
     * Delete a given user from the repository.
     *
     * @param user the user to remove.
     */
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
