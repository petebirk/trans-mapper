package org.backbase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@ComponentScan(basePackages={"org.backbase"})
public class AppConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
  
  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
        .withUser("user")
        .password("user")
        .authorities("ROLE_USER");
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
        //.antMatchers("").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .csrf().disable();
  }
  
  
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
  
}
