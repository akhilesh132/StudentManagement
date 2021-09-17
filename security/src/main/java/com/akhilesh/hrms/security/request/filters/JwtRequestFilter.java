package com.akhilesh.hrms.security.request.filters;

import com.akhilesh.hrms.security.domain.exceptions.UserNotFoundException;
import com.akhilesh.hrms.security.domain.models.JsonWebToken;
import com.akhilesh.hrms.security.domain.models.User;
import com.akhilesh.hrms.security.domain.models.Username;
import com.akhilesh.hrms.security.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Autowired
    public JwtRequestFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String bearerToken = authorizationHeader.substring(7);
            JsonWebToken jwt = JsonWebToken.fromValue(bearerToken);
            Username username = jwt.extractUsername();
            User user = userService.findBy(username).orElseThrow(UserNotFoundException::new);
            if (jwt.isValidFor(user)) {
//                UsernamePasswordAuthenticationToken userPassAuthToken
//                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                WebAuthenticationDetails webAuthDetails = new WebAuthenticationDetailsSource().buildDetails(request);
//                userPassAuthToken.setDetails(webAuthDetails);
//                SecurityContext securityContext = SecurityContextHolder.getContext();
//                securityContext.setAuthentication(userPassAuthToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
