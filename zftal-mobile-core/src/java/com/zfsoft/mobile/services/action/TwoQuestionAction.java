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
import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.common.enums.FwbmEnum;
import com.zfsoft.mobile.common.enums.ProductEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.common.utils.FileUntils;
import com.zfsoft.mobile.common.utils.ImageTagHtml;
import com.zfsoft.mobile.common.utils.JSONUtils;
import com.zfsoft.mobile.exampaper.query.TwoResultQuery;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.entity.ExamAnwserEntity;
import com.zfsoft.mobile.services.entity.ExamDyJsEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.entity.TwoExamAnwserEntity;
import com.zfsoft.mobile.services.entity.TwoExamDyJsEntity;
import com.zfsoft.mobile.services.entity.TwoExamPaperEntity;
import com.zfsoft.mobile.services.entity.TwoExamQuestionEntity;
import com.zfsoft.mobile.services.service.IQuestionService;
import com.zfsoft.mobile.services.service.ITwoQuestionService;
import com.zfsoft.util.base.StringUtil;

public class TwoQuestionAction  extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8081788107504980301L;
	
	private String op;
	
	private ITwoQuestionService twoQuestionService;
	
	private TwoExamPaperEntity query = new TwoExamPaperEntity();
	
	private TwoExamPaperEntity model = new TwoExamPaperEntity();
	
	private TwoExamAnwserEntity examAnwserQuery = new TwoExamAnwserEntity();
	
	private TwoExamQuestionEntity questionQuery = new TwoExamQuestionEntity();
	
	private TwoExamQuestionEntity questionModel = new TwoExamQuestionEntity();
	
	private TwoResultQuery twoResultQuery = new TwoResultQuery();
	
	private PageList<TwoResultQuery> twoResultPageList = new PageList<TwoResultQuery>();
	
	private String[] id;
	
	public String checkUserHasPartIn(){
		model = twoQuestionService.getExamPaperList(query).get(0);
		Date endDate = model.getEndtime();
		Date nowDate = new Date();
		int va = nowDate.compareTo(endDate);
		if(va == 1){
			this.setErrorMessage("该问卷已结束！");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
		this.setSuccessMessage("提交成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
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
		query = twoQuestionService.getExamPaperList(query).get(0);
		examAnwserQuery.setPapermainid(query.getPapermainid());
		PageList<TwoExamAnwserEntity> examAnwserList = twoQuestionService.getExamAnwserList(examAnwserQuery);
		getValueStack().set("examAnwserList", examAnwserList);
		return "answerList";
	}
	
	
	public String userList(){
		query.setPapermainid(twoResultQuery.getPaperMainId());
		query = twoQuestionService.getExamPaperList(query).get(0);
		twoResultPageList = twoQuestionService.getUserList(twoResultQuery);
		return "userList";
	}
	
	public String list(){
		PageList<TwoExamPaperEntity> list = twoQuestionService.getExamPaperList(query);
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
			
			twoQuestionService.insert(model);
			TwoExamPaperEntity query = new TwoExamPaperEntity();
			query.setPapermainid(model.getPapermainid());
			//query.setPapermainname(model.getPapermainname());
			TwoExamPaperEntity entity = twoQuestionService.getExamPaperList(query).get(0);
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
				TwoExamDyJsEntity entity = new TwoExamDyJsEntity();
				entity.setPapermainid(model.getPapermainid());
				List<TwoExamDyJsEntity> ExamDyJsList = twoQuestionService.getExamDyJsList(entity);
				if(ExamDyJsList == null || ExamDyJsList.size() == 0){
					this.setErrorMessage("请至少添加一个问卷群组!");
					getValueStack().set(DATA, getMessage());
					return DATA;
				}
			}
			//if(!StringUtil.isEmpty(model.getEndtimeStr()) && !StringUtil.isEmpty(model.getStarttimeStr())){
			model.setTotalsocre(twoQuestionService.getTotalScoreByQus(model));
			twoQuestionService.update(model);
			//}
			int min = 1;
			Map<String, String> map = null;
			if (id != null) {
				try {
					for (int i =0; i <id.length; i++) {
						map = new HashMap<String, String>();
						map.put("index", (i+min)+"");
						map.put("id", id[i]);
						twoQuestionService.updateIndex(map);
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
		List<TwoExamQuestionEntity> questionList = twoQuestionService.getQuestionList(questionQuery);
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
					Integer count = twoQuestionService.getValueStatic(map);
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
					Integer count = twoQuestionService.getValueStatic(map);
					itemvalueTemp = new HashMap<String, String>();
					itemvalueTemp.put("value", String.valueOf(count));
					itemvalueTemp.put("name", questionList.get(i).getItem().get(j));
					itemValue.add(itemvalueTemp);
				}
				map = new HashMap<String, String>();
				map.put("questionid", questionList.get(i).getQuestionid());
				Integer counts = twoQuestionService.getValueStatic(map);
				countpeople += counts;
			}
			if(type.equals("3")){
				for(int j = 1 ; j < 6; j++){
					map = new HashMap<String, String>();
					map.put("questionid", questionList.get(i).getQuestionid());
					map.put("value", String.valueOf(j));
					Integer count = twoQuestionService.getValueStatic(map);
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
		TwoExamQuestionEntity entity = new TwoExamQuestionEntity();
		entity.setPapermainid(query.getPapermainid());
		List<TwoExamQuestionEntity> questionList = twoQuestionService.getQuestionList(entity);
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
		model = twoQuestionService.getExamPaperList(query).get(0);
		model.setStarttimeStr(!StringUtil.isEmpty(starttimeStr) ? starttimeStr : model.getStarttimeStr());
		model.setEndtimeStr(!StringUtil.isEmpty(endtimeStr) ? endtimeStr : model.getEndtimeStr());
		questionQuery.setPapermainid(model.getPapermainid());
		List<TwoExamQuestionEntity> questionList = twoQuestionService.getQuestionList(questionQuery);
		getValueStack().set("questionList", questionList);
		TwoExamDyJsEntity entity = new TwoExamDyJsEntity();
		entity.setPapermainid(model.getPapermainid());
		List<TwoExamDyJsEntity> ExamDyJsList = twoQuestionService.getExamDyJsList(entity);
		getValueStack().set("ExamDyJsList", JSONUtils.obj2json(ExamDyJsList));
		if(StringUtil.isEmpty(op)){
			op = "modify";
		}
		return "que_modify";
	}
	
	public String control(){
		twoQuestionService.control(query);
		this.setSuccessMessage("操作成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String toShenHe(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String starttimeStr = request.getParameter("starttimeStr");
		String endtimeStr = request.getParameter("endtimeStr");
		model = twoQuestionService.getExamPaperList(query).get(0);
		model.setStarttimeStr(!StringUtil.isEmpty(starttimeStr) ? starttimeStr : model.getStarttimeStr());
		model.setEndtimeStr(!StringUtil.isEmpty(endtimeStr) ? endtimeStr : model.getEndtimeStr());
		questionQuery.setPapermainid(model.getPapermainid());
		List<TwoExamQuestionEntity> questionList = twoQuestionService.getQuestionList(questionQuery);
		getValueStack().set("questionList", questionList);
		TwoExamDyJsEntity entity = new TwoExamDyJsEntity();
		entity.setPapermainid(model.getPapermainid());
		List<TwoExamDyJsEntity> ExamDyJsList = twoQuestionService.getExamDyJsList(entity);
		getValueStack().set("ExamDyJsList", JSONUtils.obj2json(ExamDyJsList));
		return "toShenHe";
	}
	
	public String addQz(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String qzid = request.getParameter("qzid");
		String papermainid = request.getParameter("papermainid");
		
		String qzidArr[] = qzid.split(",");
		TwoExamDyJsEntity entity = null;
		for(int i = 0; i < qzidArr.length ; i++){
			entity = new TwoExamDyJsEntity();
			entity.setPapermainid(papermainid);
			entity.setQzid(qzidArr[i]);
			List<TwoExamDyJsEntity> ExamDyJsList = twoQuestionService.getExamDyJsList(entity);
			if(ExamDyJsList==null || (ExamDyJsList!=null&&ExamDyJsList.size()==0)){
				twoQuestionService.insertExamDyJs(entity);
			}
		}
		
		entity = new TwoExamDyJsEntity();
		entity.setPapermainid(papermainid);
		List<TwoExamDyJsEntity> ExamDyJsList = twoQuestionService.getExamDyJsList(entity);
		String qzmc = "";
		if(ExamDyJsList!=null&&ExamDyJsList.size()>=0){
			for(TwoExamDyJsEntity dyEntity: ExamDyJsList){
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
		TwoExamDyJsEntity entity = null;
		for(int i = 0; i < qzidArr.length ; i++){
			entity = new TwoExamDyJsEntity();
			entity.setPapermainid(papermainid);
			entity.setQzid(qzidArr[i]);
			List<TwoExamDyJsEntity> ExamDyJsList = twoQuestionService.getExamDyJsList(entity);
			//if(ExamDyJsList==null || (ExamDyJsList!=null&&ExamDyJsList.size()==0)){
				twoQuestionService.deleteExamDyJs(entity);
			//}
		}
		
		entity = new TwoExamDyJsEntity();
		entity.setPapermainid(papermainid);
		List<TwoExamDyJsEntity> ExamDyJsList = twoQuestionService.getExamDyJsList(entity);
		String qzmc = "";
		if(ExamDyJsList!=null&&ExamDyJsList.size()>=0)
		for(TwoExamDyJsEntity dyEntity: ExamDyJsList){
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
		TwoExamQuestionEntity questionQuery = new TwoExamQuestionEntity();
		questionQuery.setQuestionid(questionid);
		twoQuestionService.removeQuestion(questionQuery);
		this.setSuccessMessage("删除题型成功！");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove(){
		Map<String, Object> param = new HashMap<String, Object>();
        List<String> tids = new ArrayList<String>();
        TwoExamQuestionEntity questionQuery = null;
        for (String idCheck : id) {
            tids.add(idCheck.trim());
            questionQuery = new TwoExamQuestionEntity();
            questionQuery.setPapermainid(idCheck);
            twoQuestionService.removeQuestion(questionQuery);
        }
        param.put("ids", tids);
        twoQuestionService.remove(param);
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
		questionModel.setAnwers(
								questionModel.getAnwers().substring(
										0, ( questionModel.getAnwers().length()-1 )  
									)
				);
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
			String maxSort = twoQuestionService.getMaxSort(questionModel.getPapermainid());
			questionModel.setSort(maxSort);
			twoQuestionService.insertQuestion(questionModel);
			Map<String, Object> data = new HashMap<String, Object>();
            data.put("success", true);
            getValueStack().set(DATA, data);
    		return DATA;
		}else{
			twoQuestionService.updateQuestion(questionModel);
			Map<String, Object> data = new HashMap<String, Object>();
            data.put("success", true);
            getValueStack().set(DATA, data);
    		return DATA;
		}
	}
	
	
	

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	

	public ITwoQuestionService getTwoQuestionService() {
		return twoQuestionService;
	}

	public void setTwoQuestionService(ITwoQuestionService twoQuestionService) {
		this.twoQuestionService = twoQuestionService;
	}

	

	public TwoExamPaperEntity getQuery() {
		return query;
	}

	public void setQuery(TwoExamPaperEntity query) {
		this.query = query;
	}

	public TwoExamPaperEntity getModel() {
		return model;
	}

	public void setModel(TwoExamPaperEntity model) {
		this.model = model;
	}

	public TwoExamAnwserEntity getExamAnwserQuery() {
		return examAnwserQuery;
	}

	public void setExamAnwserQuery(TwoExamAnwserEntity examAnwserQuery) {
		this.examAnwserQuery = examAnwserQuery;
	}

	public TwoExamQuestionEntity getQuestionQuery() {
		return questionQuery;
	}

	public void setQuestionQuery(TwoExamQuestionEntity questionQuery) {
		this.questionQuery = questionQuery;
	}

	public TwoExamQuestionEntity getQuestionModel() {
		return questionModel;
	}

	public void setQuestionModel(TwoExamQuestionEntity questionModel) {
		this.questionModel = questionModel;
	}

	public void setId(String[] id) {
		this.id = id;
	}

	public String[] getId() {
		return id;
	}


	public void setTwoResultQuery(TwoResultQuery twoResultQuery) {
		this.twoResultQuery = twoResultQuery;
	}


	public TwoResultQuery getTwoResultQuery() {
		return twoResultQuery;
	}


	public void setTwoResultPageList(PageList<TwoResultQuery> twoResultPageList) {
		this.twoResultPageList = twoResultPageList;
	}


	public PageList<TwoResultQuery> getTwoResultPageList() {
		return twoResultPageList;
	}
	
	
	
}
