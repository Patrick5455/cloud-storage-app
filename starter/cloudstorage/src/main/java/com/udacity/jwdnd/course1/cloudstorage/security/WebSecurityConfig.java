package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.services.securityservices.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;

    @Autowired
    public WebSecurityConfig(AuthService authService){
        this.authService = authService;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authService);
    }


    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.authorizeRequests()
//                .antMatchers("/").permitAll();

        httpSecurity.authorizeRequests()
                .antMatchers("/sessions/signup").permitAll()
                .antMatchers("/sessions/process_signup").permitAll()
                .antMatchers("/css/**", "/js/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/console/**").permitAll()
                .antMatchers("/home").permitAll()
                .anyRequest().authenticated();


        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

        httpSecurity.formLogin()
                .loginPage("/sessions/login")
                .loginProcessingUrl("/sessions/perform_login")
                .defaultSuccessUrl("/home", true)
                .permitAll();

        httpSecurity.logout()
                .logoutUrl("/sessions/logout")
                .logoutSuccessUrl("/sessions.login")
                .permitAll();

    }


}
