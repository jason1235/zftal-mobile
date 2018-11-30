<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/org/data_tip.js"></script>
    <script type="text/javascript">
    $(function(){
    	var size = $("table.formlist tbody > tr").length/$("table.formlist tbody").length;
		$("tbody img").closest("td").attr("rowspan",size);

		$("#back").click(function(){//功能条返回按钮
			location.href="${returnUrl}";
		});
		$("#change").click(function(){
			$.post(_path+'/normal/overallInfo_change.html','',function(data){
				if(data.success){
					location.href = _path+"/normal/overallInfo_list.html";
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
				}
			},"json");
		});
		$("button[name='search']").click(function(e){//搜索按钮
			$("#search").attr("action",_path+"/normal/overallInfo_list.html");
			$("#search").attr("method","post");
			$("#search").submit();
			e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
		$("tbody>tr").dblclick(function(){
			var id = $(this).find("input[id='globalid']").val();
			//location.href="<%=request.getContextPath() %>/normal/staffResume_list.html?globalid="+id+"&type=teacher";
			goUrl(_path+"/normal/staffResume_list.html?globalid="+id+"&type=teacher");
		});
		$("#list_body>tr").each(function(){
			datatips(this);
		});
		
		$("#btn_sz").click(function(){
			showWindow("显示字段设置", "<%=request.getContextPath()%>/infoclass/infoPropertyView_list.html?classId=${classId}", 480, 300 );
		});
		
		fillRows($("#pageSize").val(), "", "", false);//填充空行
    });
    /*
	*排序回调函数
	*/
	function callBackForSort(sortFieldName,asc){
		$("#sortFieldName").val(sortFieldName);
		$("#asc").val(asc);
		$("#search").attr("method","post");
		$("#search").submit();
	}
    </script>
  </head>
  <body>
  <div class="toolbox">
<!-- 按钮 -->
	<div class="buttonbox">
		<ul>
			<li><a id="btn_sz" class="btn_sz">显示字段设置</a></li>
		</ul>
		<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
	</div>
  	<p class="toolbox_fot">
		<em></em>
	</p>
	</div>
<form id="search" method="post" action="<%=request.getContextPath() %>/normal/overallInfo_list.html">
<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
<input type="hidden" id="asc" name="asc" value="${asc}"/>
  <div class="searchbox_rs">
<div class="search_rs" id="search_rs">
    <input style="width:340px;" class="text_nor text_nor_rs" name="query.fuzzyValue" type="text" value="${query.fuzzyValue }" />
    <button class="" name="search" >查询</button>
    <h1>说明： 可以针对职工号、姓名、拼音简拼进行模糊搜索</h1>
    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
</div>
<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
</div>
<div class="formbox" >
	<h3 class="datetitle_01">
		<span>${con.title }<font class="floatleft" color="#0457A7" style="font-weight:normal;">   总计： ${con.result.count }人  (双击查看详细)</font></span>
		<a class="floatright ico_list1" href="#" id="change">视图</a>
    	<a class="floatright ico_list2_sel" href="#" >列表</a>
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
		<td style="display:none;"><input type='hidden' id='globalid' value='${overall.values['globalid'] }'/></td>
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
