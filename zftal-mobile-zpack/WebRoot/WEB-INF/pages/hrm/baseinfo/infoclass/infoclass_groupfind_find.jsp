<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/org/data_tip.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
	<script type="text/javascript">
		$(function(){
			// 照片单元格合并行事件
			var size = $("table.formlist tbody > tr").length/$("table.formlist tbody").length;
			$("tbody img").closest("td").attr("rowspan",size);
			
			//监听条目点击事件
			$(".prop-item a").click(function(e){
				itemClick(e);
			});
			
			$(".moreValue_click").toggle(
				function () {
					$(this).closest("ul").find(".more_item").css("display","");
					$(this).html("缩进..");
				},
				function () {
					$(this).closest("ul").find(".more_item").css("display","none");
					$(this).html("更多..");
			 	}
			); 
			
			//查询按钮点击事件
			$(".brn_cx").click(function(e){
				$("form").attr("method","POST");
				$("form").attr("action","<%=request.getContextPath()%>/baseinfo/infogroupfind_find.html");
				$("form").submit();
			});
			//导出按钮点击事件
			$(".brn_dc").click(function(e){
				$("form").attr("method","POST");
				$("form").attr("action","<%=request.getContextPath()%>/baseinfo/infogroupfind_export.html");
				$("form").submit();
			});
			$(".demo_xxxx_title a").click(function(){
				var globalid = $(this).prev().val();
				//location.href="<%=request.getContextPath() %>/normal/staffResume_list.html?globalid="+globalid+"&type=teacher";
				goUrl(_path+"/normal/staffResume_list.html?globalid="+globalid+"&type=teacher");
			});
			
			$("#list_body>tr").dblclick(function(){
				var id = $(this).find("input[id='globalid']").val();
				
				//location.href="<%=request.getContextPath() %>/normal/staffResume_list.html?globalid="+id+"&type=teacher";
				goUrl(_path+"/normal/staffResume_list.html?globalid="+id+"&type=teacher");
			});
			/*$("#list_body>tr").each(function(){
				//datatips(this);
			});*/
			$("#btn_sz").click(function(){
				showWindow("显示字段设置", "<%=request.getContextPath()%>/infoclass/infoPropertyView_list.html?classId=${classId}", 480, 300 );
			});
			var xmtds=$("td[name=xm]");
			for ( var i=0; i<xmtds.length; i++){
				var xmtd = xmtds[i];
				var id = $(xmtd).closest("tr").find("input[id='globalid']").val();
				var html="<a style=\"color:#074695\" href=\"<%=request.getContextPath()%>/normal/staffResume_list.html?globalid="+id+"&type=teacher\" >"+$(xmtd).html()+ "</a>";
				$(xmtd).html(html);
				
			}
			var conditionItems  = $("input[name='conditionItems']");
            for(var i=0; i<conditionItems.length; i++){
                var ci = conditionItems[i];
                var cons=$(ci).val();
                var con=cons.split("&");
                var guid = "selected_item_"+con[0]+"_"+con[2]
                var aTag = $(ci).closest("dd").find("a");
                $(aTag).attr("id",guid);
                guid=guid.replace("selected_","");
                $("#"+guid).addClass("selectedValue");
                var title=$("#"+guid).closest("dd").prev().html();
                var content=$("#"+guid).html();
                var html = "<h5>"+title+"</h5>"+content+"<span class=\"close-icon\" title=\"取消\"></span>";
                $(aTag).html(html);
            }

            //监听关闭按钮事件
            $(".close-icon").click(function(e){
                closeClick(e);
            });
            
			fillRows($("#pageSize").val(), "", "", false);//填充空行

			
		});
		//关闭按钮事件
		function closeClick(e){
			var currentTarget=$(e.currentTarget);
			var guid=$(currentTarget).closest("a").attr("id");
			guid=guid.replace("selected_","");
			$("#"+guid).removeClass("selectedValue");
			$("#"+guid).click(function(e){
				itemClick(e);
			});
			$(currentTarget).closest("dd").remove();
		}
		//条目点击事件
		function itemClick(e){
			var currentTarget=$(e.currentTarget);
			if(currentTarget.attr("class")=="moreValue_click"){
				return;
			}
			if($(currentTarget).attr("class")=="selectedValue"){
				$(currentTarget).unbind("click"); 
				return false;
			}
			$(currentTarget).addClass("selectedValue");
			var title=$(currentTarget).closest("dd").prev().html();
			var type=$(currentTarget).closest("ul").attr("id");
			var fieldName=$(currentTarget).closest("dd").attr("id");
			title=title.replace(":","");
			var guid=$(currentTarget).attr("id"); 
			
			var content=$(currentTarget).html();
			
			content="<dd> <a id=\"selected_"+guid+"\" href=\"#\"><h5>"+title+"</h5>"+content
				+"<span class=\"close-icon\" title=\"取消\"></span></a> "
				+"<input type=\"hidden\" name=\"conditionItems\" value=\""+fieldName+"&"+type+"&"+guid.replace("item_"+fieldName+"_","")+"\"> </dd>";
			
			content = $(content);
			$(content).appendTo($(".selected-attr dl"));
			$(content).find(".close-icon").click(function(e){
				closeClick(e);
			});
			$(currentTarget).unbind("click"); 
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
			$("#search").submit();
		}
	</script>
