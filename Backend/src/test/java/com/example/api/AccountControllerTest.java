package com.example.api;

import com.example.api.controllers.account.AccountController;
import com.example.api.controllers.account.requests.AuthRequest;
import com.example.api.controllers.account.requests.ChangePasswordRequest;
import com.example.api.controllers.account.requests.DeleteUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
/*
 * Unit test for the /user/* endpoints.
 */
public class AccountControllerTest extends AbstractApiTest {

    @Test
    public void loginValidUsernameAndPassword() throws Exception {
        // TODO: Expect JWT token to be returned in header
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
        // TODO: Expect JWT token to be returned in header
        ResultActions result = testPostEndpoint("/user", "register",
                new AuthRequest(VALID_REGISTER_USERNAME, VALID_REGISTER_PASSWORD),
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
    public void changePasswordValidUsernameAndPassword() throws Exception {
        ResultActions result = testAuthenticatedPostEndpoint("/user", "change-password",
                new ChangePasswordRequest(TEST_PASSWORD, "some-password"),
                "Success");
        result.andExpect(status().isOk());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void changePasswordValidUsernameWrongPassword() throws Exception {
        ResultActions result = testAuthenticatedPostEndpoint("/user", "change-password",
                new ChangePasswordRequest("incorrect", "some-password"),
                "Your password was incorrect, please try again.");
        result.andExpect(status().isUnauthorized());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void changePasswordWrongUsername() throws Exception {
        String username = "doesntexist";
        ResultActions result = testAuthenticatedPostEndpoint("/user", "change-password",
                new ChangePasswordRequest(TEST_PASSWORD, "some-password"),
                MessageFormat.format("The user with username {0} cannot be found.", username));
        result.andExpect(status().isUnauthorized());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void deleteUserValidUsernameAndPassword() throws Exception {
        ResultActions result = testAuthenticatedDeleteEndpoint("/user", TEST_USERNAME + "/delete",
                new DeleteUserRequest(TEST_PASSWORD),
                "Success");
        result.andExpect(status().isOk());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void deleteUserValidUsernameWrongPassword() throws Exception {
        ResultActions result = testAuthenticatedDeleteEndpoint("/user", TEST_USERNAME + "/delete",
                new DeleteUserRequest("incorrect"),
                "Your password was incorrect, please try again.");
        result.andExpect(status().isUnauthorized());
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void deleteUserWrongUsername() throws Exception {
        String username = "doesntexist";
        ResultActions result = testAuthenticatedDeleteEndpoint("/user", username + "/delete",
                new DeleteUserRequest(TEST_PASSWORD),
                MessageFormat.format("The user with username {0} cannot be found.", username));
        result.andExpect(status().isUnauthorized());
        MvcResult mvcResult = result.andReturn();
    }

}
