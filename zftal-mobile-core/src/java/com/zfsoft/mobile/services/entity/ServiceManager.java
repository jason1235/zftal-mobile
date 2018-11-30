package com.zfsoft.mobile.services.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceManager {

	private String classId;
	
	private String classFwbm;
	
	private String classFwmc;
	
	private String classSsywxt;
	
	private String classSscp;
	
	private String classFwlx;
	
	private String classFbzt;
	
	private String classFwly;
	
	private String classFwms;
	
	private String classFwlj;
	
	private String classFwtbdz;
	
	private String classFwtbid;
	
	private int    classRdpx;
	
	private String classAppyydz;
	
	private Date classCjsj;
	
	private Date classGxsj;
	
	private String classCjzid;
	
	private String classGxzid;

	private String classDeleted;
	
	private String classShowway;
	
	private String classIosUrl;
	
	private String classAndroidUrl;
	
	private String classWXUrl;
	
	private String xtdz;
	
	private String appUrl;
	
	private String webUrl;
	
	private String choice;
	
	private String procode;
	
	private String xtbm;
	
	private String iosURLScheme;
	
	private String iosURLiTunes;
	
	private String fileId;
	
	private String filedz;
	
	private String apkfilename;
	
	private String newsid;
	
	private String otherFlag;
	
	private String iscommon;
	
	private String isSignal;
	
	private String signalUrl;
	
	private String signalXtbm;
	
	private String recommendFlag;
	
	public String getFiledz() {
		return filedz;
	}

	public void setFiledz(String filedz) {
		this.filedz = filedz;
	}

	public String getIosURLScheme() {
		return iosURLScheme;
	}

	public void setIosURLScheme(String iosURLScheme) {
		this.iosURLScheme = iosURLScheme;
	}

	public String getIosURLiTunes() {
		return iosURLiTunes;
	}

	public void setIosURLiTunes(String iosURLiTunes) {
		this.iosURLiTunes = iosURLiTunes;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getClassIosUrl() {
		return classIosUrl;
	}

	public void setClassIosUrl(String classIosUrl) {
		this.classIosUrl = classIosUrl;
	}

	public String getClassAndroidUrl() {
		return classAndroidUrl;
	}

	public void setClassAndroidUrl(String classAndroidUrl) {
		this.classAndroidUrl = classAndroidUrl;
	}

	public String getClassWXUrl() {
		return classWXUrl;
	}

	public void setClassWXUrl(String classWXUrl) {
		this.classWXUrl = classWXUrl;
	}

	private List<String> classSscpList = new ArrayList<String>();
	
	private List<String> classShowList = new ArrayList<String>();
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassFwbm() {
		return classFwbm;
	}

	public void setClassFwbm(String classFwbm) {
		this.classFwbm = classFwbm;
	}

	public String getClassFwmc() {
		return classFwmc;
	}

	public void setClassFwmc(String classFwmc) {
		this.classFwmc = classFwmc;
	}

	public String getClassSsywxt() {
		return classSsywxt;
	}

	public void setClassSsywxt(String classSsywxt) {
		this.classSsywxt = classSsywxt;
	}

	public String getClassSscp() {
		return classSscp;
	}

	public void setClassSscp(String classSscp) {
		if(classSscp != null){
			String[] list = classSscp.split(",");
			for(String temp : list){
				classSscpList.add(temp.trim());
			}
			//this.classSscpList = java.util.Arrays.asList(list);
		}
		this.classSscp = classSscp;
	}

	public String getClassFwlx() {
		return classFwlx;
	}

	public void setClassFwlx(String classFwlx) {
		this.classFwlx = classFwlx;
	}

	public String getClassFbzt() {
		return classFbzt;
	}

	public void setClassFbzt(String classFbzt) {
		this.classFbzt = classFbzt;
	}

	public String getClassFwly() {
		return classFwly;
	}

	public void setClassFwly(String classFwly) {
		this.classFwly = classFwly;
	}

	public String getClassFwms() {
		return classFwms;
	}

	public void setClassFwms(String classFwms) {
		this.classFwms = classFwms;
	}

	public String getClassFwlj() {
		return classFwlj;
	}

	public void setClassFwlj(String classFwlj) {
		this.classFwlj = classFwlj;
	}

	public String getClassFwtbdz() {
		return classFwtbdz;
	}

	public void setClassFwtbdz(String classFwtbdz) {
		this.classFwtbdz = classFwtbdz;
	}

	public String getClassFwtbid() {
		return classFwtbid;
	}

	public void setClassFwtbid(String classFwtbid) {
		this.classFwtbid = classFwtbid;
	}

	public int getClassRdpx() {
		return classRdpx;
	}

	public void setClassRdpx(int classRdpx) {
		this.classRdpx = classRdpx;
	}

	public String getClassAppyydz() {
		return classAppyydz;
	}

	public void setClassAppyydz(String classAppyydz) {
		this.classAppyydz = classAppyydz;
	}

	public Date getClassCjsj() {
		return classCjsj;
	}

	public void setClassCjsj(Date classCjsj) {
		this.classCjsj = classCjsj;
	}

	public Date getClassGxsj() {
		return classGxsj;
	}

	public void setClassGxsj(Date classGxsj) {
		this.classGxsj = classGxsj;
	}

	public String getClassCjzid() {
		return classCjzid;
	}

	public void setClassCjzid(String classCjzid) {
		this.classCjzid = classCjzid;
	}

	public String getClassGxzid() {
		return classGxzid;
	}

	public void setClassGxzid(String classGxzid) {
		this.classGxzid = classGxzid;
	}

	public void setClassDeleted(String classDeleted) {
		this.classDeleted = classDeleted;
	}

	public String getClassDeleted() {
		return classDeleted;
	}
	
	public void setClassShowway(String classShowway) {
		if(classShowway != null){
			String[] list = classShowway.split(",");
			for(String temp : list){
				classShowList.add(temp.trim());
			}
			//this.classSscpList = java.util.Arrays.asList(list);
		}
		this.classShowway = classShowway;
	}
	
	public String getClassShowway() {
		return classShowway;
	}
	
	public void setClassShowList(List<String> classShowList) {
		this.classShowList = classShowList;
	}
	
	public List<String> getClassShowList() {
		return classShowList;
	}


	public void setClassSscpList(List<String> classSscpList) {
		/*if(classSscp != null){
			String[] list = classSscp.split(", ");
			this.classSscpList = java.util.Arrays.asList(list);
		}*/
		this.classSscpList = classSscpList;
	}

	public List<String> getClassSscpList() {
		return classSscpList;
	}

	@Override  
	public int hashCode() {  
		return 1;
	}
	
	@Override  
    public boolean equals(Object obj) {  
		if (this == obj) {  
	        return true;  
	    }  
	    if (obj == null) {  
	        return false;  
	    }  
	    if(getClass() != obj.getClass()) {  
	        return false;  
	    }  
	    ServiceManager p = (ServiceManager)obj; 
	    
	    if (classId == null) {  
	        if(p.classId != null) {  
	            return false;  
	        }  
	    } else if (!classId.equals(p.classId)) {  
	        return false;  
	    }
	    
	    if (classFwbm == null) {  
	        if(p.classFwbm != null) {  
	            return false;  
	        }  
	    } else if (!classFwbm.equals(p.classFwbm)) {  
	        return false;  
	    }
	    
	    if (classFwmc == null) {  
	        if(p.classFwmc != null) {  
	            return false;  
	        }  
	    } else if (!classFwmc.equals(p.classFwmc)) {  
	        return false;  
	    }
	    
	    if (classSsywxt == null) {  
	        if(p.classSsywxt != null) {  
	            return false;  
	        }  
	    } else if (!classSsywxt.equals(p.classSsywxt)) {  
	        return false;  
	    }
	    
	    if (classSscp == null) {  
	        if(p.classSscp != null) {  
	            return false;  
	        }  
	    } else if (!classSscp.equals(p.classSscp	)) {  
	        return false;  
	    }
	    
	    if (classFwlx == null) {  
	        if(p.classFwlx != null) {  
	            return false;  
	        }  
	    } else if (!classFwlx.equals(p.classFwlx)) {  
	        return false;  
	    }
	    
	    if (classFbzt == null) {  
	        if(p.classFbzt != null) {  
	            return false;  
	        }  
	    } else if (!classFbzt.equals(p.classFbzt)) {  
	        return false;  
	    }
	    
	    if (classFwly == null) {  
	        if(p.classFwly != null) {  
	            return false;  
	        }  
	    } else if (!classFwly.equals(p.classFwly)) {  
	        return false;  
	    }
	    
	    if (classFwms == null) {  
	        if(p.classFwms != null) {  
	            return false;  
	        }  
	    } else if (!classFwms.equals(p.classFwms)) {  
	        return false;  
	    }
	    
	    if (classFwlj == null) {  
	        if(p.classFwlj != null) {  
	            return false;  
	        }  
	    } else if (!classFwlj.equals(p.classFwlj)) {  
	        return false;  
	    }
	    
	    if (classFwtbdz == null) {  
	        if(p.classFwtbdz != null) {  
	            return false;  
	        }  
	    } else if (!classFwtbdz.equals(p.classFwtbdz)) {  
	        return false;  
	    }
	    
	    if (classFwtbid == null) {  
	        if(p.classFwtbid != null) {  
	            return false;  
	        }  
	    } else if (!classFwtbid.equals(p.classFwtbid)) {  
	        return false;  
	    }
	    
	    if (classRdpx != p.classRdpx) {  
	        return false;  
	    }
	    
	    if (classAppyydz == null) {  
	        if(p.classAppyydz != null) {  
	            return false;  
	        }  
	    } else if (!classAppyydz.equals(p.classAppyydz)) {  
	        return false;  
	    }
	    
	    return true;
	}

	/**
	 * @return the xtdz
	 */
	public String getXtdz() {
		return xtdz;
	}

	/**
	 * @param xtdz the xtdz to set
	 */
	public void setXtdz(String xtdz) {
		this.xtdz = xtdz;
	}

	/**
	 * @return the choice
	 */
	public String getChoice() {
		return choice;
	}

	/**
	 * @param choice the choice to set
	 */
	public void setChoice(String choice) {
		this.choice = choice;
	}

	/**
	 * @return the procode
	 */
	public String getProcode() {
		return procode;
	}

	/**
	 * @param procode the procode to set
	 */
	public void setProcode(String procode) {
		this.procode = procode;
	}

	/**
	 * @return the xtbm
	 */
	public String getXtbm() {
		return xtbm;
	}

	/**
	 * @param xtbm the xtbm to set
	 */
	public void setXtbm(String xtbm) {
		this.xtbm = xtbm;
	}

	/**
	 * @return the appUrl
	 */
	public String getAppUrl() {
		return appUrl;
	}

	/**
	 * @param appUrl the appUrl to set
	 */
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	/**
	 * @return the webUrl
	 */
	public String getWebUrl() {
		return webUrl;
	}

	/**
	 * @param webUrl the webUrl to set
	 */
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public void setApkfilename(String apkfilename) {
		this.apkfilename = apkfilename;
	}

	public String getApkfilename() {
		return apkfilename;
	}

	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}

	public String getNewsid() {
		return newsid;
	}

	public void setOtherFlag(String otherFlag) {
		this.otherFlag = otherFlag;
	}

	public String getOtherFlag() {
		return otherFlag;
	}

	public void setIscommon(String iscommon) {
		this.iscommon = iscommon;
	}

	public String getIscommon() {
		return iscommon;
	}

	public void setIsSignal(String isSignal) {
		this.isSignal = isSignal;
	}

	public String getIsSignal() {
		return isSignal;
	}

	public void setSignalUrl(String signalUrl) {
		this.signalUrl = signalUrl;
	}

	public String getSignalUrl() {
		return signalUrl;
	}

	public void setSignalXtbm(String signalXtbm) {
		this.signalXtbm = signalXtbm;
	}

	public String getSignalXtbm() {
		return signalXtbm;
	}

	public void setRecommendFlag(String recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	public String getRecommendFlag() {
		return recommendFlag;
	}


	
	
}
