package com.applydigital.hackernews.infrastructure.config.security;

import com.applydigital.hackernews.application.user.retrieve.get.GetUserByEmailUseCase;
import com.applydigital.hackernews.domain.data.user.User;
import com.applydigital.hackernews.domain.data.user.UserID;
import com.applydigital.hackernews.domain.exceptions.InvalidTokenException;
import com.applydigital.hackernews.infrastructure.users.persistence.entity.UserJpaEntity;
import com.applydigital.hackernews.infrastructure.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;
    private final GetUserByEmailUseCase getUserByEmailUseCase;

    public JwtFilter(final JwtService jwtService, final GetUserByEmailUseCase getUserByEmailUseCase) {
        this.jwtService = jwtService;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getToken(request);

        if(token != null){

            try{

                String email = jwtService.getEmailFromToken(token);

                var user = getUserByEmailUseCase.execute(email);
                var userToGetAccessToken =  User.with(UserID.from(user.id()), user.name(), user.email(), user.password(), user.createdAt());
                var userJpa = UserJpaEntity.from(userToGetAccessToken);

                setUserAsAuthenticated(userJpa);

            } catch (InvalidTokenException e){
                log.error("Token invalido: {} ", e.getMessage());
            } catch (Exception e){
                log.error("Erro na validacao do token: {} ", e.getMessage());
            }

        }

        filterChain.doFilter(request, response);
    }

    private void setUserAsAuthenticated(UserJpaEntity user){
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(authHeader!=null){
            String[] authHeaderParts = authHeader.split(" ");
            if(authHeaderParts.length == 2){
                return authHeaderParts[1];
            }
        }
        return null;
    }

//    protected  boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return request.getRequestURI().contains("/v1/users");
//    }
}
