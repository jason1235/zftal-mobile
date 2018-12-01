/**
 * 
 */
package com.zfsoft.mobile.txl.action;

import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.util.Calendar;
import java.util.Date;
import java.util.List;  
  
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;  
import org.apache.poi.hssf.usermodel.HSSFPicture;  
import org.apache.poi.hssf.usermodel.HSSFPictureData;  
import org.apache.poi.hssf.usermodel.HSSFShape;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;  
import org.apache.poi.ss.usermodel.PictureData;  
import org.apache.poi.ss.usermodel.WorkbookFactory;  
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * @author zhangxu
 * @description 
 * @date 2017-7-26 下午03:41:40
 */
public class test {
	
	public static String getStr(String str1, String str2,String str3){
		StringBuilder sb = new StringBuilder();
		sb.append(str1.substring(0, str2.length()+str1.indexOf(str2))).
		append(str3).
		append(str1.substring(str2.length()+str1.indexOf(str2) , str1.length() ) );
		return sb.toString();
	}

	public static void main(String[] args) throws DocumentException {  
		System.out.println(
				getStr(
					"http://mail.zjjy.com.cn/coremail/main.jsp?sid=BAySGkRRpHKDiTvyYCRRQwPdekXGHbve", 
					"coremail", 
					"iphone"
					)
				);
//		String xml="<?xml version=\"1.0\" encoding=\"utf-8\" ?><table><row><dqz>8</dqz><kcmc>女生健美操10</kcmc><rkjs>严丽萍</rkjs><kcdm>120</kcdm><kcxz>必修</kcxz><xf>1.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>3</xqj><js>8,9</js><skjs>操场</skjs><kcb>女生健美操10<br>周三第8,9节{第1-16周}<br>严丽萍<br>操场</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>刑法总论</kcmc><rkjs>袁继红</rkjs><kcdm>309020</kcdm><kcxz>必修</kcxz><xf>3.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>1</xqj><js>6,7</js><skjs>文1-102</skjs><kcb>刑法总论<br>周一第6,7节{第1-16周}<br>袁继红<br>文1-102</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>刑法总论</kcmc><rkjs>袁继红</rkjs><kcdm>309020</kcdm><kcxz>必修</kcxz><xf>3.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>1</xqj><js>8</js><skjs>文1-102</skjs><kcb>刑法总论<br>周一第8节{第1-16周}<br>袁继红<br>文1-102</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>物权法</kcmc><rkjs>李海龙</rkjs><kcdm>309095</kcdm><kcxz>必修</kcxz><xf>2.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>4</xqj><js>6,7</js><skjs>文1-120</skjs><kcb>物权法<br>周四第6,7节{第1-16周}<br>李海龙<br>文1-120</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>大学物理</kcmc><rkjs>凌俐</rkjs><kcdm>A01016</kcdm><kcxz>必修</kcxz><xf>2.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>1</xqj><js>3,4</js><skjs>教七201</skjs><kcb>大学物理<br>周一第3,4节{第1-16周}<br>凌俐<br>教七201</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>ACCESS数据库应用</kcmc><rkjs>谢红霞</rkjs><kcdm>A01020</kcdm><kcxz>必修</kcxz><xf>3.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>3</xqj><js>1,2</js><skjs>理4-420</skjs><kcb>ACCESS数据库应用<br>周三第1,2节{第1-16周}<br>谢红霞<br>理4-420</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>ACCESS数据库应用</kcmc><rkjs>谢红霞</rkjs><kcdm>A01020</kcdm><kcxz>必修</kcxz><xf>3.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>5</xqj><js>3,4</js><skjs>理4-420</skjs><kcb>ACCESS数据库应用<br>周五第3,4节{第1-16周}<br>谢红霞<br>理4-420</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>大学英语(IV)</kcmc><rkjs>徐莉</rkjs><kcdm>A08019</kcdm><kcxz>必修</kcxz><xf>4.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>2</xqj><js>6,7</js><skjs>教一411</skjs><kcb>大学英语(IV)<br>周二第6,7节{第1-16周}<br>徐莉<br>教一411</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>大学英语(IV)</kcmc><rkjs>徐莉</rkjs><kcdm>A08019</kcdm><kcxz>必修</kcxz><xf>4.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>4</xqj><js>1,2</js><skjs>教一411</skjs><kcb>大学英语(IV)<br>周四第1,2节{第1-16周}<br>徐莉<br>教一411</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>毛泽东思想和中国特色社会主义理论体系概论</kcmc><rkjs>陈麟</rkjs><kcdm>A20001</kcdm><kcxz>必修</kcxz><xf>4.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>2</xqj><js>1,2</js><skjs>教一421</skjs><kcb>毛泽东思想和中国特色社会主义理论体系概论<br>周二第1,2节{第1-16周}<br>陈麟<br>教一421</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>毛泽东思想和中国特色社会主义理论体系概论</kcmc><rkjs>陈麟</rkjs><kcdm>A20001</kcdm><kcxz>必修</kcxz><xf>4.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>2</xqj><js>3,4</js><skjs>教一421</skjs><kcb>毛泽东思想和中国特色社会主义理论体系概论<br>周二第3,4节{第1-16周}<br>陈麟<br>教一421</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>法理学</kcmc><rkjs>蔡萍琴</rkjs><kcdm>B09024</kcdm><kcxz>必修</kcxz><xf>2.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>4</xqj><js>8,9</js><skjs>文1-102</skjs><kcb>法理学<br>周四第8,9节{第1-16周}<br>蔡萍琴<br>文1-102</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>应用口才训练</kcmc><rkjs>范莉</rkjs><kcdm>C09051</kcdm><kcxz>限选</kcxz><xf>2.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>3</xqj><js>6,7</js><skjs>文3-103</skjs><kcb>应用口才训练<br>周三第6,7节{第1-16周}<br>范莉<br>文3-103</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>经济学基础</kcmc><rkjs>吴奕立</rkjs><kcdm>D05001</kcdm><kcxz>公选</kcxz><xf>2.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>2</xqj><js>8,9</js><skjs>教三505</skjs><kcb>经济学基础<br>周二第8,9节{第1-16周}<br>吴奕立<br>教三505</kcb><dsz> </dsz></row><row><dqz>8</dqz><kcmc>现代管理基础</kcmc><rkjs>黄宇驰</rkjs><kcdm>D05002</kcdm><kcxz>公选</kcxz><xf>2.0</xf><skz>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</skz><xqj>4</xqj><js>10,11</js><skjs>教三105</skjs><kcb>现代管理基础<br>周四第10,11节{第1-16周}<br>黄宇驰<br>教三105</kcb><dsz> </dsz></row></table>";
//		Document document = DocumentHelper.parseText(xml);
//        Element elementTemplate = document.getRootElement();  
//        Element ResultInfo = (Element)elementTemplate.selectSingleNode("//table"); 
//        Element code = (Element)elementTemplate.selectSingleNode("//row"); 
//
//		if(ResultInfo != null && code != null){
//			String errorCode = elementTemplate.elementText("dqz");
//			System.out.println(errorCode);
//		}
		
//		Date nowDate = new Date();
//		Calendar cal = Calendar.getInstance();
//        cal.setTime(nowDate);
//		int week_of_year = cal.get(Calendar.DAY_OF_WEEK);
//		System.out.println(week_of_year);
        
	}  

	private static void savePic(int i, PictureData pic) throws Exception {  
	
		String ext = pic.suggestFileExtension();  
		
		byte[] data = pic.getData();  
		if (ext.equals("jpeg")) {  
	    FileOutputStream out = new FileOutputStream(  
	            "C:\\Users\\Administrator\\Desktop\\images\\test\\pict" + i + ".jpg");  
	    out.write(data);  
	    out.close();  
		}  
		if (ext.equals("png")) {  
		    FileOutputStream out = new FileOutputStream(  
		            "C:\\Users\\Administrator\\Desktop\\images\\test\\pict" + i + ".png");  
			    out.write(data);  
			    out.close();  
			}  
		}  
	}
