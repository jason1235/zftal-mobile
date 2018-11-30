package com.zfsoft.mobile.servlet.wiseduServlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.common.Config;
import com.google.gson.Gson;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.UploadFileUtil;
import com.zfsoft.mobile.MD5Util.MD5Util;
import com.zfsoft.mobile.common.service.IMobileCommonService;
import com.zfsoft.mobile.common.utils.Authentication;
import com.zfsoft.mobile.common.utils.MobileSystemHolder;
import com.zfsoft.mobile.servlet.MobileLoginServlet;
import com.zfsoft.mobile.servlet.entity.LoginEntity;
import com.zfsoft.mobile.servlet.entity.ResultEntity;
import com.zfsoft.mobile.servlet.service.commonHttp.CommonHttp;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author zhangxu
 *
 */
public class WiseduServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(MobileLoginServlet.class);
	private final String infromation=Config.getString("mobile.infromation");
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//String sign = request.getParameter("m_sign");
//		String sign = new String(request.getParameter("m_sign").getBytes("ISO8859-1"), "UTF-8");
////		String sign = StringUtil.isEmpty(request.getParameter("m_sign")) ? 
////   			 "" : java.net.URLDecoder.decode(request.getParameter("m_sign"), "UTF-8");
//		String appKey = Config.getString("wiseduAppkey");
//		String secretKey = Config.getString("wiseduSecretKey");
//		response.setContentType("text/html;charset=UTF-8");  
//		PrintWriter out = response.getWriter();
//		Gson gson = new Gson();
//		if(StringUtil.isEmpty(appKey)
//				|| StringUtil.isEmpty(secretKey)){
//			logger.error("后台没有配置金智单点登录Appkey和SecretKey");
//			ResultEntity result = new ResultEntity<LoginEntity>(0, "后台没有配置金智单点登录！", null);
//			out.print(gson.toJson(result));
//		    out.flush();
//		    out.close();
//		}else if (StringUtil.isEmpty(sign)){//判断sign字符串是否为空
//			logger.error("用户身份验证签名参数为空");
//			ResultEntity result = new ResultEntity<LoginEntity>(0, "身份参数值为空！", null);
//			out.print(gson.toJson(result));
//		    out.flush();
//		    out.close();
//		}else{
//			CloudUser cu = new CloudUser();
//			logger.error("sign:"+sign);
//			cu = JSON.parseObject(sign, CloudUser.class);
//			if (cu == null || cu.getSign() == null){
//				// SccException.throwSccException(“云登录失败”);  编写自己代码
//				//校验签名是否正确
//				logger.error("用户为空或用户身份签名为空");
//				ResultEntity result = new ResultEntity<LoginEntity>(0, "用户为空或用户身份签名为空！", null);
//				out.print(gson.toJson(result));
//			    out.flush();
//			    out.close();
//			}else{
//				if (!CloudUtils.check(cu,secretKey)){
//				    // SccException.throwSccException(“云登录失败”);  编写自己代码
//					logger.error("用户为空或用户身份签名为空");
//					ResultEntity result = new ResultEntity<LoginEntity>(0, "用户登录失败，请联系管理员核对！", null);
//					out.print(gson.toJson(result));
//				    out.flush();
//				    out.close();
//				}else{
//					String result = wiseduLogin(cu.getIdsNo(), "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS");
//					out.print(result);
//				    out.flush();
//				    out.close();
//				}
//			}
//		}
		// SccException.throwSccException(“云登录失败”);  编写自己代码
		
		
		

		
	}
	
	
