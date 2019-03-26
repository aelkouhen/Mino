package com.carhub.api.media.utils.exception

import org.hibernate.TypeMismatchException
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.core.annotation.Order
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.http.converter.{HttpMessageNotReadableException, HttpMessageNotWritableException}
import org.springframework.validation.BindException
import org.springframework.web.bind.{MethodArgumentNotValidException, MissingPathVariableException, MissingServletRequestParameterException, ServletRequestBindingException}
import org.springframework.web.{HttpMediaTypeNotAcceptableException, HttpMediaTypeNotSupportedException, HttpRequestMethodNotSupportedException}
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler}
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException


@Order(0)
@ControllerAdvice
class ApiErrorHandler {

  @ExceptionHandler(Array(classOf[Exception],
    classOf[ContentNotFoundException[_]],
    classOf[ElementNotFoundException[_]],
    classOf[ElementNotCreatedException[_]],
    classOf[ElementNotUpdatedException[_]],
    classOf[HttpRequestMethodNotSupportedException],
    classOf[HttpMediaTypeNotSupportedException],
    classOf[HttpMediaTypeNotAcceptableException],
    classOf[MissingPathVariableException],
    classOf[MissingServletRequestParameterException],
    classOf[ServletRequestBindingException],
    classOf[ConversionNotSupportedException],
    classOf[TypeMismatchException],
    classOf[HttpMessageNotReadableException],
    classOf[HttpMessageNotWritableException],
    classOf[MethodArgumentNotValidException],
    classOf[MissingServletRequestPartException],
    classOf[BindException],
    classOf[NoHandlerFoundException],
    classOf[AsyncRequestTimeoutException]))
  def handleAll(ex: Throwable, request: WebRequest): ResponseEntity[ApiError] = ex match{
      case ex : ElementNotFoundException[_] => buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND,"error" , ex))
      case ex : ContentNotFoundException[_] => buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND,"error" , ex))
      case ex : ElementNotCreatedException[_] => buildResponseEntity(new ApiError(HttpStatus.EXPECTATION_FAILED,"error" , ex))
      case ex : ElementNotUpdatedException[_] => buildResponseEntity(new ApiError(HttpStatus.EXPECTATION_FAILED,"error" , ex))
      case ex : HttpRequestMethodNotSupportedException => buildResponseEntity(new ApiError(HttpStatus.METHOD_NOT_ALLOWED,"error" , ex))
      case ex : HttpMediaTypeNotSupportedException => buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"error" , ex))
      case ex : HttpMediaTypeNotAcceptableException => buildResponseEntity(new ApiError(HttpStatus.NOT_ACCEPTABLE,"error" , ex))
      case ex : MissingPathVariableException => buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"error" , ex))
      case ex : MissingServletRequestParameterException => buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,"error" , ex))
      case ex : ServletRequestBindingException => buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,"error" , ex))
      case ex : ConversionNotSupportedException => buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"error" , ex))
      case ex : TypeMismatchException => buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,"error" , ex))
      case ex : HttpMessageNotReadableException => buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,"error" , ex))
      case ex : HttpMessageNotWritableException => buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"error" , ex))
      case ex : MethodArgumentNotValidException => buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,"error" , ex))
      case ex : MissingServletRequestPartException => buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,"error" , ex))
      case ex : BindException => buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,"error" , ex))
      case ex : NoHandlerFoundException => buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND,"error" , ex))
      case ex : AsyncRequestTimeoutException => buildResponseEntity(new ApiError(HttpStatus.SERVICE_UNAVAILABLE,"error" , ex))
      case _ => buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"error" , ex))
  }

  private def buildResponseEntity(error : ApiError) = new ResponseEntity[ApiError](error, new HttpHeaders(), error.httpStatus)
}