<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<script type="text/javascript">
		$(function(){
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("td[name='id'] span").text();
				modify(id);
			});
			
			$('a[class="btn_zj"]').click(function(){
				add();								
			});
			
			$('a[class="btn_xg"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要修改的邮件模板！');
					return;
				}
				
				if(!checkSingleSelect('ids')){
					alert('只能选择一条邮件模板进行修改！');
					return;
				}
				var id = getSingleSelect('ids');
				modify(id);
			});
			
			$('a[class="btn_sc"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要删除的邮件模板！');
					return;
				}
				
				del();
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

			$("#configset").click(function(){
				showWindow("服务配置","<%=request.getContextPath()%>/mail/mailTemplate_config.html", 600,420);
			});
			
			fillRows("16", "", "", false);//填充空行
			$("tbody > tr").attr('style','line-height:16px;');
		});
		
		function modify(id){
			window.location.href = "<%=request.getContextPath()%>/mail/mailTemplate_toPage.html?id=" + id;
		}
		
		function add(){
			window.location.href = "<%=request.getContextPath()%>/mail/mailTemplate_toPage.html";
		}
		
		function del(){
			showConfirm("确定要删除邮件模板吗？");
		
			$("#why_cancel").click(function(){
				divClose();
			});
		
			$("#why_sure").click(function(){
				$.post('<%=request.getContextPath()%>/mail/mailTemplate_remove.html',$("#list_body input").serialize(),function(data){
					if(data.success){
						window.location.href = "<%=request.getContextPath()%>/mail/mailTemplate_list.html";
					}else{
						tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
						$("#window-sure").click(function() {
							divClose();
						});
					}
				},"json");
			});
		}
		
		function getInputHtml(){
			var inputHTML = "";
			$("#searchtab input").each(function(){
				inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
			});
			
			return inputHTML;
		}
		
		function selectAllOrCancel(obj,name){//全选选择框操作
			var checks = document.getElementsByName(name);
			var tr = document.getElementsByName("tr");
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
		<div class="toolbox">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul>
		            <li>
		                <a onclick="return false;" class="btn_zj" href="#">
		                   增加
		                </a>
		            </li>
		            <li>
		                <a onclick="return false;" class="btn_xg" href="#">
		                   修改
		                </a>
		            </li>
		            <li>
		                <a onclick="return false;" class="btn_sc" href="#">
		                   删除
		                </a>
		            </li>
		            <li>
                        <a onclick="return false;" id="configset" class="btn_ck" href="#">
                                        服务配置
                        </a>
                    </li>
		        </ul>
			</div>	
		</div>
		<div class="formbox">
			<!--标题start-->
		    <h3 class="datetitle_01">
		    	<span>邮件模板信息<font color="#0f5dc2" style="font-weight:normal;">（提示：双击可以修改选定行）</font></span>
		    </h3> 
		    <div class="con_overlfow">
				<!--标题end-->
				<table width="100%" class="dateline nowrap" id="tiptab" >
					<thead id="list_head">
						<tr>
							<%--<input style="display: none;" type="checkbox" onclick="selectAllOrCancel(this,'ids');" id="allCheckBoxDel"/>--%>
							<td width="5%">序号</td>
							<td>模板名称</td>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${list}" var="mailTemplate" varStatus="st">
						  <tr name="tr">
						  	<td style="display: none;" name="id"><span>${mailTemplate.id }</span></td>
						  	<td style="display: none;" >
							  	<input type="checkbox" name="ids" value="${mailTemplate.id }"/>
						  	</td>
							<td>${(st.index+1)}</td>
							<td>${mailTemplate.taskName }</td>
						  </tr>
						</c:forEach>
					</tbody>
			 	</table>
			</div>
		</div>
	</body>
</html>
