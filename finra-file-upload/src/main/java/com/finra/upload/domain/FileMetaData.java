package com.finra.upload.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FILE_META_DATA")
public class FileMetaData implements Serializable
{
	private static final long serialVersionUID = -3149246347998373929L;
	
	private long id;
	private long version;
	private String fileName;
	private Date createdDate;
	
	public FileMetaData() { }
	
	public FileMetaData(long id,String name,Date date,long version) 
	{
		this.fileName = name;
		this.createdDate = date;
		this.version = version;
		this.id=id;
	}
	
	@Column(name = "FILE_ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "VERSION")
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Id
	@Column(name = "FILE_NAME")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}