package com.zfsoft.mobile.peEvaluation.until;

/**
 * 50m跑分值计算工具类
 * @author liucb
 */
public class FiftyRunUntil {

	public static int countFiftyRunScore(double fiftyRun,String gender){
		int fiftyRunScore = 0;
		if("1".equals(gender)){
			if(fiftyRun>=10.1){
				fiftyRunScore = 10;
			}else if(fiftyRun>=9.9){
				fiftyRunScore = 20;
			}else if(fiftyRun>=9.7){
				fiftyRunScore = 30;
			}else if(fiftyRun>=9.5){
				fiftyRunScore = 40;
			}else if(fiftyRun>=9.3){
				fiftyRunScore = 50;
			}else if(fiftyRun>=9.1){
				fiftyRunScore = 60;
			}else if(fiftyRun>=8.9){
				fiftyRunScore = 62;
			}else if(fiftyRun>=8.7){
				fiftyRunScore = 64;
			}else if(fiftyRun>=8.5){
				fiftyRunScore = 66;
			}else if(fiftyRun>=8.3){
				fiftyRunScore = 68;
			}else if(fiftyRun>=8.1){
				fiftyRunScore = 70;
			}else if(fiftyRun>=7.9){
				fiftyRunScore = 72;
			}else if(fiftyRun>=7.7){
				fiftyRunScore = 74;
			}else if(fiftyRun>=7.5){
				fiftyRunScore = 76;
			}else if(fiftyRun>=7.3){
				fiftyRunScore = 78;
			}else if(fiftyRun>=7.1){
				fiftyRunScore = 80;
			}else if(fiftyRun>=7.0){
				fiftyRunScore = 85;
			}else if(fiftyRun>=6.9){
				fiftyRunScore = 90;
			}else if(fiftyRun>=6.8){
				fiftyRunScore = 95;
			}else if(fiftyRun>=6.7){
				fiftyRunScore = 100;
			}
		}else{
			if(fiftyRun>=11.3){
				fiftyRunScore = 10;
			}else if(fiftyRun>=11.1){
				fiftyRunScore = 20;
			}else if(fiftyRun>=10.9){
				fiftyRunScore = 30;
			}else if(fiftyRun>=10.7){
				fiftyRunScore = 40;
			}else if(fiftyRun>=10.5){
				fiftyRunScore = 50;
			}else if(fiftyRun>=10.3){
				fiftyRunScore = 60;
			}else if(fiftyRun>=10.1){
				fiftyRunScore = 62;
			}else if(fiftyRun>=9.9){
				fiftyRunScore = 64;
			}else if(fiftyRun>=9.7){
				fiftyRunScore = 66;
			}else if(fiftyRun>=9.5){
				fiftyRunScore = 68;
			}else if(fiftyRun>=9.3){
				fiftyRunScore = 70;
			}else if(fiftyRun>=9.1){
				fiftyRunScore = 72;
			}else if(fiftyRun>=8.9){
				fiftyRunScore = 74;
			}else if(fiftyRun>=8.7){
				fiftyRunScore = 76;
			}else if(fiftyRun>=8.5){
				fiftyRunScore = 78;
			}else if(fiftyRun>=8.3){
				fiftyRunScore = 80;
			}else if(fiftyRun>=8.0){
				fiftyRunScore = 85;
			}else if(fiftyRun>=7.7){
				fiftyRunScore = 90;
			}else if(fiftyRun>=7.6){
				fiftyRunScore = 95;
			}else if(fiftyRun>=7.5){
				fiftyRunScore = 100;
			}
		}
		return fiftyRunScore;
	}
}
