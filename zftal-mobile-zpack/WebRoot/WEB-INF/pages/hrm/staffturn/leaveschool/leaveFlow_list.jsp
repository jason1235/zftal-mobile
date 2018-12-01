<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript">
    $(function(){
        	var type=$("#type").val();

			$("#btn_zj").click(function(){//功能条增加按钮
			    // 20140422 upd start
				//showWindow("增加", "<%=request.getContextPath()%>/leaveSchool/leaveFlow_input.html", 480, 300 );
				showWindow("增加", "<%=request.getContextPath()%>/leaveSchool/leaveFlow_input.html", 480,350 );
				// 20140422 upd end
			});
			
			$('a[class="btn_dc"]').click(function(){
				exportInfo();				
			});
			
			$("#btn_dy").click(function(){
				if(current == null){
                    alert("请先点击选择要打印的信息");
                    return false;
                }
                var userId=$(current).find("td:first").html();
                $.post(_path+"/leaveSchool/leaveFlow_check.html?flow.accountId="+userId,function(data){
	    			if(data.success){
	    				goUrl(_path+"/leaveSchool/leaveFlow_infoPrint.html?flow.accountId="+userId);
	    			}else{
	    				alert(data.text);
	    			}
    			},"json");
			});
			
			function exportInfo(){
				var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/leaveSchool/leaveFlow_export.html">';
				content += getInputHtml();
				content +='	  </form>';
				$('body').append(content);
				$('#form').submit();
				$('#form').remove();
			} 
			
			function getInputHtml(){
			var inputHTML = "";
			$("#search input,#search select").each(function(){
				if($(this).attr("type") == 'checkbox' && $(this).attr("checked") != null && $(this).attr("checked") == 'checked'){
					inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
				}else if($(this).attr("type") != 'checkbox'){
					inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
				}
			});
			
			return inputHTML;
		}
			
			var current = null;

			$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("td[name='accountId']").text();
				queryEntity(id);
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/leaveSchool/leaveFlow_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
	
			initSelect('query.leaveStatus','${query.leaveStatus}')
			operationList();//初始化操作栏目
			fillRows("20", "", "", false);//填充空行
		});
	
		function queryEntity(id){//查询
			showWindow("处理情况", "<%=request.getContextPath()%>/leaveSchool/leaveFlow_info.html?flow.accountId="+id, 720, 340 );
		}
		
		function confirmEntity(id){//查询
			showWindow("离校确认", "<%=request.getContextPath()%>/leaveSchool/leaveFlow_edit.html?flow.accountId="+id, 720, 340 );
		}

		function operationList(){
			$("a[name='detail']").click(function(){
				var id = $(this).closest("tr").find("input[name='globalid']").val();
				//location.href = _path+"/normal/staffResume_list.html?globalid="+id+"&type=teacher";
				goUrl(_path+"/normal/staffResume_list.html?globalid="+id+"&type=teacher");
			});
			
			$("a[name='print']").click(function(){
				var id = $(this).closest("tr").find("input[name='globalid']").val();
				var accountId = $(this).closest("tr").find("td[name='accountId']").text();
				window.open(_path+"/leaveSchool/leaveFlow_print.html?globalid="+id+"&flow.accountId="+accountId);
			});

			$("a[name='check']").click(function(){
				var id = $(this).closest("tr").find("td[name='accountId']").text();
				queryEntity(id);
			});

			$("a[name='confirm']").click(function(){
				var id = $(this).closest("tr").find("td[name='accountId']").text();
				confirmEntity(id);
			});

			$(".select_tools a").css("cursor","pointer");

			operationHover();
		}
		
		/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#search").submit();
		}
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增 加</a>
						</li>
						<li>
		                	<a onclick="return false;" class="btn_dc" href="#">	导出</a>
		            	</li>
		            	<li>
							<a id="btn_dy" class="btn_dy">打印审批表</a>
						</li>
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form action="" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
		  <th>部　门</th>
          <td>
          	<ct:codePicker name="query.deptId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.deptId }" />
          </td>
          <th>姓　名</th>
          <td>
          	<input type="text" name="query.name" class="text_nor" style="width:180px" value="${query.name }"/>
		  </td>
		  <th>离校类型</th>
		  <td>
		      <ct:codePicker name="query.type" width="180" catalog="<%=ICodeConstants.DM_RETIRD_REASON %>" code="${query.type }" />
		  </td>
		  <!-- 20140422 add start -->
		  <td></td>
		  <!-- 20140422 add end -->
		  </tr>
		  <tr>
		  <th>离校时间</th>
          <td>
          	<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="query.leaveDateStart" id="leaveDateStart" value="${query.leaveDateStartString}" style="width: 80px;"/>  
          	至
            	<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="query.leaveDateEnd" id="leaveDateEnd" value="${query.leaveDateEndString}" style="width: 80px;"/>
          </td>	
		   <th>状　态</th>
          <td>
          	<select name="query.leaveStatus" style="width:185px">
          		<option value="0">未处理</option>
          		<option value="1">已处理</option>
          	</select>
		  </td>
		  <!-- 20140422 add start -->
		  <th>编制状况</th>
		  <!-- 20140422 add end -->
		  <td>
		  <!-- 20140422 add start -->
		    <ct:codePicker name="query.bzzk" width="180" catalog="<%=ICodeConstants.DM_GB_BZZKDMB %>" code="${query.bzzk}" />
		  <!-- 20140422 add end -->
		  </td>
		  </tr>
		  <tr>
		  <th>离校去向</th>
		  <td colspan="4">
		  	<select name="query.lxqx" style="width:190px">
		  		<option value="">全部</option>
	            <option value="ENTERPRISE">事业单位</option>
       			<option value="SCHOOL">学校</option>
       			<option value="CORPORATION">企业</option>
       			<option value="OTHER">其他</option>
		  	</select>
		  </td>
		  <td>
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
    	<span>离校流程管理<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以查看处理情况）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
					    <td width="75px">操作</td>
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
						<c:if test="${'dwm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="dwm" width="16%">部门</td>
						</c:if>
						<c:if test="${'dwm' != sortFieldName}">
						<td class="sort_title" id="dwm" width="16%">部门</td>
						</c:if>
						<c:if test="${'zc' eq sortFieldName}">
                            <td class="sort_title_current_${asc }" id="zc" width="16%">职称</td>
                        </c:if>
                        <c:if test="${'zc' != sortFieldName}">
                        <td class="sort_title" id="zc" width="16%">职称</td>
                        </c:if>
                        <td width="16%">职称级别</td>
						<!-- 20140422 add start -->
						<c:if test="${'bzzk' eq sortFieldName}">
                            <td class="sort_title_current_${asc }" id="bzzk">编制状况</td>
                        </c:if>
                        <c:if test="${'bzzk' != sortFieldName}">
                        <td class="sort_title" id="bzzk">编制状况</td>
                        </c:if>
						<!-- 20140422 add end -->
						<c:if test="${'type' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="type">离校类型</td>
						</c:if>
						<c:if test="${'type' != sortFieldName}">
						<td class="sort_title" id="type">离校类型</td>
						</c:if>
						<c:if test="${'type' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="type">现任职务</td>
						</c:if>
						<c:if test="${'type' != sortFieldName}">
						<td class="sort_title" id="type">现任职务</td>
						</c:if>
						<c:if test="${'lxzt' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="lxzt">离校状态</td>
						</c:if>
						<c:if test="${'lxzt' != sortFieldName}">
						<td class="sort_title" id="lxzt">离校状态</td>
						</c:if>
						<c:if test="${'lxsj' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="lxsj">离校时间</td>
						</c:if>
						<c:if test="${'lxsj' != sortFieldName}">
						<td class="sort_title" id="lxsj">离校时间</td>
						</c:if>
						<c:if test="${'lxqx' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="lxqx">离校去向</td>
						</c:if>
						<c:if test="${'lxqx' != sortFieldName}">
						<td class="sort_title" id="lxqx">离校去向</td>
						</c:if>
						<c:if test="${'bz' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="bz">备注</td>
						</c:if>
						<c:if test="${'bz' != sortFieldName}">
						<td class="sort_title" id="bz">备注</td>
						</c:if>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="list" var="info">
						<tr name="tr">
						<td style="display: none">
							<input type="hidden" name="globalid" value="${info.dynaBean.values.globalid }"/>
							<input type="hidden" name="processDept" value="${info.processDept }"/>
						</td>
						<td>
                          <div>
                            <div class="current_item">
                                <span class="item_text">处理情况</span>
                            </div>
                            <div class="select_tools" id="select_tools1" style=" width:120px; display:none">
                                <ul>
                                    <li><a name="check" href="#" class="first1">处理情况</a></li>
                                    <s:if test='leaveStatus == "0" && processStatus'>
                                    <li><a name="confirm" href="#" class="tools_list">离校确认</a></li>
                                    </s:if>
                                    <li><a name="detail" href="#" class="last1">详细信息</a></li>
                                    <li><a name="print" href="#" class="last1">离校单打印</a></li>
                                </ul>
                            </div>
                          </div>
                        </td>
						<td name="accountId">${info.accountId }</td>
						<td>${info.dynaBean.viewHtml['xm'] }</td>
						<td>${info.dynaBean.viewHtml['dwm'] }</td>
						<td>${info.dynaBean.viewHtml['zc'] }</td>
						<td>${info.dynaBean.viewHtml['zyjszj'] }</td>
						<!-- 20140422 add start -->
						<td><ct:codeParse catalog="<%=ICodeConstants.DM_GB_BZZKDMB %>" code="${info.bzzkCd }"/></td>
						<!-- 20140422 add end -->
						<td><ct:codeParse catalog="<%=ICodeConstants.DM_RETIRD_REASON %>" code="${info.type }"/></td>
						<td>${info.duty }</td>
						<td><s:if test='leaveStatus == "1"'>已处理</s:if><s:else>未处理</s:else></td>
						<td><s:date name="leaveDate" format="yyyy-MM-dd"></s:date></td>
						<td><s:if test='lxqx == "ENTERPRISE"'>事业单位</s:if>
							<s:if test='lxqx == "SCHOOL"'>学校</s:if>
							<s:if test='lxqx == "CORPORATION"'>企业</s:if>
							<s:if test='lxqx == "OTHER"'>其他</s:if>
						</td>
						<td>${info.remark }</td>
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${list }" />
	</div>
  	  </form>
  </body>
</html>
