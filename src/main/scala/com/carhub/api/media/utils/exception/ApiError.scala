package com.carhub.api.media.utils.exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.util.{Calendar, Date}

class ApiError {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  var time: Date = Calendar.getInstance().getTime()

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  var level : String = _

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  var httpStatus : HttpStatus = _

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
  var status : Int = _

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  var exception : String = _

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  var message : String = _

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  var component : String = _

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
  var line : Int = _


  def this(status: HttpStatus) {
    this
    this.httpStatus = status
    this.status = this.httpStatus.value
  }

  def this(status: HttpStatus, ex: Throwable) {
    this
    this.httpStatus = status
    this.status = this.httpStatus.value
    this.message = ex.getMessage
    this.exception = ex.getClass.getName
  }

  def this(status: HttpStatus, level: String, ex: Throwable) {
    this
    val stack : StackTraceElement = ex.getStackTrace().apply(0)
    this.component = stack.getClassName + "." + stack.getMethodName + " (" + stack.getFileName + ")"
    this.httpStatus = status
    this.status = this.httpStatus.value
    this.message = ex.getMessage
    this.exception = ex.getClass.getName
    this.level = level
    this.line = stack.getLineNumber
  }
}