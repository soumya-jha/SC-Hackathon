package com.hackerrank.sample.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hackerrank.sample.exception.AccountManagementException;
import com.hackerrank.sample.exception.AuthenticationException;
import com.hackerrank.sample.exception.GenericException;
import com.hackerrank.sample.exception.ValidationException;
import com.hackerrank.sample.model.Error;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = GenericException.class)
	public ResponseEntity<Error> handleGenericException(GenericException e) {

		Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = AccountManagementException.class)
	public ResponseEntity<Error> handleAccountManagementException(AccountManagementException e) {

		Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = AuthenticationException.class)
	public ResponseEntity<Error> handleAuthenticationException(AuthenticationException e) {

		Error error = new Error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<Error> handleAccessDenied(AccessDeniedException e) {

		Error error = new Error(HttpStatus.FORBIDDEN.value(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = InternalAuthenticationServiceException.class)
	public ResponseEntity<Error> handleException(InternalAuthenticationServiceException e) {

		Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity<Error> handleValidationException(ValidationException e) {

		Error error = new Error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Error> handleException(Exception e) {

		Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}