<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		<script type="text/javascript">
		$(function(){
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var tabelName = $(this).find("td[name='tabelName'] span").text();
				detail(tabelName);
			});
			
			$("a[class='btn_ck']").click(function(){//
				if(!checkSelect('ids')){
					alert('请选择要查看的表信息！');
					return;
				}
				if(!checkSingleSelect('ids')){
					alert('只能选择一条表信息进行查看！');
					return;
				}
				var tabelName = getSingleSelect('ids');
				detail(tabelName);
			});
			
			$("a[class='btn_dc']").click(function(){//
				exportInfo();
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
				$("#search").attr("action","<%=request.getContextPath()%>/ddselect/ddSelect_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
				
			fillRows("16", "", "", false);//填充空行
			$("tbody > tr").attr('style','line-height:16px;');
		});
		
		function detail(tabelName){
			showWindow("表结构信息","<%=request.getContextPath()%>/ddselect/ddSelect_detail.html?name=" + tabelName,"695","500");			
		}
		
		function getInputHtml(){
			var inputHTML = "";
			$("#searchtab input,#searchtab select,#list_body input").each(function(){
				if($(this).attr("type") == 'checkbox' && $(this).attr("checked") != null && $(this).attr("checked") == 'checked'){
					inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
				}else if($(this).attr("type") != 'checkbox'){
					inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
				}
			});
			
			return inputHTML;
		}
		
		function initSelect(name,value){
			$("select[name='"+name+"'] > option").each(function(){
				if($(this).val()==value){
					$(this).attr("selected","selected");
				}
			});
		}

		function exportInfo(){
			var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/ddselect/ddSelect_export.html">';
			content += getInputHtml();
			content +='	  </form>';
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
		<div class="toolbox">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul>
		            <li>
		                <a onclick="return false;" class="btn_ck" href="#">
		                   查看详情
		                </a>
		            </li>
		            <li>
		                <a onclick="return false;" class="btn_dc" href="#">
		                   导出
		                </a>
		            </li> 
		        </ul>
			</div>	
		</div>
		<form id="search" method="post" action="<%=request.getContextPath()%>/ddselect/ddSelect_list.html">
		    <!-- 查询条件 -->
		    <div class="searchtab" id="searchtab">
		    	<%--<h3 class="datetitle_01">
			    	<span>查询条件<font color="#0f5dc2" style="font-weight:normal;"></font></span>
			    </h3>
	        	--%><table width="100%" border="0">
		            <tbody>
			            <tr style="line-height:16px;">
			                <th width="10%">表英/中文名</th>
			                <td>
			                	<input type="text" style="width: 130px;" value="${model.queryName }" name="queryName">
							</td>
							<td width="16%">
				                <div class="btn">
				                    <button name="search" class="brn_cx">查 询</button>
				                </div>
			                </td>
			            </tr>
		            </tbody>
	          	</table>
	        </div>
			<div class="formbox">
				<!--标题start-->
			    <h3 class="datetitle_01">
			    	<span>数据字典信息<font color="#0f5dc2" style="font-weight:normal;">（提示：双击可以查看选定行）</font></span>
			    </h3>
				<!--标题end-->
				<table width="100%" class="dateline" id="tiptab" >
					<thead id="list_head">
						<tr>
							<td width="4%">
								<input type="checkbox" onclick="selectAllOrCancel(this,'ids');" id="allCheckBoxDel"/>
							</td>
							<td width="8%">序号</td>
							<td width="45%">英文名</td>
							<td>中文名</td>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${pageList}" var="ddSelect" varStatus="st">
						  <tr name="tr">
						  	<td style="display: none;" name="tabelName"><span>${ddSelect.name }</span></td>
						  	<td><input type="checkbox" name="ids" value="${ddSelect.name}"/></td>
							<td>${(st.index+1) + (model.currentPage - 1) * model.showCount }</td>
							<td>${ddSelect.name }</td>
							<td>${ddSelect.chineseName }</td>
						  </tr>
						</c:forEach>
					</tbody>
			 	</table>
			</div>
			<ct:page pageList="${pageList }" query="${model }" queryName="model"/>
		</form>
	</body>
</html>
