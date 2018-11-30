<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 	<head>
 	<%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
 	<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
 	
 		<script type="text/javascript" src="<%=request.getContextPath() %>/js/wpjs/button.js"></script>
		<script type="text/javascript">
		function saveRequest(){
			var param = $("#form1").serialize();	
			$.post(_path+'/wpjs/wpjsdeclare_save.html',param,function(data){
				var callback = function(){
					$("#search").submit();
				};
				processDataCall(data,callback);
			},"json");
		}
		$(function(){
			var id = $("#form1 input[name='declare.id']").val();//判断增加还是修改
			if(id==""){
				$("#action").click(function(){
					if(!check()) {
						return true;
					}
					saveRequest();
				});
			}else{
				$("#action").click(function(){
					updateRequest();
				});
			}
			initRadio("step.need",'${step.need}');
		});
		
		function check(){
			if($.trim($("#ygh").val()).length == 0){
				alert("职工号不能为空！");
				$("#ygh").focus();
				return false;
			}
			
			if($.trim($("#xm").val()).length == 0){
				alert("姓名不能为空！");
				$("#xm").focus();
				return false;
			}
			if($.trim($("#xb").val()).length == 0){
				alert("性别不能为空！");
				return false;
			}
			if($.trim($("#csny").val()).length == 0){
				alert("出生年月不能为空！");
				return false;
			}
			return true;
		}


		
		</script>
	</head>

<body>
	<form id="form1" name="form1" method="post">
	<input type="hidden" name="declare.id" value="${declare.id }"/>
		<div id="testID">
		  <div class="tab">
			<table align="center" class="formlist">
				<thead>
					<tr>
						<th colspan="4">
							<span id="title">新增外聘兼职教师<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tbody>
				    <tr>
				   		<th width="15%"><span style="color:red">*</span>职工号</th>
						<td width="35%">
			                	<input id="ygh" name="declare.ygh" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%"><span style="color:red">*</span>姓名1</th>
						<td width="35%">
			                	<input id="xm" name="declare.xm" type="text"  style="width: 150px;"/>			                	

						</td>						
					</tr>
					
					<tr>
				   		<th width="15%"><span style="color:red">*</span>性别</th>
						<td width="35%">
			                	<select name="declare.xb" id = "xb" >
			                		<option value="男">男</option>
			                		<option value="女">女</option>
			                	</select>
			                	
						</td>
						<th width="15%"><span style="color:red">*</span>出生年月</th>
						<td width="35%">
			                	<input id="csny" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" class="text_nor text_sel" name="declare.csny"/>
						</td>						
					</tr>
					
					<tr>	
						<th width="15%">身份证号</th>
						<td width="35%">
			                	<input name="declare.sfzh" type="text"  style="width: 150px;"/>
						</td>

						<th width="15%">部门名称</th>
						<td width="35%" style="width: 150px;">
							<ct:codePicker name="declare.bmdm" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${declare.bmdm}"/>
						</td>
					</tr>
					<tr>
						<th width="15%">学历</th>
						<td width="35%">
			                	<input name="declare.xl" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%">学位</th>
						<td width="35%">
				            <input name="declare.xw" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					<tr>
						<th width="15%">专业技术职务</th>
						<td width="35%">
			                	<input name="declare.zyjszw" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%">任教班级</th>
						<td width="35%">
				              	<input name="declare.rjbj" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="15%">任教课程</th>
						<td width="35%">
			                	<input name="declare.rjkc" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%">是否公选课</th>
						<td width="35%">
							<input type="radio" name="declare.sfggk" value="0" <c:if test="${!declare.sfggk }"> checked="true" </c:if> /> 否 
							<input type="radio" name="declare.sfggk" value="1" <c:if test="${declare.sfggk }"> checked="true" </c:if> /> 是
						</td>
					</tr>

					<tr>
						<th width="15%">聘任期限</th>
						<td width="35%">
			                	<input name="declare.prqx" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%">人员类别</th>
						<td width="35%">
				              	<input name="declare.rylb" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="15%">状态</th>
						<td width="35%">
			                	<input name="declare.zt" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="25%">
							<span class="red"></span>备注
						</th>
						<td colspan="3">
							<textarea rows="4" cols="50" name="declare.bz">${declare.bz }</textarea>
						</td>
					</tr>

				</tbody>
		    <tfoot>
		      <tr>
		        <td colspan="4">
		          	<div class="btn">
		            	<button type="button" id="action" name="action" >保 存</button>
		            	<button type="button" name="cancel" onclick='divClose();'>取 消</button>
		          	</div>
		        </td>
		      </tr>
			</tfoot>
			</table>
			</div>
		</div>
	</form>
</body>
</html>
