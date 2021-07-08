package com.example.api;

import com.example.api.controllers.account.AccountController;
import com.example.entities.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(AccountController.class)
public class AccountControllerTest extends AbstractApiTest {

    @Test
    public void loginValidUsernameAndPassword() throws Exception {
        MvcResult result = testPostEndpoint("/user", "login",
                new User(TEST_USERNAME, TEST_PASSWORD), "Login Success");
    }

    @Test
    public void loginValidUsernameWrongPassword() throws Exception {
        MvcResult result = testPostEndpoint("/user", "login",
                new User(TEST_USERNAME, "aaa"), "Login failed");
//        BadCredentialsException thrown = assertThrows(BadCredentialsException.class,
//                () -> fn(), "Expected BadCredentialsException");
    }

    @Test
    public void loginNonExistingUsername() throws Exception {
//        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class,
//                () -> fn(), "Expected UsernameTakenException");
        MvcResult result = testPostEndpoint("/user", "login",
                new User("doesntexist", TEST_PASSWORD), "doesn't exist");

    }

    @Test
    public void registerValidUsernameAndPassword() throws Exception {
        String validRegisterUsername = "test-register";
        String validRegisterPassword = "12345";
        MvcResult result = testPostEndpoint("/user", "register",
                new User(validRegisterUsername, validRegisterPassword),
                "Registration success");
    }

    @Test
    public void registerTakenUsername() throws Exception {
        MvcResult result = testPostEndpoint("/user", "register",
                new User(TEST_USERNAME, "aaa"), "already taken");
//        UsernameTakenException thrown = assertThrows(UsernameTakenException.class,
//                () -> fn(), "Expected UsernameTakenException");
    }

    @Test
    public void deleteUser() throws Exception {

    }
}
