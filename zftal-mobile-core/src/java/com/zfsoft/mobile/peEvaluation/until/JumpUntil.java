package com.zfsoft.mobile.peEvaluation.until;

/**
 * 立定跳远分值计算工具
 * @author liucb
 */
public class JumpUntil {

	public static int countJumpScore(int jump,String gender){
		int jumpScore = 0;
        if("1".equals(gender)){
        	if(jump>=273){
        		jumpScore = 100;
        	}else if(jump>=268){
        		jumpScore = 95;
        	}else if(jump>=263){
        		jumpScore = 90;
        	}else if(jump>=256){
        		jumpScore = 85;
        	}else if(jump>=248){
        		jumpScore = 80;
        	}else if(jump>=244){
        		jumpScore = 78;
        	}else if(jump>=240){
        		jumpScore = 76;
        	}else if(jump>=236){
        		jumpScore = 74;
        	}else if(jump>=232){
        		jumpScore = 72;
        	}else if(jump>=228){
        		jumpScore = 70;
        	}else if(jump>=224){
        		jumpScore = 68;
        	}else if(jump>=220){
        		jumpScore = 66;
        	}else if(jump>=216){
        		jumpScore = 64;
        	}else if(jump>=212){
        		jumpScore = 62;
        	}else if(jump>=208){
        		jumpScore = 60;
        	}else if(jump>=203){
        		jumpScore = 50;
        	}else if(jump>=198){
        		jumpScore = 40;
        	}else if(jump>=193){
        		jumpScore = 30;
        	}else if(jump>=188){
        		jumpScore = 20;
        	}else if(jump>=183){
        		jumpScore = 10;
        	}
        }else{
        	if(jump>=207){
        		jumpScore = 100;
        	}else if(jump>=201){
        		jumpScore = 95;
        	}else if(jump>=195){
        		jumpScore = 90;
        	}else if(jump>=188){
        		jumpScore = 85;
        	}else if(jump>=181){
        		jumpScore = 80;
        	}else if(jump>=178){
        		jumpScore = 78;
        	}else if(jump>=175){
        		jumpScore = 76;
        	}else if(jump>=172){
        		jumpScore = 74;
        	}else if(jump>=169){
        		jumpScore = 72;
        	}else if(jump>=166){
        		jumpScore = 70;
        	}else if(jump>=163){
        		jumpScore = 68;
        	}else if(jump>=160){
        		jumpScore = 66;
        	}else if(jump>=157){
        		jumpScore = 64;
        	}else if(jump>=154){
        		jumpScore = 62;
        	}else if(jump>=151){
        		jumpScore = 60;
        	}else if(jump>=146){
        		jumpScore = 50;
        	}else if(jump>=141){
        		jumpScore = 40;
        	}else if(jump>=136){
        		jumpScore = 30;
        	}else if(jump>=131){
        		jumpScore = 20;
        	}else if(jump>=126){
        		jumpScore = 10;
        	}
        } 		
		return jumpScore;
	}
}
