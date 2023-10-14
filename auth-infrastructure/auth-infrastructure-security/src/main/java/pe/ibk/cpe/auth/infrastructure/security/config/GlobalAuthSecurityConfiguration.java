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
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.CollaboratorUsernamePasswordAuthenticationFilter;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.handler.CollaboratorAuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.handler.CollaboratorFailureAuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.provider.CollaboratorAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.CustomerUsernamePasswordAuthenticationFilter;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.handler.CustomerAuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.handler.CustomerFailureAuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.customer.provider.CustomerAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.CustomerUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.detail.CustomerUserDetailMapper;
import pe.ibk.cpe.dependencies.common.util.JsonUtil;
import pe.ibk.cpe.dependencies.infrastructure.security.filter.CoreWardenFilter;
import pe.ibk.cpe.dependencies.infrastructure.security.token.TokenConfiguration;
import pe.ibk.cpe.dependencies.infrastructure.security.token.TokenCreationService;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class GlobalAuthSecurityConfiguration {

    @Bean
    public JsonUtil jsonUtil() {
        return new JsonUtil();
    }

    @Bean
    public TokenConfiguration tokenConfiguration() {
        return new TokenConfiguration();
    }

    @Bean
    public TokenCreationService tokenCreationService(TokenConfiguration tokenConfiguration) {
        System.out.println(" 0. :::::::::::: "+tokenConfiguration);
        if(Objects.nonNull(tokenConfiguration)){
            System.out.println(" 1. ::::::::::::::::::::::: "+tokenConfiguration.getGeneral());
            System.out.println(" 2. ::::::::::::::::::::::: "+tokenConfiguration.getCustoms());
        }
        return new TokenCreationService(tokenConfiguration);
    }

    @Bean
    public PasswordEncoder bcryptpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService customerUserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new CustomerUserDetailMapper());
    }

    @Bean
    public UserDetailsService collaboratorUserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new CustomerUserDetailMapper());
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
        authenticationManagerBuilder.parentAuthenticationManager(null);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity httpSecurity,
                                                           AuthenticationManager authenticationManager,
                                                           TokenCreationService tokenCreationService,
                                                           JsonUtil jsonUtil) throws Exception {
        CustomerUsernamePasswordAuthenticationFilter filter = new CustomerUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/api/auth/customer/v1.0/login"), authenticationManager, jsonUtil);
        filter.setAuthenticationSuccessHandler(new CustomerAuthenticationSuccessHandler(tokenCreationService, jsonUtil));
        filter.setAuthenticationFailureHandler(new CustomerFailureAuthenticationSuccessHandler(jsonUtil));
        filter.setAuthenticationManager(null);

        return httpSecurity
                .csrf(csrfConf -> csrfConf.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basicConfig -> basicConfig.disable())
                .securityMatcher("/api/auth/customer/**")
                .addFilterBefore(new CoreWardenFilter(jsonUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilterChain collaboratorSecurityFilterChain(HttpSecurity httpSecurity,
                                                               AuthenticationManager authenticationManager,
                                                               TokenCreationService tokenCreationService,
                                                               JsonUtil jsonUtil) throws Exception {

        CollaboratorUsernamePasswordAuthenticationFilter filter = new CollaboratorUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/api/auth/collaborator/v1.0/login"), authenticationManager, jsonUtil);
        filter.setAuthenticationSuccessHandler(new CollaboratorAuthenticationSuccessHandler(tokenCreationService, jsonUtil));
        filter.setAuthenticationFailureHandler(new CollaboratorFailureAuthenticationSuccessHandler(jsonUtil));

        return httpSecurity
                .securityMatcher("/api/auth/collaborator/**")
                .csrf(csrfConf -> csrfConf.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basicConfig -> basicConfig.disable())
                .addFilterBefore(new CoreWardenFilter(jsonUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
