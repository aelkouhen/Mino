package com.carhub.api.media.utils.exception

class ContentNotFoundException[A](val element : Class[A]) extends RuntimeException{
  override def getMessage: String = "No content of type " + element.getSimpleName + " was found"
}
