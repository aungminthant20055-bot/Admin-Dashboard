    package com.example.AMT.Security.SecurityConfig;

    import com.example.AMT.Security.Filter.JWTFilter;
    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.NoOpPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor

    public class AppConfig {
        private final UserDetailsService userDetailsService;
        private final JWTFilter jwtFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                        .csrf(csrf-> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/register","/login").permitAll()
                                // 1. Put the "Shared" access first (Specific URL)
                                .requestMatchers("/api/settings").hasAnyRole("ADMIN", "CASHIER")
                                // Only ADMIN can change Store Name or Tax Rate
                                // 2. Put the "Admin Only" access second (The rest of the settings like /update)
                                .requestMatchers("/api/settings/**").hasRole("ADMIN")
                                // Only MANAGERS and ADMINS can see Inventory/Reports
                                .requestMatchers("/api/inventory/**").hasAnyRole("MANAGER", "ADMIN")
                                // CASHIERS can access sales
                                .requestMatchers("/api/sales/**").hasRole("CASHIER")

                            .anyRequest().authenticated())

                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
            return config.getAuthenticationManager();
        }
    }
