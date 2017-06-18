package com.finra.upload.error;

public class InvalidInputException extends Exception 
{
	private static final long serialVersionUID = -6943301366997716100L;
	
	public InvalidInputException(String message) 
	{
		super(message);
	}
}
