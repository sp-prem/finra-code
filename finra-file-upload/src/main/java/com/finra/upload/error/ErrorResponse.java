package com.finra.upload.error;

import java.io.Serializable;

public class ErrorResponse implements Serializable
{
	private static final long serialVersionUID = 4070498595289253670L;
	
	private int errorCode;
	private String message;
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
