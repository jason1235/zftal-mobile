<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
		$(".demo_xxxx_title a").click(function(){
			var globalid = $(this).prev().val();
			location.href="<%=request.getContextPath() %>/normal/staffResume_stulist.html?globalid="+globalid+"&type=student";
			});
		$("button[name='search']").click(function(e){//搜索按钮
			$("#search").attr("action","<%=request.getContextPath()%>/normal/overallInfo_stulist.html");
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
  <div class="searchbox_rs">
<div class="search_rs" id="search_rs">
<form id="search" method="post">
    <input style="width:340px;" class="text_nor text_nor_rs" name="query.fuzzyValue" type="text" value="${query.fuzzyValue }" />
    <button class="" name="search" >搜索一下</button></form>
    <h1>说明： 可以针对职工号、姓名、拼音简拼进行模糊搜索</h1>
    <a class="ico_close03" href="#" title="关闭搜索" onmouseover="this.className='ico_close02'" onmouseout="this.className='ico_close03'" onclick="javascript:document.getElementById('search_rs').style.display='none';javascript:document.getElementById('search_rs_btn').style.display='block'; return false;" ></a>
</div>
<div class="search_rs_btn" style="display:none;" title="打开搜索" id="search_rs_btn" onclick="javascript:document.getElementById('search_rs').style.display='block';javascript:document.getElementById('search_rs_btn').style.display='none'">搜索</div>
</div>
<div class="demo_xxxx">
<form action="normal/overallInfo_stulist.html" name="stuform" id="stuform" method="post">
   <input type="hidden" name="toPage" id="toPage" value="${query.toPage }" />
</form>	
  <h3 class="datetitle_01">
    <span>${con.title }</span><font class="floatright" color="#0f5dc2" style="font-weight:normal;">　总计： ${con.result.count }人</font>
  </h3>
  <c:forEach items="${pageList}" var="bean" varStatus="vs">
  <h4 class="demo_xxxx_title"><span>${vs.count }、 ${bean.viewHtml['xm']} （学号：${bean.viewHtml['XH'] } - 在校）</span><input type="hidden" value="${bean.values['globalid']}"><a style="cursor: pointer">详细信息</a></h4>
<!--标题end-->
	<table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
		<tbody>
			<c:forEach items="${bean.viewables}" var="prop" varStatus="st">
			<c:if test="${st.count%3==1}">
			<tr>
			</c:if>
			<c:if test="${st.count==1}">
			<td width="10%"><img src="<%=request.getContextPath() %>/img/hrm/${vs.count%6+1}.jpg" width="100" height="150"></td>
			</c:if>
			<th width="13%">${prop.name }</th>
			<td width="14%">${bean.viewHtml[prop.fieldName] }</td>
			<c:if test="${st.count%3==0}">
			</tr>
			</c:if>
			<c:set value="${st.count}" var="length"></c:set>
			</c:forEach>
			<c:if test="${length%3==1}">
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				</tr>
			</c:if>
			<c:if test="${length%3==2}">
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				</tr>
			</c:if>
			
		</tbody>
  	</table>
  	
  	</c:forEach>

  	
  	<!--分页显示开始-->
				<div class="pagination">
					<div class="pageleft">
						<!-- 全选反选位置 -->
						<div class="choose">
						</div>
					</div>
					<div class="pageright">
						<!-- 分页位置 -->
						<div id="pagediv" class="paging">
							<!-- 分页器 -->
							<jsp:include flush="true" page="/commons/page/pageNav.jsp">
								<jsp:param name="theFome" value="stuform" />
							</jsp:include>
						</div>
					</div>
				</div>
	 <!--分页显示结束-->
  	</div>

  </body>
    	
</html>
