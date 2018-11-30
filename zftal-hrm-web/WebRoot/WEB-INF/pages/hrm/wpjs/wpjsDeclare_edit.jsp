<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 	<head>
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
				$("#action").click(function(){
					saveRequest();
				});
		//	initRadio("step.need",'${step.need}');
		});
		</script>
	</head>

<body>
	<form id="form1" name="form1" method="post">
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
				   		<th width="15%"><span class="red">*</span>职工号</th>
						<td width="35%">
			                	<input name="ygh" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%"><span class="red">*</span>姓名</th>
						<td width="35%">
			                	<input name="xm" type="text"  style="width: 150px;"/>
						</td>						
					</tr>
					
					<tr>
				   		<th width="15%"><span class="red">*</span>性别</th>
						<td width="35%">
			                	<input name="xb" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%"><span class="red">*</span>出生年月</th>
						<td width="35%">
			                	<input name="csny" type="text"  style="width: 150px;"/>
						</td>						
					</tr>
					
					<tr>	
						<th width="15%"><span class="red">*</span>身份证号</th>
						<td width="35%">
			                	<input name="sfzh" type="text"  style="width: 150px;"/>
						</td>

						<th width="15%"><span class="red">*</span>部门名称</th>
						<td width="35%">
			                	<input name="bmmc" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					<tr>
						<th width="15%"><span class="red">*</span>学历</th>
						<td width="35%">
			                	<input name="xl" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%"><span class="red">*</span>学位</th>
						<td width="35%">
				              	<input name="xw" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					<tr>
						<th width="15%"><span class="red">*</span>专业技术职务</th>
						<td width="35%">
			                	<input name="zyjszw" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%"><span class="red">*</span>任教班级</th>
						<td width="35%">
				              	<input name="rjbj" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="15%"><span class="red">*</span>任教课程</th>
						<td width="35%">
			                	<input name="rjkc" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%"><span class="red">*</span>是否公选课</th>
						<td width="35%">
				              	<input name="sfgxk" type="text"  style="width: 150px;"/>
						</td>
					</tr>

					<tr>
						<th width="15%"><span class="red">*</span>聘任期限</th>
						<td width="35%">
			                	<input name="prqx" type="text"  style="width: 150px;"/>
						</td>
						<th width="15%"><span class="red">*</span>人员类别</th>
						<td width="35%">
				              	<input name="rylb" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="15%"><span class="red">*</span>状态</th>
						<td width="35%">
			                	<input name="zt" type="text"  style="width: 150px;"/>
						</td>
					</tr>
					
					<tr>
						<th width="15%"><span class="red">*</span>备注</th>
						<td width="35%">
				        <input name="bz" type="textarea"  style="width: 150px;"/>
						</td>
					</tr>

				</tbody>
		    <tfoot>
		      <tr>
		        <td colspan="4">
		            <div class="bz">"<span class="red">*</span>"为必填项</div>
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
