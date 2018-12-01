<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<%@include file="/commons/hrm/head.ini" %>
		<script type="text/javascript">
		$(function(){
			$("#save").click(function(){
				save();
			});

			$("#cancel").click(function(){
				divClose();
				return false;
			});

			$("input[name='info.oid']").focusout(function(){
				var id = $(this).val();
				testOid(id);
			});
		});

		function validate(){
			var opts = $("select[name='info.type']")[0].options;
			var type = "";
			for(var i = 0; i < opts.length; i++){
				if(opts[i].selected){
					type = opts[i].value;
				}
			}
			var reg = /^[0-9]{0,20}$/;
			if($.trim($("input[name='info.oid']").val()).length == 0){
				alert("部门代码不可为空");
				return false;
			}
			if($.trim($("input[name='info.name']").val()).length == 0){
				alert("部门名称不可为空");
				return false;
			}
			if($.trim(type).length == 0){
				alert("部门类型不可为空");
				return false;
			}
			if(!reg.test($.trim($("input[name='info.oid']").val()))){
				alert("部门代码必须为数字格式，且不超过20位");
				return false;
			}
			if(!reg.test($.trim($("input[name='info.orderCode']").val()))){
				alert("顺序码必须为数字格式，且不超过20位");
				return false;
			}
			if(!reg.test($.trim($("input[name='info.bzs']").val()))){
				alert("编制数必须为数字格式，且不超过20位");
				return false;
			}
			return true;
		}

		function save(){
			if(!validate()){
				return false;
			}
			var id = $("input[name='info.oid']").val();
			if('${addIf}' == 'true'){
				$.post('<%=request.getContextPath()%>/org/org_judgeOid.html',{oid:id},function(data){
					if(data){
						$("#fontOid").attr("color","red");
						$("#fontOid").html("已被其他部门使用，不可用");
						alert("部门代码不可用");
					}
					else{
						doSave(id);
					}
				},"json");
			}
			else{
				doSave(id);
			}
		}

		function doSave(id){
			$.post('<%=request.getContextPath()%>/org/org_save.html',$("#form1 input,#form1 select").serialize(),function(data){
				var callback = function(){
					$("input[name='supperOid']").val(id);
					searchValidate();
				};
				processDataCall(data, callback);
			},"json");
		}

		function testOid(id){
			if(id == null || id == ''){
				$("#fontOid").html("");
				return;
			}
			var reg = /^[0-9]{1,20}$/;
			if(!reg.test($.trim($("input[name='info.oid']").val()))){
				$("#fontOid").attr("color","red");
				$("#fontOid").html("必须为数字格式");
				return;
			}
			$.post('<%=request.getContextPath()%>/org/org_judgeOid.html',{oid:id},function(data){
				if(data){
					$("#fontOid").attr("color","red");
					$("#fontOid").html("已被其他部门使用，不可用");
				}
				else{
					$("#fontOid").attr("color","#0f5dc2");
					//$("#fontOid").html("可以使用");
					$("#fontOid").html("");
				}
			},"json");
		}

		</script>
	</head>
  
  <body>
	
	<div class="tab">
	<form id="form1">
		<input type="hidden" name="addIf" value="${addIf }" />
		<c:if test="${info.level != null && info.level != ''}">
			<input type="hidden" name="info.level" value="${info.level }" />
		</c:if>
		<c:if test="${info.level == null || info.level == ''}">
			<c:if test="${info.parent != null && info.parent.oid != null && info.parent.oid != ''}">
				<input type="hidden" name="info.level" value="2" />
			</c:if>
			<c:if test="${info.parent == null || info.parent.oid == null || info.parent.oid == ''}">
				<input type="hidden" name="info.level" value="1" />
			</c:if>
		</c:if>
		
		<table class="formlist" width="100%">
			<thead>
		    	<tr>
		        	<th colspan="4"><span>编辑组织机构信息<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
		        </tr>
		    </thead>
			
			<tfoot>
				<tr>
					<td colspan="4">
						<font color="#0f5dc2" style="font-weight:normal;">备注：顺序码为空时默认取部门代码</font>
						<div class="btn">
							<button id="save" type="button" name="保存" >保存</button>
							<button id="cancel" type="button" name="取消" >取消</button>
						</div>
					</td>
				</tr>
			</tfoot>
			
			<tbody>
				<tr>
					<th width="25%">
						<span class="red">*</span>部门代码
					</th>
					<td>
						<c:if test="${info.oid != null && info.oid != ''}">
							${info.oid }
							<input type="hidden" name="info.oid" value="${info.oid }" />
						</c:if>
						<c:if test="${info.oid == null || info.oid == ''}">
							<input type="text" name="info.oid" maxlength="20" value="${info.oid }" />
						</c:if>
						<span><font id="fontOid" color="" style="font-weight:normal;"></font></span>
					</td>
					
					<th width="25%">
						<span class="red">*</span>部门名称
					</th>
					<td>
						<input type="text" name="info.name" maxlength="20" value="${info.name }" />
					</td>
				</tr>
				
				<tr>
					 <th width="25%"><span class="red">*</span>部门类别</th>
					 <td>
						<select id="info.type" name="info.type" <c:if test="${info.parent != null && info.parent.oid != null && info.parent.oid != ''}"> disabled="true" </c:if>>
							<option value=''>----- 选择部门类型 -----</option>
							<c:forEach items="${typeList}" var="type" varStatus="sta_type">
								<option value="${type.name }" <c:if test="${info.type != null && info.type != '' && info.type == type.name}"> selected="selected" </c:if>>${type.text }</option>
							</c:forEach>
						</select>
						<c:if test="${info.parent != null && info.parent.oid != null && info.parent.oid != ''}">
						 	<input type="hidden" name="info.type" value="${info.type }"/> 
						</c:if>
					</td>
					
					<th width="25%"><span class="red"></span>上级部门</th>
						<td>
							<select id="parentSel" name="info.parent.oid" <c:if test="${info.parent == null || info.parent.oid == null || info.parent.oid == ''}"> disabled="true" </c:if>>
								<c:if test="${info.parent == null || info.parent.oid == null || info.parent.oid == ''}">
									<option value=''>--------&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--------</option>
								</c:if>
								<c:forEach items="${infoList}" var="org" varStatus="sta_parent">
									<c:if test="${info.oid != org.oid}">
										<option value="${org.oid }" <c:if test="${info.parent != null && info.parent.oid != null && info.parent.oid == org.oid}"> selected="selected" </c:if>>${org.name }</option>
									</c:if>
								</c:forEach>
							</select>
							<c:if test="${info.parent == null || info.parent.oid == null || info.parent.oid == ''}">
							 	<input type="hidden" name="info.parent.oid" value="${info.parent.oid }"/> 
							</c:if>
						</td>
					</tr>
				
				<tr>
					<th width="25%">主管</th>
					<td>
						<ct:selectPerson name="info.manager" id="info.manager" value="${info.manager}" width="150px" />
					</td>
					
					<th width="25%">负责人</th>
					<td>
						<ct:selectPerson name="info.prin" id="info.prin" value="${info.prin}" width="150px" />
					</td>
				</tr>
				
				<tr>
					<th width="25%">
						<span class="red"></span>顺序码
					</th>
					<td>
						<input type="text" name="info.orderCode" maxlength="5" value="${info.orderCode }" />
					</td>
					
					<th width="25%">备注</th>
					<td>
						<input type="text" name="info.remark" value="${info.remark }"/>
					</td>
				</tr>
				<tr>
					<th width="25%">
						<span class="red"></span>单位全称
					</th>
					<td>
						<input type="text" name="info.dwqc" maxlength="5" value="${info.dwqc }" />
					</td>
					
					<th width="25%">所属总支</th>
					<td>
						<ct:codePicker name="info.sszz" catalog="DM_DEF_SSZZ" code="${info.sszz }"/>
					</td>
				</tr>
				<tr>
					<th width="25%">
						<span class="red"></span>编制数
					</th>
					<td>
						<input type="text" name="info.bzs" maxlength="5" value="${info.bzs }" />
					</td>
					<th colspan="2">
					</th>
				</tr>
			</tbody>
		</table>
	</form>
	</div>	
  </body>
</html>
