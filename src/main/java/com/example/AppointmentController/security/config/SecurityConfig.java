package com.example.AppointmentController.security.config;


import com.elijah.doctorsappointmentbookingsystem.security.filter.CustomAuthenticationFilter;
import com.elijah.doctorsappointmentbookingsystem.security.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/access/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/access/login/**").permitAll();
        http.authorizeRequests().antMatchers("/login", "/appUser/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(POST,"/appUser/user/create/").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/appUser/role/addToUser").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/appUser/role/save").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET,"/appUser/all/").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/patient/signUp").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers(POST,"/patient/signIn").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers(POST,"/patient/getInfo").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers(POST,"/appointment/book").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers(POST,"/patient/update").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().antMatchers(PUT,"/patient/signUp").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers(DELETE,"/appointment/delete").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().antMatchers(GET,"/appointment/info").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().antMatchers(GET,"/appointment/list/all").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().antMatchers(GET,"/appointment/list/cancel").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().antMatchers(GET,"/appointment/list/approved").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().antMatchers(GET,"/appointment/list/pending").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().antMatchers(GET,"/appointment/list/attended").hasAnyAuthority("ADMIN","MANAGER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
