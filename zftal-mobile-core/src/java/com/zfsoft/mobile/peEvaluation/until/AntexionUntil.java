package com.zfsoft.mobile.peEvaluation.until;

/**
 * 坐位体前屈分值计算工具类
 * @author liucb
 */
public class AntexionUntil {
 
	public static int countAntexionScore(double antexion,String gender){
		int antexionScore = 0;
		if("1".equals(gender)){
			if(antexion>=24.9){
				antexionScore = 100;
			}else if(antexion>=23.1){
				antexionScore = 95;
			}else if(antexion>=21.3){
				antexionScore = 95;
			}else if(antexion>=19.5){
				antexionScore = 85;
			}else if(antexion>=17.7){
				antexionScore = 80;
			}else if(antexion>=16.3){
				antexionScore = 78;
			}else if(antexion>=14.9){
				antexionScore = 76;
			}else if(antexion>=13.5){
				antexionScore = 74;
			}else if(antexion>=12.1){
				antexionScore = 72;
			}else if(antexion>=10.7){
				antexionScore = 70;
			}else if(antexion>=9.3){
				antexionScore = 68;
			}else if(antexion>=7.9){
				antexionScore = 66;
			}else if(antexion>=6.5){
				antexionScore = 64;
			}else if(antexion>=5.1){
				antexionScore = 62;
			}else if(antexion>=3.7){
				antexionScore = 60;
			}else if(antexion>=2.7){
				antexionScore = 50;
			}else if(antexion>=1.7){
				antexionScore = 40;
			}else if(antexion>=0.7){
				antexionScore = 30;
			}else if(antexion>=-0.3){
				antexionScore = 20;
			}else if(antexion>=-1.3){
				antexionScore = 10;
			}
		}else{
			if(antexion>=25.8){
				antexionScore = 100;
			}else if(antexion>=24.0){
				antexionScore = 95;
			}else if(antexion>=22.2){
				antexionScore = 95;
			}else if(antexion>=20.6){
				antexionScore = 85;
			}else if(antexion>=19.0){
				antexionScore = 80;
			}else if(antexion>=17.7){
				antexionScore = 78;
			}else if(antexion>=16.4){
				antexionScore = 76;
			}else if(antexion>=15.1){
				antexionScore = 74;
			}else if(antexion>=13.8){
				antexionScore = 72;
			}else if(antexion>=12.5){
				antexionScore = 70;
			}else if(antexion>=11.2){
				antexionScore = 68;
			}else if(antexion>=9.9){
				antexionScore = 66;
			}else if(antexion>=8.6){
				antexionScore = 64;
			}else if(antexion>=7.3){
				antexionScore = 62;
			}else if(antexion>=6.0){
				antexionScore = 60;
			}else if(antexion>=5.2){
				antexionScore = 50;
			}else if(antexion>=4.4){
				antexionScore = 40;
			}else if(antexion>=3.6){
				antexionScore = 30;
			}else if(antexion>=2.8){
				antexionScore = 20;
			}else if(antexion>=2.0){
				antexionScore = 10;
			}
		}
		return antexionScore;
	}
}
