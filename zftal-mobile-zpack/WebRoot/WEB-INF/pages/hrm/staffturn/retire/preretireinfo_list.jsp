<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/lockTableTitle.js"></script>
		<script type="text/javascript">

		$(function(){
			//监听单击行
			$("tbody > tr[name='list_tr']").click(function(){			
				var gh = $(this).attr("xuhaobianma");
				if($(this).is(".current")){
					$("#MyTable_tableColumn tr[xuhaobianma='"+gh+"']").find("input[name='ids']").removeAttr("checked");
					$("tr[xuhaobianma='"+gh+"']").removeAttr("class");
				}else{
					$("tr[xuhaobianma='"+gh+"']").attr("class", "current");
					$("#MyTable_tableColumn tr[xuhaobianma='"+gh+"']").find("input[name='ids']").attr("checked","checked");
				}
			});

			$("tbody > tr[name='list_tr']").dblclick(function(){
				var userId = $(this).attr("id");
				retireDeal( userId );
			});
			
			$("#search_btn").click(function(){
			   
				$("#selectForm").attr("action","<%=request.getContextPath()%>/retire/preretire_list.html");
				$("#selectForm").attr("method","post");
				$("#selectForm").submit();
			});
			
			$("#btn_zj").click(function(){
				showWindow("增加","<%=request.getContextPath()%>/retire/preretire_add.html", 480, 180);
			});
			
			$("#btn_sc").click(function(){
					var checked=$("input[name='ids']:checked");
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
						$.post("<%=request.getContextPath() %>/retire/preretire_delete.html?model.userId="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							processDataCall(data, callback);
						}, "json");
					});
				});
			
			$("#btn_dc").click(function(){
				exportInfo();
			});


            //$("#btn_download").click(function(){
            //   var checkObj=$("input[name='ids']:checked");
            //   if(checkObj.length==0){
            //   		alert("请选择要导出退休审批表的人员！");
            //   		return false;
            //   }else if(checkObj.length>1){
            //   		alert("该操作只能选择1条记录！");
            //   		return false;
            //   }
            //   
            //   window.open(_path+"/retire/preretire_exportInfo.html?userId="+checkObj.val());
            //});
            
            
            $("#btn_redownload").click(function(){
               var checkObj=$("input[name='ids']:checked");
               if(checkObj.length==0){
               		alert("请选择要导出延长退休审批表的人员！");
               		return false;
               }else if(checkObj.length>1){
               		alert("该操作只能选择1条记录！");
               		return false;
               }
               
               window.open(_path+"/retire/preretire_exportEmpInfo.html?userId="+checkObj.val());
            });
			
			fillRows("20", "", "", false);//填充空行
			
			$("select[name='query.isDoctorMentor']").val(${query.isDoctorMentor});
			
			operationList();
			
	        $("#btn_modify_batch").click(function(){
	        	var checkObj=$("input[name='ids']:checked");
	            if(checkObj.length==0){
	           		alert("请选择要操作的记录！");
	           		return false;
	            }
	            showWindow("批量修改退休文号","<%=request.getContextPath()%>/retire/preretire_plxgtxwhInit.html",350,200);
	        });
		});
		
		function operationList(){
			$("a[name='detail']").click(function(){//行数据修改链接
				var globalid=$(this).closest("li").attr("name");
				//location.href="<%=request.getContextPath() %>/normal/staffResume_list.html?globalid="+globalid+"&type=teacher";
				goUrl(_path+"/normal/staffResume_list.html?globalid="+globalid+"&type=teacher");
			});
			
			$("a[name='modifyRetire']").click(function(){
				var userId=$(this).closest("tr").attr("id");
				modifyRetire( userId );
			});
			$("a[name='retireDeal']").click(function(){
				var userId=$(this).closest("tr").attr("id");
				retireDeal( userId );
			});
			$("a[name='retireCancel']").click(function(){//行数据修改链接 add by heyc on 20130806
				var userId=$(this).closest("tr").attr("id");
				retireCancel( userId );
			});
			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
		//======新弹框范例======//
		function modifyRetire( userId ) {
				showWindow("修改预退休","<%=request.getContextPath()%>/retire/preretire_modifyInit.html?"+"model.userId="+userId, 680, 420);
		}
		function retireDeal( userId ) {
				showWindow("预退休处理","<%=request.getContextPath()%>/retire/preretire_retireDeal.html?"+"model.userId="+userId, 680, 420);
		}
	    function retireCancel( userId ) {//   撤销预退休 人员    ---- add by heyc on 20130806
	        function okFun(){
				 goUrl(_path+"/retire/preretire_retireCancel.html?model.userId="+userId,"json");
			}
			function cancelFun(){
				divClose();
			}
			showConfirmDivLayer("确定要撤销吗？",{okFun:okFun,cancelFun:cancelFun});
		}
        function exportInfo(){
            var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/retire/preretire_export.html">';
            content += getInputHtml();
            content +='   </form>';
            $('body').append(content);
            $('#form').submit();
            $('#form').remove();
        }
        function getInputHtml(){
        	return $(".searchtab").html();
        }
		
	    function selectAllOrCancel(obj,name){//全选选择框操作
            var checks = $("#MyTable_tableColumn input[name='"+name+"']");
            var tr = $("#list_body tr[name='list_tr']");

            if(obj.checked){
                for ( var i = 0; i < checks.length; i++) {
                    checks[i].checked = true;
                }
                for ( var i = 0; i < tr.length; i++) {
                    tr[i].className='current';
                }
            }else{
                for ( var i = 0; i < checks.length; i++) {
                    checks[i].checked = false;
                }
                for ( var i = 0; i < tr.length; i++) {
                    tr[i].className='';
                }
            }
        }
		
		/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#selectForm").submit();
		}
		</script>
		<script type="text/javascript">
		$(document).ready(function () { 
			//第一个参数：table的ID，第二个参数：要锁定的列数目，第三个参数：显示的宽度，第四个参数：显示的高度。注意table里面都必须为td，th的话会出现问题
			FixTable("MyTable", 4, "", "");
		});
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
						<a id="btn_dc" class="btn_dc">导 出</a>
						<%--
						<a id="btn_download" class="btn_dc">下载退休审批表</a>
						 --%>
						<a id="btn_modify_batch" class="btn_xg">批量修改退休文号</a>
						<a id="btn_redownload" class="btn_dc">下载延长退休审批表</a>
					</li>
				</ul>
			</div>	
		</div>
		<form id="selectForm" enctype="application/x-www-form-urlencoded" method="post">
			<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 			<input type="hidden" id="asc" name="asc" value="${asc}"/>
		<div class="searchtab">
			<table width="100%" border="0">
				<tbody>
					<tr>
						<th>职工号</th>
						<td>
							<input type="text" name="query.userId" class="text_nor" style="width: 120px;" value="${query.userId }" />
						</td>
						<th>
							姓 　名
						</th>
						<td>
							<input type="text" name="query.userName" class="text_nor" style="width: 120px;" value="${query.userName }" />
						</td>					
						<th>部 　门</th>
						<td>
							 <ct:codePicker name="query.deptCode" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.deptCode }" width="180" />
						</td>
					</tr>
					<tr>
						<th>预退休时间</th>
						<td colspan="3">
							<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" type="text" name="query.startRetireTime" id="retireDateStr" style="width: 100px;" value="${query.startRetireTimeString }" />
							至
							<input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" type="text" name="query.endRetireTime" id="retireDateEnd" style="width: 100px;" value="${query.endRetireTimeString }" />
						</td>
						
						<th>是否博士生导师</th>
						<td>
							<select name="query.isDoctorMentor" style="width: 120px;">
								<option value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
							<div class="btn" colspan="2">
							  <button id="search_btn" type="button">查  询</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h3 class="datetitle_01">
			 	<span>预退休人员信息<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以退休处理）</font></span>
			 </h3>
		<div class="con_overlfow">
			<table summary="" class="dateline tablenowrap" align="" width="100%" id="MyTable">
				<thead id="list_head">
					<tr>
						<td width="5%"><input type="checkbox" onclick="selectAllOrCancel(this,'ids')"/></td>
						<td>操作</td>
						<c:if test="${'userId' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="userId">职工号</td>
						</c:if>
						<c:if test="${'userId' != sortFieldName}">
						<td class="sort_title" id="userId">职工号</td>
						</c:if>
						<c:if test="${'xm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="xm">姓名</td>
						</c:if>
						<c:if test="${'xm' != sortFieldName}">
						<td class="sort_title" id="xm">姓名</td>
						</c:if>
						<c:if test="${'xbm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="xbm">性别</td>
						</c:if>
						<c:if test="${'xbm' != sortFieldName}">
						<td class="sort_title" id="xbm">性别</td>
						</c:if>
						<c:if test="${'csrq' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="csrq">出生年月</td>
						</c:if>
						<c:if test="${'csrq' != sortFieldName}">
						<td class="sort_title" id="csrq">出生年月</td>
						</c:if>
						<c:if test="${'retireTime' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="retireTime">预退休时间</td>
						</c:if>
						<c:if test="${'retireTime' != sortFieldName}">
						<td class="sort_title" id="retireTime">预退休时间</td>
						</c:if>
						<c:if test="${'num' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="num">退休文号</td>
						</c:if>
						<c:if test="${'num' != sortFieldName}">
						<td class="sort_title" id="num">退休文号</td>
						</c:if>
						<c:if test="${'dwm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="dwm">部门</td>
						</c:if>
						<c:if test="${'dwm' != sortFieldName}">
						<td class="sort_title" id="dwm">部门</td>
						</c:if>
						<c:if test="${'rzgwm' eq sortFieldName}">
                            <td class="sort_title_current_${asc }" id="rzgwm">任职岗位</td>
                        </c:if>
                        <c:if test="${'rzgwm' != sortFieldName}">
                        <td class="sort_title" id="rzgwm">任职岗位</td>
                        </c:if>
						<c:if test="${'zc' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="zc">职称</td>
						</c:if>
						<c:if test="${'zc' != sortFieldName}">
						<td class="sort_title" id="zc">职称</td>
						</c:if>
						<c:if test="${'zwjbdm' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="zwjbdm">行政职务级别</td>
						</c:if>
						<c:if test="${'zwjbdm' != sortFieldName}">
						<td class="sort_title" id="zwjbdm">行政职务级别</td>
						</c:if>
						<td>专技职务级别</td>
						<td>工人等级</td>
						<c:if test="${'xrzw' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="xrzw">现任职务</td>
						</c:if>
						<c:if test="${'xrzw' != sortFieldName}">
						<td class="sort_title" id="xrzw">现任职务</td>
						</c:if>
						<c:if test="${'sfbssds' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="sfbssds">是否博士生导师</td>
						</c:if>
						<c:if test="${'sfbssds' != sortFieldName}">
						<td class="sort_title" id="sfbssds">是否博士生导师</td>
						</c:if>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${retireInfoList}" var="bean" varStatus="st">
					<tr id="${bean.userId}" name="list_tr">
						<td><input type="checkbox" name="ids" value="${bean.userId }" /></td>
						<td>
                             <div>
                                <div class="current_item">
                                    <span class="item_text">退休处理</span>
                                </div>
                                <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
                                    <ul>
                                        <li><a name="retireDeal" class="first1">处理</a></li>
                                        <li><a name="modifyRetire" class="first1">修改</a></li>
                                                           
                                         <li><a name="retireCancel" class="first1">撤销处理</a></li>
                                        <li name="${bean.overall.values['globalid']}"><a name="detail" class="last1">个人信息</a></li>
                                    </ul>
                                </div>
                              </div>
                        </td>
						<td>${bean.userId}</td>
						<td>${bean.overall.viewHtml['xm']}</td>
						<td>${bean.overall.viewHtml['xbm']}</td>
						<td>${bean.overall.viewHtml['csrq']}</td>
						<td>${empty bean.retireTime ? bean.oldRetireTimeString : bean.retireTimeString}</td>
						<td>${bean.num}</td>
						<td>${bean.overall.viewHtml['dwm']}</td>
						<td>${bean.overall.viewHtml['rzgwm']}</td>
						<td>${bean.overall.viewHtml['zc']}</td>
						<td>${bean.overall.viewHtml['zwjbdm']}</td>
						<td>${bean.overall.viewHtml['zj']}</td>
						<td>${bean.overall.viewHtml['grdj']}</td>
						<td>${bean.overall.viewHtml['xrzw']}</td>
						<td>${bean.overall.viewHtml['sfbssds']}</td>	
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<ct:page pageList="${retireInfoList }" />
		</form>
	</div>
</html>
