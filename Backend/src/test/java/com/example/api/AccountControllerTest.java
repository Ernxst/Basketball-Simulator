package com.example.api;

import com.example.api.controllers.account.requests.AuthRequest;
import com.example.api.controllers.account.requests.ChangePasswordRequest;
import com.example.api.controllers.account.requests.DeleteUserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Unit test for the /user/* endpoints.
 */
public class AccountControllerTest extends AbstractApiTest {

    private void expectJwtTokenInHeader(MvcResult result)
            throws UnsupportedEncodingException, JsonProcessingException {
        MockHttpServletResponse response = result.getResponse();
        assertTrue(response.containsHeader(HttpHeaders.AUTHORIZATION));
        String header = response.getHeader(HttpHeaders.AUTHORIZATION);
        assertTrue(header.startsWith("Bearer "));
        assertTrue(getResponse(result).containsKey("token"));
    }

    @Test
    public void loginValidUsernameAndPassword() throws Exception {
        ResultActions result = testPostEndpoint("/users", "login",
                new AuthRequest(TEST_USERNAME, TEST_PASSWORD), HttpStatus.CREATED);
        MvcResult mvcResult = result.andReturn();
        expectJwtTokenInHeader(mvcResult);
    }

    @Test
    public void loginValidUsernameWrongPassword() throws Exception {
        ResultActions result = testPostEndpoint("/users", "login",
                new AuthRequest(TEST_USERNAME, "aaa"), HttpStatus.UNAUTHORIZED);
        MvcResult mvcResult = result.andReturn();
        expectFieldInResponse(mvcResult, "error", BAD_CREDENTIALS_MESSAGE);
    }

    @Test
    public void loginNonExistingUsername() throws Exception {
        ResultActions result = testPostEndpoint("/users", "login",
                new AuthRequest("doesntexist", TEST_PASSWORD), HttpStatus.UNAUTHORIZED);
        MvcResult mvcResult = result.andReturn();
        expectFieldInResponse(mvcResult, "error", BAD_CREDENTIALS_MESSAGE);
    }

    @Test
    public void registerValidUsernameAndPassword() throws Exception {
        ResultActions result = testPostEndpoint("/users", "register",
                new AuthRequest(VALID_REGISTER_USERNAME, VALID_REGISTER_PASSWORD), HttpStatus.CREATED);
        MvcResult mvcResult = result.andReturn();
        expectJwtTokenInHeader(mvcResult);
    }

    @Test
    public void registerTakenUsername() throws Exception {
        ResultActions result = testPostEndpoint("/users", "register",
                new AuthRequest(TEST_USERNAME, "aaa"), HttpStatus.CONFLICT);
        String expectedMessage = MessageFormat.format("The username {0} is already in use, please try another. Did you mean to sign in?", TEST_USERNAME);
        MvcResult mvcResult = result.andReturn();
        expectFieldInResponse(mvcResult, "error", expectedMessage);
    }

    @Test
    public void changePasswordValidUsernameAndPassword() throws Exception {
        ResultActions result = testAuthenticatedPatchEndpoint("/users", TEST_USERNAME + "/change_password",
                new ChangePasswordRequest(TEST_PASSWORD, "some-password"), HttpStatus.NO_CONTENT);
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void changePasswordValidUsernameWrongPassword() throws Exception {
        ResultActions result = testAuthenticatedPatchEndpoint("/users", TEST_USERNAME + "/change_password",
                new ChangePasswordRequest("incorrect", "some-password"),
                HttpStatus.UNAUTHORIZED);
        MvcResult mvcResult = result.andReturn();
        expectFieldInResponse(mvcResult, "error", BAD_CREDENTIALS_MESSAGE);
    }

    @Test
    public void changePasswordWrongUsername() throws Exception {
        String username = "doesntexist";
        ResultActions result = testAuthenticatedPatchEndpoint("/users", username + "/change_password",
                new ChangePasswordRequest(TEST_PASSWORD, "some-password"), HttpStatus.UNAUTHORIZED);
        String expectedMessage = MessageFormat.format("The user with username {0} cannot be found.", username);
        MvcResult mvcResult = result.andReturn();
        expectFieldInResponse(mvcResult, "error", expectedMessage);

    }

    @Test
    public void deleteUserValidUsernameAndPassword() throws Exception {
        ResultActions result = testAuthenticatedDeleteEndpoint("/users", TEST_USERNAME,
                new DeleteUserRequest(TEST_PASSWORD), HttpStatus.NO_CONTENT);
        MvcResult mvcResult = result.andReturn();
    }

    @Test
    public void deleteUserValidUsernameWrongPassword() throws Exception {
        ResultActions result = testAuthenticatedDeleteEndpoint("/users", TEST_USERNAME,
                new DeleteUserRequest("incorrect"), HttpStatus.UNAUTHORIZED);
        MvcResult mvcResult = result.andReturn();
        expectFieldInResponse(mvcResult, "error", BAD_CREDENTIALS_MESSAGE);
    }

    @Test
    public void deleteUserWrongUsername() throws Exception {
        String username = "doesntexist";
        ResultActions result = testAuthenticatedDeleteEndpoint("/users", username,
                new DeleteUserRequest(TEST_PASSWORD), HttpStatus.UNAUTHORIZED);
        String expectedMessage = MessageFormat.format("The user with username {0} cannot be found.", username);
        MvcResult mvcResult = result.andReturn();
        expectFieldInResponse(mvcResult, "error", expectedMessage);
    }
}
