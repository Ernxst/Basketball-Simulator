package com.example.services.user;

import com.example.entities.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceInterface extends UserDetailsService {
    /**
     * Register a new user and store their details in the database, encrypting their password.
     *
     * @param user the user to insert into the database.
     * @return the created user object.
     * @throws UsernameTakenException if the chosen username is already present in the database.
     */
    User register(User user) throws UsernameTakenException;

    /**
     * Change a given user's password.
     *
     * @param user        the user whose password to change.
     * @param newPassword the user's desired new password.
     * @return the user with their new password set.
     */
    User changePassword(User user, String newPassword);

    /**
     * Return whether a given username is already present in the database.
     *
     * @param username the username to test.
     * @return a boolean, representing whether the username already exists.
     */
    boolean usernameExists(String username);

    /**
     * Test whether an entered password matches the password for a given user.
     *
     * @param user            the user whose password is being matched to.
     * @param enteredPassword the input password.
     * @return a boolean representing whether the passwords match.
     */
    boolean passwordMatches(User user, String enteredPassword);

    /**
     * Delete a given user from the repository.
     *
     * @param user the user to remove.
     */
    void deleteUser(User user);
}
