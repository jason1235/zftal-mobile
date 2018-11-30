<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
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
      }--%>
    </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
	    $("#btn_xq").click(function(){
	    	var paperMainId = $("input[name='twoResultQuery.paperMainId']").val();
	    	var yhid = $("input[name='id']:checked").val();
	    	var url = "<%=request.getContextPath() %>/serviceManager/twoQuestion_answerList.html?query.papermainid="+paperMainId+"&examAnwserQuery.yhid="+yhid;
	    	window.open(url);
	    	
	    });
	    
	    $("#list_body tr").bind("click",function(e){
		 	var check = $(this).find("input[name='id']").prop("checked");
		 	//alert(check);
		 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			//$(this).closest("tbody").find("tr").removeClass("current");
				//alert(check);
				if(!check){
				   $(this).find("input[name='id']").prop("checked",true);
				}
		});
		
		$("#list_body tr").find("input").bind("click",function(e){
				var check = $(this).prop("checked");
	     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
				//$(this).closest("tbody").find("tr").removeClass("current");
				if(!check){
				   $(this).prop("checked",true);
				   //$(this).closest("tr").attr("class","current");
				}
		 });
		
    })
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
  
  	<div class="toolbox">
          <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_xq">查看用户答案详情</button>
          </div>
      </div>
      
      
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath() %>/serviceManager/twoQuestion_userList.html" name="search" id="form_edit" method="post">
          <div class="form-horizontal sl_all_form">
          <div class="row">
            	<div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label"> 问卷名称</label>
                        <div class="col-xs-7">
                            ${query.papermainname }
                            <input type="hidden" name="twoResultQuery.paperMainId" value="${twoResultQuery.paperMainId }"/>
                        </div>
                    </div>
                </div>
          </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">用户id</label>
                        <div class="col-xs-7">
                            <input maxlength="20" placeholder="最多20个字符" class="form-control" name="twoResultQuery.yhid" value="${twoResultQuery.yhid }"/>
                        </div>
                    </div>
                </div>
                
                <div class="col-xs-4">
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
                    </div>
                </div>
                
            </div>
          </div>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                	<th style="width:64px;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th >用户id</th>
                    <th>姓名</th>
                    <th>得分</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="twoResultPageList" var="menu" status="st">
                <tr name="tr" class="data">
                	<td>
                    	<input type="checkbox" id="id" name="id" value="${menu.yhid }"/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/></td>
                    <td class="autocut" >${menu.yhid }</td>
                    <td class="autocut" >${menu.xm }</td>
                    <td class="autocut" >${menu.score}</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${twoResultPageList }" query="${twoResultQuery }" queryName="twoResultQuery"/>
      </form>
  </div>
	</body>
</html>