</head>

<body>
	<form id="search" method="post" action="<%=request.getContextPath() %>/baseinfo/infogroupfind_find.html">
		<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
		<input type="hidden" id="asc" name="asc" value="${asc}"/>
		<input  type="hidden" name="resourceId" value="sys_user_findAllUser1"/>
		<div class="search_advanced" id="myTbody3">
			<div class="adv_filter">
			     <table border="0" width="92%">
			        <tbody>
			          <tr>
			            <td style="padding-left:68px;">
							查询条件: <input name="query.fuzzyValue" value="${query.fuzzyValue }" type="text" size="50" />
			              	<button type="button" class="brn_cx"  name="查询" >查 询</button>
							<button type="button" class="brn_dc"  name="导出" >导出</button>
			            </td>
			          </tr>
			        </tbody>
			      </table>
			</div>
			<div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
				<h3>已选条件:</h3>
				<dl>
				<c:forEach items="${conditionItems}" var="conditionItem">
					<dd><a href="#">
					<span class="close-icon" title="取消"></span></a>
					<input type="hidden" name="conditionItems" value="${conditionItem}"> </dd>
				</c:forEach>
				</dl>
			</div>
			<c:forEach items="${keyset }" var="key" >
				<div class="more--item_top"><p><a href="#" class="up" onclick="showTbody(this,'${key}','up','down','收 起','${key }');return false">收 起</a></p></div>
				  	<div id="${key }" class="prop-item ">
					  	<c:forEach items="${typeMap[key] }" var="bean" >
					  		<dl>
					  			<dt>${bean.name}:</dt>
						  		<dd id="${bean.fieldName }">
									<ul id="${bean.codeTableName }">
						  			<c:forEach items="${bean.values}" var="valueBean" varStatus="i">
						  				<c:if test="${i.index < 5 }">
						  					<li><a id="item_${bean.fieldName }_${valueBean.guid }" href="#">${valueBean.description}</a></li>
						  				</c:if>
						  				<c:if test="${i.index >= 5 }">
						  					<li class="more_item" style="display:none"><a id="item_${bean.fieldName }_${valueBean.guid }"  href="#">${valueBean.description}</a></li>
						  				</c:if>
						  			</c:forEach>
						  			<c:forEach items="${bean.values}" var="valueBean" varStatus="i">
						  				<c:if test="${i.index eq 5 }"><li><a class="moreValue_click" href="#">更多..</a></li></c:if>
						  			</c:forEach>
						  			</ul>
						  		</dd>
					  		</dl>
					  	</c:forEach>
				   </div>
		   	</c:forEach>
		 <div class="adv_filter">
				<table border="0" width="92%">
					<tbody>
						<tr>
						 	<td style="padding-left:68px;">
						 		部门
							</td>
							<td>
								<ct:codePicker name="deptId"
										catalog="<%=ICodeConstants.DM_DEF_ORG%>" code="${deptId }" />
							</td>
						 	<td>
						 		出生年月<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="birthDateStart" id="birthDateStart" value="${birthDateStartString }"/>
						 		至               <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="birthDateEnd" id="birthDateEnd" value="${birthDateEndString }"/>
							</td>
						</tr>
					</tbody>
				</table>	
			</div>
		</div>
	<div class="toolbox">
<!-- 按钮 -->
	<div class="buttonbox">
		<ul>
			<li><a id="btn_sz" class="btn_sz">显示字段设置</a></li>
		</ul>
	</div>
  	<p class="toolbox_fot">
		<em></em>
	</p>
	</div>
	<div class="formbox" >
		<h3 class="datetitle_01">
			<span>组合查询结果<font color="#0457A7" style="font-weight:normal;"> (双击查看详细)</font></span>
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
			<td style="display:none;"><input type='hidden' id='globalid' value="${overall.values['globalid'] }"/></td>
			<s:iterator value="query.clazz.viewables" var="p">
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
