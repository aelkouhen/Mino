package com.carhub.api

import javax.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.{EnableResourceServer, ResourceServerConfigurerAdapter}
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.jwt.crypto.sign.RsaVerifier
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.provider.token.RemoteTokenServices
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ResourceServerConfig extends ResourceServerConfigurerAdapter{

  @Value("${security.oauth2.resource.id}")
  val resourceId : String = null

  @Value("${security.oauth2.resource.jwt.key-value}")
  val publicKey : String = null

  @Autowired
  val loadBalancer : LoadBalancerClient = null

  @Value("${oauth2.token-check-path}")
  val checkPath : String = null

  @Value("${oauth2.service.name}")
  val authServiceName : String = null

  @Value("${security.oauth2.resource.client.client-id}")
  val clientId : String = null

  @Value("${security.oauth2.resource.client.client-secret}")
  val clientSecret : String = null

  @Value("${oauth2.auth-server-uri}")
  val authServer : String = null

  override def configure(http: HttpSecurity): Unit = {
    http.authorizeRequests().anyRequest().permitAll().and().cors().disable().csrf().disable().httpBasic().disable()
      .exceptionHandling()
      .authenticationEntryPoint(
        (request, response, authException) => response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
      .accessDeniedHandler(
        (request, response, authException) => response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
  }

  override def configure(resources: ResourceServerSecurityConfigurer): Unit = {
    resources
            .resourceId(resourceId)
            .tokenServices(tokenServices)
  }

  @Bean
  def accessTokenConverter(): JwtAccessTokenConverter = {
    val converter = new JwtAccessTokenConverter
    converter.setVerifier(new RsaVerifier(publicKey))
    converter
  }

  @Primary
  @Bean
  def tokenServices(): RemoteTokenServices = {
    val tokenService = new RemoteTokenServices
    val authService : ServiceInstance = loadBalancer.choose(authServiceName)
    var tokenCheckEndpoint : String = ""

    if(authService == null)
      tokenCheckEndpoint = authServer + checkPath
    else
      tokenCheckEndpoint = authService.getUri + checkPath

    tokenService.setCheckTokenEndpointUrl(tokenCheckEndpoint)
    tokenService.setAccessTokenConverter(accessTokenConverter)
    tokenService.setClientId(clientId)
    tokenService.setClientSecret(clientSecret)
    tokenService
  }
}
