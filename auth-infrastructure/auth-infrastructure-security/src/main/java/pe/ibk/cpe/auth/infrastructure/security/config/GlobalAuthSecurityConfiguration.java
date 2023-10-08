package pe.ibk.cpe.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import pe.ibk.cpe.auth.infrastructure.security.provider.CollaboratorAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.provider.CustomerAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.service.CustomerUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.service.detail.UserDetailMapper;

@Configuration
@EnableWebSecurity
public class GlobalAuthSecurityConfiguration {

    @Bean
    public PasswordEncoder bcryptpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService customerUserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new UserDetailMapper());
    }

    @Bean
    public UserDetailsService collaboratorUserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new UserDetailMapper());
    }

    @Bean
    public AuthenticationProvider customerAuthenticationProvider(UserDetailsService customerUserDetailsService, PasswordEncoder bcryptpasswordEncoder) {
        return new CustomerAuthenticationProvider(customerUserDetailsService, bcryptpasswordEncoder);
    }

    @Bean
    public AuthenticationProvider collaboratorAuthenticationProvider(UserDetailsService collaboratorUserDetailsService, PasswordEncoder bcryptpasswordEncoder) {
        return new CollaboratorAuthenticationProvider(collaboratorUserDetailsService, bcryptpasswordEncoder);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider customerAuthenticationProvider) throws Exception {
        SecurityFilterChain securityFilterChain = httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .authenticationProvider(customerAuthenticationProvider)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/api/auth/customer/**").permitAll();
                })
                .build();

        System.out.println("Customer securityFilterChain : " + securityFilterChain.getFilters());

        return securityFilterChain;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain collaboratorSecurityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider collaboratorAuthenticationProvider) throws Exception {
        SecurityFilterChain securityFilterChain = httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .authenticationProvider(collaboratorAuthenticationProvider)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/api/auth/collaborator/**").permitAll();
                })
                .build();

        System.out.println("Collaborator securityFilterChain : " + securityFilterChain.getFilters());

        return securityFilterChain;
    }


}
