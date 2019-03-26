package com.carhub.api.media.services.query

import java.util.UUID

import com.carhub.api.media.repositories.VideoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{PageRequest, Sort}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Autowired
@Transactional(readOnly = true)
@Service
class VideoQueryService(videoRepository : VideoRepository) {

  def getVideosPage(page : Int, size: Int, sortDirection : Sort.Direction, sort : String) =
    videoRepository.findAll(PageRequest.of(page, size, sortDirection, sort))

  def getVideosListAsc(page : Int, size: Int, sort : String) =
    getVideosPage(page, size, Sort.Direction.ASC, sort).getContent

  def getVideosListDesc(page : Int, size: Int, sort : String) =
    getVideosPage(page, size, Sort.Direction.DESC, sort).getContent

  def countAllVideos() = videoRepository.count

  def findVideoById(videoId : UUID) = videoRepository.findById(videoId).get

  def findVideosByName(name : String) = videoRepository.findVideosByName(name)

  def findVideosByExtension(format : String) = videoRepository.findVideosByExtension(format)
}
