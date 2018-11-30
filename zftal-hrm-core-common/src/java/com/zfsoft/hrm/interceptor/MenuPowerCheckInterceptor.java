package com.zfsoft.hrm.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.GnmkModel;
import com.zfsoft.service.svcinterface.IGnmkService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-3
 * @version V1.0.0
 */
public class MenuPowerCheckInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123667230194260358L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
        String path=request.getServletPath();
        if(path.indexOf("_")==-1){
            return actionInvocation.invoke();
        }
        IGnmkService gnmkService=(IGnmkService)ServiceFactory.getService("gnmkService");
        GnmkModel gnmkModel=new GnmkModel();
        String queryStr = request.getQueryString();
        gnmkModel.setDyym(path+"%");
        List<GnmkModel> list = gnmkService.getModelList(gnmkModel);

        if(list!=null&&list.size()>0){
        	gnmkModel= list.get(0);
        	int no=0;
        	if(StringUtil.isNotEmpty(queryStr)){
        		String[] pList = queryStr.split("&");
        		String url = null;
        		int i;
        		for (GnmkModel gnmk : list) {
        			i=0;
        			url = gnmk.getDyym();
        			for (String p : pList) {
						if (url.indexOf(p)!=-1) {
							i++;
						}
					}
        			if(i>no){
        				no=i;
        				gnmkModel=gnmk;
        			}
				}
        		
        	}
            String gnmkdm=gnmkModel.getGnmkdm();
            List<GnmkModel> czList=null;
            User user = SessionFactory.getUser();
            if(user!=null&&user.getJsdms()!=null){
                List<String> jsdmArray=user.getJsdms();
                for(int i=0;i<jsdmArray.size();i++){
                    GnmkModel cz=new GnmkModel();
                    cz.setJsdm(jsdmArray.get(i));
                    cz.setGnmkdm(gnmkdm);
                    czList = gnmkService.cxCzdm(cz);
                    if(czList!=null&&czList.size()>0)
                    	return actionInvocation.invoke();
                }
            }
        }else{
            return actionInvocation.invoke();
        }  
        ValueStack vs = ServletActionContext.getValueStack(request);
        vs.set("tzurl", "xtgl/login_logout.html");
        return "sessionOut";
        
	}

}