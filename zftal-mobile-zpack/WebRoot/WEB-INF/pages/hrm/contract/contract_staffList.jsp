<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<script type="text/javascript">
		jsImport_<%=pageIndex%>("<%=request.getContextPath() %>/js/hrm/code.js");
		$(function(){
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[name='ids']").val();
				toDetail(id);
			});
			
			$('a[class="btn_xg"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要签订合同的教职工信息！');
					return;
				}
				
				if(!checkSingleSelect('ids')){
					alert('只能选择一条教职工信息进行签订！');
					return;
				}
				var id = getSingleSelect('ids');
				toModify(id);
			});
				
			$('a[class="btn_zj"]').click(function(){
				apply();
			});
			
			$('a[class="btn_sc"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要删除的申报信息！');
					return;
				}
				
				del();
			});
			
			$('a[class="btn_qxsh"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要取消提交的申报信息！');
					return;
				}
				
				cancel();
			});
			
			$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				var current = $(this).attr("class");
				if(current != null && current != '') {
					$(this).removeClass("current");
					$(this).find("input[name='ids']").removeAttr("checked");
				}else{
					$(this).attr("class", "current");
					$(this).find("input[name='ids']").attr("checked","checked");
				}
			});
			
			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/contract/contractNew_staffList.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
				
			initSelect("type","${model.type}");
			initRadio("sign","${model.sign}");
			fillRows("16", "", "", false);//填充空行
			$("tbody > tr").attr('style','line-height:16px;');
		});
		
		function del(){
			//showConfirm("此操作将会删除所有申报信息，确定要删除吗？");
		
			//$("#why_cancel").click(function(){
				//divClose();
			//});
		
			//$("#why_sure").click(function(){
				$.post('<%=request.getContextPath()%>/contract/contract_delete.html',$("#list_body input").serialize(),function(data){
					if( data.success ) {
						var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/contract/contract_applyList.html">';
						content += getInputHtml();
						content +='    </form>';
						$('body').append(content);
						$('#form').submit();
						$('#form').remove();
					}else{
						tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
						$("#window-sure").click(function() {
							divClose();
						});
					}
					
				},"json");
			//});
		}

		function cancel(){
			$.post('<%=request.getContextPath()%>/contract/contract_cancelPush.html',$("#list_body input").serialize(),function(data){
				if( data.success ) {
					var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/contract/contract_applyList.html">';
					content += getInputHtml();
					content +='    </form>';
					$('body').append(content);
					$('#form').submit();
					$('#form').remove();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
					$("#window-sure").click(function() {
						divClose();
					});
				}
				
			},"json");
		}
		
		function apply(){
			var spBillConfigId = $("input[name='spBillConfig.id']").val();
			var content = '<form id="form" method="post" target="frame" action="<%=request.getContextPath()%>/contract/contract_input.html">';
			content +=' 	<input type="hidden" name="spBillConfigId" value="' + spBillConfigId + '" />';
			content +='    </form>';
			$('body').append(content);
			$('#form').submit();
			$('#form').remove();
		}
		
		function add(){
			var spBillConfigId = $("input[name='spBillConfig.id']").val();
			var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/contract/contract_add.html">';
			content +=' 	<input type="hidden" name="spBillConfigId" value="' + spBillConfigId + '" />';
			content +='    </form>';
			$('body').append(content);
			$('#form').submit();
			$('#form').remove();
		}
		
		function tipsRefresh(msg,suc){
			if(suc == 'false'){
				tipsWindown("提示信息","text:"+msg,"340","120","true","","true","id");
			}else{
				add();
			}
			
			$("#window-sure").click(function() {
				divClose();
			});
		}
		
		
		function toModify(id){
			var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/contract/contractNew_toSign.html">';
			content +=' 	<input type="hidden" name="workNum" value="' + id + '" />';
			content +='    </form>';
			$('body').append(content);
			$('#form').submit();
			$('#form').remove();
		}
		
		
		function selectAllOrCancel(obj,name){//全选选择框操作
			var checks = document.getElementsByName(name);
			var body = document.getElementById("list_body");
			var tr = body.getElementsByTagName("tr");
			if(obj.checked){
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='current';
					checks[i].checked = true;
				}
			}else{
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='';
					checks[i].checked = false;
				}
			}
		}

		function checkSelect(name){//选择check
			var checks = document.getElementsByName(name);
			var selectedChk = 0;
			for ( var i = 0; i < checks.length; i++) {
				if(checks[i].checked){
					selectedChk++;
				}
			}
			if(selectedChk == 0){
				return false;
			}
			
			return true;
		}

		function checkSingleSelect(name){//选择check
			var checks = document.getElementsByName(name);
			var selectedChk = 0;
			for ( var i = 0; i < checks.length; i++) {
				if(checks[i].checked){
					selectedChk++;
				}
			}
			if(selectedChk > 1){
				return false;
			}
			
			return true;
		}

		function getSingleSelect(name){//选择check
			var checks = document.getElementsByName(name);
			for ( var i = 0; i < checks.length; i++) {
				if(checks[i].checked){
					return checks[i].value;
				}
			}
			return "";
		}

		</script>
	</head>
	<body>
		<iframe name="frame" src="about:blank" style="display:none"></iframe>
		<div class="toolbox">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul>
		        	<c:if test="${!model.sign }">
			            <li>
			                <a onclick="return false;" class="btn_xg" href="#">
			                  签定合同
			                </a>
			            </li>
		        	</c:if>
		        </ul>
			</div>	
		</div>
		<form id="search" method="post" action="<%=request.getContextPath()%>/contract/contractNew_staffList.html">
		    <!-- 查询条件 -->
		    <div class="searchtab" id="searchtab">
		    	<table width="100%" border="0">
		            <tbody>
			            <tr>
							<th width="10%">
								工号
							</th>
							<td width="18%">
								<input type="text" name="workNum" class="text_nor" style="width:130px" value="${model.workNum }" />
							</td>
							<th width="10%">
								姓名
							</th>
							<td width="18%">
								<input type="text" name="fullName" class="text_nor" style="width:130px" value="${model.fullName }" />
							</td>
							<th width="10%">合同种类</th>
			                <td width="18%" colspan="3">
								<select style="width:134px" name="type">
				                  <c:forEach items="${typeList }" var="st">
						              <option value="${st.htzldm }">${st.htzlmc }</option>
				                  </c:forEach>
				                </select>
							</td>
						</tr>
						<tr>
							<th width="10%">
								是否签定过
							</th>
							<td colspan="3">
								<input name="sign" value="true" type="radio">是
								<input name="sign" value="false" checked="" type="radio">否
							</td>
							<td colspan="2">
			           			<div class="bz"><span class="red"></span></div>
		         				<div class="btn">
		           					<button id="search" name="search" >查 询</button>
		         				</div>
		         			</td>
						</tr>
		            </tbody>
	          	</table>
	        </div>
			<div class="formbox">
				<!--标题start-->
			    <h3 class="datetitle_01">
			    	<span>教职工信息明细<%--<font color="#0f5dc2" style="font-weight:normal;">（提示：双击可以查看选定行）</font>--%></span>
			    </h3>
				<!--标题end-->
				<table width="100%" class="dateline" id="tiptab" >
					<thead id="list_head">
						<tr>
							<td width="4%">
								<input type="checkbox" onclick="selectAllOrCancel(this,'ids');" id="allCheckBoxDel"/>
							</td>
							<td width="5%">序号</td>
							<td>工号</td>
							<td>姓名</td>
							<td>所属部门</td>
							<td>性别</td>
							<td>出生日期</td>
							<td>进校时间</td>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${pageList}" var="contract" varStatus="st">
						  <tr name="tr">
						  	<td><input type="checkbox" name="ids" value="${contract.workNum }"/></td>
							<td>${(st.index+1) + (model.currentPage - 1) * model.showCount }</td>
							<td>${contract.workNum }</td>
							<td>${contract.fullName }</td>
							<td>${contract.deptName }</td>
							<td>${contract.sexValue }</td>
							<td><fmt:formatDate value="${contract.birthDay }" pattern="yyyy-MM-dd" /></td>
							<td><fmt:formatDate value="${contract.recruitDate }" pattern="yyyy-MM-dd" /></td>
						  </tr>
						</c:forEach>
					</tbody>
			 	</table>
			</div>
			<ct:page pageList="${pageList }" query="${model }" queryName="model"/>
		</form>
	</body>
</html>
