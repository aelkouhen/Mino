package com.carhub.api.media.controllers.command

import java.text.SimpleDateFormat
import java.util.{Locale, UUID}

import com.carhub.api.media.domain.Video
import com.carhub.api.media.services.command.VideoCommandService
import com.carhub.api.media.utils.exception.{ElementNotCreatedException, ElementNotUpdatedException}
import io.swagger.annotations.{Api, ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation._
import org.springframework.web.multipart.MultipartFile

@Api(value = "Video", tags = Array("Video Commands"), description = "This API commands the Video concept.")
@RestController
@RequestMapping(value = Array("/v1"))
class VideoCommandRestController(@Autowired val videoCommandService: VideoCommandService) {

  @ApiOperation(value = "Create a video.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('CREATE_PRIVILEGE')")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(value = Array("/videos"))
  def createVideo(@ApiParam(name = "video", value = "A Video object.", required = true) @RequestBody video: Video): ResponseEntity[_] = {
    val created = videoCommandService.addVideo(video)
    if(created == null) throw new ElementNotCreatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.CREATED).body(created)
  }

  @ApiOperation(value = "Upload a video.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(value = Array("/videos/upload"))
  def uploadVideo(@ApiParam(name = "video", value = "A Multipart video.", required = true) @RequestParam(name = "video") video : MultipartFile): ResponseEntity[_] = {
    val created = videoCommandService.addVideo(video)
    if(created == null) throw new ElementNotCreatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.CREATED).body(created)
  }

  @ApiOperation(value = "Update a video.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PutMapping(value = Array("/videos/{id}"))
  def updateVideo(@ApiParam(name = "id", value = "The Video's ID.", required = true) @PathVariable(value = "id") videoId : String, @RequestBody video: Video): ResponseEntity[_] = {
    val updated = videoCommandService.updateVideo(UUID.fromString(videoId), video)
    if(updated == null) throw new ElementNotUpdatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a Video's caption.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/videos/{id}"), params = Array("caption"))
  def updateVideoCaption(@ApiParam(name = "id", value = "The Video's ID.", required = true) @PathVariable(value = "id") videoId : String, @ApiParam(name = "caption", value = "The video's description.", required = true) @RequestParam(name = "caption") caption : String) = {
    val updated = videoCommandService.updateVideoCaption(UUID.fromString(videoId), caption)
    if(updated == null) throw new ElementNotUpdatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a Video's content.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/videos/{id}/content"))
  def updateVideoContent(@ApiParam(name = "id", value = "The Video's ID.", required = true) @PathVariable(value = "id") videoId : String, @ApiParam(name = "video", value = "The video's content.", required = true) @RequestParam(name = "video") video : MultipartFile) = {
    val updated = videoCommandService.updateVideoContent(UUID.fromString(videoId), video)
    if(updated == null) throw new ElementNotUpdatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a Video's creation date.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/videos/{id}"), params = Array("date"))
  def updateVideoCreationDate(@ApiParam(name = "id", value = "The Video's ID.", required = true) @PathVariable(value = "id") videoId : String, @ApiParam(name = "date", value = "The video's creation date in the (dd/MM/yyyy) format.", required = true) @RequestParam(name = "date") date : String) = {
    val updated = videoCommandService.updateVideoCreationDate(UUID.fromString(videoId), new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(date))
    if(updated == null) throw new ElementNotUpdatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a Video's format.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/videos/{id}"), params = Array("format"))
  def updateVideoFormat(@ApiParam(name = "id", value = "The Video's ID.", required = true) @PathVariable(value = "id") videoId : String, @ApiParam(name = "format", value = "The video's format.", required = true) @RequestParam(name = "format") format : String) = {
    val updated = videoCommandService.updateVideoExtension(UUID.fromString(videoId), format)
    if(updated == null) throw new ElementNotUpdatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a Video's URL.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/videos/{id}"), params = Array("url"))
  def updateVideoLink(@ApiParam(name = "id", value = "The Video's ID.", required = true) @PathVariable(value = "id") videoId : String, @ApiParam(name = "url", value = "The video's URL.", required = true) @RequestParam(name = "url") url : String) = {
    val updated = videoCommandService.updateVideoLink(UUID.fromString(videoId), url)
    if(updated == null) throw new ElementNotUpdatedException[Video](classOf[Video])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Delete the Video.", response = classOf[Video])
  @PreAuthorize("#oauth2.hasScope('DELETE_PRIVILEGE')")
  @ResponseBody
  @DeleteMapping(Array("/videos/{id}"))
  def deleteVideo(@ApiParam(name = "id", value = "The Video ID.", required = true) @PathVariable(value = "id") videoId : String) = {
    videoCommandService.deleteVideo(UUID.fromString(videoId))
    ResponseEntity.status(HttpStatus.NO_CONTENT).build()
  }
}
