package com.carhub.api.media.domain

import javax.persistence._
import scala.beans.BeanProperty

@Entity
@Table(name = "video")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("VIDEO")
class Video extends Resource with Serializable {

  @BeanProperty
  var (width, height) = (0, 0)

  @BeanProperty
  var definition: String = _
}