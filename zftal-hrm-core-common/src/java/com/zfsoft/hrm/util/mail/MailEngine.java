package com.zfsoft.hrm.util.mail;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.hrm.mail.entities.MailTemplate;
import com.zfsoft.hrm.mail.service.svcinterface.IMailTemplateService;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Class for sending e-mail messages based on Velocity templates or with
 * attachments.
 *
 */
public class MailEngine {
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	private IMailTemplateService mailTemplateService;
	private String from;
	private static final String LOCALE = "locale";

	/**
	 * Convenience method for sending messages with attachments.
	 *
	 * @param recipients
	 *            array of e-mail addresses
	 * @param sender
	 *            e-mail address of sender
	 * @param resource
	 *            attachment from classpath
	 * @param bodyText
	 *            text in e-mail
	 * @param subject
	 *            subject of e-mail
	 * @param attachmentName
	 *            name for attachment
	 * @throws MessagingException
	 *             thrown when can't communicate with SMTP server
	 */
	public void sendMessage(final String[] recipients, final String sender,
			final String subject, String taskId,
			final Map emailTemplateData, final ClassPathResource resource,
			final String attachmentName) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();

		// use the true flag to indicate you need a multipart message

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(recipients);

		// use the default sending if no sender specified
		if (sender == null) {
			helper.setFrom(from);
		} else {
			helper.setFrom(sender);
		}
		String emailTempFileName = "";
//		Locale locale = (Locale)emailTemplateData.get(LOCALE);
//		if(locale == null){
//			emailTempFileName = taskId.substring(0, taskId.lastIndexOf(".")) + "_en_US.vm";
//		}
//		else{
//			emailTempFileName = taskId.substring(0, taskId.lastIndexOf(".")) + "_" + locale.getLanguage() + "_" + locale.getCountry() + ".vm";
//			try{
//				Resource mailTemp = new ClassPathResource(emailTempFileName);
//				if(!mailTemp.exists()){
//					emailTempFileName = taskId.substring(0, taskId.lastIndexOf(".")) + "_en_US.vm";
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
		String bodyText = getEmailBody(taskId, emailTemplateData);

		helper.setText(bodyText, true);
		helper.setSubject(subject);

		if (attachmentName != null) {
			helper.addAttachment(attachmentName, resource);
		}

		mailSender.send(message);
	}
	
	public void sendSimpleMessage(final String[] recipients, final String sender,
			final String subject,
			final String bodyText) throws MessagingException {
		MimeMessage message = ((JavaMailSenderImpl) mailSender)
				.createMimeMessage();

		// use the true flag to indicate you need a multipart message

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(recipients);

		// use the default sending if no sender specified
		if (sender == null) {
			helper.setFrom(from);
		} else {
			helper.setFrom(sender);
		}
		helper.setText(bodyText, true);
		helper.setSubject(subject);

		mailSender.send(message);
	}

	public String previewMessage(String taskId, final Map emailTemplateData)
			throws MessagingException {

		return this.getEmailBody(taskId, emailTemplateData);
	}
	
	public void sendMessage(final String recipient, final String subject,
			String taskId, final Map emailTemplateData)
			throws MessagingException {

		this.sendMessage(recipient.split(",") , null, subject,
				taskId, emailTemplateData, (ClassPathResource)null, null);
	}

	public void sendMessageWithSender(final String recipient, final String subject,
			final String taskId, final Map emailTemplateData, final String sender)
			throws MessagingException {

		this.sendMessage(recipient.split(","), sender, subject,
				taskId, emailTemplateData, (ClassPathResource)null, null);
	}

	private String getEmailBody(final String taskId,
			final Map emailTemplateData) {
		String result = null;
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateLoader(new TemplateLoader() {
			public void closeTemplateSource(Object templateSource) throws IOException
			{
			}

			public Object findTemplateSource(String taskId) throws IOException
			{
				Locale locale = Locale.getDefault();
				int index = taskId.indexOf(locale.getLanguage());
				taskId = taskId.substring(0,index - 1);
				MailTemplate mailTemplate = new MailTemplate();
				mailTemplate.setTaskId(taskId);
				mailTemplate.setDefaultValue(0);
				mailTemplate = mailTemplateService.getMailTemplateByTaskId(mailTemplate); 
				if(mailTemplate == null){
					MailTemplate mailTemplate2 = new MailTemplate();
					mailTemplate2.setTaskId(taskId);
					mailTemplate2.setDefaultValue(1);
					mailTemplate = mailTemplateService.getMailTemplateByTaskId(mailTemplate2);
				}
				return mailTemplate.getContent();
			}
			public long getLastModified(Object templateSource)
			{
				return -1l;
			}
			public Reader getReader(Object templateSource, String encodeType)
					throws IOException
			{
				StringReader reader=new StringReader(templateSource.toString());
				return reader;
			}
		});
		// 装载模板
		Template template = null;
		try {
			template = configuration.getTemplate(taskId);
			result = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailTemplateData);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} 
//		if(result == null){
//			try {
//				result = VelocityEngineUtils.mergeTemplateIntoString(
//						velocityEngine, taskId, "UTF-8",
//						emailTemplateData);
//			} catch (VelocityException e) {
//				e.printStackTrace();
//			}
//		}

		return result;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(final JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(final VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	/**
	 *
	 * @author Ahearn Lin
	 *
	 * @param recipients
	 * @param sender
	 * @param subject
	 * @param taskId
	 * @param emailTemplateData
	 * @param attachment
	 * @param attachmentName
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public void sendMessage(final String[] recipients, final String sender, final String senderName, final String subject,
			final String taskId, final Map emailTemplateData, final File attachment,
			final String attachmentName) throws MessagingException, UnsupportedEncodingException{
		final MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

		// use the true flag to indicate you need a multipart message

		final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(recipients);

		// use the default sending if no sender specified
		if (sender == null){
			helper.setFrom(from,senderName);
		}else{
			helper.setFrom(sender,senderName);
		}

		final String bodyText = getEmailBody(taskId, emailTemplateData);

		helper.setText(bodyText, true);
		helper.setSubject(subject);

		if (attachmentName != null){
			helper.addAttachment(attachmentName, attachment);
		}

		mailSender.send(message);
	}

	/**
	 * @return the mailTemplateService
	 */
	public IMailTemplateService getMailTemplateService() {
		return mailTemplateService;
	}

	/**
	 * @param mailTemplateService the mailTemplateService to set
	 */
	public void setMailTemplateService(IMailTemplateService mailTemplateService) {
		this.mailTemplateService = mailTemplateService;
	}

}
