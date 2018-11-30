package com.zfsoft.mobile.peEvaluation.until;

/**
 * 肺活量分值计算工具
 * @author liucb
 */
public class PulmonaryUntil {
    
	/**
	 * 计算肺活量分值
	 * @return
	 */
	public static int countPulmonaryScore(int pulmonary,String gender){
		int pulmonaryScore = 0;
		if("1".equals(gender)){
			if(pulmonary>=5040){
				pulmonaryScore = 100;
			}else if(pulmonary>=4920){
				pulmonaryScore = 95;
			}else if(pulmonary>=4800){
				pulmonaryScore = 90;
			}else if(pulmonary>=4550){
				pulmonaryScore = 85;
			}else if(pulmonary>=4300){
				pulmonaryScore = 80;
			}else if(pulmonary>=4180){
				pulmonaryScore = 78;
			}else if(pulmonary>=4060){
				pulmonaryScore = 76;
			}else if(pulmonary>=3940){
				pulmonaryScore = 74;
			}else if(pulmonary>=3820){
				pulmonaryScore = 72;
			}else if(pulmonary>=3700){
				pulmonaryScore = 70;
			}else if(pulmonary>=3580){
				pulmonaryScore = 68;
			}else if(pulmonary>=3460){
				pulmonaryScore = 66;
			}else if(pulmonary>=3340){
				pulmonaryScore = 64;
			}else if(pulmonary>=3220){
				pulmonaryScore = 62;
			}else if(pulmonary>=3100){
				pulmonaryScore = 60;
			}else if(pulmonary>=2940){
				pulmonaryScore = 50;
			}else if(pulmonary>=2780){
				pulmonaryScore = 40;
			}else if(pulmonary>=2620){
				pulmonaryScore = 30;
			}else if(pulmonary>=2460){
				pulmonaryScore = 20;
			}else if(pulmonary>=2300){
				pulmonaryScore = 10;
			}
		}else{
			if(pulmonary>=3440){
				pulmonaryScore = 100;
			}else if(pulmonary>=3350){
				pulmonaryScore = 95;
			}else if(pulmonary>=3300){
				pulmonaryScore = 90;
			}else if(pulmonary>=3150){
				pulmonaryScore = 85;
			}else if(pulmonary>=3000){
				pulmonaryScore = 80;
			}else if(pulmonary>=2900){
				pulmonaryScore = 78;
			}else if(pulmonary>=2800){
				pulmonaryScore = 76;
			}else if(pulmonary>=2700){
				pulmonaryScore = 74;
			}else if(pulmonary>=2600){
				pulmonaryScore = 72;
			}else if(pulmonary>=2500){
				pulmonaryScore = 70;
			}else if(pulmonary>=2400){
				pulmonaryScore = 68;
			}else if(pulmonary>=2300){
				pulmonaryScore = 66;
			}else if(pulmonary>=2200){
				pulmonaryScore = 64;
			}else if(pulmonary>=2100){
				pulmonaryScore = 62;
			}else if(pulmonary>=2000){
				pulmonaryScore = 60;
			}else if(pulmonary>=1960){
				pulmonaryScore = 50;
			}else if(pulmonary>=1920){
				pulmonaryScore = 40;
			}else if(pulmonary>=1880){
				pulmonaryScore = 30;
			}else if(pulmonary>=1840){
				pulmonaryScore = 20;
			}else if(pulmonary>=1800){
				pulmonaryScore = 10;
			}
		}
		return pulmonaryScore;
	}
	
	public static void main(String[] args) {
		System.out.println(countPulmonaryScore(4551,"1"));
	}
}
