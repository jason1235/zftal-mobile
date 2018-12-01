package com.zfsoft.hrm.common;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.hrm.mail.entities.MailConfig;
import com.zfsoft.hrm.mail.service.svcinterface.IMailConfigService;
import com.zfsoft.hrm.util.mail.MailEngine;
import com.zfsoft.util.encrypt.DBEncrypt;

/**
 * 人事是系统基础Action
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-28
 * @version V1.0.0
 */
public abstract class HrmAction extends BaseAction {

	private static final long serialVersionUID = 1577874760931510447L;

	protected static final String SESSION_FORM_INFO = "_s_from_info";

	private MailEngine mailEngine;
	
	private IMailConfigService mailConfigService;
	
	/**
	 * 列表页面
	 */
	protected static final String LIST_PAGE = "list";

	/**
	 * 编辑页面
	 */
	protected static final String EDIT_PAGE = "edit";

	private Message message = new Message();

	/**
	 * 设置错误信息
	 * 
	 * @param text信息内容
	 */
	public void setErrorMessage(String text) {
		setMessage(false, text);
	}

	/**
	 * 设置成成功信息
	 * 
	 * @param text
	 *            信息内容
	 */
	public void setSuccessMessage(String text) {
		setMessage(true, text);
	}

	/**
	 * 返回操作结果信息
	 */
	public Message getMessage() {
		return message;
	}

	protected void setMessage(boolean success, String text) {
		message.setSuccess(success);
		message.setText(text);
	}

	/**
	 * 从页面中获取int类型的值
	 * 
	 * @param num
	 * @return
	 */
	public int getInt(String num) {
		String number = getRequest().getParameter(num);
		if (number == null || "".equals(number)) {
			return -1;
		}

		return Integer.parseInt(number.trim());
	}

	/**
	 * 从页面http请求中获到String类型的参数值
	 * 
	 * @param str
	 *            http参数名称
	 * @return 如果http请求中含有该参数则返加该参数对应的值,如果没有则返回null
	 */
	public String getString(String str) {
		String st = getRequest().getParameter(str);
		if (st == null) {
			return null;
		}
		return st.trim();
	}

	/**
	 * 将一个对象存放到我们的ActionContext的上下文环境context中。
	 * 
	 * @param key
	 * @param value
	 */
	public void setInActionContext(String key, Object value) {

		ActionContext.getContext().put(key, value);
	}
	
	/**
	 * 
	 * 方法描述：获取登录用户所具有的角色数组
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-9 上午09:21:04
	 */
	public String[] getRole(){
		return getUser().getJsdms().toArray(new String[getUser().getJsdms().size()]);
	}

	public void sendEmailMessage(Map<String, Object> messageMap) throws MessagingException {
        // TODO: figure out how to get bundle specified
        // model.put("bundle", getTexts());
        String emailTo = (String) messageMap.get("mailTo");
//        emailTo="slw@10.71.32.115";
        if (StringUtils.isNotEmpty(emailTo)) {
            messageMap.put("sendDate", new Date(System.currentTimeMillis()));
//            if (mailEngine.getMailSender()==null) {
				setMailConfig(mailConfigService.findByType("EMAIL"));
//			}
            mailEngine.sendMessage(emailTo,
                    messageMap.get("subject")+"", (String)messageMap.get("taskId"),
                    messageMap);
        } else {
            throw new MessagingException("email key is null!");
        }
    
    }

	public String previewEmailMessage(Map<String, Object> messageMap) throws MessagingException {
        String emailTo = (String) messageMap.get("mailTo");
//        emailTo="slw@10.71.32.115";
        if (StringUtils.isNotEmpty(emailTo)) {
            messageMap.put("sendDate", new Date(System.currentTimeMillis()));
            return mailEngine.previewMessage((String)messageMap.get("taskId"), messageMap);
        } else {
            throw new MessagingException("邮箱没有设置！");
        }
    
    }
	
	/**
	 * @return the mailEngine
	 */
	public MailEngine getMailEngine() {
		return mailEngine;
	}

	/**
	 * @param mailEngine the mailEngine to set
	 */
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	/**
	 * 设置邮件服务器
	 * 发送邮件之前需要配置
	 * @param config 
	 */
	public void setMailConfig(MailConfig config) {
        JavaMailSenderImpl send = new JavaMailSenderImpl();
        Properties p = new Properties();
        p.put("mail.smtp.host", config.getHost());
        p.put("mail.smtp.auth", "true");
        send.setJavaMailProperties(p);
        send.setUsername(config.getUser());
        send.setPort(config.getPort());
        try{
        	send.setPassword(new DBEncrypt().dCode( config.getPwd().getBytes()));
        }catch (Exception e) {
        	send.setPassword(config.getPwd());
		}
        mailEngine.setFrom(config.getSend());
        mailEngine.setMailSender(send);
	}

	/**
	 * 返回
	 */
	public IMailConfigService getMailConfigService() {
		return mailConfigService;
	}

	/**
	 * 设置
	 * @param mailConfigService 
	 */
	public void setMailConfigService(IMailConfigService mailConfigService) {
		this.mailConfigService = mailConfigService;
	}
	
}
