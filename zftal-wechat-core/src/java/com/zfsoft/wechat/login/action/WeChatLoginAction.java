package com.zfsoft.wechat.login.action;
import com.zfsoft.wechat.login.entity.WeChatBinding;
import com.zfsoft.wechat.login.service.IWeChatBsnsService;
import com.zfsoft.wechat.login.service.IWeChatLoginService;
import com.zfsoft.hrm.common.HrmAction;

/**
 * 
 * @author Administrator
 *
 */
public class WeChatLoginAction extends HrmAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2916662840415953338L;
	public static final String FROM_URL_KEY ="wechat_jump_url";
	private IWeChatLoginService wechatLoginService;
	private IWeChatBsnsService weChatBsnsService;
//	private ILoginService loginService;
	private WeChatBinding model = new WeChatBinding();

    /**
     * @return the wechatLoginService
     */
    public IWeChatLoginService getWechatLoginService() {
        return wechatLoginService;
    }

    /**
     * @param wechatLoginService the wechatLoginService to set
     */
    public void setWechatLoginService(IWeChatLoginService wechatLoginService) {
        this.wechatLoginService = wechatLoginService;
    }
    public String binding() {
    	if(!weChatBsnsService.checkAccount(model.getYhm(), model.getMm())){
    		setErrorMessage("用户名或密码错误！");
    		getValueStack().set(DATA, getMessage());
    		return DATA;
    	}

    	if(!weChatBsnsService.login(model.getYhm(), getRequest())){
    		setErrorMessage("用户名或密码错误！");
    	}else{
			wechatLoginService.bindOpenId(model);
		}
		getValueStack().set(DATA, getMessage());
    	return DATA;
    }
    
    /**
	 * 会话失效
	 * 
	 * @return
	 * @throws Exception
	 */
	public String timeout() throws Exception {
		getValueStack().set("tzurl", "wechat/login_gotoReLoad.html");
		return "sessionOut";
	}
    
	/**
	 * 重新登录
	 * @return
	 */
	public String gotoReLoad() {
		return "gotoReLoad";
	}

	/**
	 * 返回
	 */
	public WeChatBinding getModel() {
		return model;
	}

	/**
	 * 设置
	 * @param model 
	 */
	public void setModel(WeChatBinding model) {
		this.model = model;
	}

	/**
	 * 设置
	 * @param weChatBsnsService 
	 */
	public void setWeChatBsnsService(IWeChatBsnsService weChatBsnsService) {
		this.weChatBsnsService = weChatBsnsService;
	}

}
