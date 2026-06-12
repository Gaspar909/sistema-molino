package com.molinosystem.sistema_molino.config;

import com.molinosystem.sistema_molino.services.JwtService;
import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain) throws ServletException, IOException {

            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userName;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7);

            try{
                userName = jwtService.extractUserName(jwt);

                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
                    
                    if (jwtService.isTokenValid(jwt, userName)){
                        String role = jwtService.extractRol(jwt);

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userName,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority(role))
                        ); 

                        authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e){
                System.out.println("Error al validar el token JWT: " + e.getMessage());
            }
            
            filterChain.doFilter(request, response);
    }

}
