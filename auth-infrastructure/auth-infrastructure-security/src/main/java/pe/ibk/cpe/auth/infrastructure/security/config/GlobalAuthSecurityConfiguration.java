package pe.ibk.cpe.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.filter.CollaborartorUsernamePasswordAuthenticationFilter;
import pe.ibk.cpe.auth.infrastructure.security.filter.CustomerUsernamePasswordAuthenticationFilter;
import pe.ibk.cpe.auth.infrastructure.security.filter.handler.CustomerAuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.filter.handler.CustomerFailureAuthenticationSuccessHandler;
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
    public UserDetailsService customerUserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new UserDetailMapper());
    }

    @Bean
    public UserDetailsService collaboratorUserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new UserDetailMapper());
    }

    @Bean
    public AuthenticationProvider customerAuthenticationProvider(UserDetailsService customerUserDetailsService,
                                                                 PasswordEncoder bcryptpasswordEncoder) {
        return new CustomerAuthenticationProvider(customerUserDetailsService, bcryptpasswordEncoder);
    }

    @Bean
    public AuthenticationProvider collaboratorAuthenticationProvider(UserDetailsService collaboratorUserDetailsService,
                                                                     PasswordEncoder bcryptpasswordEncoder) {
        return new CollaboratorAuthenticationProvider(collaboratorUserDetailsService, bcryptpasswordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       AuthenticationProvider customerAuthenticationProvider,
                                                       AuthenticationProvider collaboratorAuthenticationProvider) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customerAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(collaboratorAuthenticationProvider);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        SecurityFilterChain securityFilterChain = httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .securityMatcher("/api/auth/customer/**")
                .addFilterBefore(new CoreWardenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildCustomerUsernamePasswordAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .build();

        System.out.println("Customer securityFilterChain : " + securityFilterChain.getFilters());

        return securityFilterChain;
    }


    @Bean
    public SecurityFilterChain collaboratorSecurityFilterChain(HttpSecurity httpSecurity,
                                                               AuthenticationProvider collaboratorAuthenticationProvider) throws Exception {
        SecurityFilterChain securityFilterChain = httpSecurity
                .securityMatcher("/api/auth/collaborator/**")
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable())
                .addFilterBefore(new CoreWardenFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CollaborartorUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

        System.out.println("Collaborator securityFilterChain : " + securityFilterChain.getFilters());

        return securityFilterChain;
    }


    private CustomerUsernamePasswordAuthenticationFilter buildCustomerUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        CustomerUsernamePasswordAuthenticationFilter filter = new CustomerUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/api/auth/customer/v1.0/login"), authenticationManager);
        filter.setAuthenticationSuccessHandler(new CustomerAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new CustomerFailureAuthenticationSuccessHandler());

        return filter;
    }

}
