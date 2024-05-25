/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlcc.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qlcc.services.UserService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author DELL
 */
@Component
@PropertySource("classpath:application.properties")
public class JwtRequestFilter extends OncePerRequestFilter{

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader(AUTHORIZATION);

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            try {
                String token = authenticationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(env.getProperty("secret.key").getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();

                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                try {
                    throw new Exception(e.getMessage());
                } catch (Exception ex) {
                    Logger.getLogger(JwtRequestFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
