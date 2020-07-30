package com.example.springdemo.user;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests()
                .antMatchers("/get/**").permitAll();
*/

        http.csrf().disable();
        /*
        http.csrf()
                .ignoringAntMatchers("/get/**")
                .and()
                .authorizeRequests()
                .antMatchers("/get/**").permitAll();*/
    }
}


