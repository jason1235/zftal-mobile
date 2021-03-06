package com.zfsoft.framework.util;


import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhDsqConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhParamsConfig;
import com.zfsoft.sjzx.jhpz.wspz.model.WsjhdzConfig;
import com.zfsoft.sjzx.jhpz.wspz.service.WsjhConfigService;

public  class WebServiceSimpleJob implements StatefulJob{

	private Logger log = LoggerFactory.getLogger(WebServiceJobDetail.class);
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		JobDetail jd=	jec.getJobDetail();
		WsjhDsqConfig wdc = (WsjhDsqConfig) jd.getJobDataMap().get("wdc");
		//System.out.println("####" + wdc.getDsqxh());
		task(wdc);
		//try{
		//jd.doaction();
		//jd.dolog();
		// }catch(Exception e){
		//	e.printStackTrace();
		//}
	}

	public void task(WsjhDsqConfig wdc) {
		log.info("WebService 交换定时器任务调度开始!");
		WsjhConfigService wsjhConfigService = (WsjhConfigService)SpringUtil.getBean("wsjhConfigService");
		if(wdc!=null){
			WsjhConfig wsjhConfig = new WsjhConfig();
			long time = System.currentTimeMillis();
			log.info(wdc.getJhpzmc()+" 执行任务调度开始 ");
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
				if(wdc.getJhlx()!=null&&2==wdc.getJhlx().intValue()){//绵阳报送配置定时跑
					result = wsjhConfigService.getMessageByWsbs(wc, wpcList);
				}else{//交换配置定时跑
					result= wsjhConfigService.getMessage(wpcList, wcList, wc);
				}
				long endtime = System.currentTimeMillis();
				log.info(wdc.getJhpzmc()+" 执行任务调度结束!"+(endtime-time)/1000+" 秒!");
				log.info("WebService 交换定时器任务调度执行完毕!"+result);
			}catch(Exception e){
				e.printStackTrace();
				log.info("WebService 交换定时器任务报错："+e.getMessage());
			}
		}
		log.info("WebService 交换定时器任务调度结束!");
	}



}
