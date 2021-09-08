package com.example.api;

import com.example.Application;
import com.example.config.JwtTokenUtil;
import com.example.entities.user.User;
import com.example.repositories.UserRepository;
import com.example.services.NameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.matches;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractApiTest {
    protected static final String TEST_USERNAME = "testuser";
    protected final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    protected final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
    };
    protected final String BAD_CREDENTIALS_MESSAGE = "Your username or password was incorrect, please try again.";
    protected final String TEST_PASSWORD = "password";
    protected final String VALID_REGISTER_USERNAME = "test-register";
    protected final String VALID_REGISTER_PASSWORD = "test-password";
    protected final User testUser = new User(TEST_USERNAME, passwordEncoder.encode(TEST_PASSWORD));
    protected final User newUser = new User(VALID_REGISTER_USERNAME, passwordEncoder.encode(VALID_REGISTER_PASSWORD));
    protected final User registrationRequest = new User(VALID_REGISTER_USERNAME, VALID_REGISTER_PASSWORD);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private Connection connection;

    @MockBean
    private NameService nameService;

    @BeforeEach
    public void setup() {
        Mockito.when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(testUser));
        String notTestUserMatcher = matches("^((?!" + TEST_USERNAME + ").)*$");
        Optional<User> blankUser = Optional.empty();
        Mockito.when(userRepository.findByUsername(notTestUserMatcher)).thenReturn(blankUser);
        Mockito.when(userRepository.save(argThat(new UserMatcher(registrationRequest)))
        ).thenReturn(newUser);
    }

    protected void expectFieldInResponse(MvcResult result, String key, String expectedMessage)
            throws UnsupportedEncodingException, JsonProcessingException {
        Map<String, Object> response = getResponse(result);
        assertTrue(response.containsKey(key));
        assertEquals(expectedMessage, response.get(key));
    }

    protected Map<String, Object> getResponse(MvcResult result)
            throws UnsupportedEncodingException, JsonProcessingException {
        String response = result.getResponse().getContentAsString();
        return objectMapper.readValue(response, typeRef);
    }

    protected String toSnakeCase(String input) {
        String ret = input.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z])([A-Z])", "$1_$2");
        return ret.toLowerCase();
    }

    protected Map<String, Object> toSnakeCaseMap(Map<String, Object> map) {
        Map<String, Object> formatted = new HashMap<>();
        map.forEach((String key, Object value) -> {
            String formattedKey = toSnakeCase(key);
            if (value instanceof String)
                value = toSnakeCase((String) value);
            else if (value instanceof Map)
                value = toSnakeCaseMap((Map<String, Object>) value);
            formatted.put(formattedKey, value);
        });
        return formatted;
    }

    private ResultActions testEndpoint(String contextPath, String endpoint, HttpMethod method,
                                       Object requestBody, HttpStatus expectedStatus, boolean auth)
            throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .request(method, contextPath + "/" + endpoint);
        if (auth) {
            String token = jwtTokenUtil.generateToken(testUser);
            request = request.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }

        if (!method.equals(HttpMethod.GET)) {
            Map<String, Object> body = objectMapper.convertValue(requestBody, typeRef);
            body = toSnakeCaseMap(body);
            request = request.content(objectMapper.writeValueAsString(body));
        }

        request.with(csrf())
                .secure(true)
                .header(HttpHeaders.ORIGIN, "http://localhost:" + Application.APP_PORT)
                .header(HttpHeaders.CONNECTION, "keep-alive")
                .characterEncoding(StandardCharsets.UTF_8.name())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        return mockMvc.perform(request).andExpect(status().is(expectedStatus.value()));
    }

    protected ResultActions testGetEndpoint(String contextPath, String endpoint,
                                            HttpStatus expectedStatus) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.GET, null, expectedStatus, false);
    }

    protected ResultActions testAuthenticatedGetEndpoint(String contextPath, String endpoint,
                                                         HttpStatus expectedStatus) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.GET, null, expectedStatus, true);
    }

    protected ResultActions testPostEndpoint(String contextPath, String endpoint, Object requestBody,
                                             HttpStatus expectedStatus) throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.POST, requestBody, expectedStatus, false);
    }

    protected ResultActions testAuthenticatedPostEndpoint(String contextPath, String endpoint,
                                                          Object requestBody, HttpStatus expectedStatus)
            throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.POST, requestBody, expectedStatus, true);
    }

    protected ResultActions testAuthenticatedDeleteEndpoint(String contextPath, String endpoint,
                                                            Object requestBody, HttpStatus expectedStatus)
            throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.DELETE, requestBody, expectedStatus, true);
    }

    protected ResultActions testAuthenticatedPatchEndpoint(String contextPath, String endpoint,
                                                           Object requestBody, HttpStatus expectedStatus)
            throws Exception {
        return testEndpoint(contextPath, endpoint, HttpMethod.PATCH, requestBody, expectedStatus, true);
    }

    private static class UserMatcher implements ArgumentMatcher<User> {
        private final User user;

        public UserMatcher(User user) {
            this.user = user;
        }

        @Override
        public boolean matches(User otherUser) {
            return user.getUsername().equals(otherUser.getUsername());
        }
    }
}
