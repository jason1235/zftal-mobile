<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/lockTableTitle.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
	
	<script type="text/javascript">
		$(function(){
			$("#btn_zj").click(function(){
				showWindow("增加","<%=request.getContextPath()%>/manoeuvre/manoeuvre_edit.html","730","540");
			});
			$("tbody > tr[name^='tr']").dblclick(function(){
				var currentId = $("#current_" + $(this).attr("id")).val();
				if('${listType}' == 'declare' && (currentId == '' || currentId == null)){
					var id = $(this).attr("id");
					queryEntity(id);
				}
				else if('${listType}' == 'declare' && currentId != '' && currentId != null){
					var id = $(this).closest("tr").attr("id");
					showDtl(id);
				}
				else if('${listType}' == 'audit'){
					var id = $(this).closest("tr").attr("id");
					audit(id);
				}
			});
			$("button[name='search']").click(function(e){
				$("#searchForm").attr("action","<%=request.getContextPath()%>/manoeuvre/manoeuvre_list.html?listType=${listType}");
				$("#searchForm").attr("method","post");
				$("#searchForm").submit();
			});
			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		})
		
		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").attr("id");
				queryEntity(id);
			});
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").attr("id");
				preDel(id);
			});
			$("a[name='commit']").click(function(){
				var id = $(this).closest("tr").attr("id");
				preCommit(id);
			});
			$("a[name='audit']").click(function(){
				var id = $(this).closest("tr").attr("id");
				audit(id);
			});

			$("a[name='auditproc']").click(function(){
				var id = $(this).closest("tr").attr("id");
				auditproc(id);
			});

			$("a[name='showDtl']").click(function(){
				var id = $(this).closest("tr").attr("id");
				showDtl(id);
			});
			
			$(".select_tools a").css("cursor","pointer");
			operationHover();
			$("a[name='modify']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});
		}

		function audit(id){
			if(id == null || id == ''){
				showWarning("未选定任何记录");
				return false;
			}
			showWindow("审核","<%=request.getContextPath()%>/manoeuvre/manoeuvre_toAudit.html?guid="+id, 730, 540);
		}

		function auditproc(id){
			if(id == null || id == ''){
				showWarning("未选定任何记录");
				return false;
			}
//			showWindow("审核记录","<%=request.getContextPath()%>/manoeuvre/AuditStatus_list.html?query.manoeuvreInfo.guid="+id, 720, 540);
			$("#searchForm").attr("action","manoeuvre/AuditStatus_list.html?query.manoeuvreInfo.guid="+id);
			$("#searchForm").submit();
		}

		function showDtl(id){
			$("input[name='query.guid']").val(id);
			$("#searchForm").attr("action","manoeuvre/manoeuvre_show.html");
			$("#searchForm").submit();
		}

		function queryEntity(id){
			showWindow("修改","<%=request.getContextPath()%>/manoeuvre/manoeuvre_edit.html?guid=" + id,"730","540");
		}	

		function preDel(id){
			showConfirm("确定要删除吗？");

			$("#why_cancel").click(function(){
				divClose();
			});

			$("#why_sure").click(function(){
				delEntity(id);
			});
		}	

		function preCommit(id){
			showConfirm("确定要提交吗？");

			$("#why_cancel").click(function(){
				divClose();
			});

			$("#why_sure").click(function(){
				commit(id);
			});
		}	

		function commit(id){
			$.post('<%=request.getContextPath()%>/manoeuvre/manoeuvre_commit.html',"guid="+id,function(data){
				var callback = function(){
					$("#searchForm").submit();
				};
				processDataCall(data, callback);
			},"json");
		}
		
		function delEntity(id){
			$.post('<%=request.getContextPath()%>/manoeuvre/manoeuvre_remove.html',"guid="+id,function(data){
				var callback = function(){
					$("#searchForm").submit();
				};
				processDataCall(data, callback);
			},"json");
		}
		
		 /*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#searchForm").attr("action","<%=request.getContextPath()%>/manoeuvre/manoeuvre_list.html?listType=${listType}");
			$("#searchForm").submit();
		}
	</script>
	<script type="text/javascript">
		$(document).ready(function () { 
			//第一个参数：table的ID，第二个参数：要锁定的列数目，第三个参数：显示的宽度，第四个参数：显示的高度。注意table里面都必须为td，th的话会出现问题
			if($("#tiptab").width()>800){
				FixTable("tiptab", 4, "", "");
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
		
		
		$('a[class="btn_dc"]').click(function(){
				if(!checkSelect('idckb')){
					alert('请选择要导出的人员信息！');
					return;
				}
				if(!checkSingleSelect('idckb')){
					alert('只能选择一条人员信息导出！');
					return;
				}
				var flag = '';
				if ($(this).attr('id') == 'btn_print') {
					flag = 'print';
				} else if ($(this).attr('id') == 'btn_export') {
					flag = 'export';
				}
				exportInfo(flag);
				
			});
		
		function exportInfo(flag){
			var idc = getSingleSelect('idckb');
			var id = trim(idc.split('|')[0]);
			var staffId = trim(idc.split('|')[1]);
			if (flag == 'print') {
				window.open(_path+"/manoeuvre/manoeuvre_exportWord.html?query.guid="+id + "&staffId=" + staffId + "&category=letter");
			} else if (flag == 'export') {
				window.open(_path+"/manoeuvre/manoeuvre_exportWord.html?query.guid="+id + "&staffId=" + staffId);
			}
			
		}
		
		$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				var bm = $(this).attr('xuhaobianma');
				var current = $(this).attr("class");
				var column = $('#tiptab_tableColumn tr[xuhaobianma="'+bm+'"]');
				var data = $('#tiptab_tableData tr[xuhaobianma="'+bm+'"]');
				if(current != null && current != '') {
					//$(this).removeClass("current");
					//$(this).find("input[name='idckb']").removeAttr("checked");
					column.removeClass("current");
					column.find("input[name='idckb']").removeAttr("checked");
					data.removeClass("current");
					//data.find("input[name='idckb']").removeAttr("checked");
				}else{
//					$(this).attr("class", "current");
//					$(this).find("input[name='idckb']").attr("checked","checked");
					data.attr("class", "current");
					//data.find("input[name='idckb']").attr("checked","checked");
					column.attr("class", "current");
					column.find("input[name='idckb']").attr("checked","checked");
				}
			});
			    
		});
	</script> 
  </head>
  
  <body>
  <div class="toolbox">
  	<c:if test="${listType != 'audit'}">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">申 报</a>
							<a id="btn_export" class="btn_dc">导出人员信息</a>
						</li>
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
	 </c:if>
	</div>
 <form action="" name="searchForm" id="searchForm" method="post">
 	<input type="hidden" id="listType" value="${listType }"/>
 	<input type="hidden" id="query.guid" name="query.guid"/>
 	<input type="hidden" id="query.wideStaffid" name="query.wideStaffid" value='1'/>
 	<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 	<input type="hidden" id="asc" name="asc" value="${asc}"/>

	<c:if test="${listType != 'declare'}">
	<div class="searchtab">
	    <table width="100%" border="0">
	      <tbody>
	        <tr>
	          <th width="10%">调入部门</th>
	          <td>
	            <ct:codePicker name="query.planOrg" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.planOrg }" width="130"/>
	          </td>
	          <th width="10%">调出部门</th>
	          <td>
	            <ct:codePicker name="query.currentOrg" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.currentOrg }" width="130"/>
	          </td>

	        <%--<tr>
			  <th width="10%">起始申请时间</th>
			  <td width="35%">
				<input type="text" name="query.applyTimeMin" onclick="hideDownError(this);" style="width:160px" value="${query.applyTimeMinText}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
			  </td>
			  <th width="10%">终止申请时间</th>
			  <td width="35%">
				<input type="text" name="query.applyTimeMax" onclick="hideDownError(this);" style="width:160px" value="${query.applyTimeMaxText}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
			  </td>
	        </tr>
	        --%>
	          <th width="10%">变更类别</th>
	          <td>
	            <ct:codePicker name="query.manoeuvreType" catalog="<%=ICodeConstants.DM_XB_RYDPLB %>" code="${query.manoeuvreType }" width="100"/>
	          </td>
	         
	          <td colspan="6">
	            <div class="btn">
	              <button class="btn_cx" name="search" type="button" >查 询</button>
	            </div>
	          </td>
	          </tr>
	        </tr>
	      </tbody>
	    </table>
	  </div>
	</c:if>
	
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>人员变更管理<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
					    <td><input type="checkbox" disabled /></td>
                        <c:if test="${op == null}">
                        <td width="80px">操作</td>
                        </c:if>
                        <td>状态</td>
						<c:if test="${'gh' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="gh">职工号</td>
						</c:if>
						<c:if test="${'gh' != sortFieldName}">
						<td class="sort_title" id="gh">职工号</td>
						</c:if>
						<c:if test="${'xm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="xm">姓名</td>
						</c:if>
						<c:if test="${'xm' != sortFieldName}">
						<td class="sort_title" id="xm">姓名</td>
						</c:if>
						<c:if test="${'ybm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ybm">调出部门</td>
						</c:if>
						<c:if test="${'ybm' != sortFieldName}">
						<td class="sort_title" id="ybm">调出部门</td>
						</c:if>
						<c:if test="${'drbm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="drbm">调入部门</td>
						</c:if>
						<c:if test="${'drbm' != sortFieldName}">
						<td class="sort_title" id="drbm">调入部门</td>
						</c:if>
						<c:if test="${'ygw' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ygw">当前岗位</td>
						</c:if>
						<c:if test="${'ygw' != sortFieldName}">
						<td class="sort_title" id="ygw">当前岗位</td>
						</c:if>
						<%--<td>当前岗位类别</td>--%>
						<c:if test="${'xgw' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="xgw">调任岗位</td>
						</c:if>
						<c:if test="${'xgw' != sortFieldName}">
						<td class="sort_title" id="xgw">调任岗位</td>
						</c:if>
						<%--<td>调任岗位类别</td>--%>
						<c:if test="${'dplb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="dplb">变更类别</td>
						</c:if>
						<c:if test="${'dplb' != sortFieldName}">
						<td class="sort_title" id="dplb">变更类别</td>
						</c:if>
						<c:if test="${'ddlx' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ddlx">调动类型</td>
						</c:if>
						<c:if test="${'ddlx' != sortFieldName}">
						<td class="sort_title" id="ddlx">调动类型</td>
						</c:if>
						<c:if test="${'sqsj' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="sqsj">申请时间</td>
						</c:if>
						<c:if test="${'sqsj' != sortFieldName}">
						<td class="sort_title" id="sqsj">申请时间</td>
						</c:if>
						<c:if test="${'bzlb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="bzlb">调入编制类别</td>
						</c:if>
						<c:if test="${'bzlb' != sortFieldName}">
						<td class="sort_title" id="bzlb">调入编制类别</td>
						</c:if>
						<c:if test="${'bgsj' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="bgsj">变更时间</td>
						</c:if>
						<c:if test="${'bgsj' != sortFieldName}">
						<td class="sort_title" id="bgsj">变更时间</td>
						</c:if>
						
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="PageList" var="info">
						<tr name="tr" id="${info.guid }">
						<td><input type="checkbox" name="idckb" value="${info.guid }|${info.staffid }" /></td>
                        <c:if test="${op == null}">
                        <td>
                            <c:if test="${listType != 'audit'}">
                            
                                <c:choose>
                                    <c:when test="${info.currentNodeId == null || info.currentNodeId == ''}">
                                        <div>
                                            <div class="current_item">
                                                <span class="item_text">修改</span>
                                            </div>
                                            <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
                                                <ul>
                                                    <li><a name="modify" href="#" class="first1">修改</a></li>
                                                    <li><a name="del" href="#" class="last1">删除</a></li>
                                                    <li><a name="commit" href="#" class="last1">提交</a></li>
                                                    <c:if test="${info.beenDeclared}">
                                                        <li><a name="showDtl" class="last1">查看详情</a></li>
                                                    </c:if>
                                                </ul>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div>
                                            <div class="current_item">
                                                <span class="item_text">审核情况</span>
                                            </div>
                                            <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
                                                <ul>
                                                    <%--<li><a name="auditproc" class="first1">审核记录</a></li>
                                                    --%><li><a name="showDtl" class="last1">查看详情</a></li>
                                                </ul>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                    
                                
                            </c:if>
                            
                            <c:if test="${listType == 'audit'}">
                                <div>
                                    <div class="current_item">
                                        <span class="item_text">审核</span>
                                    </div>
                                    <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
                                        <ul>
                                            <li><a name="audit" class="first1">审核</a></li>
                                            <li><a name="auditproc" class="last1">审核记录</a></li>
                                            <li><a name="showDtl" class="last1">查看详情</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </c:if>
                            
                        </td>
                        </c:if>
                        <td>
                            <input type="hidden" id="current_${info.guid }" <c:if test="${info.currentNode != null }"> value="${info.currentNode.nid }" </c:if> />
                            <c:if test="${listType != 'audit'}">
                                <c:choose>
                                    <c:when test="${!info.finishAudit && (info.currentNodeId == null || info.currentNodeId == '')}">
                                        <font color="red">未提交</font>
                                    </c:when>
                                    <c:otherwise>
                                         <c:if test="${info.finishAudit}">${info.auditResultText}</c:if><c:if test="${!info.finishAudit}">${info.currentNode.nodeName } : 审核中</c:if>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            
                            <c:if test="${listType == 'audit'}">
                            ${info.currentNode.nodeName }
                            </c:if>
                            
                        </td>
						<td>${info.staffid }</td>
						<td>${info.personInfo.viewHtml['xm'] }</td>
						<td>${info.currentOrgText }</td>
						<td>${info.planOrgText }</td>
						<td>${info.currentPostText }</td>
						<%--<td>${info.currentPostTypeText }</td>--%>
						<td>${info.planPostText }</td>
						<%--<td>${info.planPostTypeText }</td>--%>
						<td>${info.manoeuvreTypeText }</td>
						<td>${info.ddlxText }</td>
						<td>${info.applyTimeText }</td>
						<td>    <ct:codeParse catalog="<%=ICodeConstants.AUTH_TYPE %>" code="${info.formationType }" />       </td>
						<td><fmt:formatDate value="${info.changeTime}" pattern="yyyy-MM-dd"/></td>
						
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
     <ct:page pageList="${pageList }" />
	</div>
  	  </form>
  </body>
</html>
