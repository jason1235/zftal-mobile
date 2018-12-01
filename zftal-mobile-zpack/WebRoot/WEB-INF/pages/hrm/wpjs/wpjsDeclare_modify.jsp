<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 	<head>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
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
					saveRequest();
				});
			}else{
				$("#action").click(function(){
					updateRequest();
				});
			}
			initRadio("step.need",'${step.need}');
		});
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
							<span id="title">修改外聘兼职教师<font color="#0f5dc2" style="font-weight:normal;"></font></span>
<%--							<p>${declare.csnyText }</p>--%>
						</th>
					</tr>
				</thead>
				<tbody>
				    <tr>
				   		<th width="15%">职工号</th>
						<td width="35%">
			                <input id="ygh" name="declare.ygh" value="${declare.ygh }" style="width: 150px;"/>
						</td>
						

						</td>
						
						<th width="15%">姓名</th>
						<td width="35%">
			                <input id="xm" name="declare.xm" value="${declare.xm }" style="width: 150px;"/> 
						</td>						
					</tr>
					
					<tr>
				   		<th width="15%">性别</th>
						<td width="35%">
<%--			                	<input name="declare.xb" value="${declare.xb }" style="width: 150px;"/>--%>
			                	<select name="declare.xb" value="${desclare.xb}">
			                		<option value="男">男</option>
			                		<option value="女">女</option>			                	
			                	</select>
						</td>
						<th width="15%">出生年月</th>
						<td width="35%">
			                	<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" class="text_nor text_sel" name="declare.csny" value="${declare.csnyText }"/>
						</td>
					</tr>
					
					<tr>	
						<th width="15%">身份证号</th>
						<td width="35%">
			                	<input name="declare.sfzh" type="text"  value="${declare.sfzh }"  style="width: 150px;"/>
						</td>

						<th width="15%">部门名称</th>
						<td width="35%">
			                	<input name="declare.bmdm" type="text"  value="${declare.bmdm }"  style="width: 150px;"/>
						</td>
					</tr>
					<tr>
						<th width="15%">学历</th>
						<td width="35%">
			                	<input name="declare.xl" type="text"  value="${declare.xl }"  style="width: 150px;"/>
						</td>
						<th width="15%">学位</th>
						<td width="35%">
				            <input name="declare.xw" type="text"  value="${declare.xw }"  style="width: 150px;"/>
						</td>
					</tr>
					<tr>
						<th width="15%">专业技术职务</th>
						<td width="35%">
			                	<input name="declare.zyjszw" type="text"  value="${declare.zyjszw }"  style="width: 150px;"/>
						</td>
						<th width="15%">任教班级</th>
						<td width="35%">
				              	<input name="declare.rjbj" type="text"  value="${declare.rjbj }"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="15%">任教课程</th>
						<td width="35%">
			                	<input name="declare.rjkc" type="text"  value="${declare.rjkc }"  style="width: 150px;"/>
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
			                	<input name="declare.prqx" type="text"  value="${declare.prqx }"  style="width: 150px;"/>
						</td>
						<th width="15%">人员类别</th>
						<td width="35%">
				              	<input name="declare.rylb" type="text"  value="${declare.rylb }"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="15%">状态</th>
						<td width="35%">
			                	<input name="declare.zt" type="text"  value="${declare.zt }"  style="width: 150px;"/>
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
