package com.zfsoft.hrm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author ChenMinming
 * @date 2013-7-3
 * @version V1.0.0
 */
public class ExportMap extends HashMap<String, Object>{

	private static final long serialVersionUID = 154768165489L;
	private Map<String, Integer> lengMap;
	public ExportMap() {
	}
	
	public ExportMap(Map<? extends String, ? extends Object> m) {
		super.putAll(m);
	}
	
	@Override
	public Object get(Object key) {				
		Object o = super.get(key);
		if(o == null) o=do4Key(key);
		if(o == null&&key.toString().indexOf('(')==-1){
			o=super.get(key.toString()+"(1");
		}
		if(o == null) return "";
		if(o instanceof String){
			String str = o.toString();
			str = str.replaceAll("<br/>", "  ");
			return str;
		}
		return o;
	}
	
	private Object do4Key(Object key){
		return do4Key(key.toString());
	}

	private Object do4Key(String key){
		
		if(!key.matches(".*:\\d+"))return null;
		
		String[] s1 = key.split(":");
		
		if(s1.length!=2) return null; 
		
		String[] s2 = s1[0].split(",");
		int num = Integer.valueOf(s1[1]);
		int arrayLength=0;
		
		for (String s : s2) {
			Integer n = getLengMap().get(s.split("\\.")[0]);
			if(n==null) continue;;
			if(arrayLength+n<num){
				arrayLength+=n;
				continue;
			}
			num=num-arrayLength;
			String v="";
			for (String str : s.split(" ")) {
				v+=get(str+"("+num)+" ";
			}
			return v;
		}
		return null;
	}
//	public static void main(String[] args) {
//		ExportMap a = new ExportMap();
//		a.put("xlxwb.xm(1", "xlxwb.xm1");
//		a.put("xlxwb.xm(2", "xlxwb.xm2");
//		a.put("xlxwb.xm(3", "xlxwb.xm3");
//		a.put("xlxwb1.xbm(1", "xlxwb1.xbm1");
//		a.put("xlxwb1.xbm(2", "xlxwb1.xbm2");
//		a.put("xlxwb1.xbm(3", "xlxwb1.xbm3");
//		a.put("xlxwb1.xbmA(1", "xlxwb1.xbmA1");
//		a.put("xlxwb1.xbmA(2", "xlxwb1.xbmA2");
//		System.out.println(a.get("xlxwb.xm:3"));
//		System.out.println(a.get("xlxwb.xm,xlxwb1.xbm:3"));
//		System.out.println(a.get("xlxwb.xm,xlxwb1.xbm:6"));
//		System.out.println(a.get("xlxwb1.xbm,xlxwb.xm:3"));
//		System.out.println(a.get("xlxwb1.xbmA,xlxwb.xm:4"));
//		System.out.println(a.get("xlxwb1.xbmA xlxwb1.xbm,xlxwb.xm:2"));
//	}

	/**
	 * 返回
	 */
	public Map<String, Integer> getLengMap() {
		if(lengMap==null){
			lengMap = new HashMap<String, Integer>();
			Set<String> keys = keySet();
			for (String key : keys) {
				if(!key.matches(".*\\(\\d+"))continue;
				Integer n = Integer.valueOf(key.split("\\(")[1]);
				String k = key.split("\\.")[0];
				Integer kn = lengMap.get(k);
				if(kn==null||kn<n){
					lengMap.put(k, n);
				}
			}
		}
		return lengMap;
	}

}