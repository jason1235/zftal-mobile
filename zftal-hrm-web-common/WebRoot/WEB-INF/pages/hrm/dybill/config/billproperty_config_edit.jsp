<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/select.js"></script>
	<script type="text/javascript">
		$(function(){
			//保存
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("属性名称不得为空，请重新输入！");
					return false;
				}
				
				if( $("#fieldName").val() == "" ) {
					alert("字段名不得为空，请重新输入！");
					return false;
				}
				
				$("#windown-content").unbind("ajaxStart");
				
				$.post(_path+'/bill/config_saveXmlBillProperty.html', $("form[id='form2']").serialize(), function(data){
					var callback = function(){
						location.href="<%=request.getContextPath() %>/bill/config_xmlBillClassList.html?spBillConfig.id=${spBillConfig.id }";
					};
					
					processDataCall(data, callback);
				}, "json");
				
				return false;
			});
			
			$("#cancel").click(function(){
				divClose();
			});
			
			function dealType(type){
				if(type=="COMMON"){
					$("#tbody").append(
							"<tr class=\"fieldType\" id=\"COMMON\">"+
							"<th>"+
							"<span class=\"red\">*</span>字段长度"+
                            "</th>"+
                            "<td>"+
                            "<input type='text' class='text_nor' maxlength='3' name='xmlBillProperty.maxLength' value = '${xmlBillProperty.maxLength}' />"+
                            "</td>"+
									"<th>"+
									"<span class=\"red\">*</span>验证类型"+
								"</th>"+
								"<td>"+
								"<select name='xmlBillProperty.verifyType'>"+
									$("#common_type").html()+
								"</select></td>"+
								
							"</tr>");
				}
				if(type=="SIGLE_SEL"){
					$("#tbody").append(
							"<tr class=\"fieldType\" id=\"SIGLE_SEL\">"+
									"<th>"+
									"<span class=\"red\">*</span>标记字段"+
								"</th>"+
								"<td>"+
									"<input class=\"text_nor\" name=\"xmlBillProperty.mark\" value=\"true\" checked=\"checked\" type=\"radio\">是"+
									"<input class=\"text_nor\" name=\"xmlBillProperty.mark\" value=\"false\" type=\"radio\"><font class=\"red\">否</font>"+
								"</td>"+
								"<th>"+
								"</th>"+
								"<td>"+
								"</td>"+
							"</tr>");
				}
				if(type=="CODE"){
					$("#tbody").append(
							"<tr class=\"fieldType\" id=\"CODE\">"+
									"<th>"+
									"<span class=\"red\">*</span>引用代码库"+
								"</th>"+
								"<td>"+
								"<input id=\"codeId\" name=\"xmlBillProperty.codeId\" "+
								"value='${xmlBillProperty.codeId}'"+
								"type=\"hidden\">"+
								"<input class=\"text_nor text_sel\" "+
								"value='${xmlBillProperty.codeStr}' "+
								"onmouseover=\"initSelectConsole(this, '/code/codeCatalog_load.html')\" style=\"width: 200px;\" readonly=\"readonly\" type=\"text\">"+
								"</td>"+
								"<th>"+
								"<span class=\"red\">*</span>显示样式"+
								"</th>"+
								"<td>"+
								$("#viewStyles").html()+
								"</td>"+
							"</tr>");
				}
				if(type=="NUMBER"){
					$("#tbody").append(
							"<tr class=\"fieldType\" id=\"NUMBER\">"+
								"<th><span class=\"red\">*</span>验证类型"+
								"</th>"+
								"<td>"+
								"<select name='xmlBillProperty.verifyType'>"+
								$("#number_type").html()+
								"</select></td>"+
								"<th>"+
								"<span class=\"red\">*</span>小数位"+
								"</th>"+
								"<td>"+
									"<input type=\"text\" class=\"text_nor\" id=\"digits\" name=\"xmlBillProperty.digits\" maxlength=\"2\" value=\"${xmlBillProperty.digits }\" />"+
								"</td>"+
							"</tr>");
				}
				if(type=='FILE'||
						type == 'PHOTO'||
						type == 'IMAGE'){
					var unit="k";
					if(type == 'FILE'){
						unit="M";
					}
					$("#tbody").append(
							"<tr class=\"fieldType\" id=\"FILE\">"+
									"<th>"+
									"<span class=\"red\">*</span>文件大小"+
								"</th>"+
								"<td>"+
									"<input type=\"text\" class=\"text_nor\" id=\"size\" name=\"xmlBillProperty.size\" maxlength=\"3\" value=\"${xmlBillProperty.size }\" />"+
									unit+
								"</td>"+
								"<th>"+
								"</th>"+
								"<td>"+
								"</td>"+
							"</tr>");
				}
			}
			
			$("#fieldType").change(function(){
				var type=$(this).val();
				$("#tbody").find(".fieldType").remove();
				dealType(type);
			});
			
			dealType("${xmlBillProperty.fieldType}");
			getDefInputStyle();
		});
		function getDefInputStyle(){
		    $.post(_path+'/bill/config_getDefInputStyle.html', $("form[id='form2']").serialize(), function(data){
		        if(data.success){
		            $("#defaultStyle").html(data.result);
		        }else{
		            alert(data.text);
		        }
		    }, "json");
		}
	</script>
</head>

