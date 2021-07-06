package com.example.api.controllers.account;

import com.example.api.responses.GenericResponse;
import com.example.api.responses.RegistrationSuccessResponse;
import com.example.api.responses.UserLoginResponse;
import com.example.config.JwtTokenUtil;
import com.example.entities.requests.AuthRequest;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * @param user the request body, containing a username and password.
     * @return a HTTP response including the username if registration succeeds and an error message otherwise.
     */
    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(@RequestBody User user) {
        try {
            String username = userService.register(user).getUsername();
            return ResponseEntity.ok()
                    .body(new RegistrationSuccessResponse(username));
        } catch (UsernameTakenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new GenericResponse(e.getMessage(), HttpStatus.CONFLICT));
        }
    }

    /**
     * Login into an existing account and return a JSON response with a JWT authenticated token in the header.
     *
     * @param authRequest the request body, containing a username and password.
     * @return a HTTP response including a JWT token if login succeeds and an error message otherwise.
     */
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            User user = (User) authenticate.getPrincipal();

            String accessToken = jwtTokenUtil.generateAccessToken(user);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .body(new UserLoginResponse(user.getUsername(), accessToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new GenericResponse("Login failed, either your username or password was incorrect, please try again.", HttpStatus.UNAUTHORIZED));
        }
    }

    /**
     * Delete a user from the database.
     *
     * @param user the user to delete.
     */
    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
    }
}
