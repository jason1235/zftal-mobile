package com.zfsoft.mobile.services.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.enums.FwbmEnum;
import com.zfsoft.mobile.common.enums.ProductEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.entity.ExamAnwserEntity;
import com.zfsoft.mobile.services.entity.ExamDyJsEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IQuestionService;
import com.zfsoft.util.base.StringUtil;

public class QuestionAction  extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8081788107504980301L;
	
	private String op;
	
	private IQuestionService questionService;
	
	private ExamPaperEntity query = new ExamPaperEntity();
	
	private ExamPaperEntity model = new ExamPaperEntity();
	
	private ExamAnwserEntity examAnwserQuery = new ExamAnwserEntity();
	
	private ExamQuestionEntity questionQuery = new ExamQuestionEntity();
	
	private ExamQuestionEntity questionModel = new ExamQuestionEntity();
	
	private String[] id;
	
	public ExamAnwserEntity getExamAnwserQuery() {
		return examAnwserQuery;
	}

	public void setExamAnwserQuery(ExamAnwserEntity examAnwserQuery) {
		this.examAnwserQuery = examAnwserQuery;
	}

	/**
	 * 
	* @author: zhangxu
	* @Title: answerList 
	* @Description: 查询当前时间的答题人对每道题的答案
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String answerList(){ 
		query = questionService.getExamPaperList(query).get(0);
		examAnwserQuery.setPapermainid(query.getPapermainid());
		PageList<ExamAnwserEntity> examAnwserList = questionService.getExamAnwserList(examAnwserQuery);
		getValueStack().set("examAnwserList", examAnwserList);
		return "answerList";
	}
	
	public String list(){
		PageList<ExamPaperEntity> list = questionService.getExamPaperList(query);
		getValueStack().set("list", list);
		
		return "list";
	}
	
	public String toadd(){
		op = "add";
		return "edit";
	}
	
	public String toModify(){
		op = "modify";
		return "edit";
	}
	
	public String save(){
		if(StringUtil.isEmpty(model.getPapermainid())){
			model.setCreater(SessionFactory.getUser().getYhm());
			model.setCreatetime(new Date());
			questionService.insert(model);
			ExamPaperEntity query = new ExamPaperEntity();
			query.setPapermainid(model.getPapermainid());
			//query.setPapermainname(model.getPapermainname());
			ExamPaperEntity entity = questionService.getExamPaperList(query).get(0);
			Map<String, Object> data = new HashMap<String, Object>();
            data.put("success", true);
            data.put("id", entity.getPapermainid());
            getValueStack().set(DATA, data);
    		return DATA;
		}
		return DATA;
	}
	/**
	 * 保存修改后的索引顺序Action
	 * @return
	 */
	public String saveAll() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if(!StringUtil.isEmpty(model.getEndtimeStr()) && !StringUtil.isEmpty(model.getStarttimeStr())){
				Date date = sdf.parse(model.getEndtimeStr());
				model.setEndtime(date);
				Date startdate = sdf.parse(model.getStarttimeStr());
				model.setStarttime(startdate);
				if(date.getTime() < startdate.getTime()){
					this.setErrorMessage("问卷结束时间不能小于开始时间!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
				Date nowDate = new Date();
				if(date.getTime() < nowDate.getTime()){
					this.setErrorMessage("问卷结束时间不能小于当前时间!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
				ExamDyJsEntity entity = new ExamDyJsEntity();
				entity.setPapermainid(model.getPapermainid());
				List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
				if(ExamDyJsList == null || ExamDyJsList.size() == 0){
					this.setErrorMessage("请至少添加一个问卷群组!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
			//if(!StringUtil.isEmpty(model.getEndtimeStr()) && !StringUtil.isEmpty(model.getStarttimeStr())){
				questionService.update(model);
			//}
			int min = 1;
			Map<String, String> map = null;
			if (id != null) {
				try {
					for (int i =0; i <id.length; i++) {
						map = new HashMap<String, String>();
						map.put("index", (i+min)+"");
						map.put("id", id[i]);
						questionService.updateIndex(map);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.setSuccessMessage("保存成功！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		} catch (ParseException e1) {
			e1.printStackTrace();
			this.setErrorMessage("保存失败");
			getValueStack().set(DATA, getMessage());
			return DATA;
		} 
	}
	
	public String getExamResult(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String papermainid = request.getParameter("papermainid");
		questionQuery.setPapermainid(papermainid);
		List<ExamQuestionEntity> questionList = questionService.getQuestionList(questionQuery);
		List<Map<String, String>> itemValue = null;
		Map<String, String> map = null;
		Map<String, String> itemvalueTemp = null;
		List<String> item = new ArrayList<String>();
		item.add("1(分)");item.add("2(分)");item.add("3(分)");item.add("4(分)");item.add("5(分)");
		for (int i = 0; i < questionList.size(); i++) {
			int countpeople = 0;
			String type = questionList.get(i).getType();
			
			//questionList.get(i).setTitle(StringUtil.collapseString(questionList.get(i).getTitle(), 16, "\r\n"));
			
			itemValue = new ArrayList<Map<String, String>>();
			List<String> newItem = new ArrayList<String>() ;
			for(int j = 0; j < questionList.get(i).getItem().size(); j++){
				newItem.add(StringUtil.collapseString(questionList.get(i).getItem().get(j), 10, "\r\n"));
			}
			questionList.get(i).setItem(newItem);
			
			
			if(type.equals("0")){
				for(int j = 0 ; j < questionList.get(i).getItem().size() ; j++){
					map = new HashMap<String, String>();
					map.put("questionid", questionList.get(i).getQuestionid());
					map.put("value", String.valueOf(j+1));
					Integer count = questionService.getValueStatic(map);
					itemvalueTemp = new HashMap<String, String>();
					itemvalueTemp.put("value", String.valueOf(count));
					itemvalueTemp.put("name", questionList.get(i).getItem().get(j));
					itemValue.add(itemvalueTemp);
					countpeople += count;
				}
			}
			if(type.equals("1")){
				for(int j = 0 ; j < questionList.get(i).getItem().size() ; j++){
					map = new HashMap<String, String>();
					map.put("questionid", questionList.get(i).getQuestionid());
					map.put("value", String.valueOf(j+1));
					Integer count = questionService.getValueStatic(map);
					itemvalueTemp = new HashMap<String, String>();
					itemvalueTemp.put("value", String.valueOf(count));
					itemvalueTemp.put("name", questionList.get(i).getItem().get(j));
					itemValue.add(itemvalueTemp);
				}
				map = new HashMap<String, String>();
				map.put("questionid", questionList.get(i).getQuestionid());
				Integer counts = questionService.getValueStatic(map);
				countpeople += counts;
			}
			if(type.equals("3")){
				for(int j = 1 ; j < 6; j++){
					map = new HashMap<String, String>();
					map.put("questionid", questionList.get(i).getQuestionid());
					map.put("value", String.valueOf(j));
					Integer count = questionService.getValueStatic(map);
					itemvalueTemp = new HashMap<String, String>();
					itemvalueTemp.put("value", String.valueOf(count));
					itemvalueTemp.put("name", String.valueOf(j)+"(分)");
					itemValue.add(itemvalueTemp);
					countpeople += count;
				}
				questionList.get(i).setItem(item);
			}
			questionList.get(i).setTotalpeople(countpeople);
			questionList.get(i).setItemValue(itemValue);
		}
		getValueStack().set("questionList", JSONUtils.obj2json(questionList));
		for(int i = 0; i < questionList.size(); i++){
			List<Map<String, String>>  itemValueList = questionList.get(i).getItemValue();
			for(int j = 0 ; j < itemValueList.size(); j++){
				itemValueList.get(j).put("name", itemValueList.get(j).get("name").replaceAll("\r\n", ""));
			}
			questionList.get(i).setItemValue(itemValueList);
		}
		getValueStack().set("questionListlength", questionList);
		
		return "ExamResult";
	}
	
	public String checkHasQue(){
		ExamQuestionEntity entity = new ExamQuestionEntity();
		entity.setPapermainid(query.getPapermainid());
		List<ExamQuestionEntity> questionList = questionService.getQuestionList(entity);
		if(questionList == null || questionList.size() == 0){
			this.setErrorMessage("该问卷不存在题型，请先修改此问卷添加题型再审核！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{
			this.setSuccessMessage("操作成功！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
	}
	
	
	public String toModifyQuestion(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String starttimeStr = request.getParameter("starttimeStr");
		String endtimeStr = request.getParameter("endtimeStr");
		model = questionService.getExamPaperList(query).get(0);
		model.setStarttimeStr(!StringUtil.isEmpty(starttimeStr) ? starttimeStr : model.getStarttimeStr());
		model.setEndtimeStr(!StringUtil.isEmpty(endtimeStr) ? endtimeStr : model.getEndtimeStr());
		questionQuery.setPapermainid(model.getPapermainid());
		List<ExamQuestionEntity> questionList = questionService.getQuestionList(questionQuery);
		getValueStack().set("questionList", questionList);
		ExamDyJsEntity entity = new ExamDyJsEntity();
		entity.setPapermainid(model.getPapermainid());
		List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
		getValueStack().set("ExamDyJsList", JSONUtils.obj2json(ExamDyJsList));
		if(StringUtil.isEmpty(op)){
			op = "modify";
		}
		return "que_modify";
	}
	
	public String control(){
		questionService.control(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toShenHe(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String starttimeStr = request.getParameter("starttimeStr");
		String endtimeStr = request.getParameter("endtimeStr");
		model = questionService.getExamPaperList(query).get(0);
		model.setStarttimeStr(!StringUtil.isEmpty(starttimeStr) ? starttimeStr : model.getStarttimeStr());
		model.setEndtimeStr(!StringUtil.isEmpty(endtimeStr) ? endtimeStr : model.getEndtimeStr());
		questionQuery.setPapermainid(model.getPapermainid());
		List<ExamQuestionEntity> questionList = questionService.getQuestionList(questionQuery);
		getValueStack().set("questionList", questionList);
		ExamDyJsEntity entity = new ExamDyJsEntity();
		entity.setPapermainid(model.getPapermainid());
		List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
		getValueStack().set("ExamDyJsList", JSONUtils.obj2json(ExamDyJsList));
		return "toShenHe";
	}
	
	public String addQz(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String qzid = request.getParameter("qzid");
		String papermainid = request.getParameter("papermainid");
		
		String qzidArr[] = qzid.split(",");
		ExamDyJsEntity entity = null;
		for(int i = 0; i < qzidArr.length ; i++){
			entity = new ExamDyJsEntity();
			entity.setPapermainid(papermainid);
			entity.setQzid(qzidArr[i]);
			List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
			if(ExamDyJsList==null || (ExamDyJsList!=null&&ExamDyJsList.size()==0)){
				questionService.insertExamDyJs(entity);
			}
		}
		
		entity = new ExamDyJsEntity();
		entity.setPapermainid(papermainid);
		List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
		String qzmc = "";
		if(ExamDyJsList!=null&&ExamDyJsList.size()>=0){
			for(ExamDyJsEntity dyEntity: ExamDyJsList){
				qzmc += dyEntity.getQzmc()+",";
			}
			if(!StringUtil.isEmpty(qzmc)){
				qzmc = qzmc.substring(0, qzmc.length()-1);
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        data.put("qzmc", qzmc);
        getValueStack().set(DATA, data);
		return DATA;
		
		/*this.setSuccessMessage("添加群组成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;*/
	}
	
	public String deleteQz(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String qzid = request.getParameter("qzid");
		String papermainid = request.getParameter("papermainid");
		
		String qzidArr[] = qzid.split(",");
		ExamDyJsEntity entity = null;
		for(int i = 0; i < qzidArr.length ; i++){
			entity = new ExamDyJsEntity();
			entity.setPapermainid(papermainid);
			entity.setQzid(qzidArr[i]);
			List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
			//if(ExamDyJsList==null || (ExamDyJsList!=null&&ExamDyJsList.size()==0)){
				questionService.deleteExamDyJs(entity);
			//}
		}
		
		entity = new ExamDyJsEntity();
		entity.setPapermainid(papermainid);
		List<ExamDyJsEntity> ExamDyJsList = questionService.getExamDyJsList(entity);
		String qzmc = "";
		if(ExamDyJsList!=null&&ExamDyJsList.size()>=0)
		for(ExamDyJsEntity dyEntity: ExamDyJsList){
			qzmc += dyEntity.getQzmc()+",";
		}
		if(!StringUtil.isEmpty(qzmc)){
			qzmc = qzmc.substring(0, qzmc.length()-1);
		}
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        data.put("qzmc", qzmc);
        getValueStack().set(DATA, data);
		return DATA;
		/*this.setSuccessMessage("删除群组成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;*/
	}
	
	public String removeQuestion(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String questionid = request.getParameter("questionid");
		ExamQuestionEntity questionQuery = new ExamQuestionEntity();
		questionQuery.setQuestionid(questionid);
		questionService.removeQuestion(questionQuery);
		this.setSuccessMessage("删除题型成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        ExamQuestionEntity questionQuery = null;
        for (String idCheck : id) {
            tids.add(idCheck.trim());
            questionQuery = new ExamQuestionEntity();
            questionQuery.setPapermainid(idCheck);
            questionService.removeQuestion(questionQuery);
        }
        param.put("ids", tids);
        questionService.remove(param);
		//serviceManagerService.remove(model);
		this.setSuccessMessage("删除成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String addType(){
		op = "add";
		String papermainid = model.getPapermainid();
		getValueStack().set("papermainid", papermainid);
		return "addType";
	}
	
	public String typeSave(){
		if(StringUtil.isEmpty(questionModel.getQuestionid())){
			String aaa = questionModel.getItems();
			String[] bbb = aaa.split("\r\n");
			for(int i = 0; i < bbb.length; i++){
				if(bbb[i].length() > 500){
					this.setErrorMessage("选项的长度不能大于500个字符，第"+(i+1)+"项的长度大于500个字符，请检查!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
			if(questionModel.getTitle().length() > 500){
				this.setErrorMessage("题目主题不能大于500个字符，请检查!");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
			
			/*if(questionModel.getType().equals("1")){
				if(Integer.parseInt(questionModel.getMaxItem()) > bbb.length){
					this.setErrorMessage("最多选项不能大于选项值!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
				if(bbb.length <= 1){
					this.setErrorMessage("请设置多个选项!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
				if(Integer.parseInt(questionModel.getMaxItem()) <= 1){
					this.setErrorMessage("多选题最多可选必须大于1!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
				
			}*/
			String maxSort = questionService.getMaxSort(questionModel.getPapermainid());
			questionModel.setSort(maxSort);
			questionService.insertQuestion(questionModel);
			Map<String, Object> data = new HashMap<String, Object>();
            data.put("success", true);
            getValueStack().set(DATA, data);
    		return DATA;
		}else{
			questionService.updateQuestion(questionModel);
			Map<String, Object> data = new HashMap<String, Object>();
            data.put("success", true);
            getValueStack().set(DATA, data);
    		return DATA;
		}
	}
	

	public void setOp(String op) {
		this.op = op;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public ExamPaperEntity getQuery() {
		return query;
	}

	public void setQuery(ExamPaperEntity query) {
		this.query = query;
	}

	public ExamPaperEntity getModel() {
		return model;
	}

	public void setModel(ExamPaperEntity model) {
		this.model = model;
	}

	public ExamQuestionEntity getQuestionQuery() {
		return questionQuery;
	}

	public void setQuestionQuery(ExamQuestionEntity questionQuery) {
		this.questionQuery = questionQuery;
	}

	public ExamQuestionEntity getQuestionModel() {
		return questionModel;
	}

	public void setQuestionModel(ExamQuestionEntity questionModel) {
		this.questionModel = questionModel;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String[] getId() {
		return id;
	}
	
	
	
}
