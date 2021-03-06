<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
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
		$(".demo_xxxx_title a").click(function(){
			var globalid = $(this).prev().val();
			//location.href="<%=request.getContextPath() %>/normal/staffResume_list.html?globalid="+globalid+"&type=teacher";
			goUrl(_path+"/normal/staffResume_list.html?globalid="+globalid+"&type=teacher");
			});
		$("button[name='search']").click(function(e){//搜索按钮
			$("#search").attr("action","<%=request.getContextPath()%>/normal/overallInfo_list.html");
			$("#search").attr("method","post");
			$("#search").submit();
			e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
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
<form id="search" method="post" action="normal/overallInfo_list.html">
  <div class="searchbox_rs">
<div class="search_rs" id="search_rs">
    <input style="width:340px;" class="text_nor text_nor_rs" name="query.fuzzyValue" type="text" value="${query.fuzzyValue }" />
    <button class="" name="search" >查询</button>
    <h1>说明： 可以针对职工号、姓名、拼音简拼进行模糊搜索</h1>
    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
</div>
<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
</div>
<div class="demo_xxxx">
  <h3 class="datetitle_01">
    <span>${con.title }</span><font class="floatleft" color="#0f5dc2" style="font-weight:normal;">　总计： ${con.result.count }人</font>
    <a class="floatright ico_list1_sel" href="#">视图</a>
    <a class="floatright ico_list2" href="#" id="change">列表</a>
  </h3>
  <c:forEach items="${pageList}" var="bean" varStatus="vs">
  <h4 class="demo_xxxx_title"><span>${vs.count }、 ${bean.viewHtml['xm']} （职工号：${bean.viewHtml['gh'] }）</span><input type="hidden" value="${bean.values['globalid']}"><a style="cursor: pointer">详细信息</a></h4>
<!--标题end-->
	<table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
		<tbody>
			<c:set value="1" var="cnt"></c:set>
			<c:forEach items="${bean.viewables}" var="prop" varStatus="st">
			<c:if test="${cnt%3==1}">
			<tr>
			</c:if>
			<c:if test="${st.count==1}">
			<td width="10%">${bean.viewHtml['zp'] }</td>
			</c:if>
			<c:if test="${prop.fieldName!='zp'}">
			<th width="13%">${prop.name }</th>
			<td width="14%">${bean.viewHtml[prop.fieldName] }</td>
			<c:if test="${cnt%3==0}">
			</tr>
			</c:if>
			<c:set value="${cnt+1 }" var="cnt"></c:set>
			</c:if>
			</c:forEach>
			<c:if test="${(cnt-1)%3==1}">
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				</tr>
			</c:if>
			<c:if test="${(cnt-1)%3==2}">
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				</tr>
			</c:if>
			
		</tbody>
  	</table>
  	</c:forEach>
  	<ct:page pageList="${pageList }" />
  	</div>
  	</form>
  </body>
</html>
