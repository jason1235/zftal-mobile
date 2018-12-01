package com.zfsoft.hrm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-13
 * @version V1.0.0
 */
public class WordExportUtil {
		
		private static WordExportUtil instance;
		private Configuration configuration;
		private WordExportUtil(String path) throws Exception{
			if(configuration == null){
				configuration = new Configuration();
				configuration.setDirectoryForTemplateLoading(new File(path));
			}
		}
		/**
		 * 该方法不关闭输出流 请自行关闭
		 * @param out 输出流对象
		 * @param exportTemp 模板名
		 * @param data 填充数据对象
		 * @throws Exception
		 */
		public void exportTable(OutputStream out,String exportTemp,Map<String, Object> data) throws Exception{
			Writer writer = null;
			//模版文件名
			Template template=configuration.getTemplate(exportTemp,"utf-8");
			writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			template.process(data, writer);
		}

		/**
		 * 获取实例（有参）
		 * 需要传入模板文件夹路径 
		 * 方法不存储对象实例
		 * @throws Exception 
		 */
		public static WordExportUtil getInstance(String path) throws Exception {
			path = Struts2Utils.getSession().getServletContext().getRealPath(path);
			return new WordExportUtil(path);
		}
		/**
		 * 获取实例（无参） 
		 * 读取项目路径下WEB-INF/classes/exportModel作为模板文件夹
		 * 会存储对象实例
		 * @return
		 * @throws Exception
		 */
		public static WordExportUtil getInstance() throws Exception {
			if(instance == null){
				String path="WEB-INF"+File.separator+"classes"+File.separator+"exportModel";;
				path = Struts2Utils.getSession().getServletContext().getRealPath(path);
				instance = new WordExportUtil(path);
			}
			return instance;
		}
		/**
		 * 该方法不关闭输出流 请自行关闭
		 * @param out 输出流对象
		 * @param exportTemp 模板名
		 * @param spBillInstance 
		 * @param spBillConfig
		 * @throws Exception
		 */
		public void exportTable(OutputStream out,String exportTemp,SpBillInstance spBillInstance,SpBillConfig spBillConfig) throws Exception{
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("table", new ExportMap(getValueMapForExport(spBillConfig, spBillInstance)));
			Writer writer = null;
			//模版文件名
			Template template=configuration.getTemplate(exportTemp,"utf-8");
			writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			template.process(data, writer);
		}
		
//		public static void main(String[] args) throws Exception {
//			OutputStream out = new FileOutputStream("D:\\test\\test.doc");
//			File file = new File("D:\\test\\新建 BMP 图像.bmp");
//			InputStream in = new FileInputStream(file);
//			byte[] b = new byte[1024];
//			byte[] sum = new byte[0];
//			while(in.read(b)!=-1){
//				sum = ArrayUtils.addAll(sum, b);
//			}
//			System.out.println();
////			System.out.println(s.getBytes().length);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("image", new BASE64Encoder().encode(sum));
//			new FreeMarkUtil("D:\\test").exportTable(out, "grjl.xml", map);
//		}
		/**
		 * 解析信息类获取导出数据DATA MAP
		 * 参数num 为标示字段，
		 * 为空时
		 * 【key】 为   【 信息类标识符.属性字段名】（全小写）
		 * 不为空时
		 * 【key】 为   【 信息类标识符.属性字段名（参数num】（全小写）
		 */
		public static Map<String, String> getValueMapForExport(DynaBean dynaBean,Integer num) {
			if(dynaBean==null || dynaBean.getClazz() == null){
				return new HashMap<String, String>();
			}
			Map<String, String> valueMap=new HashMap<String, String>();
			InfoClass clazz = dynaBean.getClazz();
			String identityName = clazz.getIdentityName().toLowerCase();
			byte[] def_photo = null;
			for (InfoProperty p : clazz.getProperties()) {
					/**
					 * 照片或照片
					 * 因为照片及图片存在默认设置 
					 * 与其他信息主要从实例中获取循环条件有所不同 
					 * 需要循环配置属性
					 */
				String key = StringUtils.lowerCase(identityName+"."+p.getFieldName());
				if(num!=null){
					key+="("+num;
				}
				if(Type.PHOTO.equals(p.getFieldType())||Type.IMAGE.equals(p.getFieldType())){
					byte[] fileContent = null;
					if(dynaBean.getValue(p.getFieldName())==null||StringUtil.isEmpty(dynaBean.getValueString(p.getFieldName()))){
						if(def_photo == null){
							ImageDB image = ImageDBUtil.getImageDBByGuid("default_"+p.getFieldType());
							def_photo = getContent(image,p.getFieldType());
						}
						fileContent = def_photo;
					}else{
						ImageDB image = ImageDBUtil.getImageDBByGuid(dynaBean.getValueString(p.getFieldName()));
						fileContent = getContent(image,p.getFieldType());
					}
					valueMap.put(key, new BASE64Encoder().encode(fileContent));
				}
				else{
					valueMap.put(key, dynaBean.getViewHtml().get(p.getFieldName()));
				}
			}
			return valueMap;
		}
		
		
		/**
		 * 解析自定义表单获取导出数据DATA MAP
		 * 【key】 为   【 表单标识符.属性字段名】
		 * 当表单允许多条属性时 采用 【表单标识符.属性字段名(数字】 进行区分
		 * 数字从1开始
		 */
		public static Map<String, String> getValueMapForExport(SpBillConfig spBillConfig,
				SpBillInstance spBillInstance) {
			if(spBillInstance==null || spBillInstance.getXmlValueClasses().getValueClasses() == null){
				return new HashMap<String, String>();
			}
			Map<String, String> valueMap=new HashMap<String, String>();
			XmlBillClasses xmlBillClasses=spBillConfig.getXmlBillClasses();
			XmlBillClass xmlBillClass;
			String identityName;
			String fieldName;
			byte[] def_photo = null;
			byte[] def_image = null;
			for (XmlValueClass xmlValueClass : 
					spBillInstance.getXmlValueClasses().getValueClasses()) {
				xmlBillClass=xmlBillClasses.getBillClassById(xmlValueClass.getBillClassId());
				if(xmlBillClass == null){//排除修改表单模板后，实例对象中的历史数据，查询表单类为空的问题
					continue;
				}
				xmlValueClass.setXmlBillClass(xmlBillClass);
				List<XmlValueEntity> xmlValueEntityList=xmlValueClass.getValueEntities();
				if(xmlValueEntityList==null||xmlValueEntityList.size()==0){
					continue;
				}
				int num=0;
				if(xmlBillClass.getMaxLength()>1){
					num=1;
				}
				identityName=xmlBillClass.getIdentityName();
				for (XmlValueEntity xmlValueEntity : xmlValueEntityList) {
					if(xmlValueEntity.getValueProperties()==null){
						continue;
					}
					/**
					 * 照片
					 * 因为照片及图片存在默认设置 
					 * 与其他信息主要从实例中获取循环条件有所不同 
					 * 需要循环配置属性
					 */
					for(XmlBillProperty phoneProperty:xmlBillClass.getPhotoBillPropertys()){
						XmlValueProperty xmlValueProperty = xmlValueEntity.getValuePropertyById(phoneProperty.getId());
						String key = StringUtils.lowerCase(identityName+"."+phoneProperty.getFieldName());
						byte[] fileContent = null;
						if(xmlValueProperty==null||"undefined".equals(xmlValueProperty.getValue())||StringUtil.isEmpty(xmlValueProperty.getValue())){
							if(def_photo == null){
								ImageDB image = ImageDBUtil.getImageDBByGuid("default_"+Type.PHOTO);
								def_photo = getContent(image,Type.PHOTO);
							}
							fileContent = def_photo;
						}else{
							ImageDB image = ImageDBUtil.getImageDBByGuid(xmlValueProperty.getValue());
							fileContent = getContent(image,Type.PHOTO);
						}
						valueMap.put(key, new BASE64Encoder().encode(fileContent));
					}
					/**
					 * 图片
					 * 因为照片及图片存在默认设置 
					 * 与其他信息主要从实例中获取循环条件有所不同 
					 * 需要循环配置属性
					 */
					for(XmlBillProperty imageProperty:xmlBillClass.getImageBillPropertys()){
						XmlValueProperty xmlValueProperty = xmlValueEntity.getValuePropertyById(imageProperty.getId());
						String key = StringUtils.lowerCase(identityName+"."+imageProperty.getFieldName());
						byte[] fileContent = null;
						if(xmlValueProperty==null||"undefined".equals(xmlValueProperty.getValue())||StringUtil.isEmpty(xmlValueProperty.getValue())){
							if(def_image == null){
								ImageDB image = ImageDBUtil.getImageDBByGuid("default_"+Type.IMAGE);
								def_image = getContent(image,Type.IMAGE);
							}
							fileContent = def_image;
						}else{
							ImageDB image = ImageDBUtil.getImageDBByGuid(xmlValueProperty.getValue());
							fileContent = getContent(image,Type.IMAGE);
						}
						valueMap.put(key, new BASE64Encoder().encode(fileContent));
					}
					/**
					 * 其他内容
					 */
					for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
						XmlBillProperty property = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
						if(property ==null){
							continue;
						}
						fieldName=property.getFieldName();
						String key = StringUtils.lowerCase(identityName+"."+fieldName);
						if(num>0){
							key+="("+num;
						}
						if("CODE".equals(property.getFieldType())){
							String value = CodeUtil.getItemValue(property.getCodeId(), xmlValueProperty.getValue());
							if(StringUtil.isEmpty(valueMap.get(key))){
								valueMap.put(key, value);
							}
						}
						if("SIGLE_SEL".equals(property.getFieldType())){
							String value =  "1".equals(xmlValueProperty.getValue())?"是":"否";
							if(StringUtil.isEmpty(valueMap.get(key))){
								valueMap.put(key, value);
							}
						}
						else{
							if(StringUtil.isEmpty(valueMap.get(key))){
								valueMap.put(key, xmlValueProperty.getValue());
							}
						}
					}
					if(num>0)
						num++;
				}
				
			}
			return valueMap;
		}
		
		
		
		private static byte[] getContent(ImageDB imageDB,String type){
			String path = Struts2Utils.getSession().getServletContext().getRealPath("/");
			if(imageDB==null){
				path+="upload"+File.separator+"default_"+type.toLowerCase()+".jpg";
				return Byte_File_Object.getBytesFromFile(new File(path));
			}else{
				if(StringUtil.isEmpty(imageDB.getPath())){
					return imageDB.getFileContent();
				}else{
					if(imageDB.getFileContent()==null&&!StringUtil.isEmpty(imageDB.getPath())){
						path+="upload"+File.separator+imageDB.getGuid()+"."+imageDB.getSuffixs();
						return Byte_File_Object.getBytesFromFile(new File(path));
					}else{
						return imageDB.getFileContent();
					}
				}
			}
		}
	}
