package com.example.config;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    public JWTAuthenticationFilter (AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public Authentication attemptAuthentication (HttpServletRequest req, HttpServletResponse res) {
//        try {
//            Users creds = new ObjectMapper().readValue(req.getInputStream(), Users.class);
//
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getUsername(),
//                            creds.getPassword()
//            ));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req,
//                                            HttpServletResponse res,
//                                            FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//
//        String token = JWT.create()
//                .withSubject(((User) auth.getPrincipal()).getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(HMAC512(SECRET.getBytes()));
//        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
//    }
}
