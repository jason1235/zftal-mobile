package com.zfsoft.hrm.message.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.message.config.MessageTimerSendConfig;
import com.zfsoft.service.base.BaseAnService;

public interface MessageTimerSendConfigService extends BaseAnService<MessageTimerSendConfig> {
	
	/**
	 * 根据消息定时发送配置，抓取数据并发送消息
	 * @param messageTimerSendConfig
	 */
	public void sendMessages(MessageTimerSendConfig messageTimerSendConfig);
	
	public void executeSelectSql(String sql);

	void sendMessage(MessageTimerSendConfig messageTimerSendConfig,
			List<Map<String, Object>> list);

}
