package com.carhub.api.media.controllers.query

import java.util
import java.util.UUID

import com.carhub.api.media.domain.Video
import com.carhub.api.media.services.query.VideoQueryService
import com.carhub.api.media.utils.exception.{ContentNotFoundException, ElementNotFoundException}
import io.swagger.annotations.{Api, ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, ResponseEntity}
import org.springframework.web.bind.annotation._

@Api(value = "Video", tags = Array("Video Queries"), description = "This API queries the Video concept.")
@RestController
@RequestMapping(value = Array("/v1"))
class VideoQueryRestController(@Autowired val videoQueryService : VideoQueryService) {

  @ApiOperation(value = "List the Videos : Retrieve the Videos list paged and sorted by field.", notes = "It takes the page number, a size for each page, a sorting order and the field on which the list is sorted.", response = classOf[util.List[Video]], responseContainer = "List")
  @GetMapping(value = Array("/videos"))
  @ResponseBody
  def getVideosList(@ApiParam(name = "page", example="0", value = "The page number.", required = true) @RequestParam page : Int,
                    @ApiParam(name = "size", example="10", value = "The size of the page.", required = true) @RequestParam size : Int,
                    @ApiParam(name = "order", example="DESC", value = "The sorting order (DESC or ASC).", required = true) @RequestParam(name = "order") sortDirection : String,
                    @ApiParam(name = "field", example="name", value = "The sort field name.", required = true) @RequestParam(name = "field") sort : String ) : ResponseEntity[_] =
  {
    var result : util.List[Video] = new util.ArrayList[Video]
    sortDirection.toLowerCase match {
      case "asc" => result = videoQueryService.getVideosListAsc(page, size, sort)
      case _ => result = videoQueryService.getVideosListDesc(page, size, sort)
    }
    if (result == null || result.isEmpty) throw new ElementNotFoundException[Video](classOf[Video])
    ResponseEntity.ok(result)
  }

  @ApiOperation(value = "Count the Videos.", response = classOf[Long], responseContainer = "Long")
  @GetMapping(value = Array("/videos/count"))
  @ResponseBody
  def countAllVideos() : Long = videoQueryService.countAllVideos

  @ApiOperation(value = "Filter Videos by name", response = classOf[util.List[Video]], responseContainer = "List")
  @GetMapping(value = Array("/videos/find"), params = Array("name"))
  @ResponseBody
  def findVideosByName(@ApiParam(name = "name", value = "The filtering expression.", required = true) @RequestParam name : String) : ResponseEntity[_]  = {
    val result = videoQueryService.findVideosByName(name)
    if (result == null || result.isEmpty) throw new ElementNotFoundException[Video](classOf[Video])
    ResponseEntity.ok(result)
  }

  @ApiOperation(value = "Filter Videos by ID", response = classOf[Video])
  @GetMapping(value = Array("/videos/find"), params = Array("id"))
  @ResponseBody
  def findVideoById(@ApiParam(name = "id", value = "The Video ID.", required = true) @RequestParam(name = "id") videoId : String) : ResponseEntity[_]  = {
    val result = videoQueryService.findVideoById(UUID.fromString(videoId))
    if (result == null) throw new ElementNotFoundException[Video](classOf[Video])
    ResponseEntity.ok(result)
  }

  @ApiOperation(value = "Filter Video content by ID", response = classOf[Video])
  @GetMapping(value = Array("/videos/content/{id}"))
  @ResponseBody
  def findVideoContentById(@ApiParam(name = "id", value = "The Video ID.", required = true) @PathVariable(name = "id") videoId : String) : ResponseEntity[_]  = {
    val result = videoQueryService.findVideoById(UUID.fromString(videoId))
    if (result == null) throw new ElementNotFoundException[Video](classOf[Video])
    if (result.content == null || result.content.isEmpty) throw new ContentNotFoundException[Video](classOf[Video])
    ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
      "attachment; filename=\"" + result.name + "." + result.format + "\"").body(result.content)
  }

  @ApiOperation(value = "Filter Videos by format", response = classOf[util.List[Video]], responseContainer = "List")
  @GetMapping(value = Array("/videos/find"), params = Array("format"))
  @ResponseBody
  def findVideosByExtension(@ApiParam(name = "format", value = "The filtering expression.", required = true) @RequestParam format : String) : ResponseEntity[_]  = {
    val result = videoQueryService.findVideosByExtension(format)
    if (result == null || result.isEmpty) throw new ElementNotFoundException[Video](classOf[Video])
    ResponseEntity.ok(result)
  }
}