//	public String wiseduLogin(String userName, String sign){
//		Gson gson = new Gson();
//		LoginEntity entity = new LoginEntity();
//		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(sign)){
//			ResultEntity result = new ResultEntity<LoginEntity>(0, "参数传值出错！", new LoginEntity());
//			return gson.toJson(result);
//		}
//		try {
//			
//		
//		if(infromation.equals("0")){
//			logger.error("获取登陆接口："+"userName="+userName+",strKey="+sign);
//			}
//		StringBuilder sb = new StringBuilder();
//		if (Authentication.authenticate(sign)) {
//			LoginModel model = new LoginModel();
//			model.setYhm(userName);
//			Map<String, String> map = new HashMap<String, String>();
//			ILoginService loginService = (ILoginService)SpringHolder.getBean("loginService");
//			User user = loginService.cxYhxx(model);
//			YhglModel yhglModel = new YhglModel();
//			yhglModel.setZgh(userName);
//			String keyCode = null;
//			try {
//				keyCode = MD5Util.md5Encode(
//										userName + 
//										Config.getString("key", "WYNn2rNOtkuMGGlPrFSaMB0rQoBUmssS") + 
//										System.currentTimeMillis()
//									);
//				if(StringUtils.isEmpty(keyCode))
//					throw new Exception("产生秘钥异常!");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			yhglModel.setStrKey(keyCode);
//			loginService.updateStrKey(yhglModel);
//			if (user != null) {
//				if(!StringUtil.isEmpty(user.getSfqy()) && !user.getSfqy().equals("1")){
//					ResultEntity result = new ResultEntity<LoginEntity>(0, "用户没有被启用！", new LoginEntity());
//					return gson.toJson(result);
//				}
//				String dqxnxq = "";
//				String loginXML = "";
//				String appName = MobileSystemHolder.getPropertiesValue("app_name");
//				entity.setName(user.getXm());
//				if ("student".equals(user.getYhlx())) {
//					entity.setRole("XS");
//				} else {
//					entity.setRole("JS");
//				}
//				entity.setDepartment(user.getBmmc());
//				entity.setSchoolName("");
//				entity.setClassName("");
//				entity.setGradeName("");
//				entity.setUserId(user.getYhm());
//				entity.setAppname(appName);
//				sb.append("<yhm>").append(user.getYhm()).append("</yhm>");
//				entity.setNowSchoolYearTerm(dqxnxq);
//				entity.setApp_token(keyCode);
//			}else{
//				ResultEntity result = new ResultEntity<LoginEntity>(0, "后台显示用户为空", entity);
//				return gson.toJson(result);
//			}
//			if(infromation.equals("0")){
//				logger.error("登陆接口返回为："+entity);
//			}	
//			//ArrayList<LoginEntity> list = new ArrayList<LoginEntity>(); 
//			//list.add(entity);
//			//我的头像读取start
//			String headPicturePath = null;
//			IMobileCommonService mobileCommonService = (IMobileCommonService) SpringHolder.getBean("mobileCommonService");
//			List<ImageDB> imageList = mobileCommonService.getMyPicture(userName);
//			ImageDB image = imageList != null && imageList.size() > 0 ? imageList.get(0) : null;
//			if(image == null){
//				logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
//				headPicturePath = "";
//			}else{
//				String path = image.getPath();
//				byte[] content = image.getFileContent();
//				String headname = image.getFileName();
//				String filename = StringUtil.isEmpty(headname) ? userName+"headPicture" : headname;
//				
//				HttpServletRequest request = ServletActionContext.getRequest();
//				
//				if(content == null && StringUtil.isEmpty(path)){
//					logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
//					headPicturePath = "";
//				}else{
//					String pathFile = BaseHolder.getPropertiesValue("MyPicture","headPicture");
//					String pathurl = request.getSession().getServletContext().getRealPath("/") + pathFile;
//					File newFile = new File(pathurl);
//					if (!newFile.exists()) {
//						newFile.mkdir();
//					}
//					String url = request.getSession().getServletContext().getRealPath("/") + path;
//					url = url.replace("\\", "/");
//					File outFile = new File(url);
//					if (!outFile.exists()) {
//						try {
//							outFile.createNewFile();
//							
//							if(content == null){
//								logger.error("我的门户接口:头像数据库图片不存在，路径也不存在！");
//								headPicturePath = "";
//							}
//							ImageIO.setUseCache(false);
//							ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
//							BufferedImage newImage = ImageIO.read(byteArrayInputStream);
//							ImageIO.write(newImage, UploadFileUtil.checkedFileName(filename), outFile);
//						} catch (IOException e) {
//							logger.error("我的头像生成产生异常----！");
//							logger.error(e,e.fillInStackTrace());
//						}
//					} 
//					headPicturePath = getImageHost() + path;
//					entity.setHeadPicturePath(headPicturePath);
//				}
//			}
//			//我的头像读取end
//			logger.error("成功----:"+entity);
//			ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(1, "成功", entity);
//			logger.error("成功result----:"+result);
//			return gson.toJson(result);
//		}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		ResultEntity<LoginEntity> result = new ResultEntity<LoginEntity>(0, "对不起，您无权访问", new LoginEntity());
//		return gson.toJson(result);
//		/*FailureEntity failure = new FailureEntity();
//		failure.failure = "对不起，您无权访问";
//		return gson.toJson(failure);*/
//	}
	/**
	 * 获取服务器访问路径
	 * @return
	 */
	private String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}
	
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	
	public static void main(String[] args) {
//		CloudUser cu = new CloudUser();
//		String sign = "{'birthday':0,'enterYear':0,'gender':0,'idsNo':'201625080702','nickName':'测试','sign':{'appKey':'snc-njnu','check':'f9fe2186f0875cf1f312b8ebc4ce5aa89754bcb9','nonce':'mnT_zkt4','timestamp':1494315677914,'token':'c888e634eb646e63958c4e47814b9a70f61085ba'}}";
//		cu = JSON.parseObject(sign, CloudUser.class);
//		try {
//			System.out.println(java.net.URLEncoder.encode(sign, "UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//System.out.println(CloudUtils.check(cu,"iVeNtORywaRBERaCsjadskadkslowiwyTTHoLEmlwlueoqq"));
	}
}
