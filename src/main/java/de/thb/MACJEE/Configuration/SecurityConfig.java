package de.thb.MACJEE.Configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(conf -> {
                    conf.loginPage("/login");
                    conf.defaultSuccessUrl("/dashboard");
                    conf.failureUrl("/login?error=true");
                }).logout(conf -> {
                    conf.logoutUrl("/logout");
                    conf.invalidateHttpSession(true);
                    conf.deleteCookies("SESSION");
                }).sessionManagement(conf ->{
                    conf.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                }).authorizeHttpRequests(conf -> {
                    conf
                            .requestMatchers("/dashboard").authenticated()
                            .requestMatchers("/job/**").authenticated()
                            // Spring security automatically parses the Strings to "ROLE_"+<Role>
                            .requestMatchers("/customer/**").hasRole("CUSTOMER")
                            .requestMatchers("/company/**").hasRole("COMPANY")
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/job/applicants").hasRole("COMPANY")
                    .requestMatchers("/**").permitAll();
                });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager (
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
