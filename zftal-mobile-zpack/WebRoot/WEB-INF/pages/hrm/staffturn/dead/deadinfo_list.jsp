<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/hrm/inputPrompt.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/jquery.ui.all.css"
			type="text/css" media="all" />

		<script type="text/javascript">
		$(function(){
			$("#btn_zj").click(function(){
			    // 20140422 upd start
				// showWindow("增加","<%=request.getContextPath()%>/dead/deadinfo_add.html", 480, 180);
				showWindow("增加","<%=request.getContextPath()%>/dead/deadinfo_add.html", 480, 200);
				// 20140422 upd end
			});
			
			$("#btn_sc").click(function(){
					var checked=$("input[name='idckb']:checked");
                    if(checked.length==0){
                        alert("请选择行");
                        return false;
                    }
                    var ids = [];
                    checked.each(function(){
                    	ids.push('\'' + $(this).val() + '\'');
                    });
					var id = ids.join(',');
                    showConfirm("确定要删除吗？");

					$("#why_cancel").click(function(){
						alertDivClose();
					});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/dead/deadinfo_delete.html?query.userId="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							processDataCall(data, callback);
						}, "json");
					});
				});
			
			$("input[name='query.deptCode']").prev().click(function(){
				//codePicker(this,"${code['deptCode']}");
			});
			
			$("#search_btn").click(function(){
				$("#selectForm").attr("action","<%=request.getContextPath()%>/dead/deadinfo_list.html");
				$("#selectForm").attr("method","post");
				$("#selectForm").submit();
			});

			var current = null;

			$("tbody > tr[name='list_tr']").click(function(){	//监听单击行
//				if(current != null) {
//					current.removeClass("current");
//				}
//				$(this).attr("class", "current");
				current = $(this).attr("class");
				if (current != null && current != '') {
					$(this).find("input[name='idckb']").removeAttr("checked");
					$(this).removeClass("current");
				} else {
					$(this).attr("class", "current");
					$(this).find("input[name='idckb']").attr("checked","checked");
				}
				
			});
			
			$('#selectAll').bind('click',function(){
				var checked = $(this).prop('checked');
				$("input[name='idckb']").each(function(){
					$(this).prop('checked',checked);
				})
			})
			
			$("#list_body tr").dblclick(function(){//行数据修改链接
				var userId=$(this).find("[name='idckb']").val();
				modify( userId );
			});
			
			fillRows("20", "", "", false);//填充空行
			
			operationList();
			
		});
		
		function operationList(){
			$("a[name='detail']").click(function(){//行数据修改链接
				var globalid=$(this).closest("li").attr("name");
				//location.href="<%=request.getContextPath()%>/normal/staffResume_list.html?globalid="+globalid+"&type=teacher";
				goUrl(_path+"/normal/staffResume_list.html?globalid="+globalid+"&type=teacher");
			});

			$("a[name='modify']").click(function(){//行数据修改链接
				var userId=$(this).closest("td").attr("name");
				modify( userId );
			});
			
			$(".select_tools a").css("cursor","pointer");
			operationHover();
			
		}

		function modify( userId ) {
			showWindow("维护信息","<%=request.getContextPath()%>/dead/deadinfo_modify.html?query.userId=" + userId, 600, 220);
		}

	/*
	 *排序回调函数
	 */
	function callBackForSort(sortFieldName, asc) {
		$("#sortFieldName").val(sortFieldName);
		$("#asc").val(asc);
		$("#selectForm").submit();
	}
