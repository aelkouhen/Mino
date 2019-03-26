package com.carhub.api.media.controllers.command

import java.text.SimpleDateFormat
import java.util.{Locale, UUID}

import com.carhub.api.media.domain.File
import com.carhub.api.media.services.command.FileCommandService
import com.carhub.api.media.utils.exception.{ElementNotCreatedException, ElementNotUpdatedException}
import io.swagger.annotations.{Api, ApiOperation, ApiParam}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation._
import org.springframework.web.multipart.MultipartFile

@Api(value = "File", tags = Array("File Commands"), description = "This API commands the File concept.")
@RestController
@RequestMapping(value = Array("/v1"))
class FileCommandRestController(@Autowired val fileCommandService: FileCommandService) {

  @ApiOperation(value = "Create a file.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('CREATE_PRIVILEGE')")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(value = Array("/files"))
  def createFile(@ApiParam(name = "file", value = "A File object.", required = true) @RequestBody file: File): ResponseEntity[_] = {
    val created = fileCommandService.addFile(file)
    if(created == null) throw new ElementNotCreatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.CREATED).body(created)
  }

  @ApiOperation(value = "Upload a file.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @PostMapping(value = Array("/files/upload"))
  def uploadFile(@ApiParam(name = "file", value = "A Multipart file.", required = true) @RequestParam(name = "file") file : MultipartFile): ResponseEntity[_] = {
    val created = fileCommandService.addFile(file)
    if(created == null) throw new ElementNotCreatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.CREATED).body(created)
  }

  @ApiOperation(value = "Update a file.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PutMapping(value = Array("/files/{id}"))
  def updateFile(@ApiParam(name = "id", value = "The File's ID.", required = true) @PathVariable(value = "id") id : String, @RequestBody file: File): ResponseEntity[_] = {
    val updated = fileCommandService.updateFile(UUID.fromString(id), file)
    if(updated == null) throw new ElementNotUpdatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a File's caption.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/files/{id}"), params = Array("caption"))
  def updateFileCaption(@ApiParam(name = "id", value = "The File's ID.", required = true) @PathVariable(value = "id") id : String, @ApiParam(name = "caption", value = "The file's description.", required = true) @RequestParam(name = "caption") caption : String) = {
    val updated = fileCommandService.updateFileCaption(UUID.fromString(id), caption)
    if(updated == null) throw new ElementNotUpdatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a File's content.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/files/{id}/content"))
  def updateFileContent(@ApiParam(name = "id", value = "The File's ID.", required = true) @PathVariable(value = "id") id : String, @ApiParam(name = "file", value = "The file's content.", required = true) @RequestParam(name = "file") file : MultipartFile) = {
    val updated = fileCommandService.updateFileContent(UUID.fromString(id), file)
    if(updated == null) throw new ElementNotUpdatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a File's creation date.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/files/{id}"), params = Array("date"))
  def updateFileCreationDate(@ApiParam(name = "id", value = "The File's ID.", required = true) @PathVariable(value = "id") id : String, @ApiParam(name = "date", value = "The file's creation date in the (dd/MM/yyyy) format.", required = true) @RequestParam(name = "date") date : String) = {
    val updated = fileCommandService.updateFileCreationDate(UUID.fromString(id), new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(date))
    if(updated == null) throw new ElementNotUpdatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a File's format.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/files/{id}"), params = Array("format"))
  def updateFileExtension(@ApiParam(name = "id", value = "The File's ID.", required = true) @PathVariable(value = "id") id : String, @ApiParam(name = "format", value = "The file's format.", required = true) @RequestParam(name = "format") format : String) = {
    val updated = fileCommandService.updateFileExtension(UUID.fromString(id), format)
    if(updated == null) throw new ElementNotUpdatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a File's URL.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/files/{id}"), params = Array("url"))
  def updateFileLink(@ApiParam(name = "id", value = "The File's ID.", required = true) @PathVariable(value = "id") id : String, @ApiParam(name = "url", value = "The file's URL.", required = true) @RequestParam(name = "url") url : String) = {
    val updated = fileCommandService.updateFileLink(UUID.fromString(id), url)
    if(updated == null) throw new ElementNotUpdatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Update a File's format.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('UPDATE_PRIVILEGE')")
  @ResponseBody
  @PatchMapping(value = Array("/files/{id}"), params = Array("size"))
  def updateFileSize(@ApiParam(name = "id", value = "The File's ID.", required = true) @PathVariable(value = "id") id : String, @ApiParam(name = "size", value = "The file's URL.", required = true, example = "0") @RequestParam(name = "size") size : Long) = {
    val updated = fileCommandService.updateFileSize(UUID.fromString(id), size)
    if(updated == null) throw new ElementNotUpdatedException[File](classOf[File])
    ResponseEntity.status(HttpStatus.OK).body(updated)
  }

  @ApiOperation(value = "Delete the File.", response = classOf[File])
  @PreAuthorize("#oauth2.hasScope('DELETE_PRIVILEGE')")
  @ResponseBody
  @DeleteMapping(Array("/files/{id}"))
  def deleteFile(@ApiParam(name = "id", value = "The File ID.", required = true) @PathVariable(value = "id") fileId : String) = {
    fileCommandService.deleteFile(UUID.fromString(fileId))
    ResponseEntity.status(HttpStatus.NO_CONTENT).build()
  }
}
