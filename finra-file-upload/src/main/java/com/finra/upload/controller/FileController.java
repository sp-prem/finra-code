package com.finra.upload.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.finra.upload.domain.FileMetaData;
import com.finra.upload.error.ErrorResponse;
import com.finra.upload.error.InvalidInputException;
import com.finra.upload.service.FileService;

@RestController
@RequestMapping(value="/files")
public class FileController 
{
	@Autowired private FileService fileService;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody FileMetaData saveFile(@RequestParam("file") MultipartFile file) throws FileUploadException,InvalidInputException
	{
		return fileService.saveFile(file);
	}
	
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<Resource> downloadFile(@RequestParam("fileId") long fileId) throws FileUploadException,InvalidInputException,FileNotFoundException
	{
		return fileService.downloadFile(fileId);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody FileMetaData getMetaData(@RequestParam("fileId") long fileId) throws FileUploadException,InvalidInputException,IOException
	{
		return fileService.getFileMetaData(fileId);
	}

	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<ErrorResponse> handleFileUploadException(Exception ex) 
	{

		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.OK);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorResponse> handleIOException(Exception ex) 
	{

		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.OK);
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorResponse> handleInvalidInputException(Exception ex) 
	{

		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.BAD_REQUEST);
	}
}