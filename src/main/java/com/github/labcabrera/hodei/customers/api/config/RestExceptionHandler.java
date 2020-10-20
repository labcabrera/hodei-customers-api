package com.github.labcabrera.hodei.customers.api.config;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.labcabrera.hodei.model.commons.MessageEntry;
import com.github.labcabrera.hodei.model.commons.actions.OperationResult;
import com.github.labcabrera.hodei.rsql.exception.PredicateParseException;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex) {
		log.warn("Handling exception {} ({})", ex.getMessage(), ex.getClass().getName());
		Class<? extends Exception> clazz = ex.getClass();
		if (AccessDeniedException.class.isAssignableFrom(clazz)) {
			log.debug("Access denied: {}", ex.getMessage(), ex);
			return new ResponseEntity<>(OperationResult.builder().code("403").message("Forbidden").build(), HttpStatus.FORBIDDEN);
		}
		else if (HttpClientErrorException.class.equals(clazz)) {
			log.debug("HTTP client error: {}", ex.getMessage(), ex);
			HttpClientErrorException ce = (HttpClientErrorException) ex;
			HttpStatus status = ce.getStatusCode();
			String code = String.valueOf(status.value());
			return new ResponseEntity<>(OperationResult.builder().code(code).message(ce.getMessage()).build(), HttpStatus.FORBIDDEN);
		}
		else if (ex instanceof PredicateParseException) {
			return new ResponseEntity<>(OperationResult.builder().code("400")
				.message("Invalid search expression")
				.messages(Arrays.asList(MessageEntry.builder()
					.text(ex.getMessage())
					.build()))
				.build(), HttpStatus.FORBIDDEN);
		}
		else {
			log.error("Catch exception {}", ex.getMessage(), ex);
		}
		OperationResult<Void> result = OperationResult.<Void>builder()
			.code("500")
			.message("Internal server error: " + ex.getMessage())
			.messages(Arrays.asList(MessageEntry.builder()
				.text("Exception: " + ex.getClass().getName())
				.build()))
			.build();
		return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status,
		WebRequest request) {
		log.error("Internal error: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("500").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.debug("Not readable message: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.debug("Unsupported MediaType: {}", ex.getMessage(), ex);
		String code = String.valueOf(status.value());
		return new ResponseEntity<>(OperationResult.builder().code(code).message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("Bind exception: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.debug("Conversion not supported: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.debug("Request binding exception: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.debug("Method not supported exception: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status,
		WebRequest request) {
		log.debug("No handler found exception: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status,
		WebRequest request) {
		log.debug("Missing path parameter: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		MessageEntry messageEntry = MessageEntry.builder().text("Errors").params(new LinkedHashMap<>()).build();
		ex.getBindingResult().getAllErrors().stream().forEach(e -> {
			String code = e.getCode();
			String key = e.getCodes()[0].substring(code.length() + 1);
			String value = e.getDefaultMessage();
			messageEntry.getParams().put(key, value);
		});
		OperationResult<Void> result = OperationResult.<Void>builder()
			.code("400")
			.timestamp(LocalDateTime.now())
			.message("Invalid request")
			.messages(Arrays.asList(messageEntry))
			.build();
		log.debug("Method argument not valid: {}", ex.getMessage());
		return new ResponseEntity<>(result, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.debug("Media type not acceptable: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		log.debug("Message not writable: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status,
		WebRequest request) {
		log.debug("Type mismatch: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(OperationResult.builder().code("400").message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
	}
}