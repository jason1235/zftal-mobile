package com.zfsoft.hrm.message.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.hrm.message.config.MessageTimerSendConfig;
/**
 * 消息定时发送配置dao
 * @author gonghui
 *
 */
public interface MessageTimerSendConfigDao extends BaseAnDao<MessageTimerSendConfig> {

	public List<Map<String,Object>> executeSelectSql(String sql);
}
