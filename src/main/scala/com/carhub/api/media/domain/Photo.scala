package com.carhub.api.media.domain

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "photo")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PHOTO")
class Photo extends Resource with Serializable {

  @BeanProperty
  var (width, height) = (0, 0)
}
