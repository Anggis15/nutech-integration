package com.apply.applyKerja.handler;


import com.apply.applyKerja.util.GlobalFunction;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

/*
	KODE EXCEPTION
	VALIDATION		= 107
	DATA			= 108
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private List<ApiValidationError> lsSubError = new ArrayList<ApiValidationError>();
	private String [] strExceptionArr = new String[2];

	public GlobalExceptionHandler() {
		strExceptionArr[0] = "GlobalExceptionHandler";
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatusCode status,
																  WebRequest request) {
		lsSubError.clear();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			lsSubError.add(new ApiValidationError(fieldError.getField(),
					fieldError.getDefaultMessage(),
					fieldError.getRejectedValue()));
		}
		ApiError apiError =
				new ApiError("Data Tidak Valid !!",ex,107);
		apiError.setSubErrors(lsSubError);
		return new ResponseEntity<Object>(apiError,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
		return buildResponseEntity(new ApiError("DATA TIDAK VALID",ex,107),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UnexpectedRollbackException.class)
	public ResponseEntity<Object> unexpectedRollbackException(UnexpectedRollbackException ex, HttpServletRequest request) {
		return buildResponseEntity(new ApiError("DATA TIDAK VALID",ex,108),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> usernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
		return buildResponseEntity(new ApiError("DATA TIDAK VALID",ex,108),HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handleTokenTidakValid(HttpServletRequest request) {
		return GlobalFunction.generateResponse(108,HttpStatus.UNAUTHORIZED,"Token tidak tidak valid atau kadaluwarsa",null,request);
	}
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Object> tokenExpired(HttpServletRequest request) {
		return GlobalFunction.generateResponse(108,HttpStatus.UNAUTHORIZED,"Token tidak tidak valid atau kadaluwarsa",null,request);
	}
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Object> apiException(HttpServletRequest request) {
		return GlobalFunction.generateResponse(108,HttpStatus.UNAUTHORIZED,"Token tidak tidak valid atau kadaluwarsa",null,request);
	}



	private ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus status) {
		return ResponseEntity.status(status).body(apiError);
	}
//
//	@ExceptionHandler(MultipartException.class)
//	public ResponseEntity<Object> multipartException(MultipartException ex, HttpServletRequest request) {
//		strExceptionArr[1] = "MultipartException(MultipartException ex, HttpServletRequest request) \n"+RequestCapture.allRequest(request);
//		return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"Konten harus Multipart",ex,request.getPathInfo(),"X-04-001"));
//	}
//	@ExceptionHandler(FileUploadException.class)
//	public ResponseEntity<Object> fileUploadException(FileUploadException ex, HttpServletRequest request) {
//		strExceptionArr[1] = "fileUploadException(FileUploadException ex, HttpServletRequest request) \n"+RequestCapture.allRequest(request);
//		return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"File Tidak Sesuai",ex,request.getPathInfo(),"X-04-002"));
//	}
//
//	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, HttpServletRequest request) {
//		strExceptionArr[1] = "handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, HttpServletRequest request) \n"+RequestCapture.allRequest(request);//perubahan 12-12-2023
//		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"TIDAK DAPAT DIPROSES",ex,request.getPathInfo(),"X-99-001"));
//	}
}