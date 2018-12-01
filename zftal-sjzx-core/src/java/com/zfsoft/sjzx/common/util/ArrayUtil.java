package com.zfsoft.sjzx.common.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
    public static List<?> getList(List<?> list,int pageNo,int pageSize){
		List<Object> result = new ArrayList<Object>();
		int total = list.size();
		if(list!=null&&total>0){
			int count=(int)(((pageNo==0?1:pageNo)-1)*pageSize);
			int i=count;
			 while(i<=(int)(count+pageSize-1)&&i<total){				
				 result.add(list.get(i));				
				 i++;
				 if(i>total)break;
			 }
		}
		return result;
	}
    public static List<?> getList(List<?> list,int pageNo,int pageSize,int nextPage){
		List<Object> result = new ArrayList<Object>();
		int total = list.size();
		if(list!=null&&total>0){
		    int count=(int)(((pageNo==0?1:pageNo)-1)*pageSize);
            int i=count;
             while(i<=(int)(count+pageSize-1)&&i<total){                
                 result.add(list.get(i));               
                 i++;
                 if(i>total)break;
             }
		}
		return result;
	}
}
