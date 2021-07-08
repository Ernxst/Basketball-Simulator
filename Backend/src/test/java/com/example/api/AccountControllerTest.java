package com.example.api;

import com.example.api.controllers.account.AccountController;
import com.example.entities.requests.AuthRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
/*
 * Unit test for the /user/* endpoints.
 */
public class AccountControllerTest extends AbstractApiTest {

    @Test
    public void loginValidUsernameAndPassword() throws Exception {
        ResultActions result = testPostEndpoint("/user", "login",
                new AuthRequest(TEST_USERNAME, TEST_PASSWORD), "Login success");
        result.andExpect(status().isOk());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void loginValidUsernameWrongPassword() throws Exception {
        ResultActions result = testPostEndpoint("/user", "login",
                new AuthRequest(TEST_USERNAME, "aaa"), "Login failed");
        result.andExpect(status().isUnauthorized());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void loginNonExistingUsername() throws Exception {
        ResultActions result = testPostEndpoint("/user", "login",
                new AuthRequest("doesntexist", TEST_PASSWORD), "does not exist");
        result.andExpect(status().isUnauthorized());
        MvcResult mvcResult = result.andReturn();

    }

    @Test
    public void registerValidUsernameAndPassword() throws Exception {
        String validRegisterUsername = "test-register";
        String validRegisterPassword = "12345";
        ResultActions result = testPostEndpoint("/user", "register",
                new AuthRequest(validRegisterUsername, validRegisterPassword),
                "Registration success");
        result.andExpect(status().isOk());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void registerTakenUsername() throws Exception {
        ResultActions result = testPostEndpoint("/user", "register",
                new AuthRequest(TEST_USERNAME, "aaa"), "already in use");
        result.andExpect(status().isConflict());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void deleteUser() throws Exception {

    }
}
