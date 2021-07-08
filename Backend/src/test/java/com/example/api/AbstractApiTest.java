package com.example.api;

import com.example.Application;
import com.example.config.JwtTokenFilter;
import com.example.config.JwtTokenUtil;
import com.example.config.WebConfig;
import com.example.config.WebSecurityConfig;
import com.example.entities.user.User;
import com.example.repositories.UserRepository;
import com.example.services.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @WithMockUser(username = "user1", password = "pwd") - mock a user
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebSecurityConfig.class, WebConfig.class, JwtTokenFilter.class, JwtTokenUtil.class})
public abstract class AbstractApiTest {
    protected final String TEST_USERNAME = "testuser";
    protected final String TEST_PASSWORD = "password";
    protected User testUser;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    private void createTestUser() throws Exception {
//        testUser = userService.register(new User(TEST_USERNAME, TEST_PASSWORD));
    }

    @AfterEach
    private void deleteTestUser() throws Exception {
//        userService.deleteUser(testUser);
    }

    private MvcResult testEndpoint(String contextPath, String endpoint, HttpMethod method,
                                   Object requestBody, String expectedMessage, boolean auth) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(method, contextPath + "/" + endpoint);
        if (auth)
            request = request.with(user(TEST_USERNAME));
        return mockMvc.perform(request
                .with(csrf())
                .header("Access-Control-Request-Method", method.toString())
                .header("Origin", "http://localhost:" + Application.APP_PORT)
                .contextPath(contextPath)
                .content(objectMapper.writeValueAsString(requestBody))
                .characterEncoding(StandardCharsets.UTF_8.name())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedMessage)))
                .andReturn();
    }

    protected MvcResult testGetEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.GET, requestBody, expectedMessage, false);
    }

    protected MvcResult testAuthenticatedGetEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.GET, requestBody, expectedMessage, true);
    }

    protected MvcResult testPostEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.POST, requestBody, expectedMessage, false);
    }

    protected MvcResult testAuthenticatedPostEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.POST, requestBody, expectedMessage, true);
    }
}
