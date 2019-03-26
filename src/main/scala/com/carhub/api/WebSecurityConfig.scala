package com.carhub.api

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  override def configure(http: HttpSecurity): Unit = {
    http
      .csrf().disable()
      .antMatcher("/**") // this will apply to the entire web server
      .authorizeRequests()
      .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/**").permitAll() // this should allow swagger to operate
  }
}

