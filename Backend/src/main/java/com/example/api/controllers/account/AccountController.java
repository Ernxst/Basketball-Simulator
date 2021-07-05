package com.example.api.controllers.account;

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

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class AccountController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;


    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            return userService.register(user).getUsername();
        } catch (UsernameTakenException e) {
            return null;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            User user = (User) authenticate.getPrincipal();
            System.out.println("New login by: " + user.getUsername());

            String accessToken = jwtTokenUtil.generateAccessToken(user);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, accessToken)
                    .body(new UserLoginResponse(user.getUsername(), accessToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/delete")
    public void deleteUser(User user) {
        userService.deleteUser(user);
    }
}
