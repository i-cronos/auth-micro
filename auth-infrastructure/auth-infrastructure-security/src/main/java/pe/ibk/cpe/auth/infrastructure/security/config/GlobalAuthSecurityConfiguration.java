package pe.ibk.cpe.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.CollaboratorUsernamePasswordAuthenticationFilter;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.handler.CollaboratorAuthenticationFailureHandler;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.handler.CollaboratorAuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.provider.CollaboratorAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.service.CollaboratorUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.service.detail.CollaboratorUserMapper;
import pe.ibk.cpe.auth.infrastructure.security.common.configuration.AppSecurityConfiguration;
import pe.ibk.cpe.auth.infrastructure.security.common.filter.PerimeterFilter;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.CustomerUsernamePasswordAuthenticationFilter;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.handler.CustomerAuthenticationFailureHandler;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.handler.CustomerAuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.customer.provider.CustomerAuthenticationProvider;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.CustomerUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.detail.CustomerUserDetailMapper;
import pe.ibk.cpe.dependencies.common.util.JsonUtil;
import pe.ibk.cpe.dependencies.infrastructure.security.token.TokenCreationService;
import pe.ibk.cpe.dependencies.infrastructure.security.token.configuration.TokenGeneralConfiguration;
import pe.ibk.cpe.dependencies.infrastructure.security.token.configuration.TokenGroupConfiguration;

@Configuration
@EnableWebSecurity
public class GlobalAuthSecurityConfiguration {

    @Bean
    public JsonUtil jsonUtil() {
        return new JsonUtil();
    }

    @Bean
    public TokenGeneralConfiguration tokenGeneralConfiguration() {
        return new TokenGeneralConfiguration();
    }

    @Bean
    public TokenGroupConfiguration tokenGroupConfiguration() {
        return new TokenGroupConfiguration();
    }

    @Bean
    public AppSecurityConfiguration appSecurityConfiguration() {
        return new AppSecurityConfiguration();
    }

    @Bean
    public TokenCreationService tokenCreationService(TokenGeneralConfiguration tokenGeneralConfiguration,
                                                     TokenGroupConfiguration tokenGroupConfiguration) {
        return new TokenCreationService(tokenGeneralConfiguration, tokenGroupConfiguration);
    }

    @Bean
    public PasswordEncoder bcryptpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomerUserDetailsService customerUserDetailsService(UserRepository userRepository) {
        return new CustomerUserDetailsService(userRepository, new CustomerUserDetailMapper());
    }

    @Bean
    public CollaboratorUserDetailsService collaboratorUserDetailsService(UserRepository userRepository) {
        return new CollaboratorUserDetailsService(userRepository, new CollaboratorUserMapper());
    }

    @Bean
    public AuthenticationProvider customerAuthenticationProvider(CustomerUserDetailsService customerUserDetailsService,
                                                                 PasswordEncoder bcryptpasswordEncoder) {
        return new CustomerAuthenticationProvider(customerUserDetailsService, bcryptpasswordEncoder);
    }

    @Bean
    public AuthenticationProvider collaboratorAuthenticationProvider(CollaboratorUserDetailsService collaboratorUserDetailsService,
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
                                                           AppSecurityConfiguration appSecurityConfiguration,
                                                           JsonUtil jsonUtil) throws Exception {
        CustomerUsernamePasswordAuthenticationFilter filter = new CustomerUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/api/auth/customer/v1.0/login"), authenticationManager, jsonUtil);
        filter.setAuthenticationSuccessHandler(new CustomerAuthenticationSuccessHandler(tokenCreationService, jsonUtil));
        filter.setAuthenticationFailureHandler(new CustomerAuthenticationFailureHandler(jsonUtil));

        return httpSecurity
                .securityMatcher(appSecurityConfiguration.getCustomerPath())
                .csrf(csrfConf -> csrfConf.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basicConfig -> basicConfig.disable())
                .addFilterBefore(new PerimeterFilter(jsonUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilterChain collaboratorSecurityFilterChain(HttpSecurity httpSecurity,
                                                               AuthenticationManager authenticationManager,
                                                               TokenCreationService tokenCreationService,
                                                               AppSecurityConfiguration appSecurityConfiguration,
                                                               JsonUtil jsonUtil) throws Exception {

        CollaboratorUsernamePasswordAuthenticationFilter filter = new CollaboratorUsernamePasswordAuthenticationFilter(new AntPathRequestMatcher("/api/auth/collaborator/v1.0/login"), authenticationManager, jsonUtil);
        filter.setAuthenticationSuccessHandler(new CollaboratorAuthenticationSuccessHandler(tokenCreationService, jsonUtil));
        filter.setAuthenticationFailureHandler(new CollaboratorAuthenticationFailureHandler(jsonUtil));

        return httpSecurity
                .securityMatcher(appSecurityConfiguration.getCollaboratorPath())
                .csrf(csrfConf -> csrfConf.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basicConfig -> basicConfig.disable())
                .addFilterBefore(new PerimeterFilter(jsonUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
