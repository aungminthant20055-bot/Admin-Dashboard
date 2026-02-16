package com.example.AMT.Security.Filter;

import com.example.AMT.Security.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get the header
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2. Check if it's the right kind of header (The "Envelope" check)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 3. Extract just the token (Cutting off "Bearer ")
            token = authHeader.substring(7);

            // 4. Use your service to find out whose name is on this token
            username = jwtService.extractUsername(token);

            // Helpful for debugging in your console
            System.out.println("Filter found username: " + username);
        }

        // 5. THE "SECURITY CHECK": Proceed only if we have a name
        // AND the user isn't already logged in for this request
        // FIXED: changed (username == null) to (username != null)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // A. Get the full user info from the Database (The "Manager" check)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // B. Ask your JWTService: "Is this token actually valid for this user?"
            if (jwtService.validateToken(token, userDetails)) {

                // C. Create the official "Verified Badge" (The Green Light)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,               // Password not needed, token is enough
                        userDetails.getAuthorities()
                );

                // D. Link the ticket to the current web request details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // E. Put the badge in the "Security Pocket"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }


        // 6. Tell the request to move to the next filter/controller
        // VERY IMPORTANT: Don't forget this, or the app will hang!
        filterChain.doFilter(request, response);
    }
}   