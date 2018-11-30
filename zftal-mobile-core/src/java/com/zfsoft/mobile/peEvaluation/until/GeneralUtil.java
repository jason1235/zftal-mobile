package com.zfsoft.mobile.peEvaluation.until;

import java.text.DecimalFormat;

import com.zfsoft.mobile.peEvaluation.query.PEDataQuery;


//综合评定计算方法
public class GeneralUtil {

	public static String getGeneral(PEDataQuery peDataQuery,String gender){
		String general = "";
		DecimalFormat df = new DecimalFormat("0.00");
		Double result = (double) 0;
		try {
			//判断性别
			if ("1".equals(gender)) {
				//男
				result = (Double.parseDouble(peDataQuery.getBMI() == null?"0":peDataQuery.getBMI()) * 0.15) + (Double.parseDouble(peDataQuery.getPulmonaryScore() == null?"0":peDataQuery.getPulmonaryScore()) * 0.15) + 
								(Double.parseDouble(peDataQuery.getFiftyRunScore() == null?"0":peDataQuery.getFiftyRunScore()) * 0.2) + (Double.parseDouble(peDataQuery.getJumpScore() == null?"0":peDataQuery.getJumpScore()) * 0.1) + 
								(Double.parseDouble(peDataQuery.getAntexionScore() == null?"0":peDataQuery.getAntexionScore()) * 0.1) + (Double.parseDouble(peDataQuery.getOneThousandRunScore() == null?"0":peDataQuery.getOneThousandRunScore()) * 0.2) + 
								(Double.parseDouble(peDataQuery.getPullUpScore() == null?"0":peDataQuery.getPullUpScore()) * 0.1);
			}else {
				//女
				result = (Double.parseDouble(peDataQuery.getBMI() == null?"0":peDataQuery.getBMI()) * 0.15) + (Double.parseDouble(peDataQuery.getPulmonaryScore() == null?"0":peDataQuery.getPulmonaryScore()) * 0.15) + 
								(Double.parseDouble(peDataQuery.getFiftyRunScore() == null?"0":peDataQuery.getFiftyRunScore()) * 0.2) + (Double.parseDouble(peDataQuery.getJumpScore() == null?"0":peDataQuery.getJumpScore()) * 0.1) + 
								(Double.parseDouble(peDataQuery.getAntexionScore() == null?"0":peDataQuery.getAntexionScore()) * 0.1) + (Double.parseDouble(peDataQuery.getEightHundredRunScore() == null?"0":peDataQuery.getEightHundredRunScore()) * 0.2) + 
								(Double.parseDouble(peDataQuery.getSitUpScore() == null?"0":peDataQuery.getSitUpScore()) * 0.1);
			}
			
			general = df.format(result);
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			return general;
		}
	}
}
