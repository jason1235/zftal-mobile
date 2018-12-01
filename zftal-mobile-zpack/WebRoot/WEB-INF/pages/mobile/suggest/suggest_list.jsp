<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
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
    
     $("#btn_reply").click(function(){//功能条增加按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
    	 var id = $("input[name='id']:checked").val();
         var url = _path+"/suggest/suggest_toReply.html?query.id=" + id;
         location.href=url;
     });
     
     $("#btn_detail").click(function(){//功能条增加按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
    	 var id = $("input[name='id']:checked").val();
         var url = _path+"/suggest/suggest_getDetail.html?query.id=" + id;
         window.open(url);
     });
            
            
     $("button[name='search']").click(function(e){//搜索按钮
    	$("#search").submit();
        e.preventDefault();//阻止默认的按钮事件，防止多次请求
     });
            
     
	 
	$("#list_body tr").bind("click",function(e){
	 	var check = $(this).find("input[name='id']").prop("checked");
	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
		var procodeMax = $(this).find("input[name='procode']").val();
		var status = $(this).find("#status").val();
			if(check){
			   $("#btn_qy").show();
			   $("#btn_ty").show();
			   $("#btn_sc").show();
			}else{
			   $(this).find("input[name='id']").prop("checked",true);
			   if (status=="1") {
					$("#btn_qy").hide();
					$("#btn_ty").show();
					$("#btn_sc").hide();
			   } else {
					$("#btn_qy").show();
					$("#btn_ty").hide();
					$("#btn_sc").show();
						
			   }
			   
			}
	});
	
	$("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			}
	 });
      //fillRows("20", "", "", false);//填充空行
    });
    
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_reply">回复</button>
              <button type="button" class="btn btn-default" id="btn_detail">详情</button>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath()%>/suggest/suggest_list.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">意见内容</label>
                        <div class="col-xs-7">
                            <input maxlength="100" placeholder="最多100个字符" class="form-control" name="query.textContent" value="${query.textContent }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">学校编码</label>
                        <div class="col-xs-7">
                            <input maxlength="15" placeholder="最多15个字符" name="query.schoolCode" class="form-control" value="${query.schoolCode }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">app版本</label>
                        <div class="col-xs-7">
                            <input maxlength="15" placeholder="最多15个字符" name="query.versionNumber" class="form-control" value="${query.versionNumber }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">用户名</label>
                        <div class="col-xs-7">
                            <input maxlength="15" placeholder="最多15个字符" name="query.userName" class="form-control" value="${query.userName }"/>
                        </div>
                    </div>
                </div>
            </div>
          </div>
          <div class="row sl_aff_btn">
              <div class="col-xs-12">
                  <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
              </div>
          </div>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th style="width:15%;">意见反馈描述</th>
                  	<th style="width:8%;">学校编码</th>
                    <th style="width:8%;">app版本号</th>
                    <th style="width:9%;">用户名</th>
                    <th style="width:15%;">创建时间</th>
                    <th style="width:11%;">用户手机号</th>
                    <th style="width:9%;">用户qq号码</th>
                    <th style="width:11%;">回复内容</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="pageList" var="menu" status="st">
                <tr name="tr" class="data pxtr">
                    <td>
                    	<input type="checkbox" id="id" name="id" value="${menu.id }"/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.textContent }</td>
                   	<td class="autocut" >${menu.schoolCode}</td>
                    <td class="autocut" >
                    ${menu.versionNumber}
                    </td>
                    <td class="autocut discription">${menu.userName}</td>
                    <td class="autocut" >
                    	<s:date name="#menu.createTime" format="yyyy-MM-dd HH:mm:ss" />
                    </td>
                    
                    <td class="autocut" >${menu.telephone }</td>
                    <td class="autocut" >${menu.qq }</td>
                    <td class="autocut" >
                    	<c:if test="${not empty menu.replyContent }"><font color="green">已回复</font></c:if>
                    	<c:if test="${empty menu.replyContent }"><font color="red">未回复</font></c:if>
                    </td>
                </tr>
                </s:iterator>
            </tbody>
        	</table>
        <ct:page pageList="${pageList }" query="${query }" />
      </form>
  </div>
	</body>
</html>