<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	
	<script type="text/javascript">
		$(function(){
			$("#save").click(function(){
				if( $("#name").val() == "" ) {
					alert("名称不得为空，请重新输入！");
					return false;
				}
				
				if( $("#identityName").val() == "" ) {
					alert("标识名不得为空，请重新输入！");
					return false;
				}
				var p = new RegExp("^[a-zA-Z][\\w]*$");
				var res = p.test($("#identityName").val());
				if(!res){
					alert("标识名只能以字母开头，由字母数字及_组成");
					return false;
				}
				
				if( $("#type").val() == "" ) {
					alert("类型不得为空，请重新输入！");
					return false;
				}
				if( $("#index").val() == "" ) {
                    alert("顺序码不得为空，请重新输入！");
                    return false;
                }
				var num = new RegExp("^[0-9]{1,2}$");
                if(!num.test($("#index").val())){
                    alert("顺序码必须是0-99的数字");
                    return false;
                }
				
				$.post('<%=request.getContextPath() %>/infoclass/infoclass_save.html', $('form:last').serialize(), function(data){
					var callback = function(){
						$("form:first").submit();
					};
					
					processDataCall(data, callback);
				}, "json");

				return false;
			})
			
			$("#cancel").click(function(){
				divClose();
				return false;
			})
			
			$("#displayNum").val('${model.displayNum}');
			$("#scanStyle").val('${model.scanStyle}');
			$("#pxsx").val('${model.pxsx}');
			$("#pxfs").val('${model.pxfs}');
		})
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="guid" value="${model.guid }" />
		<input type="hidden" name="catalog.guid" value="${model.catalog.guid }" />
		<input type="hidden" name="menuId" value="${model.menuId }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>信息类维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
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
				<tbody>
					<c:if test="${!empty model.guid }">
					<tr>
						<th>
							<span class="red">*</span>信息类id
						</th>
						<td>
							${model.guid }
						</td>
					</tr>
					</c:if>
					<tr>
						<th>
							<span class="red">*</span>名称
						</th>
						<td>
							<input type="text" class="text_nor" id="name" name="name" style="width:200px" value="${model.name }"  maxlength="16"/>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>标识名
						</th>
						<td>
							<c:if test="${empty model.guid }">
							<input type="text" class="text_nor" id="identityName" name="identityName" style="width:200px" maxlength="16"
									style="ime-mode:disabled" title="请输入英文字符或下划线" value="${model.identityName }" />
							</c:if>
							<c:if test="${!empty model.guid }">
							<input type="text" class="text_nobor" id="identityName" name="identityName" style="width:200px" maxlength="16"
									readonly="readonly" value="${model.identityName }" />
							</c:if>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>类型
						</th>
						<td>
							<select id="type" name="type" style="width:206px">
								<option value="">--请选择--</option>
								<c:forEach items="${types }" var="type">
								<c:if test="${type.editable}">
								<option value="${type.name }" <c:if test="${type.name eq model.type }" >selected="selected"</c:if> >${type.text }</option>
								</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class="red">*</span>每行显示列数
						</th>
						<td>
							<select id="displayNum" name="displayNum" style="width:206px;">
								<option value="1">一列/行</option>
								<option value="2">二列/行</option>
							</select>
						</td>
					</tr>
					<tr>
                        <th>
                            <span class="red">*</span>显示类型
                        </th>
                        <td>
                            <select id="scanStyle" name="scanStyle" style="width:206px;">
                                <option value="TILE">平铺</option>
                                <option value="LIST">列表</option>
                            </select>
                        </td>
                    </tr>
					<tr>
						<th>
							<span class="red"></span>最少一条记录
						</th>
						<td>
							<input type="radio" class="text_nor" name="lessThanOne" value="true" <c:if test="${model.lessThanOne eq true}">checked="checked"</c:if> />是
							<input type="radio" class="text_nor" name="lessThanOne" value="false" <c:if test="${!model.lessThanOne eq true}">checked="checked"</c:if>  />否
						</td>
					</tr>
					
					<tr>
						<th>
							<span class="red"></span>信息来源
						</th>
						<td>
							<input type="hidden" name="xxly" value="${model.xxly}" />
							<c:if test="${model.xxly == 'system'}">
							系统初始化
							</c:if>
							<c:if test="${model.xxly != 'system'}">
							用户自定义
							</c:if>
						</td>
					</tr>
					
					<tr>
                        <th>
                            <span class="red"></span>顺序码
                        </th>
                        <td>
                            <input type="text" class="text_nor" id="index" name="index" style="width:200px" maxlength="30" value="${model.index}" />
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red"></span>排序
                        </th>
                        <td>
                            <select name="pxsx" id="pxsx">
	                            <option value="">--请选择--</option>
	                            <c:forEach items="${model.properties }" var="p">
	                            <c:if test="${!p.virtual}">
	                                <option value="${p.fieldName }" >${p.name }</option>
	                            </c:if>
	                            </c:forEach>
                            </select>
                            <select name="pxfs" id="pxfs">
                                <option value="" >顺序 </option>
                                <option value="desc" >倒序</option>
                            </select>
                        </td>
                    </tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>