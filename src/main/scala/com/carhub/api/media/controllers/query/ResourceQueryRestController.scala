package com.carhub.api.media.controllers.query

import java.util
import java.util.UUID

import com.carhub.api.media.domain.Resource
import com.carhub.api.media.services.query.ResourceQueryService
import com.carhub.api.media.utils.exception.{ContentNotFoundException, ElementNotFoundException}
import io.swagger.annotations.{Api, ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, ResponseEntity}
import org.springframework.web.bind.annotation._

@Api(value = "Resource", tags = Array("Resource Queries"), description = "This API queries the Resource concept.")
@RestController
@RequestMapping(value = Array("/v1"))
class ResourceQueryRestController(@Autowired val resourceQueryService : ResourceQueryService) {

  @ApiOperation(value = "List the Resources : Retrieve the Resources list paged and sorted by field.", notes = "It takes the page number, a size for each page, a sorting order and the field on which the list is sorted.", response = classOf[util.List[Resource]], responseContainer = "List")
  @GetMapping(value = Array("/resources"))
  @ResponseBody
  def getResourcesList(@ApiParam(name = "page", example="0", value = "The page number.", required = true) @RequestParam page : Int,
                   @ApiParam(name = "size", example="10", value = "The size of the page.", required = true) @RequestParam size : Int,
                   @ApiParam(name = "order", example="DESC", value = "The sorting order (DESC or ASC).", required = true) @RequestParam(name = "order") sortDirection : String,
                   @ApiParam(name = "field", example="name", value = "The sort field name.", required = true) @RequestParam(name = "field") sort : String ) : ResponseEntity[_] =
  {
    var result : util.List[Resource] = new util.ArrayList[Resource]
    sortDirection.toLowerCase match {
      case "asc" => result = resourceQueryService.getResourceListAsc(page, size, sort)
      case _ => result = resourceQueryService.getResourceListDesc(page, size, sort)
    }
    if (result == null || result.isEmpty) throw new ElementNotFoundException[Resource](classOf[Resource])
    ResponseEntity.ok(result)
  }

  @ApiOperation(value = "Count the Resources.", response = classOf[Long], responseContainer = "Long")
  @GetMapping(value = Array("/resources/count"))
  @ResponseBody
  def countAllResources() : Long = resourceQueryService.countAllResources()

  @ApiOperation(value = "Filter Resources by name", response = classOf[util.List[Resource]], responseContainer = "List")
  @GetMapping(value = Array("/resources/find"), params = Array("name"))
  @ResponseBody
  def findResourceByName(@ApiParam(name = "name", value = "The filtering expression.", required = true) @RequestParam name : String) : ResponseEntity[_]  = {
    val result = resourceQueryService.findResourcesByName(name)
    if (result == null || result.isEmpty) throw new ElementNotFoundException(classOf[Resource])
    ResponseEntity.ok(result)
  }

  @ApiOperation(value = "Filter Resources by ID", response = classOf[Resource])
  @GetMapping(value = Array("/resources/find"), params = Array("id"))
  @ResponseBody
  def findResourceById(@ApiParam(name = "id", value = "The Resource ID.", required = true) @RequestParam(name = "id") resourceId : String) : ResponseEntity[_]  = {
    val result = resourceQueryService.findResourceById(UUID.fromString(resourceId))
    if (result == null) throw new ElementNotFoundException[Resource](classOf[Resource])
    ResponseEntity.ok(result)
  }

  @ApiOperation(value = "Filter Resource content by ID", response = classOf[Array[Byte]])
  @GetMapping(value = Array("/resources/content/{id}"))
  @ResponseBody
  def findResourceContentById(@ApiParam(name = "id", value = "The Resource ID.", required = true) @PathVariable(name = "id") resourceId : String) : ResponseEntity[_]  = {
    val result = resourceQueryService.findResourceById(UUID.fromString(resourceId))
    if (result == null) throw new ElementNotFoundException[Resource](classOf[Resource])
    if (result.content == null || result.content.isEmpty) throw new ContentNotFoundException[Resource](classOf[Resource])
    ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
      "attachment; filename=\"" + result.name + "." + result.format + "\"").body(result.content)
  }

  @ApiOperation(value = "Filter Resources by format", response = classOf[util.List[Resource]], responseContainer = "List")
  @GetMapping(value = Array("/resources/find"), params = Array("format"))
  @ResponseBody
  def findResourceByExtension(@ApiParam(name = "format", value = "The filtering expression.", required = true) @RequestParam format : String) : ResponseEntity[_]  = {
    val result = resourceQueryService.findResourcesByExtension(format)
    if (result == null || result.isEmpty) throw new ElementNotFoundException[Resource](classOf[Resource])
    ResponseEntity.ok(result)
  }
}