</script>
	</head>

	<body>
		<div class="formbox">
			<div class="toolbox">
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增 加</a>
							<a id="btn_sc" class="btn_sc">删 除</a>
						</li>
					</ul>
				</div>
			</div>
			<form id="selectForm" enctype="application/x-www-form-urlencoded"
				method="post">
				<input type="hidden" id="sortFieldName" name="sortFieldName"
					value="${sortFieldName}" />
				<input type="hidden" id="asc" name="asc" value="${asc}" />
				<div class="searchtab">
					<table width="100%" border="0">
						<tbody>
							<tr>
								<th width="80px">
									部 门
								</th>
								<td>
									<ct:codePicker name="query.deptCode"
										catalog="<%=ICodeConstants.DM_DEF_ORG %>"
										code="${query.deptCode }" />
								</td>
								<!-- 20140422 add start -->
								<th width="100px">
									人员类别
								</th>
								<td>
									<ct:codePicker name="query.ryztCode"
										catalog="<%=ICodeConstants.DM_GB_LSRYZTDMB %>"
										code="${query.ryztCode}" />
								</td>
								<!-- 20140422 add end -->
								<td>
									<div class="btn">
										<button id="search_btn" type="button">
											查 询
										</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
                <h3 class="datetitle_01">
                        <span>离世人员信息<font color="#0457A7"
                            style="font-weight: normal;">（提示：单击一行可以选定，双击一行可以维护信息）</font>
                        </span>
                    </h3>
				<div class="con_overlfow">
					
					<table summary="" class="dateline tablenowrap" align=""
						width="100%">
						<thead id="list_head">
							<tr>
							<td><input type="checkbox" id="selectAll" /> </td>
							    <td>
                                                                                                           操作
                                </td>
								<c:if test="${'userId' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="userId">
										职工号
									</td>
								</c:if>
								<c:if test="${'userId' != sortFieldName}">
									<td class="sort_title" id="userId">
										职工号
									</td>
								</c:if>
								<c:if test="${'xm' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="xm">
										姓名
									</td>
								</c:if>
								<c:if test="${'xm' != sortFieldName}">
									<td class="sort_title" id="xm">
										姓名
									</td>
								</c:if>
								<c:if test="${'dwm' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="dwm">
										部门
									</td>
								</c:if>
								<c:if test="${'dwm' != sortFieldName}">
									<td class="sort_title" id="dwm">
										部门
									</td>
								</c:if>
								<c:if test="${'xbm' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="xbm">
										性别
									</td>
								</c:if>
								<c:if test="${'xbm' != sortFieldName}">
									<td class="sort_title" id="xbm">
										性别
									</td>
								</c:if>
								<c:if test="${'csrq' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="csrq">
										出生年月
									</td>
								</c:if>
								<c:if test="${'csrq' != sortFieldName}">
									<td class="sort_title" id="csrq">
										出生年月
									</td>
								</c:if>
								<td>退休日期</td>
								<!-- 20140422 add start -->
								<c:if test="${'ryztm' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="ryztm">
										人员类别
									</td>
								</c:if>
								<c:if test="${'ryztm' != sortFieldName}">
									<td class="sort_title" id="ryztm">
										人员类别
									</td>
								</c:if>
								<!-- 20140422 add end -->
								<!-- <td>退休时间</td> -->
								<c:if test="${'deadTime' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="deadTime">
										离世日期
									</td>
								</c:if>
								<c:if test="${'deadTime' != sortFieldName}">
									<td class="sort_title" id="deadTime">
										离世日期
									</td>
								</c:if>
								<td>离世年龄</td>
								<c:if test="${'deadSubsidy' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="deadSubsidy">
										离世抚恤金
									</td>
								</c:if>
								<c:if test="${'deadSubsidy' != sortFieldName}">
									<td class="sort_title" id="deadSubsidy">
										离世抚恤金
									</td>
								</c:if>
								<c:if test="${'receiver' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="receiver">
										签收人
									</td>
								</c:if>
								<c:if test="${'receiver' != sortFieldName}">
									<td class="sort_title" id="receiver">
										签收人
									</td>
								</c:if>
								<c:if test="${'receiveDate' eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="receiveDate">
										签收日期
									</td>
								</c:if>
								<c:if test="${'receiveDate' != sortFieldName}">
									<td class="sort_title" id="receiveDate">
										签收日期
									</td>
								</c:if>
							</tr>
						</thead>
						<tbody id="list_body">
							<c:forEach items="${deadInfoList}" var="bean">
								<tr name="list_tr">
								<td><input type="checkbox" name="idckb" value="${bean.userId}" /></td>
								    <td name="${bean.userId}">
                                        <div>
                                            <div class="current_item">
                                                <span class="item_text">维护信息</span>
                                            </div>
                                            <div class="select_tools" id="select_tools1"
                                                style="width: 80px; display: none">
                                                <ul>
                                                    <li>
                                                        <a name="modify" class="first1">维护信息</a>
                                                    </li>
                                                    <li name="${bean.overall.values['globalid']}">
                                                        <a name="detail" class="last1">查看详细</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </td>
									<td>
										${bean.userId}
									</td>
									<td>
										${bean.overall.viewHtml['xm']}
									</td>
									<td>
										${bean.overall.viewHtml['dwm']}
									</td>
									<td>
										${bean.overall.viewHtml['xbm']}
									</td>
								    <td>
								    	${bean.overall.viewHtml['csrq']}
								    </td>
									<td>
										${bean.retireInfo.retireTimeString}
									</td>
									<!-- 20140422 add start -->
									<td>
										<ct:codeParse catalog="<%=ICodeConstants.DM_GB_LSRYZTDMB %>"
											code="${bean.ryztm }" />
									</td>
									<!-- 20140422 add end -->
									<td>
										${bean.deadTimeString}
									</td>
									<td>
										${bean.deadAge }
									</td>
									<td>
										${bean.deadSubsidy}
									</td>
									<td>
										${bean.receiver}
									</td>
									<td>
										${bean.receiveDateString}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<ct:page pageList="${deadInfoList }" query="${query }" queryName="query" />
		</div>
		</form>
	</body>
</html>
