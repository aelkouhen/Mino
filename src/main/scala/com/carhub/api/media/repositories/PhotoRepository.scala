package com.carhub.api.media.repositories

import java.util
import java.util.UUID

import com.carhub.api.media.domain.Photo
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
trait PhotoRepository extends JpaRepository[Photo, UUID] {

  @Query(value = "SELECT r.* FROM resource r where r.resource_type = 'PHOTO' AND r.name like CONCAT('%',:name,'%')", nativeQuery=true)
  def findPhotosByName(@Param("name") name : String) : util.List[Photo]

  @Query(value = "SELECT r.* FROM resource r where r.resource_type = 'PHOTO' AND r.format like CONCAT('%',:format,'%')", nativeQuery=true)
  def findPhotosByExtension(@Param("format") format : String) : util.List[Photo]
}
