<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript">
        	var _tr = null;
   		$(function(){
			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/pushMsg/pushMsg_list.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			operationList();//操作栏初始化
		});
   		
   		function operationList(){
			
			$('a[name="detail"]').click(function(){//
				var id = $(this).closest("tr").find("td[name='tsid'] span").text();
				queryEntity(id);
			});
			
			$(".select_tools a").css("cursor","pointer");
			operationHover();
		}
   		
   		function queryEntity(id){//查询
   		    var url = "<%=request.getContextPath()%>/pushMsg/pushMsg_query.html?pushMsg.tsid="+id;
            $("#myModal_toji").find(".modal-content").width(720);
            $("#myModal_toji").find(".modal-content").height(200);
            $("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
			//showWindow("查看","<%=request.getContextPath()%>/pushMsg/pushMsg_query.html?pushMsg.tsid="+id,720,270);
			//tipsWindown("查看","url:post?<%=request.getContextPath()%>/pushMsg/pushMsg_query.html?pushMsg.tsid="+id,"720","270","true","","true","id");
		}
    </script>
  </head>
  <body>
    <div class="container_ydht sl_all_bg">
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
        <form id="search" method="post" action="<%=request.getContextPath()%>/pushMsg/pushMsg_list.html">
            <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
            <input type="hidden" id="asc" name="asc" value="${asc}"/>
            <div class="row sl_aff_btn">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-sm" name="search">查询</button>
                </div>
            </div>
            <table class="table table-striped sl_all_bg table-bordered text-left">
	            <thead id="list_head">
	                <tr>
	                    <th style="width:64px;">序号</th>
	                    <th style="display:none;">编号</th>
	                    <th>发送时间</th>
	                    <th>内容</th>
	                    <th>操作</th>
	                </tr>
	            </thead>
	            <tbody id="list_body" >
	                <c:forEach items="${pageList}" var="pushMsg" varStatus="st">
                        <tr name="tr">
                        <td>${st.index+1 }</td>
                        <td name="tsid" style="display:none;"><span>${pushMsg.tsid }</span></td>
                        <td name="tssj"><fmt:formatDate value="${pushMsg.tssj}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td name="tsnr">${pushMsg.tsnr }</td>
                        <td>
                            <a name="detail" href="#" class="tools_list">查看</a>
                        </td>
                    </tr>
                    </c:forEach>
	            </tbody>
	        </table>
	        <ct:page pageList="${pageList }" query="${query }" />
        </form>
    </div>
  <%--
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
					</ul>
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<form id="search" method="post" action="<%=request.getContextPath()%>/pushMsg/pushMsg_list.html">
<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
<input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchbox">
    <p class="search_con">
    
		<button class="btn_cx" name="search" >查 询</button>
	</p>
	<p class="search_title">
		<em></em>
	</p>
    
</div>
<!------------鼠标移上去显示的工具栏-start------------>
<div id="positionbox" style="position:relative; float:left;">
<div class="toolbox_fd" id="div_tools" style="display:none;position:absolute;">
<div class="buttonbox">
  <ul>

  </ul>
</div>
</div>
</div>
		
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>查看详情<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以查看）</font></span>
    </h3>
<!--标题end-->
		<table width="100%" class="dateline" id="tiptab">
				<thead id="list_head">
					<tr>
						<td>序号</td>
						<td style="display:none;">编号</td>
						<td>发送时间</td>
						<td>内容</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="list_body">
					<c:forEach items="${pageList}" var="pushMsg" varStatus="st">
						<tr name="tr">
						<td>${st.index+1 }</td>
						<td name="tsid" style="display:none;"><span>${pushMsg.tsid }</span></td>
						<td name="tssj"><fmt:formatDate value="${pushMsg.tssj}" pattern="yyyy-MM-dd HH:mm"/></td>
						<td name="tsnr">${pushMsg.tsnr }</td>
						<td>
						  <div>
					      	<div class="current_item">
					        	<span class="item_text">条目维护</span>
					        </div>
					        <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
					            <ul>
					            	<li><a name="maintain" href="#" class="first1">条目维护</a></li>
					                <li><a name="detail" href="#" class="tools_list">查看</a></li>
					            </ul>
					        </div>
					      </div>
						</td>
					</tr>
					</c:forEach>
				</tbody>
  	</table>
  	<ct:page pageList="${pageList }" />
  </div>
  </form> --%>
  </body>
</html>
