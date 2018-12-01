<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
    <%--<style type="text/css">
      .autocut {
          overflow:hidden;
          white-space:normal;
          text-overflow:ellipsis;
          -o-text-overflow:ellipsis;
          -icab-text-overflow:ellipsis;
          -khtml-text-overflow:ellipsis;
          -moz-text-overflow:ellipsis;
          -webkit-text-overflow:ellipsis;
      }

      .autocut:hover {
          overflow:visible;
          white-space:normal;
          word-wrap:break-word;
      }
    </style>--%>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
            
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
	 
	$("#list_body tr").bind("click",function(e){
	 	var check = $(this).find("input[name='id']").prop("checked");
	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			if(!check){
			   $(this).find("input[name='id']").prop("checked",true);
			   var jsdm = $(this).find("input[name='id']").val();
			   var zgh = $("input[name='yhglModelQuery.zgh']").val();
			   $.post(_path+"/xtgl/yhgl_updateJs.html","yhglModelQuery.zgh="+zgh+"&yhglModelQuery.jsdm="+jsdm,function(data){
	                var callback = function(){
	                	$("#search",window.parent.document).submit();
					};
					processCall(data, callback);
	            },"json");
			}
	});
	
	$("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			   var jsdm = $(this).val();
			   var zgh = $("input[name='yhglModelQuery.zgh']").val();
			   $.post(_path+"/xtgl/yhgl_updateJs.html","yhglModelQuery.zgh="+zgh+"&yhglModelQuery.jsdm="+jsdm,function(data){
	                var callback = function(){
	                	$("#search",window.parent.document).submit();
					};
					processCall(data, callback);
	            },"json");
			   
			}
	 });
	
    });
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="xtgl/yhgl_fpjs.html" name="search" id="search" method="post">
      	<input type="hidden"  name="yhglModelQuery.zgh" value="${yhglModelQuery.zgh }"/>
      	<input type="hidden"  name="yhglModelQuery.jsdm" value="${yhglModelQuery.jsdm }"/>
          <div class="form-horizontal sl_all_form">
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                	<th style="width: 5%;">选择</th>
                    <th style="width: 5%;">序号</th>
                    <th style="width: 22%;">角色代码</th>
                    <th style="width: 22%;">角色名称</th>
                    <th style="width: 22%;">用户数</th>
                    <th style="width: 22%;">角色创建人</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="JsglModelList" var="menu" status="st">
                <tr name="tr" class="data pxtr">
                    <td>
                    	<input type="checkbox" id="id" name="id" value="${menu.jsdm }" 
                    	<c:if test="${menu.jsdm == yhglModelQuery.jsdm}">checked="checked"</c:if>
                    	/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.jsdm }</td>
                    <td class="autocut" >${menu.jsmc}</td>
                    <td class="autocut" >${menu.yhnum}</td>
                    <td class="autocut" >${menu.jscjrxm}</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${JsglModelList }" query="${NewJsglQuery }" queryName="NewJsglQuery"/>
      </form>
  </div>
	</body>
</html>