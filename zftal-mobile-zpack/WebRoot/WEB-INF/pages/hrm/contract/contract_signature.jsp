<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<script type="text/javascript">
		$(function(){
			$('a[class="btn_bc"]').click(function(){
			});
			
			$('a[class="btn_up"]').click(function(){
				var id = $("input[name='id']").val();
				if(id.length == 0){
		    		push();
				}else{
					modify();
				}
			});
			
			$('a[class="btn_fh"]').click(function(){
				back();
			});
			initSelect("type","${model.type}");
			fillRows("10", "", "", false);//填充空行
		});
		
		function push(){
			if(!validate()){
				return false;
			}
			
			$.post('<%=request.getContextPath()%>/contract/contractNew_sign.html',$("#demo_xxxx input, #demo_xxxx select, #demo_xxxx textarea").serialize(),function(data){
				if( data.success ) {
					divClose();
					back();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						alertDivClose();
					});
				}
			},"json");
		}
		
		function save(){
			if(!validate()){
				return false;
			}
			
			$.post('<%=request.getContextPath()%>/contract/contractNew_insert.html',$("#demo_xxxx input, #demo_xxxx select, #demo_xxxx textarea").serialize(),function(data){
				if( data.success ) {
					divClose();
					back();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						alertDivClose();
					});
				}
			},"json");
		}
		
		function modify(){
			if(!validate()){
				return false;
			}
			if('${category}' != null && '${category}'.length > 0){
				$.post('<%=request.getContextPath()%>/contract/contractNew_sequel.html',$("#demo_xxxx input, #demo_xxxx select, #demo_xxxx textarea").serialize(),function(data){
					if( data.success ) {
						divClose();
						list();
					}else{
						tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
						$("#window-sure").click(function() {
							alertDivClose();
						});
					}
				},"json");
				
				return;
			}
			$.post('<%=request.getContextPath()%>/contract/contractNew_modify.html',$("#demo_xxxx input, #demo_xxxx select, #demo_xxxx textarea").serialize(),function(data){
				if( data.success ) {
					divClose();
					requestData1('/contractNew_signatureList',$("#search").serialize());
					back();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						alertDivClose();
					});
				}
			},"json");
		}
		
		function back(){
			if('${bol}' == 'true'){
				divClose();
				return;
			}
			var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/contract/contractNew_staffList.html">';
			content +='    </form>';
			$('body').append(content);
			$('#form').submit();
			$('#form').remove();
		}
		
		function checkNum(input,oldNum){
			var v = $(input).val();
			var p = new RegExp("^([0-9]*)$");
			var res = p.test(v);
			if(!res){
				alert("请输入数字！");
				$(input).val(oldNum);
				$(input).focus();
				return false;
			}
		}
		
		function validate(){
			var v = $("#form1 input[name='number']");
			if(v.val().length == 0){
				alert("合同编号不能为空");
				return false;
			}
			v = $("#form1 input[name='term']");
			if(v.val().length == 0){
				alert("合同期限不能为空");
				return false;
			}
			
			v = $("#form1 input[name='startDate']");
			if(v.val().length == 0){
				alert("合同起始日期不能为空");
				return false;
			}
			//v = $("#form1 input[name='probationDate']");
			//if(v.val().length == 0){
			////	alert("试用期截止日期不能为空");
			//	return false;
			//}
			v = $("#form1 input[name='endDate']");
			if(v.val().length == 0){
				alert("合同终止日期不能为空");
				return false;
			}
			return true;
		}
		</script>
	</head>
	<body>
		<!--友情提醒end-->
		<div class="toolbox">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul class="btn_xxxx" style="background:none;border-top:none;">
		            <li>
		                <a onclick="return false;" class="btn_up" href="#">
		                  确认
		                </a>
		            </li>
		            <%--<li class="btn_xxxx_bc" style="background:url('<%=request.getContextPath() %>/img/welfareinsurance/line_01.gif') no-repeat scroll right center transparent;">
		                <a onclick="return false;" class="btn_bc" href="#" style="float:none;">
		                  保存
		                </a>
		            </li>
		        --%>
		            <li>
		                <a onclick="return false;" class="btn_fh" href="#">
		                   返回
		                </a>
		            </li>
		        </ul>
			</div>	
		</div>
		<div style="margin: 0px auto; clear:both;width:100% ">
			<input type="hidden" name="id" value="${model.id }"/>
	   		<div class="demo_xxxx" id="demo_xxxx">
	   			<table class="formlist">
					<thead>
						<tr>
							<th colspan="4">
								<span>合同信息维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
							</th>
						</tr>
					</thead>
		         	<tbody id="form1">
						<tr>
							<th width="20%">
								职工号
							</th>
							<td width="30%">
								${model.workNum }
								<input type="hidden" name="workNum" value="${model.workNum }"/>
							</td>
							<th width="20%">
								姓名
							</th>
							<td width="30%">
								${model.fullName }
								<input type="hidden" name="fullName" value="${model.fullName }"/>
							</td>
						</tr>
						<tr>
							<th>
								部门
							</th>
							<td>
							    ${model.deptName }
							    <input type="hidden" name="deptId" value="${model.deptId }"/>
							</td>
							<th>
								性别
							</th>
							<td>
								 ${model.sexValue }
								<input type="hidden" name="sex" value="${model.sex }"/>
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>合同编号
							</th>
							<td>
								<input type="text" name="number" class="text_nor" style="width:150px" value="${model.number }"/>
							</td>
							<th>
								<span class="red">*</span>合同种类
							</th>
							<td>
								<c:if test="${category != null }">
									<input type="hidden" name="type" value="${model.type }"/>
									<select style="width:134px" name="type" disabled="disabled">
					                  <c:forEach items="${typeList }" var="st">
							              <option value="${st.htzldm }">${st.htzlmc }</option>
					                  </c:forEach>
					                </select>
								</c:if>
								<c:if test="${category == null }">
									<select style="width:134px" name="type" >
					                  <c:forEach items="${typeList }" var="st">
							              <option value="${st.htzldm }">${st.htzlmc }</option>
					                  </c:forEach>
					                </select>
								</c:if>
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>合同期限
							</th>
							<td>
								<input type="text" name="term" class="text_nor" style="width:150px" value="${model.term}" maxlength="2" onchange="checkNum(this,'${model.term} ');"/>&nbsp;年
							</td>
							<th>
								<span class="red">*</span>合同起始日期
							</th>
							<td>
								<input type="text" name="startDate" style="width:154px" value="<fmt:formatDate value="${model.startDate }" pattern="yyyy-MM-dd" />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
						</tr>
						<tr>
							<th>
								试用期截止日期
							</th>
							<td>
								<input type="text" name="probationDate" style="width:154px" value="<fmt:formatDate value="${model.probationDate}" pattern="yyyy-MM-dd" />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
							<th>
								<span class="red">*</span>合同终止日期
							</th>
							<td>
								<input type="text" name="endDate" style="width:154px" value="<fmt:formatDate value="${model.endDate}" pattern="yyyy-MM-dd" />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
						</tr>
						<tr>
							<th>备注</th>
							<td colspan="3">
								<textarea  name="remark" style="width:88%;height:85px;">${model.remark }</textarea>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4" ><div class="bz">"<span class="red">*</span>"为必填项</div></td>
						</tr>
					</tfoot>
				</table>
		    </div>
		    <c:if test="${!bol }">
			    <div class="formbox">
					<!--标题start-->
				    <h3 class="datetitle_01">
				    	<span>历史合同息明细<%--<font color="#0f5dc2" style="font-weight:normal;">（提示：双击可以查看选定行）</font>--%></span>
				    </h3>
					<!--标题end-->
					<table width="100%" class="dateline" id="tiptab" >
						<thead id="list_head">
							<tr>
								<td width="5%">序号</td>
								<td>合同编号</td>
								<td>合同种类</td>
								<td>合同期限</td>
								<td>合同起始日期</td>
								<td>合同终止日期</td>
								<td>合同实际终止日期</td>
							</tr>
						</thead>
						<tbody id="list_body">
							<c:forEach items="${pageList}" var="contract" varStatus="st">
							  <tr name="tr">
								<td>${(st.index+1) + (model.currentPage - 1) * model.showCount }</td>
								<td>${contract.number }</td>
								<td>${contract.type }</td>
								<td>${contract.term }年</td>
								<td><fmt:formatDate value="${contract.startDate }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${contract.endDate }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${contract.actualEndDate }" pattern="yyyy-MM-dd" /></td>
							  </tr>
							</c:forEach>
						</tbody>
				 	</table>
				</div>
		    </c:if>
			<%--<ct:page pageList="${pageList }" query="${model }" queryName="model"/>--%>
		</div>
	</body>
</html>
