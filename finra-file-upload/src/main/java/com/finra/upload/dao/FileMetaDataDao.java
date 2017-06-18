package com.finra.upload.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.finra.upload.domain.FileMetaData;

@Repository
public interface FileMetaDataDao extends CrudRepository<FileMetaData, String> 
{
	List<FileMetaData> findByCreatedDateBetween(Date startDate, Date currentDate);
	FileMetaData findById(Long fileId);
}
