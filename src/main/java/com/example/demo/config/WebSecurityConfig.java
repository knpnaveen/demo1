package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("role1").password(passwordEncoder().encode("password")).roles("ROLE1")
                .and()
                .withUser("role2").password(passwordEncoder().encode("password")).roles("ROLE2")
                .and()
                .withUser("role3").password(passwordEncoder().encode("password")).roles("ROLE3")
                .and()
                .withUser("role4").password(passwordEncoder().encode("password")).roles("ROLE4");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/home").access("hasRole('ROLE2') or hasRole('ROLE3') or hasRole('ROLE4')")
                .antMatchers("/enquiry").access("hasRole('ROLE1')")
                .antMatchers("/login*").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin()
                .successHandler((request, response, authentication) -> {
                    String url = authentication.getAuthorities()
                            .stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().matches("ROLE_ROLE1")) ? "/enquiry" : "/home";
                    new DefaultRedirectStrategy().sendRedirect(request,response,url);
                });
        ;
    }

}
