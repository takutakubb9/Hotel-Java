package com.example.hotelproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
           .and().csrf().ignoringAntMatchers("/h2-console/**")//don't apply CSRF protection to /h2-console
           .and().headers().frameOptions().sameOrigin();//allow use of frame to same origin urls
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // パスワード
        String password = passwordEncoder().encode("password");

        // インメモリの認証を行うための設定
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("user").password(password).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}