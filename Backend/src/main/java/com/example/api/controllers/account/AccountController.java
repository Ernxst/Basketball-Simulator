package com.example.api.controllers.account;

import com.example.api.responses.GenericResponse;
import com.example.api.responses.RegistrationSuccessResponse;
import com.example.api.responses.UserLoginResponse;
import com.example.config.JwtTokenUtil;
import com.example.entities.requests.AuthRequest;
import com.example.entities.requests.ChangePasswordRequest;
import com.example.entities.user.User;
import com.example.services.user.UserService;
import com.example.services.user.UsernameTakenException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Controller managing anything pertaining to the user's account.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class AccountController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Register a new account and return a JSON response.
     *
     * @param authRequest the request body, containing a username and password.
     * @return a HTTP response including the username if registration succeeds and an error message otherwise.
     */
    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(@RequestBody User authRequest) {
        try {
            User registeredUser = userService.register(authRequest);
            String username = registeredUser.getUsername();
            return ResponseEntity.ok()
                    .body(new RegistrationSuccessResponse(username));
        } catch (UsernameTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT.value())
                    .body(new GenericResponse(e.getMessage(), HttpStatus.CONFLICT));
        }
    }

    /**
     * Log into an existing account and return a JSON response with a JWT authenticated token in the header.
     *
     * @param authRequest the request body, containing a username and password.
     * @return a HTTP response including a JWT token if login succeeds and an error message otherwise.
     */
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
            User user = (User) authenticate.getPrincipal();

            String accessToken = jwtTokenUtil.generateAccessToken(user);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .body(new UserLoginResponse(username, accessToken));
        } catch (BadCredentialsException ex) {
            if (!userService.usernameExists(username))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                        .body(new GenericResponse("Login failed, the username " + username + " does not exist; are you trying to sign up?", HttpStatus.UNAUTHORIZED));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                    .body(new GenericResponse("Login failed, either your username or password was incorrect, please try again.", HttpStatus.UNAUTHORIZED));
        }
    }

    /**
     * Delete a user from the database.
     *
     * @param authRequest the request body, containing the username and password.
     * @return a HTTP response indicating whether the deletion was successful.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse> deleteUser(@RequestBody AuthRequest authRequest) {
        try {
            User user = new User(authRequest.getUsername(), authRequest.getPassword());
            userService.deleteUser(user);
            return ResponseEntity.status(HttpStatus.OK.value())
                    .body(new GenericResponse("Success"));
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                    .body(new GenericResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED));
        }
    }

    /**
     * Change a given user's password.
     *
     * @param request the request body, containing the username, current and new password.
     * @return a HTTP response indicating whether the password change was successful.
     */
    @PostMapping("/change-password")
    public ResponseEntity<GenericResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            User user = new User(request.getUsername(), request.getCurrentPassword());
            userService.changePassword(user, request.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK.value())
                    .body(new GenericResponse("Success"));
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                    .body(new GenericResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED));
        }
    }
}
