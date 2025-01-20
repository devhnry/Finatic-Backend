package dev.finaticbackend.config;

import dev.finaticbackend.entities.AuthToken;
import dev.finaticbackend.repositories.TokenRepository;
import dev.finaticbackend.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String accessToken = null;
        String emailAddress = null;

        try {
            if (authorizationHeader == null || authorizationHeader.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (authorizationHeader.startsWith("Bearer ")) {
                accessToken = authorizationHeader.substring(7);
                emailAddress = jwtService.extractUsername(accessToken);
            }

            if (emailAddress != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(emailAddress);

                Function<AuthToken, Boolean> validateToken =
                        t -> !t.getExpired().equals(true) && !t.getRevoked().equals(true);

                boolean isAccessTokenValid = tokenRepository.findByAccessToken(accessToken).map(
                        validateToken
                ).orElse(false);

                if (jwtService.isTokenValid(accessToken, userDetails) && isAccessTokenValid) {
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    UsernamePasswordAuthenticationToken newAccessToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    newAccessToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(newAccessToken);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
