package com.zfsoft.hrm.baseinfo.infoclass.cache.test;

import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;

/** 
 * @author jinjj
 * @date 2013-2-1 下午03:01:33 
 *  
 */
public class InfoClassTriggerGeneratorTest extends BaseTxTestCase {

	private StringBuilder sb = new StringBuilder();
	private InfoClass clazz;
	private String targetOwner = "chg_hrm";
	private String[] ignoreColumn = new String[]{"globalid","lastmodifytime"};
	
	@Test
	public void test(){
		String classId = "D4A4195F8CD36BEFE040007F0100101A";
		//List<InfoClass> classes = InfoClassCache.getInfoClasses();
		IInfoClassService service = (IInfoClassService)this.applicationContext.getBean("baseInfoClassService");
		clazz = service.getFullInfoClass(classId);
//		clazz = InfoClassCache.getInfoClass(classId);
		createTrigger();
		System.out.println(sb.toString());
	}
	
	private void createTrigger(){
		sb.append("create or replace trigger trig_hrm_"+clazz.getIdentityName()+" \n");
		sb.append("\t after insert or update or delete on "+clazz.getIdentityName()+" for each row \n");
		sb.append("declare \n");
		sb.append("\t num number;\n");
		sb.append("begin \n");
		sb.append("\t if inserting or updating then \n");
		sb.append("\t\t select count(*) into num from "+targetOwner+".gxjg_"+clazz.getIdentityName()+"zl where id=:new.globalid; \n");
		sb.append("\t\t if num > 0 then \n");
		createUpdateOperate();
		sb.append("\t else \n");
		createInsertOperate();
		sb.append("\t end if; \n");
		sb.append("\t elsif deleting then \n");
		createDeleteOperate();
		sb.append("\t end if; \n");
		sb.append("end; \n");
	}
	
	private void createUpdateOperate(){
		sb.append("\t\t update "+targetOwner+".gxjg_"+clazz.getIdentityName()+"zl set ");
		for(InfoProperty p : clazz.getProperties()){
			String column = p.getFieldName().toLowerCase();
			boolean include = true;
			for(String str:ignoreColumn){
				if(column.equalsIgnoreCase(str)){
					include = false;
					break;
				}
			}
			if(include){
				if(p.getTypeInfo().getDataType().equalsIgnoreCase("date")){
					String format = p.getTypeInfo().getFormat().toLowerCase().replaceAll("-", "");
					sb.append(column+"=to_char(:new."+column+",'"+format+"')");
				}else{
					sb.append(column+"=:new."+column);
				}
				sb.append(",");
			}
		}
		sb.append("zhgxsj=to_char(sysdate,'yyyymmddhh24miss'),scbz='n' where id=:new.globalid; \n");
	}
	
	private void createInsertOperate(){
		sb.append("\t\t insert into "+targetOwner+".gxjg_"+clazz.getIdentityName()+"zl(id,");
		for(InfoProperty p : clazz.getProperties()){
			String column = p.getFieldName().toLowerCase();
			boolean include = true;
			for(String str:ignoreColumn){
				if(column.equalsIgnoreCase(str)){
					include = false;
					break;
				}
			}
			if(include){
				sb.append(column);
				sb.append(",");
			}
		}
		sb.append("ZHGXSJ,SCBZ)values(:new.globalid,");
		for(InfoProperty p : clazz.getProperties()){
			String column = p.getFieldName().toLowerCase();
			boolean include = true;
			for(String str:ignoreColumn){
				if(column.equalsIgnoreCase(str)){
					include = false;
					break;
				}
			}
			if(include){
				if(p.getTypeInfo().getDataType().equalsIgnoreCase("date")){
					String format = p.getTypeInfo().getFormat().toLowerCase().replaceAll("-", "");
					sb.append("to_char(:new."+column+",'"+format+"')");
				}else{
					sb.append(":new."+column);
				}
				sb.append(",");
			}
		}
		sb.append("to_char(sysdate,'yyyymmddhh24miss'),'n'); \n");
	}
	
	private void createDeleteOperate(){
		sb.append("\t\t update "+targetOwner+".gxjg_"+clazz.getIdentityName()+"zl set ");
		sb.append("scbz = 'y',zhgxsj=to_char(sysdate,'yyyymmddhh24miss') where id=:OLD.globalid; \n");
	}
}
