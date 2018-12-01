<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="com.zfsoft.dao.page.Paginator"%>
<%
Map context = ActionContext.getContext().getContextMap(); 
Paginator pg = (Paginator)context.get("paginator");
int[] slider = pg.getSlider(5);
%> 
<style type="text/css">
	.pppppp{
		cursor : default;
	} 
	.ppppgaaa{
		cursor : default;
	}
	.text {
		border: 1px solid #9AC9EA;
	    color: #373737;
	    display: inline-block;
	    padding: 2px 8px 2px 8px;
	}
	.pagination .pageright .paging a {
	    padding: 2px 8px 2px 8px;
	}
</style>
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
<table border="0" cellspacing="0" cellpadding="0"  class="page"><tr>
<td>
<table border="0" cellspacing="4"  align="right" style="border-collapse: separate;">
  <tr>
  	<td>
  		每页显示
  		<input id="pageSize" name="query.perPageSize" value="${query.perPageSize }" size="2"/>条记录
    </td>
    <td>/共
	    <span style="color: red;"><%=pg.getItems() %></span>条 第
	    <span style="color: red;"><%=pg.getBeginIndex() %></span>—
	    <span style="color: red;"><%=pg.getEndIndex() %></span>条&nbsp;&nbsp;
    </td>
	<td><%if (pg.getPage()>1 && pg.getPages()>0) {%><a href="javascript:void(0);" onClick="gotoPage('1');return false;" >首页</a><%} else {%><span class="text ">首页</span><%}%></td>
    <td><%if (pg.getPage()>1) {%><a href="javascript:void(0);" onClick="gotoPage('<%=pg.getPreviousPage()%>');return false;">上一页</a><%} else {%><span class="text">上一页</span><%}%></td>
	<td>
	
      <%
      if(pg.getPages()>0) {
    	  int s=1;
    	  int len = slider.length;
    	  int cru = pg.getPage();
    		if (cru > 4) {
    			s = s+cru-4;
    			len = len+cru-4;
    			if (pg.getPages() < len) {
        			len = pg.getPages();
        			s = len-slider.length+1;
        		}
    		}
      	for(int t=s;t<=len;t++) {
      		if(t == pg.getPage()) {
      %>
	  <span class="text"><%=t%></span>
	  <%} else {%>
	  <a href="javascript:void(0);" onClick="gotoPage('<%=t%>');return false;"><%=t%></a>
	  <%
      		}
      	}
	  }
	  %>
     
    </td>
	<td><%if (pg.getPages() > pg.getPage()) {%><a href="javascript:void(0);"  onClick="gotoPage('<%=pg.getNextPage() %>');return false;">下一页</a><%} else { %><span class="text">下一页</span><%} %></td>
	<td><%if (pg.getPages() > pg.getPage()) {%><a href="javascript:void(0);" onClick="gotoPage('<%=pg.getPages() %>');return false;">末页</a><%} else { %><span class="text">末页</span><%} %></td>
    <td nowrap="nowrap">跳转到 <input id="biblepage" value="<%=pg.getPage() %>" type="text" size="2" />&nbsp;页</td>
    <td><a href="javascript:void(0);"   class="text" onClick="turnPage(this);return false;" id="gopage"><span>跳转</span></a></td></tr>
    <input type="hidden" id="toPage" name="query.toPage" value="${query.toPage }"/>
</table>
</td>
</tr>
</table>
</div>
		</div>
	</div>
<script language="javascript">
	var clickCount = 0;//防止连续点击翻页
	function turnPage(evt){
		var pages = document.getElementById("gopage");
		gotoPage(document.getElementById("biblepage").value);
	}

    function gotoPage(pageNumber) { 
    	//var test = pg.getPages();
		if (clickCount++ > 1) {
			//alert("你连击了");
			return false;
		}
        if (!isNumber(pageNumber)) {
        	pageNumber = 1;
        }
        if (pageNumber > <%=pg.getPages()%>) {
            //alert("对不起，您不能够转到该页！");
            pageNumber = <%=pg.getPages()%>;
        } else if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (parseInt(pageNumber,10) != pageNumber) {
            pageNumber = 1;
        }
        var form = $("#pagediv").closest("form");
        if(form.length==0){
        	alert("请把翻页器置入表单内");
        }
        var pageSize = $("#pageSize").val();
        if(!isNumber(pageSize)){
        	pageSize = "${query.perPageSize }";
        	$("#pageSize").val(pageSize);
        }
        $("#toPage").val(pageNumber);
        //document.getElementById('toPage').value = pageNumber;
        //document.forms["mainAction"].perPageSize.value = $pageSize;
        form.submit();
    }
    
    function isNumber( chars ) {
	    var re=/^\d*$/;
	    if (chars.match(re) == null)
		    return false;
	    else
		    return true;
    }
</script>