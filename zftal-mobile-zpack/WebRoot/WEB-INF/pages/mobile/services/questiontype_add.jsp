<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
    	var type = '${questionModel.type }';
    	if(type == "1"){
			$("#maxitem").show();
		}else{
			$("#maxitem").hide();
		}
    	$("#type").change(function(){
    		var type = $(this).find("option:selected").val();	
    		if(type == "2" || type == "3"){
    			$("#myModal_toji",window.parent.document).find(".modal-content").height(360);
    			$("#item").hide();
    			IFrameResize();
    		}else{
    			$("#item").show();
    			IFrameResize();
    		}
   			if(type == "1"){
   	         	$("#myModal_toji",window.parent.document).find(".modal-content").height(566);
       			$("#maxitem").show();
       			IFrameResize();
       		}else{
       			$("#maxitem").hide();
       			IFrameResize();
       		}
    	});
    	
        $("#save_btn").click(function(){
            if(!check()){
                 return false;
            }
            
            $.post(_path+"/serviceManager/question_typeSave.html",$("#form_edit").serialize(),function(data){
                if(data.success){
                	var papermainid = $("#papermainid").val();
                	var starttimeStr = parent.$("input[name='model.starttimeStr']").val();
                	var endtimeStr = parent.$("input[name='model.endtimeStr']").val();
                	var op = parent.$("input[name='op']").val();
                	parent.location.href = _path+"/serviceManager/question_toModifyQuestion.html?query.papermainid="+
                			papermainid+"&starttimeStr="+starttimeStr+"&endtimeStr="+endtimeStr+"&op="+op;
                }else{
                    errorAlert(data.text);
                }
            },"json");
            return false;
        });
        $('#items').bind('input propertychange', function() {
        	$("#maxvalue").empty();
        	var count = $(this).val().split("\n").length;
        	var optionHtml = "";
        	for(var i = 2; i < count+1; i++){
        		optionHtml += "<option value='"+i+"'>"+i+"</option>";
        	}
        	$("#maxvalue").append(optionHtml);
        });
        
        $("#cancel").click(function(){
        	window.parent.$('#myModal_toji').modal('hide');
            return false;
        });
        
        $(".close").click(function(){
        	window.parent.$('#myModal_toji').modal('hide');
            return false;
        });
    });
    
     function check()
    {
    	var title = $("#title").val();
    	var type  = $("#type").val();
    	if(title==null || title=='' ){
    		errorAlert("题目提示语不能为空！");
    		return false;
		}
    	if(type != "2" && type != "3"){
    		var items = $("#items").val();
    		if(items==null || items=='' ){
        		errorAlert("选项内容不能为空！");
        		return false;
    		}
    	}
    	if(type == "1"){
    		var maxitems = $("#maxvalue").val();
    		if(maxitems==null || maxitems=='' ){
        		errorAlert("最多选项不能为空！");
        		return false;
    		}
    		var itemarr = $("#items").val().split("\n");
    		for(var i=0; i<itemarr.length; i++){
    			if(itemarr[i]==null || itemarr[i]==''){
    				var j = i+1;
    				errorAlert("选项批量添加中第"+j+"项为空！");
            		return false;
    			}
    		}
    		//var chinese = /^[0-9]*$/g;
			//var flag = (chinese.test(maxitems));
			//if(!flag){
			//	errorAlert("最多选项请填入数字！");
	        //    return false;
			//}
    	}
        return true;
    }
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/serviceManager/question_typeSave.html" name="search" id="form_edit" method="post">
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">问卷题型信息</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>题型选择
                        </th>
                         <td  >
                         <c:if test="${op!='add'}">
                                <input type="text"  value="${questionModel.sort }" name="questionModel.sort"/>
                         </c:if>
                         <input type="hidden" id="papermainid" value="${papermainid }" name="questionModel.papermainid"/>
                         <input value="${questionModel.questionid }" name="questionModel.questionid" type="hidden"/>
                                <select id="type" name="questionModel.type" value="${questionModel.type }" class="form-control">
                                 <option value="0" <c:if test = "${questionModel.type eq '0'}"> selected="selected"</c:if>>单选题</option>
                                 <option value="1" <c:if test = "${questionModel.type eq '1'}"> selected="selected"</c:if>>多选题</option>
                                 <option value="2" <c:if test = "${questionModel.type eq '2'}"> selected="selected"</c:if>>简答题</option>
                                 <option value="3" <c:if test = "${questionModel.type eq '3'}"> selected="selected"</c:if>>打分题</option>
                                </select>
                         </td>
              </tr>
              <tr>
                        <th>
                            <span class="red">*</span>题目标题
                        </th>
                         <td>
                        
                         <textarea maxlength="50" placeholder="不要超过50个字符哦(^_^)" rows="3" cols="30" id="title" name="questionModel.title" value="${questionModel.title }" class="form-control">${questionModel.title }</textarea>
                        </td>
             </tr>
             <tr id="item">
                        <th >
                                                                               <span class="red">*</span>选项批量添加
                        </th>
                         <td >
                         <textarea maxlength="1500" wrap="off" style="overflow:scroll;" placeholder="每行代表一个选项，可以添加多个选项" rows="3" cols="500" id="items" name="questionModel.items" value="${questionModel.items }" class="form-control">${questionModel.items }</textarea>
                        </td>
             </tr>
             <tr id="maxitem">
                        <th >
                                                                               多选项最多可选
                        </th>
                         <td >
                         <select id="maxvalue" name="questionModel.maxItem" class="form-control" value="${questionModel.maxItem }">
                         	
                         </select>
                         </td>
             </tr>
             </tbody>
            </table>
       </div> 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
    </form>

</body>
</html>