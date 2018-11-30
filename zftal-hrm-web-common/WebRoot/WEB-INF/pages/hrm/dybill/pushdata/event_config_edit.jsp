<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<script type="text/javascript">
		$(function(){
			
			$("#eventTypeSelect").change(function(){
				var value=$(this).children("option:selected").val();
				targetName(value);
			});
			$("#infoClassType").change(function(){
				changeInfoClassType();
            });

			var infoClassType = $("option[value='${config.infoClassId}']").closest("select").attr("id");
			$("#infoClassType").val(infoClassType);
            
			function targetName(value){
				if(value=="PUSH_TO_INFOCLASS"){
					$("#messageSrc").css("display","block");
					$("#dbName").css("display","none");
					$("#infoClassSelect").css("display","block");
					$("#localTable").css("display","none");
					changeInfoClassType();
				}else{
					$("#messageSrc").css("display","none");
					$("#dbName").css("display","block");
					$("#infoClassSelect").css("display","none");
					$("#localTable").css("display","block");
				}
			}

			function changeInfoClassType(){
				var value=$("#infoClassType").val();
				$("#infoClasses").hide();
				$("#infoClasses").removeAttr("name");
				$("#businessInfoClasses").hide();
				$("#businessInfoClasses").removeAttr("name");
				$("#"+value).attr("name","config.infoClassId");
				$("#"+value).show();
	        }
			targetName($("#eventTypeSelect").children("option:selected").val());
			
			$("#billConfigSelect").change(function(){
				var value=$(this).children("option:selected").val();
				if(value==""){
					return;
				}
				billClassCatch(value);
			});
			
			function billClassCatch(value){
				$.post('<%=request.getContextPath()%>/bill/config_getXmlBillClasses.html', "spBillConfig.id="+value, function(data){
					var html="";
					var selectedClassId="${config.billClassId}";
					for(var i=0; i < data.length; i++){
						var billClass=data[i];
						if(selectedClassId==billClass.id){
							html+="<option value=\""+billClass.id+"\" selected=selected >"+billClass.name+"</option>";
						}else{
							html+="<option value=\""+billClass.id+"\">"+billClass.name+"</option>";
						}
					}
					
					$("#billClassSelect").html(html);
				}, "json");
			}
			billClassCatch($("#billConfigSelect").children("option:selected").val());
			
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("事件名称不得为空，请重新输入！");
					return false;
				}
				
				if( $("#eventTypeSelect").children("option:selected").val() != "PUSH_TO_INFOCLASS") {
					if( $("#localTable").val() == "" ) {
						alert("必须填写数据库表名！");
						return false;
					}
				}
				
				$.post('<%=request.getContextPath()%>/bill/pushdata_save_config.html', $('#form_bill').serialize(), function(data){
					var callback = function(){
						location.href="<%=request.getContextPath()%>/bill/pushdata_list_config.html";
					};
					processDataCall(data, callback);
				}, "json");

				return false;
			});
			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
		});
	</script>
</head>

<body>
	<form id="form_bill">
		<input type="hidden" name="config.id" value="${config.id }" />
		<input type="hidden" name="oper" value="${oper }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>表单事件维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">保 存</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody id="tbody_bill">
					<tr>
						<th width="80px">
							<span class="red">*</span>事件名称
						</th>
						<td width="120px">
							<input type="text" class="text_nor" id="name" name="config.name" value="${config.name }"  maxlength="255"/>
						</td>
						<th width="80px">
							<span class="red">*</span>事件类型
						</th>
						<td>
							<select id="eventTypeSelect" name="config.eventType" >
								<option value="PUSH_TO_INFOCLASS">向信息类推送</option>
								<option value="PUSH_TO_LOCAL">向数据库推送</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>表单
						</th>
						<td>
							<select id="billConfigSelect" name="config.billConfigId" >
								<c:forEach items="${billConfigs }" var="billConfig">
								<option value="${billConfig.id }" 
								<c:if test="${billConfig.id eq config.billConfigId }" >selected="selected"</c:if> >
								${billConfig.name }</option>
								</c:forEach>
							</select>
						</td>
						<th>
							<span class="red">*</span>表单类
						</th>
						<td>
							<select id="billClassSelect" name="config.billClassId" >
							</select>
						</td>
					</tr>
					<tr>
						<th width="100px">
							<font id="messageSrc" style="display:none;float: right;">信息来源</font> <font id="dbName" style="display:none;float: right;">数据库表名</font><span style="float: right;" class="red">*</span>
						</th>
						<td colspan="3">
						    <span id="infoClassSelect" style="display:none;">
						        <select id="infoClassType" >
						            <option value="infoClasses">基础信息类</option>
                                    <option value="businessInfoClasses">业务信息类</option>
                                </select>
								<select  id="infoClasses" >
									<c:forEach items="${infoClasses }" var="infoClass">
									<c:if test="${infoClass.type != 'OVERALL' }">
									<option value="${infoClass.guid }" 
									<c:if test="${infoClass.guid eq config.infoClassId }" >selected="selected"</c:if> >
									${infoClass.name }</option></c:if>
									</c:forEach>
								</select>
								<select id="businessInfoClasses" >
                                    <c:forEach items="${businessInfoClasses }" var="infoClass">
                                    <c:if test="${infoClass.type != 'OVERALL' }">
                                    <option value="${infoClass.guid }" 
                                    <c:if test="${infoClass.guid eq config.infoClassId }" >selected="selected"</c:if> >
                                    ${infoClass.name }</option></c:if>
                                    </c:forEach>
                                </select>
							</span>
							<input style="display:none;" type="text" class="text_nor" id="localTable" name="config.localTable" value="${config.localTable}"  maxlength="255"/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>操作类型
						</th>
						<td colspan="3">
							<select id="classSelect" name="config.eventOpType" >
								<option value="INSERT">增加</option>
								<option value="UPDATE">更新</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>表单式
						</th>
						<td colspan="3">
							<textarea name="config.whereExpression" style="width: 300px;height: 70px;" >${config.whereExpression}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>