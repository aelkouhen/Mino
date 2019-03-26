package com.carhub.api

import java.util
import java.util.Collections

import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.{ApiInfoBuilder, RequestHandlerSelectors}
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import com.google.common.collect.ImmutableList
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import springfox.documentation.service.{ApiDescription, ApiInfo, AuthorizationScope}
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.ApiListingBuilderPlugin
import springfox.documentation.spi.service.contexts.ApiListingContext
import springfox.documentation.swagger.common.SwaggerPluginSupport
import springfox.documentation.builders.PathSelectors
import springfox.documentation.spi.service.contexts.SecurityContext
import com.google.common.collect.Lists
import springfox.documentation.service.SecurityReference


@Configuration
@EnableSwagger2
class SwaggerConfig {

  @Value("${swagger.api.version}")
  private val apiVersion: String = None.orNull

  val AUTHORIZATION_HEADER = "Authorization"
  val DEFAULT_INCLUDE_PATTERN = "/v1/.*"

  @Bean
  def api: Docket = new Docket(DocumentationType.SWAGGER_2)
                      .select
                      .apis(RequestHandlerSelectors.basePackage("com.carhub.api.media.controllers"))
                      .paths(PathSelectors.any)
                      .build
                      .securityContexts(Collections.singletonList(securityContext))
                      .securitySchemes(Collections.singletonList(apiKey))
                      .apiInfo(getInfos)

  import springfox.documentation.service.ApiKey

  def apiKey = new ApiKey("JWT", AUTHORIZATION_HEADER, "header")

  def securityContext = SecurityContext.builder.securityReferences(defaultAuth).forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build

  def defaultAuth: util.List[SecurityReference] = {
    val authorizationScope = new AuthorizationScope("global", "accessEverything")
    val authorizationScopes = new Array[AuthorizationScope](1)
    authorizationScopes(0) = authorizationScope
    Lists.newArrayList(new SecurityReference("JWT", authorizationScopes))
  }

  @Bean
  @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
  def getApiPathEnrichPlugin: ApiListingBuilderPlugin = new ApiListingBuilderPlugin() {
    override def supports(delimiter: DocumentationType) = true

    override def apply(apiListingContext: ApiListingContext): Unit = {
      var apis = apiListingContext.apiListingBuilder.build.getApis
      val builder = ImmutableList.builder[ApiDescription]()
      if (apis != null) {
        apis.forEach(api => builder.add(new ApiDescription(api.getGroupName.get, api.getPath + "?apiDescription=" + api.getDescription, api.getDescription, api.getOperations, api.isHidden)))
        apis = builder.build
        apiListingContext.apiListingBuilder.apis(apis)
      }
    }
  }

  private def getInfos: ApiInfo = {
    new ApiInfoBuilder().
      title("Mino API").
      description(swaggerAPIDescription).
      version(apiVersion).build()
  }

  private val swaggerAPIDescription =
    s"""
       |Mino Api allow you to create / access to the Media concepts.
       |It represents various type of vehicles like (Photos, Videos, Files and so on ...).
       |
       |
       |They are represented with the following concepts:
       |  - Resource : Type of an attached resource (could be a file, a photo or a video).
       |  - File : Type of an attached file.
       |  - Photo : Type of an attached photo.
       |  - Video : Type of an attached video.
       |
       |
       |The Api follows the CQRS (Command-Query Responsibility Segregation) patterns and thus is composed of 2 parts :
       |  - Commands: Create, Update, Delete the main concepts.
       |  - Queries: to retrieve the concepts data.
       |
       |
       |How to use the API :
       |  - Run the Eureka discovery server.
       |  - Get an OAuth2 Access Token with Authentication Service (https://github.com/aelkouhen/Mima)
       |  - Calling the API with the paths bellow:
     """.stripMargin
}