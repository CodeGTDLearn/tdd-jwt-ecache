package com.wallet.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wallet.security.JwtAuthenticationEntryPoint;
import com.wallet.security.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//todo: SpringSecurity+Jwt 6 - Implementar WebSecurityConfigurerAdapter
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //todo: SpringSecurity+Jwt 6.1 - Injetar
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    //todo: SpringSecurity+Jwt 6.2 - Injetar
    @Autowired
    private UserDetailsService userDetailsService;

    //todo: SpringSecurity+Jwt 6.3 - Fornecer o 'userDetailsService' injetado acima
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    //todo: SpringSecurity+Jwt 6.4 - Definir este bean AUTHENTICATION_MANAGER
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //todo: SpringSecurity+Jwt 6.5 - Definir este bean BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //todo: SpringSecurity+Jwt 7 - Bloqueat rotas
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                //todo: SpringSecurity+Jwt 7.1 - Rotas liberadas(authorizeRequests)
                .authorizeRequests().antMatchers(
                "/auth/**" ,
                "/configuration/security" ,
                "/webjars/**" ,
                "/user/**" ,
                //Swagger routes
                "/v2/api-docs" ,
                "/swagger-resources/**" ,
                "/swagger-ui.html" ,
                "/hello-world")

                //todo: SpringSecurity+Jwt 7.2 - Rotas bloqueadas(TODAS AS OUTRAS)
                .permitAll().anyRequest().authenticated();

        http.addFilterBefore(
                authenticationTokenFilterBean() ,
                UsernamePasswordAuthenticationFilter.class);

        //todo: SpringSecurity+Jwt 7.3 - Controle de cache em HEADER)
        http.headers().cacheControl();
    }
}
