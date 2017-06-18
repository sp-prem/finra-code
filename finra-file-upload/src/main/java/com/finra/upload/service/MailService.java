package com.finra.upload.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.finra.upload.dao.FileMetaDataDao;
import com.finra.upload.domain.FileMetaData;

@Service
public class MailService 
{
	@Autowired private FileMetaDataDao fileMetaDataDao;

	@Scheduled(fixedRate = 5000)
	public void determineIfNewFilesAreAdded() 
	{
		Date currentTime = new Date();
		Date lastHour = new Date(currentTime.getTime() - 3600 * 1000);
		List<FileMetaData> files = fileMetaDataDao.findByCreatedDateBetween(lastHour,currentTime);
		
		if(files.size() >= 1)
		{
			this.sendEmail(files);
		}
	}
	
	private void sendEmail(List<FileMetaData> files)
	{
		StringBuffer emailText = new StringBuffer("Following files have added in the last hour");
		
		emailText = emailText.append("\n \n");
		
		for(FileMetaData file:files)
		{
			emailText.append(file.getFileName())
					 .append("\n");
		}

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("your-gmail-id","your-gmail-password");
			}
		});

		try 
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@finra.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("to-email-id"));
			message.setSubject("New Files have been added");
			message.setText(emailText.toString());
			Transport.send(message);
			System.out.println("Email sent");
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}