package com.bitbuy.test.config;

import com.bitbuy.test.ws.handlers.DefaultAccessDeniedHandler;
import com.bitbuy.test.ws.handlers.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Override // Authentication
    protected void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(authenticationProvider());
    }

    @Override // Authorization
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/register").permitAll()
                .antMatchers("/users/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/login")
                .failureUrl("/login?error=true")
                .successHandler(new LoginSuccessHandler())
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedHandler(new DefaultAccessDeniedHandler());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
