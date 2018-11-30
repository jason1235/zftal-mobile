<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <style media="print">
        .jyfw_bg{background:none;}
        .top_pic,.bot_jyfw,.printBtn{display: none;}
        .st_feedback,.pers_pri{padding:0;margin:0;border:none;width:auto;}
    </style>
    <script type="text/javascript">
    $(function(){
    	loadDataByCatalogAll();
    	$("h3.college_title","#infoContent-all").toggle(function(){
	    		$(this).find("a.up").removeClass("up").addClass("down").text("展开");
	    		$(this).next("div[name='clazz']").hide();
    		},function(){
    			$(this).find("a.down").removeClass("down").addClass("up").text("收起");
	    		$(this).next("div[name='clazz']").show();
    		});
    });
    
	function loadDataByCatalogAll(){
		var userid=$("#userid").val();
		<c:if test="${fn:length(catalogs)>0}">
			<c:forEach items="${catalogs}" var="catalog">
				var param="editable=false&history=true&catalogId=${catalog.guid}&gh="+userid;
				loadInfo(param);
			</c:forEach>
		</c:if>
	}
	function loadInfo(param){
		$.ajax({type:"post",
				url:_path+"/normal/staffResume_loadInfoByCatalogForAll.html",
				data:param,
				dataType:"json",
				async: false,
				success:function(data){
					if( !data.success ) {
						alert(data.title);
					}else{
						$("#infoContent-all").append(data.html);
/*						$("#infoContent-all").append("<div>");
						$("#infoContent-all").append("<button class=\"printBtn\" onclick=\"window.print();\">");
						$("#infoContent-all").append("<img src=\"../img/iframeweb/pers_pri_btn.gif\" width=\"100\" height=\"30\"/></button>");
						$("#infoContent-all").append("<button class=\"printBtn\" onclick=\"window.print();\"><img src=\"../img/iframeweb/pers_pri_btn.gif\" width=\"100\" height=\"30\"/></button>");
						$("#infoContent-all").append("</div>");*/
					}
				}
		});
	}
    </script>
  </head>
  <body>
  	<input type="hidden" name="userid" id="userid" value="${globalid }"/>
    <div id="infoContent-all" style="width: 790px">
    </div>
    <div class="st_feedback pers_pri" style="width: 810px; border-top-width: 2px; margin-top: 0px;">
    	 <button class="printBtn" onclick="window.print();"><img src="../img/iframeweb/pers_pri_btn.gif" width="120" height="34"/></button>
    </div>
  </body>
</html>
