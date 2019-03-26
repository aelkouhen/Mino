package com.carhub.api.media.services.command

import java.util.{Calendar, Date, UUID}

import com.carhub.api.media.domain.File
import com.carhub.api.media.repositories.FileRepository
import com.google.common.io.Files
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Autowired
@Transactional
@Service
class FileCommandService(fileRepository : FileRepository) {

  def addFile(file : MultipartFile) : File = {
    val fileMeta = new File
    fileMeta.size = file.getSize
    fileMeta.name = Files.getNameWithoutExtension(file.getOriginalFilename)
    fileMeta.format = Files.getFileExtension(file.getOriginalFilename)
    fileMeta.content = file.getBytes
    fileMeta.mimeType = file.getContentType
    fileMeta.created = Calendar.getInstance().getTime()
    addFile(fileMeta)
  }

  def addFile(file : File) : File = fileRepository.save(file)

  def updateFile(fileId : UUID, file : File) = {
    val fileToUpdate = fileRepository.getOne(fileId)
    fileToUpdate.caption = file.caption
    fileToUpdate.content = file.content
    fileToUpdate.created = file.created
    fileToUpdate.format = file.format
    fileToUpdate.mimeType = file.mimeType
    fileToUpdate.url = file.url
    fileToUpdate.size = file.size

    fileRepository.save(fileToUpdate)
  }

  def updateFileCaption(fileId : UUID, caption : String) = {
    val fileToUpdate = fileRepository.getOne(fileId)
    fileToUpdate.caption = caption

    fileRepository.save(fileToUpdate)
  }

  def updateFileContent(fileId : UUID, file : MultipartFile) = {
    val fileToUpdate = fileRepository.getOne(fileId)
    fileToUpdate.content = file.getBytes
    fileToUpdate.size = file.getSize
    fileToUpdate.name = Files.getNameWithoutExtension(file.getOriginalFilename)
    fileToUpdate.format = Files.getFileExtension(file.getOriginalFilename)
    fileToUpdate.mimeType = file.getContentType

    fileRepository.save(fileToUpdate)
  }

  def updateFileCreationDate(fileId : UUID, date : Date) = {
    val fileToUpdate = fileRepository.getOne(fileId)
    fileToUpdate.created = date

    fileRepository.save(fileToUpdate)
  }

  def updateFileExtension(fileId : UUID, format : String) = {
    val fileToUpdate = fileRepository.getOne(fileId)
    fileToUpdate.format = format

    fileRepository.save(fileToUpdate)
  }

  def updateFileLink(fileId : UUID, url : String) = {
    val fileToUpdate = fileRepository.getOne(fileId)
    fileToUpdate.url = url

    fileRepository.save(fileToUpdate)
  }

  def updateFileSize(fileId : UUID, size : Long) = {
    val fileToUpdate = fileRepository.getOne(fileId)
    fileToUpdate.size = size

    fileRepository.save(fileToUpdate)
  }

  def deleteFile(fileId : UUID) = {
    val fileToDelete = fileRepository.getOne(fileId)
    fileRepository.delete(fileToDelete)
  }
}
