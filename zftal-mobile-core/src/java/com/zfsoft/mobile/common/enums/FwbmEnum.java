package com.zfsoft.mobile.common.enums;

/**
 * 服务编码美剧
 */
public enum FwbmEnum {
	
	FWBM_TZGG("通知公告","301"),
	//FWBM_XXGK("学校概况","201"),                
	//FWBM_XYZX("校园资讯","202"),     
	//FWBM_XYFG("校园风光","203"),     
	FWBM_YJXT("邮件系统","302"),     
	FWBM_TXL("通讯录","303"),     
	FWBM_HYGL("会议管理","304"),     
	//FWBM_BGRC("办公日程","305"),     
	//FWBM_CLSYQK("车辆使用情况","319"),     
	//FWBM_ZBBCK("值班表查看","320"),   
	FWBM_YBSY("已办事宜","321"),  
	//FWBM_HYSSYQK("会议室使用情况","322"),  
	FWBM_DBSY("待办事宜","306"),  
	FWBM_BJSY("办结事宜","324"),  
	FWBM_WDJK("我的监考","402"),  
	FWBM_KSCX("考试查询","411"),  
	//FWBM_KBCX("课表查询","403"),  
	FWBM_PYJH("培养计划","404"),  
	FWBM_CJCX("成绩查询","401"),  
	FWBM_GRXX("个人信息","405"),  
	FWBM_KXJS("空闲教室","504"),  
	//FWBM_RYXX("人员信息","414"),  
	//FWBM_XJYJTS("学籍预警提示","415"),  
	//FWBM_GZCX("工资查询","502"),  
	FWBM_YKTCX("一卡通查询","506"),  
	//FWBM_YDXGDLDRK("移动学工独立登陆口","625"), 
	FWBM_SZDA("数字档案","508"),
	FWBM_WB("微博","809"),
	FWBM_TP("投票","509"),
	FWBM_TSG("图书馆","903"),
	FWBM_BGGG("办公公告","326"),
	FWBM_WX("微信公众号","484"),
	FWBM_XL("校历管理","483");
	
	

	/**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private FwbmEnum(String text, String key) {
        this.text = text;
        this.key = key;
    }

    /**
     * 展示文本
     * 
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 代码编号
     * 
     * @return
     */
    public String getKey() {
        return key;
    }
    
	
}
