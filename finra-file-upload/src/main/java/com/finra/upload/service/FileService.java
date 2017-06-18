package com.finra.upload.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.finra.upload.dao.FileMetaDataDao;
import com.finra.upload.domain.FileMetaData;
import com.finra.upload.error.InvalidInputException;

@Service
public class FileService 
{
	private static String UPLOAD_FOLDER = "C:\\LABDIR\\Upload";
	
	@Autowired private FileMetaDataDao fileMetaDataDao;
	
	@PostConstruct
	public void createStorageDirectory()
	{
		File storageDir = new File(UPLOAD_FOLDER);

		if (!storageDir.exists()) {
			System.out.println("creating directory: " + storageDir.getName());
			storageDir.mkdir();
		}
	}
	
	public FileMetaData saveFile(MultipartFile file) throws FileUploadException,InvalidInputException
	{
		String fileName = null;
		if (!file.isEmpty()) 
		{
			try 
			{
				fileName = file.getOriginalFilename();
				FileMetaData metaData = null;
				metaData = fileMetaDataDao.findOne(fileName);
				if(metaData!=null)
					metaData = new FileMetaData(metaData.getId(),fileName,new Date(),metaData.getVersion()+1);
				else
					metaData = new FileMetaData(System.currentTimeMillis(),fileName,new Date(),1);
				
				byte[] bytes = file.getBytes();
				BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(UPLOAD_FOLDER,fileName)));
				buffStream.write(bytes);
				buffStream.close();
				
				metaData = fileMetaDataDao.save(metaData);
				return metaData;
			} 
			catch (Exception e) 
			{
				String errMessage = "You failed to upload " + fileName + ": " + e.getMessage();
				throw new FileUploadException(errMessage);
			}
		} 
		else 
		{
			throw new InvalidInputException("File is empty");
		}
	}
	
	public ResponseEntity<Resource> downloadFile(long fileId) throws InvalidInputException,FileNotFoundException
	{
		if(fileId==0)
			throw new InvalidInputException("File Id is not provided");

		FileMetaData metaData = fileMetaDataDao.findById(fileId);
		if(metaData==null)
			throw new InvalidInputException("Input File Id is not found");
		
			
		File file = new File(UPLOAD_FOLDER,metaData.getFileName());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	public FileMetaData getFileMetaData(long fileId) throws InvalidInputException,IOException
	{
		if(fileId==0)
			throw new InvalidInputException("File Id is not provided");

		FileMetaData metaData = fileMetaDataDao.findById(fileId);
		if(metaData==null)
			throw new InvalidInputException("Input File Id is not found");

		return metaData;
	}
}