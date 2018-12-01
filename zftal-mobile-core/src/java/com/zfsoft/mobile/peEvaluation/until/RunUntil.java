package com.zfsoft.mobile.peEvaluation.until;

/**
 * 1000m(男)800m(女)分值计算
 * @author liucb
 */
public class RunUntil {

	public static int countRunScore(double run,String gender){
		int runScore = 0;
		if("1".equals(gender)){//男生1000m
			if(run>=6.12){
				runScore = 10;
			}else if(run>=5.52){
				runScore = 20;
			}else if(run>=5.32){
				runScore = 30;
			}else if(run>=5.12){
				runScore = 40;
			}else if(run>=4.52){
				runScore = 50;
			}else if(run>=4.32){
				runScore = 60;
			}else if(run>=4.27){
				runScore = 62;
			}else if(run>=4.22){
				runScore = 64;
			}else if(run>=4.17){
				runScore = 66;
			}else if(run>=4.12){
				runScore = 68;
			}else if(run>=4.07){
				runScore = 70;
			}else if(run>=4.02){
				runScore = 72;
			}else if(run>=3.57){
				runScore = 74;
			}else if(run>=3.52){
				runScore = 76;
			}else if(run>=3.47){
				runScore = 78;
			}else if(run>=3.42){
				runScore = 80;
			}else if(run>=3.34){
				runScore = 85;
			}else if(run>=3.27){
				runScore = 90;
			}else if(run>=3.22){
				runScore = 95;
			}else if(run>=3.17){
				runScore = 100;
			}
		}else{//女生800m
			if(run>=5.24){
				runScore = 10;
			}else if(run>=5.14){
				runScore = 20;
			}else if(run>=5.04){
				runScore = 30;
			}else if(run>=4.54){
				runScore = 40;
			}else if(run>=4.44){
				runScore = 50;
			}else if(run>=4.34){
				runScore = 60;
			}else if(run>=4.29){
				runScore = 62;
			}else if(run>=4.24){
				runScore = 64;
			}else if(run>=4.19){
				runScore = 66;
			}else if(run>=4.14){
				runScore = 68;
			}else if(run>=4.09){
				runScore = 70;
			}else if(run>=4.04){
				runScore = 72;
			}else if(run>=3.59){
				runScore = 74;
			}else if(run>=3.54){
				runScore = 76;
			}else if(run>=3.49){
				runScore = 78;
			}else if(run>=3.44){
				runScore = 80;
			}else if(run>=3.37){
				runScore = 85;
			}else if(run>=3.30){
				runScore = 90;
			}else if(run>=3.24){
				runScore = 95;
			}else if(run>=3.18){
				runScore = 100;
			}
		}
		return runScore;
	}
}
