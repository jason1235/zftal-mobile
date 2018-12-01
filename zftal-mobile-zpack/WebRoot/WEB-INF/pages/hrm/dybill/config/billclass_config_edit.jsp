<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<script type="text/javascript">
		$(function(){
			var op="${op}";
			var classId="${xmlBillClassBean.classId}";
			if(classId=="" & op =='add'){
				$("#append").find("option[value='true']").attr("selected","selected");
				//$("#append").css("display","none");
				//$("#appendFont").css("display","block");
			}
			$("#classSelect").change(function(){
				if($(this).children("option:selected").val()!=""){
					//$("#appendFont").css("display","none");
					//$("input[name='xmlBillClassBean.name']").attr("readonly","readonly");
					$("input[name='xmlBillClassBean.identityName']").attr("readonly","readonly");
					//$("input[name='xmlBillClassBean.choice']").remove();
					//$("input[name='xmlBillClassBean.catchRecordNum']").remove();
					//$("#xcatchRecordNum").remove();
					$("#append").find("option[value='true']").removeAttr("selected","selected");
					$("#append").find("option[value='false']").attr("selected","selected");
					//$("#append").css("display","block");
					//if($("#xcatchRecordNum").length==0){
					//	$("#tbody_bill").append("<tr id='xcatchRecordNum'>"+
					//		"<th>"+
					//			"<span class='red'></span>默认抓取条数"+
					//		"</th>"+
					//		"<td>"+
					//			"<input type='text' class='text_nor' id='catchRecordNum' name='xmlBillClassBean.catchRecordNum' value='0' />"+
					//		"</td>"+
					//		"</tr>");
					//}
					$.post("<%=request.getContextPath() %>/bill/config_getInfoClass.html",
							"xmlBillClassBean.classId="+$(this).children("option:selected").val(),
							function(data){
								if(data.message.success){
									var infoClass=data.infoClass;
									$("input[name='xmlBillClassBean.name']").val(infoClass.name);
									$("input[name='xmlBillClassBean.identityName']").val(infoClass.identityName);
								}
							},
						"json");
				}else{
					$("#append").find("option[value='false']").removeAttr("selected","selected");
					$("#append").find("option[value='true']").attr("selected","selected");
					//$("#append").css("display","none");
					//$("#appendFont").css("display","block");
					//$("#xchoice").remove();
					//$("#xcatchRecordNum").remove();
					//$("#form_bill").append(
					//		//"<input type='hidden' name='xmlBillClassBean.choice' value='${xmlBillClassBean.choice }' />"+
					//		"<input type='hidden' name='xmlBillClassBean.catchRecordNum' value='${xmlBillClassBean.catchRecordNum }' />");
					$("input[name='xmlBillClassBean.name']").removeAttr("readonly");
					$("input[name='xmlBillClassBean.identityName']").removeAttr("readonly");
					$("input[name='xmlBillClassBean.name']").val("");
					$("input[name='xmlBillClassBean.identityName']").val("");
				}
			});
			
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("名称不得为空，请重新输入！");
					return false;
				}
				
				if( $("#identityName").val() == "" ) {
					alert("标识名不得为空，请重新输入！");
					return false;
				}
				
				
					if( $("#choice").children("option:selected").val() == 'false' &&
							$("#append").children("option:selected").val() == 'false') {
						if($("#catchRecordNum").val()==""||$("#catchRecordNum").val()<=0){
							if($("#classSelect").children("option:selected").val()!=""){
								alert("默认抓取数不能为零，表单数据必需要有来源！");
								
							}else{
								alert("是否可选择与是否可追加不能同时为否，表单数据必需要有来源！");
							}
							return false;
						}
					}
				
				var p = new RegExp("^[a-zA-Z][\\w]*$");
				var res = p.test($("#identityName").val());
				if(!res){
					alert("标识名只能以字母开头，由字母数字及_组成");
					return false;
				}
				
				$.post('<%=request.getContextPath() %>/bill/config_saveXmlBillClass.html', $('form:last').serialize(), function(data){
					var callback = function(){
						location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassList.html?spBillConfig.id=${spBillConfig.id }";
					};
					processDataCall(data, callback);
				}, "json");

				return false;
			});
			
			$("#cancel").click(function(){
				divClose();
				return false;
			});
			$("#classList").hide();
			$("#SourceType").change(function(){
				$("#classList").hide();
                if($(this).children("option:selected").val()!=""){
                	$("#classList").show();
                    $("input[name='xmlBillClassBean.identityName']").attr("readonly","readonly");
                    $("#append").find("option[value='true']").removeAttr("selected","selected");
                    $("#append").find("option[value='false']").attr("selected","selected");
                    $.post("<%=request.getContextPath() %>/bill/config_getInfoClassList.html",
                            "sourceType="+$(this).children("option:selected").val(),
                            function(data){
                                if(data.message.success){
                                	 var html = '';
                                     for(var i=0;i<data.classList.length;i++){
                                         html+="<option value='"+data.classList[i].guid+"'>"+data.classList[i].name+"</option>";
                                     }
                                     $("#classSelect").html(html);
                                     $("input[name='xmlBillClassBean.name']").val(data.classList[0].name);
                                     $("input[name='xmlBillClassBean.identityName']").val(data.classList[0].identityName);
                                }
                            },
                        "json");
                }else{
                	$("#classSelect").html("<option value=''></option>)");
                    $("#append").find("option[value='false']").removeAttr("selected","selected");
                    $("#append").find("option[value='true']").attr("selected","selected");
                    $("input[name='xmlBillClassBean.name']").removeAttr("readonly");
                    $("input[name='xmlBillClassBean.identityName']").removeAttr("readonly");
                    $("input[name='xmlBillClassBean.name']").val("");
                    $("input[name='xmlBillClassBean.identityName']").val("");
                }
            });
		});
	</script>
