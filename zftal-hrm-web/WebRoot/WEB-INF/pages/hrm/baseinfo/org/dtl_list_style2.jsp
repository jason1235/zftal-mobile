<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/baseinfo/data_tip.js"></script>
    <script type="text/javascript">
    $(function(){
    	var size = $("table.formlist tbody > tr").length/$("table.formlist tbody").length;
		$("tbody img").closest("td").attr("rowspan",size);
 
		$("#back").click(function(){//功能条返回按钮
			location.href="<%=request.getContextPath()%>/baseinfo/orgSearch_list.html?searchQuery.type=${info.orgInfo.type}&resourceId=mes_manager_findOrgList";
		});
		$("#change").click(function(){
			$.post(_path+'/baseinfo/orgSearch_change.html','',function(data){
				if(data.success){
					$("#search").submit();
				}else{
					tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
				}
			},"json");
		});
		$("button[name='search']").click(function(e){//搜索按钮
			$("#search").submit();
			e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
		$("tbody>tr").dblclick(function(){
			//var id = $(this).find("input[id='globalid']").val();
			var id = $(this).attr("id");
			//location.href="<%=request.getContextPath() %>/normal/staffResume_list.html?globalid="+id+"&type=teacher";
			goUrl(_path+"/normal/staffResume_list.html?globalid="+id+"&type=teacher");
		});
		$("#list_body>tr[name='tr']").each(function(){
			datatips(this);
		});
		fillRows($("#pageSize").val(), "", "", false);//填充空行
        });
    </script>
  </head>
  <body>
  <div class="toolbox">
<!-- 按钮 -->
	<div class="buttonbox">
		<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
	</div>
  	<p class="toolbox_fot">
		<em></em>
	</p>
	</div>
<form id="search" method="post" action="<%=request.getContextPath()%>/baseinfo/orgSearch_detailList.html">
	<input type="hidden" name="oid" value="${oid }"/>
	<input type="hidden" name="searchByParent" value="${searchByParent }"/>
	<div class="searchbox_rs">
		<div class="search_rs" id="search_rs">
		    <input style="width:340px;" class="text_nor text_nor_rs" name="dyanQuery.fuzzyValue" type="text" value="${query.fuzzyValue }" />
		    <button type="button" name="search" >查询</button>
		    <h1>说明： 可以针对职工号、姓名、拼音简拼进行模糊搜索</h1>
		    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
		</div>
		<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
	</div>
<div class="formbox" >
	<h3 class="datetitle_01">
		<span>${info.orgInfo.name }</span><font class="floatleft" color="#0f5dc2" style="font-weight:normal;">　总计： ${info.result.stepCount }人( 双击查看详细 )</font>
		<a class="floatright ico_list1" href="#" id="change">视图</a>
    	<a class="floatright ico_list2_sel" href="#" >列表</a>
	</h3>
	<div style="overflow-x:auto;width:795px;">
	<table width="100%" class="dateline tablenowrap" id="tiptab" >
		<thead id="list_head">
			<tr>
			<s:iterator value="query.clazz.viewables" var="p">
			<s:if test="fieldName != 'zp'">
				<td>${p.name }</td>
			</s:if>
			</s:iterator>
			</tr>
		</thead>
		<tbody id="list_body">
			<s:iterator value="pageList" var="overall">
				<tr name="tr" id="${overall.values['globalid'] }">
					<%--<input type="hidden" id="globalid" value="${overall.values['globalid'] }">
					--%><s:iterator value="query.clazz.viewables" var="p">
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
