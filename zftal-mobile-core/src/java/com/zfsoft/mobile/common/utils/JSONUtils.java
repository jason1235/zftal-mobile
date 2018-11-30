package com.zfsoft.mobile.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JSONUtils {
	
	public static String HashMap2Json(Map<String, Object> map){
		
		String result = "{";  
		 
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {  
            Entry e = (Entry) it.next();  
            result += "'" + e.getKey() + "':";  
            result += "'" + e.getValue() + "',";  
        }  
        
        result = result.substring(0, result.lastIndexOf(","));  
        result += "}";  
        
		return result;
	}

	public static String xml2json(String xml) {
		try {
			XMLSerializer serializer = new XMLSerializer();
			return serializer.read(xml).toString();
		} catch (Exception e) {
			return "[]";
		}
		
	}
	
	public static String obj2json(Object obj) {
		if (obj != null) {
			if (obj instanceof List || obj instanceof Object[]) {
				return JSONArray.fromObject(obj).toString();
			} else {
				return JSONObject.fromObject(obj).toString();
			}
		} else {
			return "{}";
		}
		//return JSONArray.fromObject(obj).toString();
	}
	
	public static String obj2json2(Object obj) {
		if (obj instanceof List || obj instanceof Object[]) {
			return JSONArray.fromObject(obj).toString();
		}
		return JSONObject.fromObject(obj).toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xml = "<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><![CDATA[<meetingList><meeting><address>15F会议室</address><subject>阿克苏职业技术学院来访</subject><host>null</host><organizer>办公室、教务处</organizer><participants>教务处、人事处、继教处负责人，师范学院、农学院、医学院、艺术学院分管重点校专业群建设的领导</participants><pkid>046dd7bc-197d-4937-a63a-e52b92e77220</pkid><startdate>2017-10-16</startdate><starttime>15:00</starttime></meeting><meeting><address>15F会议室</address><subject>2017全国高职心理学术年会协调会</subject><host>龚永坚</host><organizer>学生处</organizer><participants>办公室、宣传部、团委、教务处、人事处、保卫处、计财处、后勤中心、公共学院负责人，信息学院、机电学院、师范学院、旅游学院分管领导，学生处相关人员</participants><pkid>c8c7ff53-0672-4ad5-9146-3d78bab20d03</pkid><startdate>2017-10-16</startdate><starttime>13:40</starttime></meeting><meeting><address>工会职工之家（学士楼1号2楼）</address><subject>工会工作会议</subject><host>朱雄才</host><organizer>工会</organizer><participants>各分工会负责人</participants><pkid>379fb058-996e-4f78-bcd6-bcc1837bdae9</pkid><startdate>2017-10-17</startdate><starttime>15:20</starttime></meeting><meeting><address>12F会议室</address><subject>重点校项目建设工作进度推进例会</subject><host>朱雄才</host><organizer>重点办</organizer><participants>校领导胡野，重点校建设7个子项目工作小组副组长、项目资金管理工作小组副组长，重点办相关人员</participants><pkid>f58562da-9825-4eed-ba9e-71490171d47f</pkid><startdate>2017-10-17</startdate><starttime>14:00</starttime></meeting><meeting><address>实验剧场</address><subject>校第九届大学生职业生涯大赛决赛</subject><host>龚永坚</host><organizer>就业服务中心</organizer><participants>省教育发展中心有关领导，学生处、团委、教务处、创业学院负责人，各二级学院分管领导、学工办负责人、就业干事，校职业生涯规划课程组全体教师，新生代表（每学院40人），就业服务中心、创业学院相关人员</participants><pkid>44068d35-f1c5-4df3-a56b-b6ca45713cd5</pkid><startdate>2017-10-18</startdate><starttime>18:00</starttime></meeting><meeting><address>机电学院三楼阅览室</address><subject>校级教研活动：重点校建设之专业对标分析研讨会</subject><host>胡野</host><organizer>重点办、教务处、机电学院</organizer><participants>教务处、督导处负责人，各二级学院分管教学领导，重点建设专业群所含各专业的负责人</participants><pkid>cf887b1b-3482-415c-84a7-9341dccea372</pkid><startdate>2017-10-19</startdate><starttime>13:30</starttime></meeting><meeting><address>1F报告厅</address><subject>校“互联网+”大学生创新创业大赛决赛</subject><host>null</host><organizer>创业学院</organizer><participants>校领导，学生处、团委、教务处、就业服务中心负责人，各二级学院分管领导及负责创新创业工作的老师，校创新创业课程组全体教师，学生代表（每学院6人），创业学院全体人员</participants><pkid>e897e276-1edf-4b27-879c-6bf3f57fade4</pkid><startdate>2017-10-19</startdate><starttime>14:00</starttime></meeting><meeting><address>10F会议室</address><subject>2018年度交流生、交换生工作布置会</subject><host>杨艳</host><organizer>港澳台办</organizer><participants>国合处负责人及相关人员，各二级学院学工办负责人</participants><pkid>1a269706-4595-45df-b8ef-0b88618192ba</pkid><startdate>2017-10-19</startdate><starttime>14:30</starttime></meeting><meeting><address>12、13、15F会议室</address><subject>省教改课题项目结题验收会</subject><host>胡野</host><organizer>教务处</organizer><participants>2015年省级教育教改、课堂教改项目负责人及项目组成员。第一组：教育教学改革组（15F会议室）；第二组：课堂教学改革大文组（12F会议室）；第三组：教育教学改革大理组（13F会议室）</participants><pkid>c712c964-1f42-4e2b-86f8-017bd35b40ff</pkid><startdate>2017-10-20</startdate><starttime>14:00</starttime></meeting><meeting><address>实验剧场</address><subject>师范教育110周年纪念活动暨特级教师论坛开幕式</subject><host>null</host><organizer>师范学院</organizer><participants>有关市领导、市教育局领导、市教研室领导，校领导，职能部门领导，师范学院班子成员、教工代表、学生代表，校友代表，合作基地代表</participants><pkid>7fb6e239-6a7c-441e-b8ae-31ee0ac91931</pkid><startdate>2017-10-20</startdate><starttime>15:00</starttime></meeting><meeting><address>稻花香广场</address><subject>校友钢琴捐赠仪式</subject><host>朱雄才</host><organizer>校友办</organizer><participants>校友代表，校领导余党军，校友办、宣传部、团委负责人，各二级学院分管领导、办公室主任、校友工作负责人、学生代表20人，校学生会及各社团负责人</participants><pkid>737c2c6e-ac67-4f1e-b6cd-5af034e7e931</pkid><startdate>2017-10-21</startdate><starttime>10:00</starttime></meeting><schoolyear>2017-2018</schoolyear><semester>第一学期</semester><week>6</week></meetingList>]]]]>><![CDATA[]]>";
		xml = xml.substring(xml.indexOf("<meetingList>"), xml.indexOf("</meetingList>")+14);
		System.out.println(xml);
		System.out.println(xml2json(xml));
		
		// TODO Auto-generated method stub
		/*String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><AppTypeItemList><AppItemType>"+
"	<module>                    "+
"    <id>jw-kb</id>             "+
"    <type>native</type>           "+
"    <name>我的课表</name>          "+
"		    <icon></icon>       "+
"		    <url></url>         "+
"		    <bak></bak>         "+
"<moduletype>jw</moduletype>    "+
" </module>                     "+
"	 <module>                   "+
"    <id>jw-ydxk</id>           "+
"    <type>web</type>              "+
"    <name>移动选课</name>          "+
"		    <icon></icon>       "+
"		    <url>http://</url>  "+
"		    <bak></bak>         "+
"<moduletype>jw</moduletype>    "+
"</module>                      "+
"</AppItemType>                 "+
"</AppTypeItemList>";*/
//		String xml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
//  " <soap:Body> "+
//  "    <ns2:getTicket2Response xmlns:ns2=\"http://service.ca.webservice.zfsoft.com/\"> "+
//  "       <return><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?><ResultInfo><code>1</code><message>用户名或密码错误。</message></ResultInfo>]]></return> "+
//  "    </ns2:getTicket2Response> "+
//  " </soap:Body> "+
//  " </soap:Envelope> ";
//		String test = xml2json(xml);
//		System.out.println(xml2json(xml));
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("aaa", "aav");
//		map.put("bbb", "bbv");
//		System.out.println(obj2json2(map));
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		list.add(map);
//		list.add(map);
//		System.out.println(obj2json2(list));
	}

}
