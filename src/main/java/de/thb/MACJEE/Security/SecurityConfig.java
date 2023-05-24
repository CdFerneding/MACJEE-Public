package de.thb.MACJEE.Security;

import de.thb.MACJEE.Service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private JwtAuthEntryPoint authEntryPoint;
    private CustomUserDetailsService userDetailsService;

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/resources/**").anyRequest();
    }

    /**
     *
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // method for customizing exceptionHandling
                .exceptionHandling()
                    // throw exception 401: unauthorized
                    .authenticationEntryPoint(authEntryPoint)
                .and()
                // configure Session Management
                .sessionManagement()
                    /* policy stateless: no session-related information is stored on the app
                     * requests are treated independently without relying on session state
                     * common approach for the use of JSON Web Token's (JWTs):
                     * necessary authentication information is stored in the token instead;
                     * thereby not server/application resources are wasted;
                     * also no vulnerabilities on session-related exploits
                     **/
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // authorization rules for different requests: main security configuration!!
                // whitelist-modell (if not specified the default access is false)
                .authorizeRequests()
                    /* see methods in Controller.AuthController
                     * everyone needs access to login/register to set the authentication (who is it),
                     * to apply the authorization rules (where are you allowed to go)
                     * static also necessary for everyone, because it contains:
                     * styling, images, js and lib's which are not personal data (not sensitive information)
                     */
                    .requestMatchers("/MACJEE/auth/login", "/MACJEE/auth/register").permitAll()
                    .requestMatchers("/", "/static/**.css").permitAll()
                    .anyRequest().authenticated()
                .and()
                /* apply http basic authentication
                 * client includes thereby username and password in each request
                 */
                .httpBasic();

        // adding custom filter to the chain, common method for adding a token generation algorithm
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // return the final security filter chain
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

    /**
     * apply JWT to the authentication process
     * @return
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
