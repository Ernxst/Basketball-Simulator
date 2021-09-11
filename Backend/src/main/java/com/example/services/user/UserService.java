package com.example.services.user;

import com.example.entities.user.User;
import com.example.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        if (usernameExists(username)) {
            throw new UsernameTakenException(MessageFormat.format("The username {0} is already in use, please try another. Did you mean to sign in?", username));
        }
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    /**
     * Change a given user's password.
     *
     * @param user        the user whose password to change.
     * @param newPassword the user's desired new password.
     * @return the user with their new password set.
     */
    public User changePassword(User user, String newPassword) {
        String username = user.getUsername();
        if (usernameExists(username)) {
            String rawPassword = user.getPassword();
            UserDetails loadedUser = loadUserByUsername(username);
            // Get real user data from repository.
            user = new User(username, loadedUser.getPassword());

            if (passwordMatches(user, rawPassword)) {
                String encryptedNewPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedNewPassword);
                return userRepository.save(user);
            }
            throw new BadCredentialsException("Your password was incorrect, please try again.");
        }
        throw new UsernameNotFoundException(MessageFormat.format("The user with username {0} cannot be found.", username));
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
     * @param user        the user whose password is being matched to.
     * @param rawPassword the input password.
     * @return a boolean representing whether the passwords match.
     */
    public boolean passwordMatches(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    /**
     * Delete a given user from the repository.
     *
     * @param user the user to remove.
     */
    public void deleteUser(User user) {
        String username = user.getUsername();
        if (usernameExists(username)) {
            String rawPassword = user.getPassword();
            UserDetails loadedUser = loadUserByUsername(username);
            // Get real user data from repository.
            user = new User(username, loadedUser.getPassword());

            if (passwordMatches(user, rawPassword)) {
                userRepository.delete(user);
                return;
            }
            throw new BadCredentialsException("Your password was incorrect, please try again.");
        }
        throw new UsernameNotFoundException(MessageFormat.format("The user with username {0} cannot be found.", username));
    }
}
