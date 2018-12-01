package com.zfsoft.globalweb.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.GnmkModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.service.svcinterface.IGnmkService;
import com.zfsoft.service.svcinterface.IYhglService;


/**
 * 
 * 
 * 类名称：GnmkCzQxInterceptor
 * 类描述：功能模块操作权限拦截器
 * 创建人：huangxp
 * 创建时间：2012-6-28
 * 修改人：huangxp
 * 修改时间：2012-6-28
 * 修改备注： 
 * @version 
 *
 */
public class GnmkCzQxInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -8983750874000672892L;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String path=request.getServletPath();
        if(path.indexOf("_")==-1){
            return actionInvocation.invoke();
        }
        String actionPath=path.substring(0,path.indexOf("_"));
        String method=path.substring(path.indexOf("_")+1, path.indexOf("html")-1);
        String czdm=null;
        if(method!=null){
            for(int i=0;i<method.length();i++){
                char c=method.charAt(i);
                if((int)c>65&&(int)c<97){
                    czdm=method.substring(0, i);
                    break;
                }
            }
            if(method.length()>0&&czdm==null){
                czdm=method;
            }
      
        }
        IGnmkService gnmkService=(IGnmkService)ServiceFactory.getService("gnmkService");
        GnmkModel gnmkModel=new GnmkModel();
        gnmkModel.setDyym(actionPath+"%");
        gnmkModel= gnmkService.getModel(gnmkModel);

        if(gnmkModel!=null){
            String gnmkdm=gnmkModel.getGnmkdm();
            
            User user = SessionFactory.getUser();
            IYhglService yhglService=(IYhglService)ServiceFactory.getService("yhglService");
            YhglModel yhglModel=new YhglModel();
			yhglModel.setZgh(user.getYhm());
            yhglModel = yhglService.getModel(yhglModel);
            String jsdms=yhglModel.getJsdm();
            
            List<GnmkModel> gnmkList=new ArrayList<GnmkModel>();
            if(jsdms!=null&&!jsdms.equals("")){
                String [] jsdmArray=jsdms.split(",");
                for(int i=0;i<jsdmArray.length;i++){
                    GnmkModel cz=new GnmkModel();
                    cz.setJsdm(jsdmArray[i]);
                    cz.setGnmkdm(gnmkdm);
                    gnmkList.addAll(gnmkService.cxCzdm(cz));
                }
            }
            
            if((czdm.startsWith("cx")||czdm.equals("xtsz")||czdm.equals("valide"))&&gnmkList.size()>0){
                return actionInvocation.invoke();
            }
            
            for(int i=0;i<gnmkList.size();i++){
                GnmkModel lm=gnmkList.get(i);
                if(lm.getCzdm().equals(czdm)){
                    return actionInvocation.invoke();
                }
            }
        }else{
            return actionInvocation.invoke();
        }  
        return Action.LOGIN;
        
       
    }

}
