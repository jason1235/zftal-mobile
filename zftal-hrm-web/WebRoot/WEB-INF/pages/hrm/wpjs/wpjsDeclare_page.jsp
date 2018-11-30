<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/wpjs/button.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
		<script type="text/javascript">
		$(function(){
			$("#btn_zj").click(function(){//功能条增加按钮
				inputRequest();
			});
			$("#btn_xg").click(function(){//功能条修改按钮
				editRequest();
			});
			$("#btn_sc").click(function(){//功能条删除按钮
				deleteRequest();
			});
			var current = null;
			$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				if(current != null) {
					current.removeClass("current");
				
					current.find("input[id='id']").removeAttr("checked");
				}
				$(this).attr("class", "current");
				$(this).find("input[id='id']").attr("checked","checked");
				current = $(this);
			});
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				
				$("#btn_xg").click();
			});
			var xmtrs=$("tr[name=tr]");
			for ( var i=0; i<xmtrs.length; i++){
				var xmtr = xmtrs[i];
				var tds = $(xmtr).closest("tr").find("td");
				var xmtd = tds[2];
			    var id = $(xmtd).closest("tr").find("input[id='id']").val();
			    var html="<a style=\"color:#074695\" href=\"#\" onclick=\"on('" + id + "')\" >"+$(xmtd).html()+ "</a>";
			    $(xmtd).html(html);
			}
			fillRows("20", "", "", false);//填充空行
		});
	   
     	function on(obj){
     	    var inputObj = document.getElementsByName(obj);
     	    inputObj[0].checked = true;
     		editRequest();
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
							<a id="btn_zj" class="btn_zj">增加</a>
							<a id="btn_xg" class="btn_xg">修改</a>
							<a id="btn_sc" class="btn_sc">删除</a>
						</li>
					</ul>
				</div>
		</div>
	 	<form id="search" method="post" action="<%=basePath %>/wpjs/wpjsdeclare_search.html">
	 		<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 			<input type="hidden" id="asc" name="asc" value="${asc}"/>   
		    <!-- 查询条件 -->
		    <div class="searchtab" id="searchtab">
			<!--     <input type="hidden" name="menuName" value="${model.menuName }"/>    -->
			    <input type="hidden" name="promotionType" value="${model.promotionType }"/>
		    	<table width="100%" border="0">
		            <tbody>
			            <tr>
			                <th width="10%">姓名</th>
			                <td width="18%" >
			                	<input name="query.xm" type="text"  value="${query.xm }" style="width: 130px;"/>
							</td>
							
							<th width="10%">部门</th>
			                <td width="18%">
			                	<ct:codePicker name="query.bmdm" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.bmdm}" width="130"/>
							</td>
			                <th width="10%">专业技术职务</th>
			                <td width="18%">
			                	<input name="query.zyjszw" type="text"  value="${query.zyjszw}" style="width: 130px;"/>
			                </td>
			            </tr>
			            
			            <tr>
							<th width="10%">学历</th>
			                <td width="18%">
			                	<input name="query.xl" type="text" value="${query.xl }" style="width: 130px;"/>
			                </td>
							<th width="10%">学位</th>
			                <td width="18%">
			                	<input name="query.xw" type="text"  value="${query.xw }" style="width: 130px;"/>
			                </td>
			                <td colspan="2">
			                  <div class="btn">
			                    <button name="search" class="brn_cx">查 询</button>
			                  </div>
		               		</td>
			            </tr>
		            </tbody>
	          	</table>
	        </div>
		    <h3 class="datetitle_01">
		    	<span>外聘兼职教师管理<font color="#0f5dc2" style="font-weight:normal;">（提示：双击可以查看选定行）</font></span>
		    </h3>
			<div class="con_overlfow">
				<table width="100%" class="dateline nowrap" id="tiptab" >
					<thead id="list_head">
						<tr>
							<td width="4%">
								<input type="checkbox" onclick="selectAllOrCancel(this,'ids');"  id="id" disabled="disabled"/>
							</td>
							<td width="5%">序号</td>
							<c:if test="${'XM' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="XM">姓名</td>
							</c:if>
							<c:if test="${'XM' != sortFieldName}">
								<td class="sort_title" id="XM">姓名</td>
							</c:if>
							<c:if test="${'XB' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="XB">性别</td>
							</c:if>
							<c:if test="${'XB' != sortFieldName}">
								<td class="sort_title" id="XB">性别</td>
							</c:if>
							<c:if test="${'CSNY' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="CSNY">出生年月</td>
							</c:if>
							<c:if test="${'CSNY' != sortFieldName}">
								<td class="sort_title" id="CSNY">出生年月</td>
							</c:if>
							<c:if test="${'BMDM' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="BMDM">所属学院(部门)</td>
							</c:if>
							<c:if test="${'BMDM' != sortFieldName}">
								<td class="sort_title" id="BMDM">所属学院(部门)</td>
							</c:if>
							<c:if test="${'XL' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="XL">学历</td>
							</c:if>
							<c:if test="${'XL' != sortFieldName}">
								<td class="sort_title" id="XL">学历</td>
							</c:if>
							<c:if test="${'XW' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="XW">学位</td>
							</c:if>
							<c:if test="${'XW' != sortFieldName}">
								<td class="sort_title" id="XW">学位</td>
							</c:if>
							<c:if test="${'ZYJSZW' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="ZYJSZW">专业技术职务</td>
							</c:if>
							<c:if test="${'ZYJSZW' != sortFieldName}">
								<td class="sort_title" id="ZYJSZW">专业技术职务</td>
							</c:if>
							<c:if test="${'ZT' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="ZT">状态</td>
							</c:if>
							<c:if test="${'ZT' != sortFieldName}">
								<td class="sort_title" id="ZT">状态</td>
							</c:if>													
						</tr>
					</thead>

					<tbody id="list_body" >
						<s:iterator value="pageList" var="p" status="st">
							<tr name="tr">
							<td><input type="checkbox"  name="${p.id }" id="id" value="${p.id }"/></td>
							<td>${st.index + 1 }</td>
							<td>${p.xm }</td>
							<td>${p.xb }</td>
							<td><s:date name="csny" format="yyyy-MM-dd"/></td>
							<td><ct:codeParse code="${p.bmdm }" catalog="<%=ICodeConstants.DM_DEF_ORG %>" /></td>
							<td>${p.xl }</td>
							<td>${p.xw }</td>
							<td>${p.zyjszw }</td>
							<td>${p.zt }</td>
						</tr>
						</s:iterator>
					</tbody>										
			 	</table>
			</div>
	  	</div>
	  	<ct:page pageList="${pageList }" />
		</div>
		</form>
	</body>
</html>
