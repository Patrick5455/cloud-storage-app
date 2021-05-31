package com.udacity.jwdnd.course1.cloudstorage.securityconfig;

import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    public WebSecurityConfig(AuthService authService){
        this.authService = authService;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authService);
    }


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .antMatchers("/sessions/signup").permitAll()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

        httpSecurity.formLogin()
                .loginPage("/sessions/login")
                .failureForwardUrl("/sessions/login-error")
//                .failureHandler(authenticationFailureHandler())
                .defaultSuccessUrl("/", true)
                .permitAll();

        httpSecurity.logout()
                .logoutUrl("/sessions/logout")
                .logoutSuccessUrl("/sessions/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }


    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (httpServletRequest, httpServletResponse, e) -> {

            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            logger.info("handling error");
            httpServletResponse.sendRedirect("/login?error=true");
        };
    }
}
