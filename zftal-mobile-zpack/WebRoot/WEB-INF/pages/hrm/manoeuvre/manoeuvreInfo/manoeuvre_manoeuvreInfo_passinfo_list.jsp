<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
				showWindow('增加','<%=request.getContextPath()%>/manoeuvre/manoeuvre_edit.html?createdByHR=true','670','400' );
			}); 
			// 20140506 add start
			$("#btn_exportList").click(function() {
                var content = '<form id="exportForm" method="post" action="<%=request.getContextPath()%>/manoeuvre/manoeuvre_exportList.html?listType=${listType}">';
                content += getInputHtml();
                content += '   </form>';
                $('body').append(content);
                $('#exportForm').submit();
                $('#exportForm').remove();
            }); 
			// 20140506 add end
			$("tbody > tr[name^='tr']").dblclick(function(){
				var id = $(this).attr("id");
				queryEntity(id);
			});
			$("button[name='search']").click(function(e){
				$("#searchForm").attr("action","<%=request.getContextPath()%>/manoeuvre/manoeuvre_list.html?listType=${listType}");
				$("#searchForm").attr("method","post");
				$("#searchForm").submit();
			});
			$("a[name='showDtl']").click(function(){
				var id = $(this).closest("tr").attr("id");
				showDtl(id);
			});
			
			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		})
		
		// 20140506 add start
		function getInputHtml() {
            var inputHTML = "";
            $("#searchForm input").each(function(){
                inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
            });

            return inputHTML;
		}
		// 20140506 add end
		
		function operationList(){
			$("a[name='modify']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").attr("id");
				queryEntity(id);
			});

			$("a[name='modifyInfo']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").attr("id");
				modify(id);
			});
			$("a[name='del']").click(function(){//行数据修改链接
				var id = $(this).closest("tr").attr("id");
				preDel(id);
			});

			$("a[name='modifystatus']").click(function(){
				var id = $(this).closest("tr").attr("id");
				modifystatus(id);
			});

			$("a[name='showDtl']").click(function(){
				var id = $(this).closest("tr").attr("id");
				showDtl(id);
			});

			$("a[name='export']").click(function(){
				var id = $(this).closest("tr").attr("id");
				exportDtl(id);
			});
			
			$(".select_tools a").css("cursor","pointer");
			operationHover();
			$("a[name='modify']").each(function(){
				$(this).attr("title","双击数据行可以进入修改");
			});
		}

		
		function showDtl(id){
			$("input[name='query.guid']").val(id);
			$("#searchForm").attr("action","manoeuvre/manoeuvre_show.html?listType=${listType}");
			$("#searchForm").submit();
		}

		function exportDtl(id){
			window.open("<%=request.getContextPath()%>/manoeuvre/manoeuvre_export.html?query.guid="+id);
		}		

		function queryEntity(id){
			showWindow("信息维护","<%=request.getContextPath()%>/manoeuvre/manoeuvre_edit.html?listType=${listType}&guid=" + id,"600","400");
		}	

		function modifystatus(id){
			showWindow("修改","<%=request.getContextPath()%>/manoeuvre/manoeuvre_edit.html?listType=${listType}&editType=status&guid=" + id,"600","400");
		}

		function modify(id){
			showWindow("修改","<%=request.getContextPath()%>/manoeuvre/manoeuvre_edit.html?guid=" + id,"650","370");
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

		function delEntity(id){
			$.post('<%=request.getContextPath()%>/manoeuvre/manoeuvre_remove.html',"guid="+id,function(data){
				var callback = function(){
					$("#searchForm").submit();
				};
				processDataCall(data, callback);
			},"json");
		}
		
		function showTbody(obj,objTbody,className1,className2,html1,html2){
			if(obj.className==className1){
				obj.className=className2;
				obj.innerHTML=html2;
				document.getElementById(objTbody).style.display="none";
			}else{
				obj.className=className1;
				obj.innerHTML=html1;
				document.getElementById(objTbody).style.display="";
			}
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
			FixTable("tiptab", 4, 795, 615);
			
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
				if ($(this).attr('id') != 'btn_exportList') {
					if(!checkSelect('idckb')){
							alert('请选择要导出的人员信息！');
							return;
						}
						if(!checkSingleSelect('idckb')){
							alert('只能选择一条人员信息导出！');
							return;
						}
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
							<a id="btn_zj" class="btn_zj">新增变更信息</a>
						</li>
						<!-- 20140506 add start -->
                        <li>
                            <a id="btn_exportList" class="btn_dc">导出</a>
                            <a id="btn_export" class="btn_dc">导出人员信息</a>
                            <a id="btn_print" class="btn_dc">打印介绍信</a>
                        </li>
                        <!-- 20140506 add end -->
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
	 </c:if>
	</div>
 <form action="" name="searchForm" id="searchForm" method="post">
 	 <div class="more--item_top"><p><a href="#" class="up" onclick="showTbody(this,'searchtab','up','down','收 起','查询条件');return false">收 起</a></p></div>
 	<input type="hidden" id="listType" value="${listType }"/>
 	<input type="hidden" id="query.guid" name="query.guid"/>
 	<input type="hidden" id="query.wideStaffid" name="query.wideStaffid" value='1'/>
	<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 	<input type="hidden" id="asc" name="asc" value="${asc}"/>

	<div class="searchtab" id="searchtab">
	    <table width="100%" border="0">	     
	      <tbody>
	      <tr>
          <th width="15%">姓名</th>
          <td >
            <input name="query.name" value="${query.name}" type="text" style="width: 130px;" />
          </td>
             <th width="15%">职工号</th>
          <td >
            <input name="query.staffid" value="${query.staffid}" type="text" style="width: 130px;" />
          </td>
        </tr>
	         <tr>	 		
		  <th width="15%">变更时间</th>
          <td>
            	<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="changeDateStartSting" id="changeDateStart" value="${changeDateStartSting}" style="width: 100px;"/>  
          	至
            	<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="changeDateEndString" id="changeDateEnd" value="${changeDateEndString}" style="width: 100px;"/>
          </td>	
          <th width="15%">变更类别</th>
	          <td>
	            <ct:codePicker name="query.manoeuvreType" catalog="<%=ICodeConstants.DM_XB_RYDPLB %>" code="${query.manoeuvreType }"  width="130"/>
	          </td>			
        </tr>      
        
	     <tr>
		  <th width="15%">原岗位类别</th>
          <td >
            	<ct:codePicker name="query.currentPostType" catalog="<%=ICodeConstants.DM_DEF_WORKPOST%>" code="${query.currentPostType }" width="130"/>
          </td>
	      <th width="15%">调入岗位类别</th>
          <td >
            	<ct:codePicker name="query.planPostType" catalog="<%=ICodeConstants.DM_DEF_WORKPOST%>" code="${query.planPostType }" width="130"/>
          </td>				
        </tr>
        <tr> 
            <th width="15%">原编制类别</th>
          <td >
            <ct:codePicker name="query.oldFormationType" catalog="<%=ICodeConstants.AUTH_TYPE %>" code="${query.oldFormationType }" width="130"/>
          </td>
						 		
		  <th width="15%">调入编制类别</th>
          <td >
            	<ct:codePicker name="query.formationType" catalog="<%=ICodeConstants.AUTH_TYPE %>" code="${query.formationType }" width="130"/>
          </td>
        </tr>
        
	        <tr>
	        	<th width="15%">原部门</th>
	          <td>
	            <ct:codePicker name="query.currentOrg" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.currentOrg }"  width="130"/>
	          </td>
	          <th width="15%">调入部门</th>
	          <td>
	            <ct:codePicker name="query.planOrg" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.planOrg }" width="130"/>
	          </td>
	          
            </tr>
	        <%--<tr>
			  <th width="10%">起始申请时间</th>
			  <td width="35%">
				<input type="text" name="query.applyTimeMin" onclick="hideDownError(this);" style="width:160px" value="${query.applyTimeMinText}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
			  
			  <input type="text" id="applyTimeMin" name="query.applyTimeMin" value="${query.applyTimeMinText}" />
			  </td>
			  <th width="10%">终止申请时间</th>
			  <td width="35%">
				<input type="text" name="query.applyTimeMax" onclick="hideDownError(this);" style="width:160px" value="${query.applyTimeMaxText}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
			  
			  <input type="text" id="applyTimeMax" name="query.applyTimeMax" value="${query.applyTimeMaxText}"/>
			  </td>
	        </tr>
	      --%>
	        <tfoot>
							<tr>
								<td colspan="4">
									<div class="btn">
										<button class="btn_cx" name="search" type="button" >查 询</button>
									</div>
								</td>
							</tr>
						</tfoot>
	      </tbody>
	    </table>
	  </div>
	
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>人员变更信息管理<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
					<td><input type="checkbox" disabled /></td>
					    <td width="80px">操作</td>
						<c:if test="${'gh' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="gh">职工号</td>
						</c:if>
						<td>执行状态</td>
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
							<td class="sort_title_current_${asc }" id="ybm">原部门</td>
						</c:if>
						<c:if test="${'ybm' != sortFieldName}">
						<td class="sort_title" id="ybm">原部门</td>
						</c:if>
						<c:if test="${'drbm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="drbm">调入部门</td>
						</c:if>
						<c:if test="${'drbm' != sortFieldName}">
						<td class="sort_title" id="drbm">调入部门</td>
						</c:if>
						<c:if test="${'ygwlb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ygwlb">原岗位类别</td>
						</c:if>
						<c:if test="${'ygwlb' != sortFieldName}">
						<td class="sort_title" id="ygwlb">原岗位类别</td>
						</c:if>
						<c:if test="${'xgwlb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="xgwlb">调入岗位类别</td>
						</c:if>
						<c:if test="${'xgwlb' != sortFieldName}">
						<td class="sort_title" id="xgwlb">调入岗位类别</td>
						</c:if>
						<c:if test="${'qbzlb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="qbzlb">原编制类别</td>
						</c:if>
						<c:if test="${'qbzlb' != sortFieldName}">
						<td class="sort_title" id="qbzlb">原编制类别</td>
						</c:if>
						<c:if test="${'bzlb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="bzlb">调入编制类别</td>
						</c:if>
						<c:if test="${'bzlb' != sortFieldName}">
						<td class="sort_title" id="bzlb">调入编制类别</td>
						</c:if>
						<c:if test="${'ygw' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ygw">原岗位</td>
						</c:if>
						<c:if test="${'ygw' != sortFieldName}">
						<td class="sort_title" id="ygw">原岗位</td>
						</c:if>
						<c:if test="${'xgw' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="xgw">调入岗位</td>
						</c:if>
						<c:if test="${'xgw' != sortFieldName}">
						<td class="sort_title" id="xgw">调入岗位</td>
						</c:if>
						<c:if test="${'dplb' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="dplb">变更类型</td>
						</c:if>
						<c:if test="${'dplb' != sortFieldName}">
						<td class="sort_title" id="dplb">变更类型</td>
						</c:if>
						<c:if test="${'ddlx' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="ddlx">调动类型</td>
						</c:if>
						<c:if test="${'ddlx' != sortFieldName}">
						<td class="sort_title" id="ddlx">调动类型</td>
						</c:if>
						<c:if test="${'dpzxsj' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="dpzxsj">执行时间</td>
						</c:if>
						<c:if test="${'dpzxsj' != sortFieldName}">
						<td class="sort_title" id="dpzxsj">执行时间</td>
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
						<td>
                            <div>
                                <div class="current_item">
                                    <span class="item_text">变更信息维护</span>
                                </div>
                                <div class="select_tools" id="select_tools1" style=" width:90px; display:none">
                                    <ul>
                                        <c:if test="${info.createdByHR}">
                                            <li><a name="modifyInfo" href="#" class="first1">修改</a></li>
                                            <%-- <li><a name="del" href="#" class="last1">删除</a></li>--%>
                                        </c:if> 
                                        
                                        <li><a name="modify" href="#" class="first1">变更信息维护</a></li>
                                        <li><a name="modifystatus" href="#" class="tools_list">变更状态维护</a></li>   
                                        <li><a name="export" href="#" class="tools_list">打印变更信息</a></li>     
                                        <li><a name="showDtl" href="#" class="last1">查看详情</a></li>
                                    </ul>
                                </div>
                            </div>
                        </td>
                        <td>${info.execuStatusText }</td>
						<%--<input type="hidden" name="guid" value="${info.guid }" />--%>
						<td>${info.staffid }</td>
						<td>${info.personInfo.viewHtml['xm'] }</td>
						<td>${info.currentOrgText }</td>
						<td>${info.planOrgText }</td>
						<td>${info.currentPostTypeText }</td>
						<td>${info.planPostTypeText }</td>
						<td>${info.oldFormationTypeText }</td>
						<td>${info.formationTypeText }</td>
						<td>${info.currentPostText }</td>
						<td>${info.planPostText }</td>
						<td>${info.manoeuvreTypeText }</td>
						<td>${info.ddlxText }</td>
						<td>${info.excuteTimeText }</td>
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
