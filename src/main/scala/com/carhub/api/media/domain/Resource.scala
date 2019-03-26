package com.carhub.api.media.domain

import java.io.Serializable
import java.util.{Date, UUID}

import javax.persistence._
import org.hibernate.annotations.{GenericGenerator, Type}
import org.hibernate.validator.constraints.URL

import scala.beans.BeanProperty

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="resource_type")
abstract class Resource extends Serializable {

  @Id
  @BeanProperty
  @Column(name = "ID")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(`type` = "uuid-char")
  var id: UUID = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var caption: String = _

  @BeanProperty
  var size: Long = _

  @BeanProperty
  var mimeType: String = _

  @BeanProperty
  var format: String = _

  @BeanProperty
  @Column(name = "CREATION_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  var created: Date = _

  @BeanProperty
  @URL
  var url: String = _

  @Lob
  @BeanProperty
  var content: Array[Byte] = _
}
