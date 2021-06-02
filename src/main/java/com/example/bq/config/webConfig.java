package com.example.bq.config;


import com.example.bq.model.AuthToken;
import com.example.bq.model.AuthTokenRepository;
import com.example.bq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.WebUtils;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Configuration
public class webConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    UserService userDetailsService;

    @Autowired
    AuthTokenRepository authTokenRepository;

    @Value("${com.exemple.auth.token}")
    private String authToken;


    @Value("${com.exemple.csrf.token}")
    private String csrfCookieTokenName;

    @Value("${com.exemple.csrf.header.token}")
    private String csrfHeaderTokenName;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/img/**", "/*.js", "/*.css", "/*.html");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()
                .antMatchers("/api/user/login", "/", "/login", "/compte", "/index", "/css/*").permitAll()
                .anyRequest().authenticated();

        http
                .addFilterBefore(new AuthenticationFilter(authTokenRepository, userDetailsService, authToken), UsernamePasswordAuthenticationFilter.class);

        http
                .logout()
                .logoutUrl("/api/user/logout")
                .logoutSuccessHandler(getLogoutSuccessHandler())
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies(authToken, csrfCookieTokenName);

        http
                .csrf()
                .requireCsrfProtectionMatcher(request ->
                        ("/api/user/login".equals(request.getRequestURI())
                                || ("/api/comments".equals(request.getRequestURI()) && HttpMethod.POST.matches(request.getMethod())
                        ))
                )
                .csrfTokenRepository(getCsrfTokenRepository())
        ;


    }

    private CookieCsrfTokenRepository getCsrfTokenRepository() {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookieCsrfTokenRepository.setCookieName(csrfCookieTokenName);
        return cookieCsrfTokenRepository;
    }

    private LogoutSuccessHandler getLogoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            private final Logger log = LoggerFactory.getLogger(this.getClass());

            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                Cookie token = WebUtils.getCookie(request, authToken);
                if (token != null) {
                    Optional<AuthToken> byUserId = authTokenRepository.findById(token.getValue());

                    byUserId.ifPresent((authToken) -> {
                        authTokenRepository.delete(authToken);
                        log.info("suppression de la session : {}", authToken.getToken());
                    });
                }
            }
        };
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

            String name = auth.getName();
            String password = auth.getCredentials()
                    .toString();


            UserDetails user = userDetailsService.loadUserByUsername(name);

            if (user == null || !bCryptPasswordEncoder().matches(password, user.getPassword())) {
                throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
            }

            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index.html");
        registry.addViewController("/index").setViewName("forward:index.html");
        registry.addViewController("/accueil").setViewName("forward:accueil.html");
        registry.addViewController("/login").setViewName("forward:login.html");
        registry.addViewController("/compte").setViewName("forward:compte.html");
    }

}
