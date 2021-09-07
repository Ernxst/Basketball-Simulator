package com.example.api.controllers.account;

import com.example.api.controllers.account.requests.AuthRequest;
import com.example.api.controllers.account.requests.ChangePasswordRequest;
import com.example.api.controllers.account.requests.DeleteUserRequest;
import com.example.api.controllers.account.responses.AuthSuccessResponse;
import com.example.api.util.AbstractResponse;
import com.example.api.util.GenericErrorResponse;
import com.example.api.util.ResponseBuilder;
import com.example.config.JwtTokenUtil;
import com.example.entities.user.User;
import com.example.services.user.UserService;
import com.example.services.user.UsernameTakenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller managing anything pertaining to the user's account.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Api(tags = "Users")
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
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @ApiOperation("Register a new user.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully registered.", response = AuthSuccessResponse.class),
            @ApiResponse(code = 409, message = "Username is already taken.", response = GenericErrorResponse.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<AuthSuccessResponse> register(@RequestBody AuthRequest authRequest)
            throws UsernameTakenException {
        User registeredUser = userService.register(new User(authRequest.getUsername(), authRequest.getPassword()));
        String token = jwtTokenUtil.generateAccessToken(registeredUser);
        String username = registeredUser.getUsername();
        AuthSuccessResponse body = new AuthSuccessResponse(username, token);
        return new ResponseBuilder<>(HttpStatus.CREATED, body, token).build();
    }

    /**
     * Log into an existing account and return a JSON response with a JWT authenticated token in the header.
     *
     * @param authRequest the request body, containing a username and password.
     * @return a HTTP response including a JWT token if login succeeds and an error message otherwise.
     */
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    @ApiOperation("Authenticate an existing user.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User successfully authenticated.", response = AuthSuccessResponse.class),
            @ApiResponse(code = 401, message = "User authentication failed.", response = GenericErrorResponse.class),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<AuthSuccessResponse> login(@RequestBody AuthRequest authRequest)
            throws BadCredentialsException, UsernameTakenException {
        String username = authRequest.getUsername();
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
        User user = (User) authenticate.getPrincipal();
        String token = jwtTokenUtil.generateAccessToken(user);
        AuthSuccessResponse body = new AuthSuccessResponse(username, token);
        return new ResponseBuilder<>(HttpStatus.CREATED, body, token).build();
    }

    /**
     * Delete a user from the database.
     *
     * @param username
     * @param request  the request body, containing the user's password.
     * @return a HTTP response indicating whether the deletion was successful.
     */
    @DeleteMapping(value = "/{username}/delete", consumes = "application/json", produces = "application/json")
    @ApiOperation("Delete an existing user.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User successfully deleted."),
            @ApiResponse(code = 401, message = "User authentication failed.", response = GenericErrorResponse.class),
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<AbstractResponse> deleteUser(@PathVariable String username,
                                                       @RequestBody DeleteUserRequest request)
            throws BadCredentialsException {
        User user = new User(username, request.getPassword());
        userService.deleteUser(user);
        return new ResponseBuilder<>(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Change a given user's password.
     *
     * @param username
     * @param request  the request body, containing the username, current and new password.
     * @return a HTTP response indicating whether the password change was successful.
     */
    @PostMapping(value = "/{username}/change_password", consumes = "application/json", produces = "application/json")
    @ApiOperation("Change a user's password.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User password successfully changed."),
            @ApiResponse(code = 401, message = "User authentication failed.", response = GenericErrorResponse.class),
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<AbstractResponse> changePassword(@PathVariable String username,
                                                           @RequestBody ChangePasswordRequest request)
            throws BadCredentialsException {
        User user = new User(username, request.getCurrentPassword());
        userService.changePassword(user, request.getNewPassword());
        return new ResponseBuilder<>(HttpStatus.NO_CONTENT).build();
    }
}
