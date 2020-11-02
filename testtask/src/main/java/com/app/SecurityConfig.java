package com.app;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * We need a configuration annotation to have the Spriing Security configartion
 * defined in WebSecurityConfigure and overriding individuals method (I could extand WebSecurityConfigurerAdapter also)
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // configuration sets up a basic in-emory auth config
        /*
         * noop-prefix - is used to specify which password encoder should be used to encode the provided password (NoOpPasswordEndcoder)
         * A user with a role can have multiple authorities and privileges (for ex. will not be able to delete users), also
         * it can be defined endpoints with what the user will able to communicate.
         */
                .withUser("sa").password("{noop}pass").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/**").hasRole("USER")
                .antMatchers("/h2").permitAll()
                .antMatchers("/h2/**").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable();
        http.headers().frameOptions().disable();
    }

}