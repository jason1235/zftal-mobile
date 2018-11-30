package com.zfsoft.sjzx.jhpz.wspz.service;

import java.util.List;


import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.zfsoft.framework.util.WebServiceJobDetail;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhdzConfig;

public class WsjhConfigTask extends WebServiceJobDetail{
	/**
	 *  ��־����
	 */
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(WsjhConfigTask.class);
 	
    public void task() {
       log.info("WebService ������ʱ��������ȿ�ʼ!");
       WsjhConfigService 	wsjhConfigService =(WsjhConfigService)getBean("wsjhConfigService");
       WsjhDsqConfig wdc =  this.getWdc();
       if(wdc!=null){
	       WsjhConfig wsjhConfig = new WsjhConfig();
	       long time = System.currentTimeMillis();
	       log.info(wdc.getJhpzmc()+" ִ��������ȿ�ʼ ");
	       wsjhConfig.setWsjhmc(wdc.getJhpzmc());       
	       wsjhConfig.setType(wdc.getJhlx());
	       WsjhConfig wc = wsjhConfigService.getWsjhConfig(wsjhConfig);
	       WsjhParamsConfig wpcp = new WsjhParamsConfig();
	       wpcp.setJhmc(wdc.getJhpzmc());
	       wpcp.setJhlx(wdc.getJhlx());
	       List<WsjhParamsConfig> wpcList = wsjhConfigService.getWsjhParamsConfigList(wpcp);
	       WsjhdzConfig wdcp = new WsjhdzConfig();
	       wdcp.setJhmc(wdc.getJhpzmc());
	       wdcp.setJhlx(wdc.getJhlx());
	       List<WsjhdzConfig> wcList = wsjhConfigService.getWsjhdzConfigList(wdcp);
	       try{
	    	  String result = "";
	    	  if(wdc.getJhlx()!=null&&2==wdc.getJhlx().intValue()){//�����������ö�ʱ��
	    		 result = wsjhConfigService.getMessageByWsbs(wc, wpcList);
	    	  }else{//�������ö�ʱ��
	    		 result= wsjhConfigService.getMessage(wpcList, wcList, wc);
	    	  }	
	    	  long endtime = System.currentTimeMillis();
	    	  log.info(wdc.getJhpzmc()+" ִ��������Ƚ���!"+(endtime-time)/1000+" ��!");
		      log.info("WebService ������ʱ���������ִ�����!"+result);
	       }catch(Exception e){
	    	   e.printStackTrace();
	    	  log.info("WebService ������ʱ�����񱨴�"+e.getMessage());
	       }
       }
       log.info("WebService ������ʱ��������Ƚ���!");
    }

	@Override
	public void doaction() {
		task();	
		System.gc();
	}
	@Override
	public void dolog() {
		
	};
}