</head>

<body>
	<form id="form_bill">
		<input type="hidden" name="xmlBillClassBean.id" value="${xmlBillClassBean.id }" />
		<input type="hidden" name="spBillConfig.id" value="${spBillConfig.id }" />
		<!--<c:if test="${empty(xmlBillClassBean.classId)}">
			<!-- <input type="hidden" name="xmlBillClassBean.choice" value="${xmlBillClassBean.choice }" /> 
			<input type="hidden" id="catchRecordNum" name="xmlBillClassBean.catchRecordNum" value="${xmlBillClassBean.catchRecordNum }" />
		</c:if>-->
		<input type="hidden" name="op" value="${op }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>表单信息类维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">保 存</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody id="tbody_bill">
					<c:if test="${op eq 'add'}">
					<tr>
                        <th>
                            <span class="red">*</span>信息来源
                        </th>
                        <td>
                            <select id="SourceType" style="width:206px">
                                <option value="">--无--</option>
                                <option value="teacher">人员信息类</option>
                                <option value="business">业务信息类</option>
                            </select>
                        </td>
                    </tr>
					<tr id="classList">
						<th>
							<span class="red">*</span>具体信息类
						</th>
						<td>
							<select id="classSelect" name="xmlBillClassBean.classId" style="width:206px">
								<option value="">--无--</option>
								<c:forEach items="${infoClasses }" var="infoClass">
								<option value="${infoClass.guid }" 
								<c:if test="${infoClass.guid eq xmlBillClassBean.classId }" >selected="selected"</c:if> >
								${infoClass.name }</option>
								</c:forEach>
							</select>

						</td>
					</tr>
					</c:if>
					<tr>
						<th>
							<span class="red">*</span>名称
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="xmlBillClassBean.name" style="width:200px" value="${xmlBillClassBean.name }"  maxlength="16"/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>标识名
						</th>
						<td>
							<input type="text" class="text_nor" id="identityName" name="xmlBillClassBean.identityName" style="width:200px" maxlength="16"
									<c:if test="${op eq 'modify'}"> readonly="readonly"</c:if> value="${xmlBillClassBean.identityName }" />
						</td>
					</tr>
					<c:if test="${op eq 'modify'}">
						<input type="hidden" name="xmlBillClassBean.classId" value="${xmlBillClassBean.classId }" />
					</c:if>
					<tr>
						<th>
							<span class="red">*</span>每行显示列数
						</th>
						<td>
							<select id="colNum" name="xmlBillClassBean.colNum" style="width:206px;">
								<option value="1" <c:if test="${xmlBillClassBean.colNum eq 1 }" >selected="selected"</c:if>>一列/行</option>
								<option value="2" <c:if test="${xmlBillClassBean.colNum eq 2 }" >selected="selected"</c:if>>二列/行</option>
								<option value="3" <c:if test="${xmlBillClassBean.colNum eq 3 }" >selected="selected"</c:if>>三列/行</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>最少记录数
						</th>
						<td>
							<input type="text" class="text_nor" id="minLength" name="xmlBillClassBean.minLength" value="${xmlBillClassBean.minLength }" 
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>最多记录数
						</th>
						<td>
							<input type="text" class="text_nor" id="maxLength" name="xmlBillClassBean.maxLength" value="${xmlBillClassBean.maxLength}" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>显示模式
						</th>
						<td>
							<select id="scanStyle" name="xmlBillClassBean.scanStyle" style="width:206px;">
								<option value="TILE" <c:if test="${xmlBillClassBean.scanStyle eq 'TILE' }" >selected="selected"</c:if>>平铺</option>
								<option value="LIST" <c:if test="${xmlBillClassBean.scanStyle eq 'LIST' }" >selected="selected"</c:if>>列表</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red"></span>是否可追加
						</th>
						<td>
							
							<select id="append" name="xmlBillClassBean.append" style="width:206px;">
								<option value="true" <c:if test="${xmlBillClassBean.append eq true }" >selected="selected"</c:if>>是</option>
								<option value="false" <c:if test="${xmlBillClassBean.append eq false }" >selected="selected"</c:if>>否</option>
							</select>
						</td>
					</tr>
					
					<tr id="xchoice">
						<th>
							<span class="red"></span>是否可选择
						</th>
						<td>
							<select id="choice" name="xmlBillClassBean.choice" style="width:206px;">
								<option value="true" <c:if test="${xmlBillClassBean.choice eq true }" >selected="selected"</c:if>>是</option>
								<option value="false" <c:if test="${xmlBillClassBean.choice eq false }" >selected="selected"</c:if>>否</option>
							</select>
						</td>
					</tr>
					<tr id="xcatchRecordNum">
						<th>
							<span class="red"></span>默认抓取条数
						</th>
						<td>
							<input type="text" class="text_nor" id="catchRecordNum" name="xmlBillClassBean.catchRecordNum" value="${xmlBillClassBean.catchRecordNum}" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>