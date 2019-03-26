package com.carhub.api.media.repositories

import java.util
import java.util.UUID

import com.carhub.api.media.domain.Video
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
trait VideoRepository extends JpaRepository[Video, UUID] {

  @Query(value = "SELECT r.* FROM resource r where r.resource_type = 'VIDEO' AND r.name like CONCAT('%',:name,'%')", nativeQuery=true)
  def findVideosByName(@Param("name") name : String) : util.List[Video]

  @Query(value = "SELECT r.* FROM resource r where r.resource_type = 'VIDEO' AND r.format like CONCAT('%',:format,'%')", nativeQuery=true)
  def findVideosByExtension(@Param("format") format : String) : util.List[Video]
}
