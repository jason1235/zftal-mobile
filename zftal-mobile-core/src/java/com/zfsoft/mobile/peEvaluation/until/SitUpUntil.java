package com.zfsoft.mobile.peEvaluation.until;

/**
 * 仰卧起坐分值计算工具
 * @author liucb
 */
public class SitUpUntil {
    
	public static int countSitUpScore(int sitUp){
		int sitUpScore = 0;
		if(sitUp>=56){
			sitUpScore = 100;
		}else if(sitUp>=54){
			sitUpScore = 95;
		}else if(sitUp>=52){
			sitUpScore = 90;
		}else if(sitUp>=49){
			sitUpScore = 85;
		}else if(sitUp>=46){
			sitUpScore = 80;
		}else if(sitUp>=44){
			sitUpScore = 78;
		}else if(sitUp>=42){
			sitUpScore = 76;
		}else if(sitUp>=40){
			sitUpScore = 74;
		}else if(sitUp>=38){
			sitUpScore = 72;
		}else if(sitUp>=36){
			sitUpScore = 70;
		}else if(sitUp>=34){
			sitUpScore = 68;
		}else if(sitUp>=32){
			sitUpScore = 66;
		}else if(sitUp>=30){
			sitUpScore = 64;
		}else if(sitUp>=28){
			sitUpScore = 62;
		}else if(sitUp>=26){
			sitUpScore = 60;
		}else if(sitUp>=24){
			sitUpScore = 50;
		}else if(sitUp>=22){
			sitUpScore = 40;
		}else if(sitUp>=20){
			sitUpScore = 30;
		}else if(sitUp>=18){
			sitUpScore = 20;
		}else if(sitUp>=16){
			sitUpScore = 10;
		}         
		
		return sitUpScore;
	}
}
