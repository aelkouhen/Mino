package com.carhub.api.media.services.query

import java.util.UUID

import com.carhub.api.media.repositories.ResourceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{PageRequest, Sort}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Autowired
@Transactional(readOnly = true)
@Service
class ResourceQueryService(resourceRepository : ResourceRepository) {

  def getResourcePage(page : Int, size: Int, sortDirection : Sort.Direction, sort : String) =
    resourceRepository.findAll(PageRequest.of(page, size, sortDirection, sort))

  def getResourceListAsc(page : Int, size: Int, sort : String) =
    getResourcePage(page, size, Sort.Direction.ASC, sort).getContent

  def getResourceListDesc(page : Int, size: Int, sort : String) =
    getResourcePage(page, size, Sort.Direction.DESC, sort).getContent

  def countAllResources() = resourceRepository.count

  def findResourceById(resourceId : UUID) = resourceRepository.findById(resourceId).get

  def findResourcesByName(name : String) = resourceRepository.findResourcesByName(name)

  def findResourcesByExtension(ext : String) = resourceRepository.findResourcesByExtension(ext)
}