<body>
	<div id="verifyTypes" style="display: none;">
			<select id="common_type" name="xmlBillProperty.verifyType">
				<c:forEach begin="0" end="5" items="${xmlBillProperty.verifyTypes}" var="verifyType" >
				<c:if test="${verifyType.type eq 'COMMON'}">
					<option value="${verifyType}" <c:if test="${verifyType==xmlBillProperty.verifyType }">selected="selected"</c:if>>
					${verifyType.desc}
					</option>	
				</c:if>
				</c:forEach>
			</select>
			<select id="number_type" name="xmlBillProperty.verifyType">
				<c:forEach begin="6" items="${xmlBillProperty.verifyTypes}" var="verifyType" >
					<c:if test="${verifyType.type eq 'NUMBER'}">
					<option value="${verifyType}" <c:if test="${verifyType==xmlBillProperty.verifyType }">selected="selected"</c:if>>
					${verifyType.desc}
					</option>	
				</c:if>	
				</c:forEach>
			</select>
	</div>
	<div id="viewStyles" style="display: none;">
	   <select id="viewStyle_type" name="xmlBillProperty.viewStyle">
           <c:forEach items="${viewStyles}" var="v" >
                <option value="${v.key}" <c:if test="${v.key==xmlBillProperty.viewStyle }">selected="selected"</c:if>>
                    ${v.text}
                </option>
            </c:forEach>
        </select>
	</div>
	<form id="form2">
		<input type="hidden" name="spBillConfig.id" value="${spBillConfig.id }" />
		<input type="hidden" name="xmlBillClassBean.id" value="${xmlBillClassBean.id }" />
		<input type="hidden" name="xmlBillProperty.id" value="${xmlBillProperty.id }" />
		<input type="hidden" name="op" value="${op }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="4">
							<span>表单信息类属性维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save" type="button">保 存</button>
								<button id="cancel" type="button">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody id="tbody">
					<tr>
						<th>
							<span class="red">*</span>属性名称
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="xmlBillProperty.name" size="25" maxlength="16" value="${xmlBillProperty.name }" />
						</td>
						<th>
							<span class="red"></span>属性描述
						</th>
						<td>
							<input type="text" class="text_nor" id="descs" name="xmlBillProperty.descs" size="25" maxlength="32" value="${xmlBillProperty.descs }" />
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>字段名称
						</th>
						<td>
							<input type="text" class="text_nor" id="fieldName" name="xmlBillProperty.fieldName" size="25"  maxlength="16" value="${xmlBillProperty.fieldName }"
									<c:if test="${!empty xmlBillProperty.id}">readonly="readonly"</c:if> />
						</td>
						<th>
							<span class="red"></span>属性提示
						</th>
						<td>
							<input type="text" class="text_nor" id="titleMessage" name="xmlBillProperty.titleMessage" size="25" maxlength="32" value="${xmlBillProperty.titleMessage }" />
						</td>
					</tr>
					<tr name="step1">
						<th>
							<span class="red">*</span>显示宽度
						</th>
						<td>
							<input type="text" class="text_nor" id="width" name="xmlBillProperty.width" maxlength="16" value="${xmlBillProperty.width }"/>
						</td>
						<th>
							<span class="red">*</span>显示高度
						</th>
						<td>
							<input type="text" class="text_nor" id="height" name="xmlBillProperty.height" maxlength="16" value="${xmlBillProperty.height }"/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>可显示
						</th>
						<td>
							<input class="text_nor" name="xmlBillProperty.visable" value="true" <c:if test="${xmlBillProperty.visable eq true }">checked="checked"</c:if> type="radio">是
							<input class="text_nor" name="xmlBillProperty.visable" value="false" <c:if test="${xmlBillProperty.visable eq false }">checked="checked"</c:if> type="radio"><font class="red">否</font>
						</td>
						<th>
							<span class="red"></span>可编辑
						</th>
						<td>
							<input class="text_nor" name="xmlBillProperty.editable" value="true" <c:if test="${xmlBillProperty.editable eq true }">checked="checked"</c:if> type="radio">是
							<input class="text_nor" name="xmlBillProperty.editable" value="false" <c:if test="${xmlBillProperty.editable eq false }">checked="checked"</c:if> type="radio"><font class="red">否</font>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>必填
						</th>
						<td>
							<input class="text_nor" name="xmlBillProperty.required" value="true" <c:if test="${xmlBillProperty.required eq true }">checked="checked"</c:if> type="radio">是
							<input class="text_nor" name="xmlBillProperty.required" value="false" <c:if test="${xmlBillProperty.required eq false }">checked="checked"</c:if> type="radio"><font class="red">否</font>
						</td>
						<th>
							<span class="red"></span>字段类型
						</th>
						<td>
							<select id="fieldType" name="xmlBillProperty.fieldType" class="text_nor" style="width: 185px;">
								<c:forEach items="${types }" var="type">
								<option value="${type.name }" <c:if test="${type.name eq xmlBillProperty.fieldType}">selected="selected"</c:if>>${type.text }</option>
								</c:forEach> 
							</select>
						</td>
					</tr>
					<tr>
					    <th>
					        <span class="red"></span>默认值
					    </th>
					    <td colspan="2" id="defaultStyle">
					        <input type="hidden" name="xmlBillProperty.defaultValue" value="${xmlBillProperty.defaultValue }"/>
					    </td>
					    <td>
					       <button type="button" onclick="getDefInputStyle()">获取样式</button>
					    </td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>