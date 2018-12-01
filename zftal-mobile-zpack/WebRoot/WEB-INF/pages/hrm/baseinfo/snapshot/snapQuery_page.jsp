<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<%@ include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/baseinfo/snapshot/snap_data.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/org/data_tip.js"></script>
	<script type="text/javascript">
		$(function(){
			var current = null;

			// 行单击选定事件
			$("#list_body tr").click( function(){
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("td[name='gh']").text();
				queryEntity(id);
			});
			
			$("button[name='search']").click(function(e){//搜索按钮
				$("#page").attr("action","<%=request.getContextPath()%>/baseinfo/snapshotQuery_page.html");
				$("#page").attr("method","post");
				$("#page").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			$("#btn_sz").click(function(){
				showWindow("显示字段设置", "<%=request.getContextPath()%>/infoclass/infoPropertyView_list.html?classId=${classId}", 480, 300 );
			});
			
			// 20140415 add start
			// 导出信息设置
			$("#btn_outputInfoSet").click(function() {
				var parm = $("input[name='hdnExpress'], select[name='snapTimeStart'], select[name='snapTimeEnd'], input[name='dwm'], input[name='gh'], input[name='xm']").serialize();
				showWindow("导出信息设置", "<%=request.getContextPath()%>/baseinfo/snapshotInfo_allInfo.html?" + parm, 750, 600);
			});
			// 20140415 add end
			
			$("#list_body>tr").each(function(){
				datatips(this);
			});
			
			$('a[class="btn_dc"]').click(function(){
				exportInfo();
				
			});
			
			if("${showMore}"=='1'){
				$("div.more--item_bottom a").removeClass().addClass("up");
	   			$("div.more--item_bottom a").text("收起");
	   			$("#total").css("display","block");
	   			$("#showMore").val("1");
				$("div.more--item_bottom a").toggle(
					hideMore,showMore
			   	);
			}else{
				$("div.more--item_bottom a").toggle(
					showMore,hideMore
			   	);
			}
					
			// 填充空行数据
			var pageSize = $("#pageSize","#page").val();
			initSelect("snapTime","${snapTime}");
			fillRows(pageSize, "", "", false);
		});
		
		function queryEntity(id){//查询
			showWindow("快照数据", "<%=request.getContextPath()%>/baseinfo/snapshotData_view.html?gh="+id, 720, 440 );
		}
		
		function exportInfo(){
			var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/baseinfo/snapshotQuery_export.html">';
			content += getInputHtml();
			content +='	  </form>';
			$('body').append(content);
			$('#form').submit();
			$('#form').remove();
		}
		
		function getInputHtml(){
			var inputHTML = "";
			$("#page input,#page select").each(function(){
				if($(this).attr("type") == 'checkbox' && $(this).attr("checked") != null && $(this).attr("checked") == 'checked'){
					inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
				}else if($(this).attr("type") != 'checkbox'){
					inputHTML +=' <input type="hidden" name="' + $(this).attr("name") +'" value="' + $(this).val() + '" />';;
				}
			});
			
			return inputHTML;
		}
		
		var openWindowCallback = function(){
			requestSnapData();
		};
		
		 /*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#page").submit();
		}
		
		function showMore(){
   			$(this).removeClass().addClass("up");
   			$(this).text("收起");
   			$("#total").slideDown();
   			$("#showMore").val("1");
   		}
	   	function hideMore(){
   			$(this).removeClass().addClass("down");
   			$(this).text("更多");
   			$("#total").slideUp();
   			$("#showMore").val("-1");
   		}
    </script>
</head>

<body>
	<div class="toolbox">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_sz" class="btn_sz">显示字段设置</a></li>
				<li>
		                <a onclick="return false;" class="btn_dc" href="#">
		                   人员导出
		                </a>
		            </li> 
		        <!-- 20140415 add start -->
		        <li> <a id="btn_outputInfoSet" class="btn_sz">导出信息设置</a></li>
		        <!-- 20140415 add end -->
			</ul>
		</div>
	</div>
	
	<form id="page" name="page" action="<%=request.getContextPath()%>/baseinfo/snapshotQuery_page.html" method="post">
	<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 	<input type="hidden" id="asc" name="asc" value="${asc}"/>
 	<input type="hidden" id="showMore" name='showMore' value="${showMore }" />
	<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
		  <th>部　门</th>
          <td>
          	<ct:codePicker name="dwm" catalog="<%=ICodeConstants.DM_DEF_ORG %>" width="120" code="${dwm }" />
          </td>
          <th>工　号</th>
          <td>
          	<input type="text" name="gh" class="text_nor" style="width: 80px;" value="${gh }"/>
		  </td>
		  <th>姓     名</th>
          <td>
          	<input type="text" name="xm" class="text_nor" style="width:80px" value="${xm }"/>
		  </td>
          <th>快照日期</th>
          	<%--
			<td>
				<select style="width:80px" name="snapTime">
		          	<c:forEach items="${snapTimes }" var="st">
	                	<option value="<fmt:formatDate value="${st.snapTime }" pattern="yyyy-MM-dd" />"><fmt:formatDate value="${st.snapTime }" pattern="yyyy-MM" /></option>
                    </c:forEach>
		        </select>
	        </td>
	         --%>
			<td>
				<select style="width:80px" name="snapTimeStart">
                	<option value=""></option>
		          	<c:forEach items="${snapTimes }" var="st">
	                	<option value="${st.snapTimeStr }" ${st.snapTimeStr==snapTimeStartStr?"selected='selected'":"" }>
	                		<fmt:formatDate value="${st.snapTime }" pattern="yyyy-MM" />
	                	</option>
                    </c:forEach>
		        </select>&nbsp;至&nbsp;
				<select style="width:80px" name="snapTimeEnd">
					<option value=""></option>
		          	<c:forEach items="${snapTimes }" var="st">
	                	<option value="${st.snapTimeStr }" ${st.snapTimeStr==snapTimeEndStr?"selected='selected'":"" }>
	                		<fmt:formatDate value="${st.snapTime }" pattern="yyyy-MM" />
	                	</option>
                    </c:forEach>
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
	<div class="comp_con">	
		<div class="search_advanced" id="total" style="display: none;">
		<table width="100%" border="0"> 
			<tbody>
				<c:forEach items="${conditions }" step="2" varStatus="vs">
					<tr>
						<c:forEach begin="${vs.index}" end="${vs.index+1}" var="idx">
							<c:if test="${fn:length(conditions)>idx }">
								<c:set var="item" value="${conditions[idx] }"/>
								<th>${item.name }</th>
								<td>
									<c:choose>
										<c:when test="${item.fieldType=='CODE'}">
											<ct:codePicker name="${item.fieldName }" catalog="${item.codeId }" code="${valuesMap[item.fieldName] }"/>
										</c:when>
										<c:when test="${item.fieldType=='DATE' ||item.fieldType=='MONTH' ||item.fieldType=='YEAR' }">
											<input type="text" name="${item.fieldName }" style="width: 110px;" 
												class="Wdate" onfocus="WdatePicker({dateFmt:'${item.typeInfo.format}'})"
												value="${valuesMap[item.fieldName][0] }"/>&nbsp;至
											<input type="text" name="${item.fieldName }" style="width: 110px;" 
												class="Wdate" onfocus="WdatePicker({dateFmt:'${item.typeInfo.format}'})"
												value="${valuesMap[item.fieldName][1] }"/>
										</c:when>
										<c:when test="${item.fieldType=='SIGLE_SEL'}">
											<select name="${item.fieldName }" style="width: 110px;">
												<option value=""></option>
												<option value="1" ${valuesMap[item.fieldName]=="1"?"selected=\"selected\"":""}>是</option>
												<option value="0" ${valuesMap[item.fieldName]=="0"?"selected=\"selected\"":""}>否</option>
											</select>
										</c:when>
										<c:otherwise>
											<input type="text" fieldType="${item.fieldType }" name="${item.fieldName }" style="width: 181px;" value="${valuesMap[item.fieldName] }"/>
										</c:otherwise>
									</c:choose>
								</td>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		<!-- 伸缩按钮 -->
		<div class="more--item_bottom" style="clear:both;margin-bottom:5px"><p><a href="#" class="down">更多</a></p></div>	
	</div>    
  </div>
  <!-- 20140429 add start -->
  <input type="hidden" name="hdnExpress" value="${express }"/>
  <!-- 20140429 add end -->
		<div class="formbox" >
			<h3 class="datetitle_01">
				<span>人员列表<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以查看快照）</font></span>
			</h3>
			<div style="overflow-x:auto;width:795px;">
			<table width="100%" class="dateline tablenowrap" id="tiptab" >
				<thead id="list_head">
					<tr>
					<s:iterator value="query.clazz.viewables" var="p">
					<s:if test="fieldName != 'zp'">
						<c:if test="${p.fieldName eq sortFieldName}">
					<td class="sort_title_current_${asc }" id="${p.fieldName }">${p.name }
					</td>
				</c:if>
				<c:if test="${p.fieldName != sortFieldName}">
					<td class="sort_title" id="${p.fieldName }">${p.name }
					</td>
				</c:if>
					</s:if>
					</s:iterator>
					</tr>
				</thead>
				<tbody id="list_body">
				<s:iterator value="pageList" var="overall">
				<tr name="tr">
				<s:iterator value="clazz.viewables" var="p">
					<s:if test="fieldName != 'zp'">
						<td name="${p.fieldName }">${overall.viewHtml[p.fieldName]}</td>
					</s:if>
				</s:iterator>
				</tr>
				</s:iterator>
				</tbody>
			</table>
			</div>
			<ct:page pageList="${pageList }"/>
		</div>
	</form>
</body>
</html>
