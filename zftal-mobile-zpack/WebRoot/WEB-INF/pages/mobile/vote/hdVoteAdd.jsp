<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/laydate/theme/default/laydate.css" type="text/css"/>
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
        
        .demo-input{padding-left: 10px; height: 38px; min-width: 262px; line-height: 38px; border: 1px solid #e6e6e6;  background-color: #fff;  border-radius: 2px;}
        
    </style>
    
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/laydate/laydate.js"></script>
    
    <script type="text/javascript">
    $(function(){
        $("#save_btn").click(function(){
        	var name = $("input[name='voteTitle']").val();
            if(name==null || name==''){
                errorAlert("投票标题不能为空");
                return false;
            }
            var voteEndDate = $("input[name='voteEndDate']").val();
            if(voteEndDate==null || voteEndDate==''){
                errorAlert("截止日期不能为空");
                return false;
            }
            
            $.post("<%=request.getContextPath()%>/vote/vote_hdVoteAdd.html",$("#form_edit").serialize(),function(data){
            	if(data.data=='success'){
	               window.parent.$('#myModal_toji').modal('hide');
	               parent.location.reload();
            	}else{
            	   errorAlert(data.msg);
            	}
            },"json");
        });
      
        
        $("#cancel").click(function(){
        	window.parent.$('#myModal_toji').modal('hide');
        	return false;
        });
        
        $(".close").click(function(){
        	window.parent.$('#myModal_toji').modal('hide');
        	return false;
        });
        
        $("#voteType").change(function(){ 
        	//获取选中值,为0文字类型，为1打分类型
        	var voteTypeVal = $("#voteType").val();
        	//errorAlert(voteTypeVal);
        	var addedHtml = "";
        	if(!!voteTypeVal){
        		//文字类型添加是否多选和最多选几项选择框，打分类型添加最大分值输入框
        		if(voteTypeVal==0){
        			$("#voteMaxScoreTr").remove();
        			$("#voteScoreMethodTr").remove();
        			
        			addedHtml+="<tr id='voteIsMultiSelectTr'><th width='30%'>是否多选</th>"+
                    "<td ><select id=\"voteIsMultiSelect\" name=\"voteIsMultiSelect\"  class=\"form-control\">"+
                         "<option value=\"1\" selected=\"selected\">是</option>"+
                         "<option value=\"0\" selected=\"selected\">否</option></select>"+
                    "</td></tr>";
                    addedHtml+="<tr id='voteMaxChoiceTr'><th width='30%'>最多选几项</th>"+
                    "<td ><select id=\"voteMaxChoice\" name=\"voteMaxChoice\"  class=\"form-control\">"+
                         "<option value=\"2\" selected=\"selected\">2</option>"+
                         "<option value=\"3\" selected=\"selected\">3</option></select>"+
                    "</td></tr>"
        		    $("#voteTypeTr").after(addedHtml);
        			//voteTypeTr
        		}else if(voteTypeVal==1){
        			$("#voteIsMultiSelectTr").remove();
        			$("#voteMaxChoiceTr").remove();
        			
        			addedHtml+="<tr id='voteMaxScoreTr'><th width='30%'>最大分值</th>"+
                    "<td ><select id=\"voteMaxScore\" name=\"voteMaxScore\"  class=\"form-control\">"+
                         "<option value=\"5\" selected=\"selected\">5</option>"+
                         "<option value=\"10\" selected=\"selected\">10</option></select>"+
                    "</td></tr>";
                    addedHtml+="<tr id='voteScoreMethodTr'><th width='30%'>分数统计方式</th>"+
                    "<td ><select id=\"voteScoreMethod\" name=\"voteScoreMethod\"  class=\"form-control\">"+
                         "<option value=\"0\" selected=\"selected\">平均分</option>"+
                         "<option value=\"1\" selected=\"selected\">总分</option></select>"+
                    "</td></tr>";
                    $("#voteTypeTr").after(addedHtml);
        		}
        	}
         });
        
        //添加选项
        $("#addOption_btn").click(function(){
        	 var appendHtml = "";
        	 appendHtml+= "<tr><th width='30%'>选项</th><td><input style='width:250px;height:30px;' maxlength=\"50\" placeholder=\"不要超过50个字符哦(^_^)\" name=\"option\" type=\"text\"/>&nbsp;<span name='deleteOption' onclick='deleteSelfTr(this);' style='color:red;cursor:pointer;font-size:20px;'>x</span></td></tr>"
        	 $("table tbody").append(appendHtml);
        });
        
        
       //执行一个laydate实例
       laydate.render({
         elem: '#voteEndDate', //指定元素
         type: 'datetime',
         format: 'yyyy-MM-dd HH:mm:ss'
       });
       
       getQzList();
     });
    
      //添加选项删除
	  function deleteSelfTr(aaa){
	    //console.log(aaa);
	    $(aaa).parent("td").parent("tr").remove();
	  }
    
      //获取 分组列表
      function getQzList(){
    	  $.get("<%=request.getContextPath()%>/vote/vote_getQzList.html",{},function(data){
          	if(data.code==1){
          	   var qzList = data.data;
          	   var qzOptionHtml = "";
          	   for(var i=0;i<qzList.length;i++){
          		 qzOptionHtml+="<option value='"+qzList[i].qzId+"'>"+qzList[i].qzName+"</option>";
          	   }
          	   $("select[name='qzId']").append(qzOptionHtml);
          	}else{
          	   errorAlert(data.msg);
          	}
          },"json");
      }
     </script>
</head>
<body>
    <form action="<%=request.getContextPath()%>/vote/vote_hdVoteAdd.html" name="search" id="form_edit" method="post">
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">投票信息</h4>
            </div>
            <div class="modal-body">
            <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
                <div class="tab" style="clear: both;max-height: 500px;overflow: auto;">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
            	<tr>
                   <th width="30%" >
                       <span class="red">*</span>投票标题
                   </th>
                    <td >
                         <input id="voteTitle" maxlength="50" placeholder="不要超过50个字符哦(^_^)" name="voteTitle" type="text" value="${model.voteTitle }" class="form-control"/>
                   </td>
              </tr>
              <tr>
                  <th width="30%" >
                     	 投票介绍
                  </th>
                   <td >
                        <textarea maxlength="200" placeholder="不要超过200个字符哦(^_^)" rows="4" cols="30" id="voteDescription" name="voteDescription" value="${model.voteDescription}" class="form-control"></textarea>
                  </td>
              </tr>
              <tr id="voteTypeTr">
                 <th width="30%" >
                                                             投票类型
                 </th>
                  <td >
                     <select id="voteType" name="voteType"  class="form-control">
                       <option value="0" selected="selected">文字</option>
                        <option value="1">打分</option>
             		</select>
                 </td>
              </tr>
              <tr id="voteIsMultiSelectTr">
                 <th width="30%" >
                                                             是否多选
                 </th>
                  <td >
                     <select id="voteIsMultiSelect" name="voteIsMultiSelect"  class="form-control">
                       <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
             		</select>
                 </td>
              </tr>
               <tr id="voteMaxChoiceTr">
                 <th width="30%" >
                                                            最多选几项
                 </th>
                  <td >
                     <select id="voteMaxChoice" name="voteMaxChoice"  class="form-control">
                        <option value="2" selected="selected">2</option>
                        <option value="3">3</option>
             		</select>
                 </td>
              </tr>
             <tr>
                 <th width="30%" >
                                                             是否匿名
                 </th>
                  <td >
                     <select name="voteIsAnonymous"  class="form-control">
                       <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
             		</select>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                                                         仅发起人可查看结果
                 </th>
                  <td >
                     <select name="voteResultOnlySee"  class="form-control">
                        <option value="1" selected="selected">是</option>
                       <option value="0" selected="selected">否</option>
             		</select>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>截止时间
                 </th>
                  <td >
                    <input type="text" id="voteEndDate" name="voteEndDate" class="form-control"  placeholder="请选择日期" readonly="readonly"/>  
                 </td>
              </tr>
               <tr>
                 <th width="30%" >
                                                         是否草稿
                 </th>
                  <td >
                     <select name="voteIsDraft"  class="form-control">
                        <option value="1" selected="selected">是</option>
                       <option value="0"  selected="selected">否</option>
             		</select>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                                                         选择分组
                 </th>
                  <td >
                     <select name="qzId"  class="form-control">
                        
             		</select>
                 </td>
              </tr>
             </tbody>
            </table>
            <div id="addOptionBtnDiv" style="text-align:center;">
               <br/>
               <button type="button" class="btn btn-primary" id="addOption_btn">添加选项</button>
            </div>
       </div> 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save_btn">保存</button>
            </div>
        </div>
    </form>

</body>
</html>