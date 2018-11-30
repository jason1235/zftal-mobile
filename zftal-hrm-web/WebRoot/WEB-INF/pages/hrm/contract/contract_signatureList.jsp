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
				//toDetail(id);
			});
			
			$('a[class="btn_xg"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要修改合同的教职工信息！');
					return;
				}
				
				if(!checkSingleSelect('ids')){
					alert('只能选择一条教职工信息进行修改！');
					return;
				}
				var id = getSingleSelect('ids');
				toModify(id);
			});
			
			$('a[class="btn_dr"]').click(function(){
				contractImport();
			});
				
			$('a[class="btn_zj"]').click(function(){
				apply();
			});
			
			$('a[class="btn_sc"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要作废合同信息！');
					return;
				}
				
				del();
			});
			
			$('a[class="btn_qxsh"]').click(function(){
				if(!checkSelect('ids')){
					alert('请选择要作废合同的申报信息！');
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
			
			$("#closeInfoWindow").click(function(e){
				requestData1('/contractNew_signatureList',$("#viewId input").serialize());		
				e.preventDefault();//阻止默认的按钮事件，防止多次请求	
			});
			
			$("button[name='search']").click(function(e){//搜索按钮
				//requestData2($("#searchtab input, #searchtab select").serialize());
				requestData1('/contractNew_signatureList',$("#search input, #search select").serialize());	
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
				
			initSelect("type","${model.type}");
			initRadio("sign","${model.sign}");
			fillRows("16", "", "", false);//填充空行
			$("tbody > tr").attr('style','line-height:16px;');
		});
		
		function contractImport(){//合同导入
			showWindow("合同导入", "<%=request.getContextPath()%>/contract/contractNew_importContract.html", 480, 150 );
		}		
		
		function del(){
			//showConfirm("此操作将会删除所有申报信息，确定要删除吗？");
		
			//$("#why_cancel").click(function(){
				//divClose();
			//});
		
			//$("#why_sure").click(function(){
				$.post('<%=request.getContextPath()%>/contract/contractNew_operate.html',$("#list_body input").serialize() + "&disuse=true",function(data){
					if( data.success ) {
						requestData1('/contractNew_signatureList',$("#searchtab input, #searchtab select").serialize());
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
			showWindow("合同信息维护","<%=request.getContextPath()%>/contract/contractNew_toSign.html?bol=true&workNum=" + id,"695","530");
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
		
		function requestProgress(){
			$("#windown-content").unbind("ajaxStart");
			$.post('<%=request.getContextPath()%>/baseinfo/infoClassData_process.html',"",function(data){
				if(data.success){
					$("#scroll").append(data.result.description);
					//var li = $("#scroll").find("li:last");
					$("#scroll").parent().scrollTop($("#scroll").height());
					if(data.progress != null){
						$("#progressTitle").text(data.progress.msg);
						$("#progressInfo").text(data.progress.percent);
						$("#progressbar").css("width",data.progress.percent);
					} 
					if(data.result.finish){//操作结束，出现关闭按钮
						$("#viewInfoWindow").fadeOut("normal",function(){
							$("#closeInfoWindow").fadeIn("normal");
						});
					}else{
						setTimeout("requestProgress()",200);//请求间隔200ms
					}
				}else{
					$("#scroll").append("<li><font color='red'>请求失败</font></li>");
				}
			},"json");
		}
		
		function showProgress(){
			$("#scroll").empty();
			$("#progressTitle").empty();
			$("#progressInfo").empty();
			$("#viewInfoWindow").show();
			$("#closeInfoWindow").hide();
			$("#progressbar").css("width","0px");
			var left = ($(document).width()-600)/2;
			$("#tips").css({"left":left+"px","top":40+"px"});
			$("#tips").fadeIn("slow");
		}
		</script>
	</head>
	<body>
		<iframe name="frame" src="about:blank" style="display:none"></iframe>
		<div class="toolbox">
		    <!-- 按钮 -->
		    <div class="buttonbox">
		        <ul>
		            <li>
		                <a onclick="return false;" class="btn_dr" href="#">
		                  合同导入
		                </a>
		            </li>
		            <li>
		                <a onclick="return false;" class="btn_xg" href="#">
		                  修改
		                </a>
		            </li>
		            <li>
		                <a onclick="return false;" class="btn_sc" href="#">
		                  合同作废
		                </a>
		            </li>
		        </ul>
			</div>	
		</div>
		
		<form id="search" method="post" action="<%=request.getContextPath()%>/contract/contractNew_view.html">
			<input type="hidden" name="path" value="/contractNew_signatureList"/>
			<input type="hidden" name="totalResult" value="${model.totalResult }"/>
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
			                <td width="18%">
								<select style="width:134px" name="type">
									<option value="">全部</option>
				                  <c:forEach items="${typeList }" var="st">
						              <option value="${st.htzldm }">${st.htzlmc }</option>
				                  </c:forEach>
				                </select>
							</td>
						</tr>
						<tr>
							<th width="10%">
								起始日期
							</th>
							<td width="18%">
								<input type="text" name="startDate" style="width:134px" value="<fmt:formatDate value="${model.startDate}" pattern="yyyy-MM-dd" />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
							<th width="10%">
								终止日期
							</th>
							<td width="18%">
								<input type="text" name="endDate" style="width:134px" value="<fmt:formatDate value="${model.endDate}" pattern="yyyy-MM-dd" />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
							<th width="10%">合同编号</th>
			                <td width="18%">
								<input type="text" name="number" class="text_nor" style="width:130px" value="${model.number }" />
							</td>
						</tr>
		            </tbody>
		            <tfoot>
			     		<tr>
			       			<td colspan="6">
			           			<div class="bz"><span class="red"></span></div>
		         				<div class="btn">
		           					<button id="search" name="search" >查 询</button>
		         				</div>
		         			</td>
		         		</tr>
		         	</tfoot>
	          	</table>
	        </div>
			<div class="formbox ">
				<!--标题start-->
			    <h3 class="datetitle_01">
			    	<span>合同信息明细<%--<font color="#0f5dc2" style="font-weight:normal;">（提示：双击可以查看选定行）</font>--%></span>
			    </h3>
				<!--标题end-->
				<div class="con_overlfow">
					<table width="100%" class="dateline nowrap" id="tiptab" >
						<thead id="list_head">
							<tr>
								<td width="4%">
									<input type="checkbox" onclick="selectAllOrCancel(this,'ids');" id="allCheckBoxDel"/>
								</td>
								<td width="5%">序号</td>
								<td>工号</td>
								<td>姓名</td>
								<td>所属部门</td>
								<td>合同编号</td>
								<td>合同种类</td>
								<td>合同期限</td>
								<td>合同状态</td>
								<td>合同起始日期</td>
								<td>合同终止日期</td>
								<td>合同实际终止日期</td>
								<td>试用期截止日期</td>
								<td>试用期实际截止日期</td>
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
								<td>${contract.number }</td>
								<td>
									<c:forEach items="${typeList }" var="st">
						                <c:if test="${st.htzldm == contract.type }">${st.htzlmc }</c:if>
					                </c:forEach>
								</td>
								<td>${contract.term }年</td>
								<td>
									<c:forEach items="${statusList }" var="st">
						                <c:if test="${st.key == contract.status }">${st.text }</c:if>
					                </c:forEach>
								</td>
								<td><fmt:formatDate value="${contract.startDate }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${contract.endDate }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${contract.actualEndDate }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${contract.probationDate }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${contract.actualProbationDate }" pattern="yyyy-MM-dd" /></td>
							  </tr>
							</c:forEach>
						</tbody>
				 	</table>
			 	</div>
			</div>
			
	<div id="tips" style="position:absolute;z-index:2000;display:none;background: none repeat scroll 0 0 #ffffff">
	<div class="readme" style="margin:0px;" style="width:640px">
	  <h2>操作信息</h2>
	  <div style="height:370px;width:640px;overflow-x:hidden;overflow-y:auto;">
	  <ul id="scroll">
	  </ul>
	  </div>
	  <table style="width:640px;">
		  <tr style="background:#E8F0FB;">
		  <td width="70px">
		  <div style="float:left;margin:5px">
			<font id="progressTitle"></font>
		  </div>
		  </td>
		  <td width="400px">
		  <div style="border: 1px solid #AAAAAA;background:#ffffff;height:20px">
		  	<div id="progressbar" class="progressbar" style="width:0px"></div>
		  </div>
		  </td>
		  <td width="70px">
		  <div style="float:left;margin:5px">
			<font id="progressInfo"></font>
		  </div>
		  </td>
		  <td>
		  <div style="float:right;margin:5px">
			<button id="closeInfoWindow" name="close">关     闭</button>
			<button id="viewInfoWindow" >处理中</button>
		  </div>
		  </td>
		  </tr>
	  </table>
	</div>
	</div>
			<ct:page pageList="${pageList }" query="${model }" queryName="model"/>
		</form>
	</body>
</html>
