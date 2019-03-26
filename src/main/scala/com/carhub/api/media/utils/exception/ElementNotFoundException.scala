package com.carhub.api.media.utils.exception

class ElementNotFoundException[A](val element : Class[A]) extends RuntimeException{
  override def getMessage: String = "No element of type " + element.getSimpleName + " was found"
}
