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

    /**
     *
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // method for customizing exceptionHandling

                // throw exception 401: unauthorized

                // configure Session Management
                /* policy stateless: no session-related information is stored on the app
                 * requests are treated independently without relying on session state
                 * common approach for the use of JSON Web Token's (JWTs):
                 * necessary authentication information is stored in the token instead;
                 * thereby not server/application resources are wasted;
                 * also no vulnerabilities on session-related exploits
                 **/

                // authorization rules for different requests: main security configuration!!
                // whitelist-modell (if not specified the default access is false)
                /* see methods in Controller.AuthController
                 * everyone needs access to login/register to set the authentication (who is it),
                 * to apply the authorization rules (where are you allowed to go)
                 * static also necessary for everyone, because it contains:
                 * styling, images, js and lib's which are not personal data (not sensitive information)
                 */

                /* apply http basic authentication
                 * client includes thereby username and password in each request
                 */
        http
                .formLogin(conf -> {
                    conf.loginPage("/login");
                    conf.defaultSuccessUrl("/dashboard");
                }).logout(conf -> {
                    conf.invalidateHttpSession(true);
                    conf.deleteCookies("SESSION");
                }).sessionManagement(conf ->{
                    conf.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                }).authorizeHttpRequests(conf -> {
                    conf
                            .requestMatchers("/dashboard").authenticated()
                            /*.requestMatchers("/customer/**").hasRole("CUSTOMER")
                            .requestMatchers("/company/**").hasRole("COMPANY")
                            .requestMatchers("/adim/**").hasRole("ADMIN")*/
                    .requestMatchers("/**").permitAll();
                });

        // adding custom filter to the chain, common method for adding a token generation algorithm
        //http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
}
