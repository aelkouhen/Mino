package com.carhub.api.media.services.command

import java.awt.image.BufferedImage
import java.util.{Calendar, Date, UUID}

import com.carhub.api.media.domain.Photo
import com.carhub.api.media.repositories.PhotoRepository
import com.google.common.io.Files
import javax.imageio.ImageIO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Autowired
@Transactional
@Service
class PhotoCommandService(photoRepository : PhotoRepository){

  def addPhoto(photo : MultipartFile) : Photo = {
    val photoMeta = new Photo
    photoMeta.size = photo.getSize
    photoMeta.name = Files.getNameWithoutExtension(photo.getOriginalFilename)
    photoMeta.format = Files.getFileExtension(photo.getOriginalFilename)
    photoMeta.content = photo.getBytes
    photoMeta.mimeType = photo.getContentType
    photoMeta.created = Calendar.getInstance().getTime()

    val bimg : BufferedImage = ImageIO.read(photo.getInputStream)
    photoMeta.width = bimg.getWidth
    photoMeta.height = bimg.getHeight

    addPhoto(photoMeta)
  }

  def addPhoto(photo : Photo) = photoRepository.save(photo)

  def updatePhoto(photoId : UUID, photo : Photo) = {
    val photoToUpdate = photoRepository.getOne(photoId)
    photoToUpdate.caption = photo.caption
    photoToUpdate.name = photo.name
    photoToUpdate.content = photo.content
    photoToUpdate.created = photo.created
    photoToUpdate.format = photo.format
    photoToUpdate.mimeType = photo.mimeType
    photoToUpdate.url = photo.url
    photoToUpdate.size = photo.size
    photoToUpdate.height = photo.height
    photoToUpdate.width = photo.width

    photoRepository.save(photoToUpdate)
  }

  def updatePhotoCaption(photoId : UUID, caption : String) = {
    val photoToUpdate = photoRepository.getOne(photoId)
    photoToUpdate.caption = caption

    photoRepository.save(photoToUpdate)
  }

  def updatePhotoContent(photoId : UUID, photo : MultipartFile) = {
    val photoToUpdate = photoRepository.getOne(photoId)
    photoToUpdate.content = photo.getBytes
    photoToUpdate.size = photo.getSize
    photoToUpdate.name = Files.getNameWithoutExtension(photo.getOriginalFilename)
    photoToUpdate.format = Files.getFileExtension(photo.getOriginalFilename)
    photoToUpdate.mimeType = photo.getContentType
    val bimg : BufferedImage = ImageIO.read(photo.getInputStream)
    photoToUpdate.width = bimg.getWidth
    photoToUpdate.height = bimg.getHeight

    photoRepository.save(photoToUpdate)
  }

  def updatePhotoCreationDate(photoId : UUID, date : Date) = {
    val photoToUpdate = photoRepository.getOne(photoId)
    photoToUpdate.created = date

    photoRepository.save(photoToUpdate)
  }

  def updatePhotoExtension(photoId : UUID, format : String) = {
    val photoToUpdate = photoRepository.getOne(photoId)
    photoToUpdate.format = format

    photoRepository.save(photoToUpdate)
  }

  def updatePhotoLink(photoId : UUID, url : String) = {
    val photoToUpdate = photoRepository.getOne(photoId)
    photoToUpdate.url = url

    photoRepository.save(photoToUpdate)
  }

  def deletePhoto(photoId : UUID) = {
    val photoToDelete = photoRepository.getOne(photoId)
    photoRepository.delete(photoToDelete)
  }
}
