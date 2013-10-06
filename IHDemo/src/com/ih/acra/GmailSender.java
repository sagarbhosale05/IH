package com.ih.acra;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.content.Context;

/**
 * The Class GmailSender.
 */
public class GmailSender extends javax.mail.Authenticator {

	/** The mailhost. */
	private String mailhost = "smtp.gmail.com";

	/** The user. */
	private String user="swandealernamequotation@gmail.com";

	/** The password. */
	private String password="abhi9226";
	/** The session. */
	private Session session;

	/** The context. */
	private Context context;

	static {
		Security.addProvider(new JSSEProvider());
	}

	/**
	 * Instantiates a new gmail sender.
	 * 
	 * @param user
	 *            the user
	 * @param password
	 *            the password
	 * @param context
	 *            the context
	 */
	public GmailSender(String user, String password, Context context) {
		this.user = user;
		this.password = password;
		this.context = context;

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", mailhost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		session = Session.getDefaultInstance(props, this);
	}

	public GmailSender(Context context) {
		this.context = context;
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", mailhost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		session = Session.getDefaultInstance(props, this);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

	/**
	 * Send mail.
	 * 
	 * @param subject
	 *            the subject
	 * @param sender
	 *            the sender
	 * @param recipients
	 *            the recipients
	 * @param attachmentName
	 *            the attachment name
	 * @param attachmentPath
	 *            the attachment path
	 * @throws Exception
	 *             the exception
	 */
	public void sendMail(String subject, String sender, String recipients,
			String attachmentName, String attachmentPath, String body)
			throws Exception {
		MimeMessage message = new MimeMessage(session);
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);
		if (recipients.indexOf(',') > 0)
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipients));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipients));

		// Attachment Part
		MimeBodyPart attachmentPart = null;
		if (attachmentPath != null) {
			attachmentPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentPath);
			attachmentPart.setDataHandler(new DataHandler(source));
			attachmentPart.setFileName(attachmentName);
		}
		// Body Part
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(body);

		Multipart multipart = new MimeMultipart();
		if (attachmentPart != null)
			multipart.addBodyPart(attachmentPart);
		multipart.addBodyPart(bodyPart);

		message.setContent(multipart);

		Transport.send(message);
	}

	/**
	 * The Class ByteArrayDataSource.
	 */
	public class ByteArrayDataSource implements DataSource {

		/** The data. */
		private byte[] data;

		/** The type. */
		private String type;

		/**
		 * Instantiates a new byte array data source.
		 * 
		 * @param data
		 *            the data
		 * @param type
		 *            the type
		 */
		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		/**
		 * Instantiates a new byte array data source.
		 * 
		 * @param data
		 *            the data
		 */
		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		/**
		 * Sets the type.
		 * 
		 * @param type
		 *            the new type
		 */
		public void setType(String type) {
			this.type = type;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.activation.DataSource#getContentType()
		 */
		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.activation.DataSource#getInputStream()
		 */
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.activation.DataSource#getName()
		 */
		public String getName() {
			return "ByteArrayDataSource";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.activation.DataSource#getOutputStream()
		 */
		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
}
