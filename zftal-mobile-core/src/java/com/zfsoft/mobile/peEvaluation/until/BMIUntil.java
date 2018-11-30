package com.zfsoft.mobile.peEvaluation.until;

import java.math.BigDecimal;

import com.zfsoft.mobile.peEvaluation.query.PEDataQuery;

/**
 * BMI值计算工具类
 * @author liucb
 */
public class BMIUntil {
	
	/**
	 * BMI值计算方法
	 * @param peDataQuery
	 * @return
	 */
	public static double countBMIScore(PEDataQuery peDataQuery){
		String height = peDataQuery.getHeight();
		String weight = peDataQuery.getWeight();
		if(height!=null&&weight!=null){
			double heightDbVal = Double.parseDouble(height);
			double weightDbVal = Double.parseDouble(weight);
			
			double result = weightDbVal/(heightDbVal*heightDbVal);
			BigDecimal b = new BigDecimal(result);   
			return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}else{
			return 0;
		}
	}
	
	public static void main(String[] args) {
		PEDataQuery peDataQuery = new PEDataQuery();
		peDataQuery.setHeight("1.78");
		peDataQuery.setWeight("70");
		System.out.println(BMIUntil.countBMIScore(peDataQuery));
	}
}
