<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	<script type="text/javascript">
	var nwin=null;
	
	function enterSub(arg){
		$.ajax({
			type:"post",
			dataType:"text",
			url:"<%=request.getContextPath()%>/xtgl/index_enterSub.html",
			data:"sysCode="+arg,
			success:function(dataText){
				try{
					var data=$.parseJSON(dataText);
					if(data.success){
						if(nwin!=null){
							nwin.close();
						}
						if(window.name=="subsystem"){
							window.name="hrm";
						}
						nwin=window.open("<%=request.getContextPath()%>/xtgl/index_initMenu.html","subsystem");
					}else{
						alert(data.text);
					}
				}catch(e){
					window.location.reload();
				}
			},
			error:function(xmlhttprequest, textStatus, errorThrown){
				alert("请求出错："+textStatus);
			}
			
		});
	}
	</script>
</head>
<body class="body_bg">
<div class="body_main_xxrz">
<!--头部-->
<div class="mainbody type_mainbody">
	<div class="topframe" id="topframe" style="z-index:1;">
	     <jsp:include page="platform_top.jsp" flush="true"></jsp:include>
	</div>
</div>
<!--中间-->
<div class="mainframe_xxrz">
	<div class="demo_xxrz">
        <ul>
        	<c:forEach items="${subsystems }" var="item">
	            <li>
	            	<div class="type">
	                	<img src="<%=request.getContextPath() %>/${item.entry_icon }" />
	                    <div class="con">
		                    <h3>${item.sysName }</h3>
		                    <p>${item.sysDesc }</p>
		                    <button onclick="enterSub('${item.sysCode}')"></button>
	                    </div>
	                </div>
				</li>
        	</c:forEach>
            
        </ul>
	</div>
	<jsp:include page="pendingtask.jsp"></jsp:include>
</div>
<!-- 版权信息 -->
	<%@include file="/WEB-INF/pages/globalweb/bottom.jsp" %>
</div>
</body>
</html>
