package com.carhub.api.media.services.command

import java.awt.image.BufferedImage
import java.util.{Calendar, Date, UUID}

import com.carhub.api.media.domain.Video
import com.carhub.api.media.repositories.VideoRepository
import com.google.common.io.Files
import javax.imageio.ImageIO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Autowired
@Transactional
@Service
class VideoCommandService(videoRepository : VideoRepository) {

  def addVideo(video : MultipartFile) : Video = {
    val videoMeta = new Video
    videoMeta.size = video.getSize
    videoMeta.name = Files.getNameWithoutExtension(video.getOriginalFilename)
    videoMeta.format = Files.getFileExtension(video.getOriginalFilename)
    videoMeta.content = video.getBytes
    videoMeta.created = Calendar.getInstance().getTime()
    videoMeta.mimeType = video.getContentType

    val bimg : BufferedImage = ImageIO.read(video.getInputStream)
    videoMeta.width = bimg.getWidth
    videoMeta.height = bimg.getHeight

    addVideo(videoMeta)
  }

  def addVideo(video : Video) = videoRepository.save(video)

  def updateVideo(videoId : UUID, video: Video) = {
    val videoToUpdate = videoRepository.getOne(videoId)
    videoToUpdate.caption = video.caption
    videoToUpdate.content = video.content
    videoToUpdate.created = video.created
    videoToUpdate.format = video.format
    videoToUpdate.mimeType = video.mimeType
    videoToUpdate.url = video.url
    videoToUpdate.size = video.size
    videoToUpdate.height = video.height
    videoToUpdate.width = video.width

    videoRepository.save(videoToUpdate)
  }

  def updateVideoCaption(videoId : UUID, caption : String) = {
    val videoToUpdate = videoRepository.getOne(videoId)
    videoToUpdate.caption = caption

    videoRepository.save(videoToUpdate)
  }

  def updateVideoContent(videoId : UUID, file : MultipartFile) = {
    val videoToUpdate = videoRepository.getOne(videoId)
    videoToUpdate.content = file.getBytes
    videoToUpdate.size = file.getSize
    videoToUpdate.mimeType = file.getContentType
    videoToUpdate.name = Files.getNameWithoutExtension(file.getOriginalFilename)
    videoToUpdate.format = Files.getFileExtension(file.getOriginalFilename)
    val bimg : BufferedImage = ImageIO.read(file.getInputStream)
    videoToUpdate.width = bimg.getWidth
    videoToUpdate.height = bimg.getHeight

    videoRepository.save(videoToUpdate)
  }

  def updateVideoCreationDate(videoId : UUID, date : Date) = {
    val videoToUpdate = videoRepository.getOne(videoId)
    videoToUpdate.created = date

    videoRepository.save(videoToUpdate)
  }

  def updateVideoExtension(videoId : UUID, format : String) = {
    val videoToUpdate = videoRepository.getOne(videoId)
    videoToUpdate.format = format

    videoRepository.save(videoToUpdate)
  }

  def updateVideoLink(videoId : UUID, url : String) = {
    val videoToUpdate = videoRepository.getOne(videoId)
    videoToUpdate.url = url

    videoRepository.save(videoToUpdate)
  }

  def deleteVideo(videoId : UUID) = {
    val videoToDelete = videoRepository.getOne(videoId)
    videoRepository.delete(videoToDelete)
  }
}
