package com.zfsoft.utility;

public class InterceptSrcForSubsystem {

	private static InterceptSrcForSubsystem instance;
	
	private Value2Entity[] interceptList;
	private Value2Entity[] interceptList2;
	
	private InterceptSrcForSubsystem(){
	}
	
	
	public Value2Entity[] getInterceptList() {
		return interceptList;
	}
	public Value2Entity[] getInterceptList2() {
		return interceptList2;
	}
	
	public void setInterceptList(Value2Entity[] interceptList) {
		this.interceptList = interceptList;
		instance=this;
	}
	
	public void setInterceptList2(Value2Entity[] interceptList2) {
		this.interceptList2 = interceptList2;
		instance=this;
	}

	public static Value2Entity[] getSrcList(){
		if(instance==null){
			return null;
		}
		return instance.getInterceptList();
	}
	
	public static Value2Entity[] getSrcList2(){
		if(instance==null){
			return null;
		}
		return instance.getInterceptList2();
	}
}
