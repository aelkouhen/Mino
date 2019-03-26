package com.carhub.api.media.repositories

import java.util
import java.util.UUID

import com.carhub.api.media.domain.File
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
trait FileRepository extends JpaRepository[File, UUID] {

  @Query(value = "SELECT r.* FROM resource r where r.resource_type = 'FILE' AND r.name like CONCAT('%',:name,'%')", nativeQuery=true)
  def findFilesByName(@Param("name") name : String) : util.List[File]

  @Query(value = "SELECT r.* FROM resource r where r.resource_type = 'FILE' AND r.format like CONCAT('%',:format,'%')", nativeQuery=true)
  def findFilesByExtension(@Param("format") format : String) : util.List[File]
}
