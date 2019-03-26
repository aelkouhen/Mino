package com.carhub.api.media.domain

import javax.persistence._

@Entity
@Table(name = "file")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("FILE")
class File extends Resource with Serializable {}
