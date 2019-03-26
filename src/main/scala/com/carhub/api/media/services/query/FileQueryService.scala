package com.carhub.api.media.services.query

import java.util.UUID

import com.carhub.api.media.repositories.FileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{PageRequest, Sort}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Autowired
@Transactional(readOnly = true)
@Service
class FileQueryService(fileRepository : FileRepository) {

  def getFilesPage(page : Int, size: Int, sortDirection : Sort.Direction, sort : String) =
    fileRepository.findAll(PageRequest.of(page, size, sortDirection, sort))

  def getFilesListAsc(page : Int, size: Int, sort : String) =
    getFilesPage(page, size, Sort.Direction.ASC, sort).getContent

  def getFilesListDesc(page : Int, size: Int, sort : String) =
    getFilesPage(page, size, Sort.Direction.DESC, sort).getContent

  def countAllFiles() = fileRepository.count

  def findFilesByName(name : String) = fileRepository.findFilesByName(name)

  def findFileById(fileId : UUID) = fileRepository.findById(fileId).get

  def findFilesByExtension(format : String) = fileRepository.findFilesByExtension(format)
}
