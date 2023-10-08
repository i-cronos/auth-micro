package pe.ibk.cpe.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.provider.CustomerAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.service.CustomerUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.service.UserDetailMapper;

@Configuration
@EnableWebSecurity
public class CustomerAuthSecurityConfiguration {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider customerAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder bcryptpasswordEncoder) {
        return new CustomerAuthenticationProvider(userDetailsService, bcryptpasswordEncoder);
    }

    @Bean
    public PasswordEncoder bcryptpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService customeruserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new UserDetailMapper());
    }

    @Bean
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider customerAuthenticationProvider) throws Exception {
        return httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .authenticationProvider(customerAuthenticationProvider)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/api/auth/customer/**").permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/api/auth/collaborator/**").permitAll();
                    authorizationManagerRequestMatcherRegistry.anyRequest().denyAll();
                })
                .build();
    }


}
