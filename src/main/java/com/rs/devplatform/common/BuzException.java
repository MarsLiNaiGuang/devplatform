package com.rs.devplatform.common;

import org.springframework.http.HttpStatus;

public class BuzException extends Throwable{

	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private HttpStatus httpStatus;

	public BuzException(String msg){
		super(msg);
	}
	
	public BuzException(String errCode, String errMsg){
		super(errMsg);
		this.errCode = errCode;
	}
	
	public BuzException(HttpStatus httpStatus, String errMsg){
		super(errMsg);
		this.httpStatus = httpStatus;
	}
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
}
