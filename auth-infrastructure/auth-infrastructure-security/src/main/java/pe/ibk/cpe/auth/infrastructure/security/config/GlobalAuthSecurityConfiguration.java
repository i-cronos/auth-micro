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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.provider.CollaboratorAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.provider.CustomerAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.service.CustomerUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.service.detail.UserDetailMapper;
import pe.ibk.cpe.dependencies.infrastructure.security.CoreWardenFilter;

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
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider customerAuthenticationProvider) throws Exception {
        SecurityFilterChain securityFilterChain = httpSecurity
                .securityMatcher("/api/auth/customer/**")
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/api/auth/customer/**").permitAll();
                })
                .addFilterAfter(new CoreWardenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

        System.out.println("Customer securityFilterChain : " + securityFilterChain.getFilters());

        return securityFilterChain;
    }


    @Bean
    public SecurityFilterChain collaboratorSecurityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider collaboratorAuthenticationProvider) throws Exception {
        SecurityFilterChain securityFilterChain = httpSecurity
                .securityMatcher("/api/auth/collaborator/**")
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .addFilterAfter(new CoreWardenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(HttpMethod.POST, "/api/auth/collaborator/**").permitAll();
                })
                .build();

        System.out.println("Collaborator securityFilterChain : " + securityFilterChain.getFilters());

        return securityFilterChain;
    }


}
