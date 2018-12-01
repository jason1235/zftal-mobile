package com.zfsoft.hrm.notice.service.test;

import junit.framework.Assert;

import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.notice.entity.Notice;
import com.zfsoft.hrm.notice.service.INoticeService;

/** 
 * @author jinjj
 * @date 2012-9-26 下午01:27:31 
 *  
 */
public class NoticeServiceTest extends BaseTxTestCase {

	private INoticeService noticeService;
	
	@Test
	public void test() throws Exception{
		noticeService = (INoticeService) this.applicationContext.getBean("noticeService");
		
		Notice notice = new Notice();
		notice.setAuthor("2012001");
		notice.setContent("测试");
		notice.setRemark("备注");
		notice.setTitle("通知测试");
		
		noticeService.save(notice);
		
		notice.setRemark("更新备注");
		noticeService.update(notice);
		
		Notice old = noticeService.getById(notice.getGuid());
		Assert.assertEquals(notice.getRemark(), old.getRemark());
		
		notice.setStatus(1);
		noticeService.updateStatus(notice);
		old = noticeService.getById(notice.getGuid());
		Assert.assertEquals(1, old.getStatus());
		
		noticeService.delete(notice.getGuid());
		old = noticeService.getById(notice.getGuid());
		Assert.assertNull(old);
	}
}
