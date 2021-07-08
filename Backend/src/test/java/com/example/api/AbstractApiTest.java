package com.example.api;

import com.example.Application;
import com.example.config.JwtTokenFilter;
import com.example.config.JwtTokenUtil;
import com.example.entities.user.User;
import com.example.repositories.UserRepository;
import com.example.services.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@ContextConfiguration(classes = {Application.class})
public abstract class AbstractApiTest {
    protected final String TEST_USERNAME = "testuser";
    protected final String TEST_PASSWORD = "password";
    protected final String VALID_REGISTER_USERNAME = "test-register";
    protected final String VALID_REGISTER_PASSWORD = "test-password";
    protected BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    protected Optional<User> testUser = Optional.of(new User(TEST_USERNAME, passwordEncoder.encode(TEST_PASSWORD)));
    protected Optional<User> newUser = Optional.of(new User(VALID_REGISTER_USERNAME, passwordEncoder.encode(VALID_REGISTER_PASSWORD)));

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
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
    private void createTestUser() {
        Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(testUser);
        Optional<User> testUser = Optional.of(new User(VALID_REGISTER_USERNAME, VALID_REGISTER_PASSWORD));
        Mockito.when(userRepository.save(testUser.get())).thenReturn(newUser.get());
    }

    private ResultActions testEndpoint(String contextPath, String endpoint, HttpMethod method,
                                       Object requestBody, String expectedMessage, boolean auth) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(method, contextPath + "/" + endpoint);
        if (auth)
            request = request.with(user(TEST_USERNAME));
        request
                .with(csrf())
                .secure(true)
                .header("Access-Control-Request-Method", method.toString())
                .header("Origin", "http://localhost:" + Application.APP_PORT)
                .content(objectMapper.writeValueAsString(requestBody))
                .characterEncoding(StandardCharsets.UTF_8.name())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(request)
                .andExpect(content().string(containsString(expectedMessage)));
    }

    protected ResultActions testGetEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.GET, requestBody, expectedMessage, false);
    }

    protected ResultActions testAuthenticatedGetEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.GET, requestBody, expectedMessage, true);
    }

    protected ResultActions testPostEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.POST, requestBody, expectedMessage, false);
    }

    protected ResultActions testAuthenticatedPostEndpoint(String contextPath, String endpoint, Object requestBody, String expectedMessage) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.POST, requestBody, expectedMessage, true);
    }
}
