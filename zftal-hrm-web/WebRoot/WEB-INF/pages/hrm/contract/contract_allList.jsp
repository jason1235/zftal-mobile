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
				//requestData2($("#searchtab input, #searchtab select").serialize());
				requestData1('/contractNew_allList',$("#search input, #search select").serialize());	
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
				
			initSelect("type","${model.type}");
			initSelect("status","${model.status}");
			initRadio("sign","${model.sign}");
			fillRows("16", "", "", false);//填充空行
			$("tbody > tr").attr('style','line-height:16px;');
		});
		
		
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
		<form id="search" method="post" action="<%=request.getContextPath()%>/contract/contractNew_view.html">
			<input type="hidden" name="path" value="/contractNew_allList"/>
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
						<tr>
							<th width="10%">合同状态</th>
			                <td width="18%">
								<select style="width:134px" name="status">
									  <option value="">全部</option>
				                  <c:forEach items="${statusList }" var="st">
						              <option value="${st.key }">${st.text }</option>
				                  </c:forEach>
				                </select>
							</td>
							<td colspan="4" width="18%">
								<input type="checkbox" name="disuse" value="true" <c:if test="${model.disuse != null && model.disuse}">checked</c:if>/>作废合同
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
								<td>延期结束日期</td>
								<td>解除日期</td>
								<td>终止原因</td>
								<td>延期原因</td>
								<td>解除原因</td>
								<td>转正说明</td>
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
								<td><fmt:formatDate value="${contract.deferDate }" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${contract.ravelDate }" pattern="yyyy-MM-dd" /></td>
								<td>${contract.overResult }</td>
								<td>${contract.deferResult }</td>
								<td>${contract.ravelResult }</td>
								<td>${contract.regularResult }</td>
							  </tr>
							</c:forEach>
						</tbody>
				 	</table>
			 	</div>
			</div>
			<ct:page pageList="${pageList }" query="${model }" queryName="model"/>
		</form>
	</body>
</html>
