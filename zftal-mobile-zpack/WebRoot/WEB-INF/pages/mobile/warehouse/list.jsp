<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
   <style type="text/css">
      <%-- .autocut {
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
      @media screen and (min-width: 768px){
    	 .form-horizontal .control-label{
    			text-align:left;
    			padding-top:inherit;
    			line-height:34px;
   		 }
      
      }
      .form-group{
      		margin-bottom:inherit;
      }
     
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
    	
    	
    	
    //新增
    
    
    
     $("#btn_bj").click(function(){//功能条修改按钮
    	 if($("input[name='id']:checked").length==0){
              openAlert("请先选中操作行！", "e");
              return false;
          }
      	 if($("input[name='id']:checked").length>1){
              openAlert("不能选中多行！", "e");
              return false;
          }	
      	 
      	 var fixId = $("input[name='id']:checked").val();
    	 var url = _path+"/reportFix/reportFix_toUpdate.html?fixId="+fixId;
    	 $("#myModal_toji").find(".modal-content").width(800);
         $("#myModal_toji").find(".modal-content").height(400);
         $("#myFrame").height(400);
         $("#myFrame").attr("src", url);
         $("#myModal_toji").modal("show");
     });
     
     $("#btn_sc").click(function(){//功能条删除按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
      	if($("input[name='id']:checked").length>1){
             openAlert("不能选中多行！", "e");
              return false;
          }	
      	if(confirm('确认删除吗!')){
	    	 var fixId = $("input[name='id']:checked").val();
	    	 $.post("/reportFix/reportFix_htReportFixRemove.html",{"fixId":fixId}, function(data){
					if(data.msg=='success'){
					    window.location.href="/reportFix/reportFix_htReportFixList.html";
					}else{
						openAlert("删除失败！", "e");
					}
			 }, "json");
      	}
     });
     
     $("button[name='search']").click(function(e){//搜索按钮
        //refulsh();
      	$("#search").submit();
          e.preventDefault();//阻止默认的按钮事件，防止多次请求
      });
     
	 /*
     $('#list_body tr').find('input').bind('click',function(e){
			e.stopPropagation();
	 });      
	 $('#list_body tr').bind('click',function(e){
			 var status = $(this).find('input[name="id"]').prop('checked');
			 $(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			 var controlStatus = $(this).find("input[name='status']").val();
			 //$(this).closest('#list_body').find('input[name="id"]').removeAttr('checked');
			 if (controlStatus == "2") {
				$("#btn_ty").show();
				$("#btn_sc").hide();
				$("#btn_ck").hide();
				$("#btn_jg").show();
				$("#btn_anwser").show();
			 } else {
				 $("#btn_ty").hide();
				 if(controlStatus == "1"){
					 $("#btn_ck").hide();
					 $("#btn_jg").show();
					 $("#btn_anwser").show();
					 $("#btn_sc").show();
					 $("#btn_sh").hide();
				 }else{
					 $("#btn_sc").show();
					 $("#btn_ck").show();
					 $("#btn_sh").show();
					 $("#btn_jg").hide();
					 $("#btn_anwser").hide();
				 }
				 if (status){
					 $("#btn_ty").show();
				 }
			 }
			 if (status) {
				$("#btn_ty").show();
				$("#btn_jg").show();
				$("#btn_anwser").show();
				$("#btn_sc").show();
				$("#btn_ck").show();
				$("#btn_sh").show();
				$(this).find('input[name="id"]').removeAttr('checked');
			 } else {
				$(this).find('input[name="id"]').prop('checked',true);
			 }
	 });
      */

    });
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd">
          	  <button type="button" class="btn btn-default" id="btn_xz">新增</button>
          	  <button type="button" class="btn btn-default" id="btn_bj">编辑</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
               <%--<button type="button" class="btn btn-default" id="btn_ty">停用</button>
              <button type="button" class="btn btn-default" id="btn_anwser">查看投票结果</button>
              --%>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath() %>/reportFix/reportFix_htReportFixList.html" name="search" id="form_edit" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-2 control-label">名称</label>
                         <%--缺字段名--%>
                        <div class="col-xs-7">
                            <input id="" maxlength="50" placeholder="最多50个字符" class="form-control" name="" value=""/>
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
                    <th style="width:5%;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th>名称</th>
                    <th>数量</th>
                    <th>备注</th>
               </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${id }"/></td>
                    <td class="autocut" ><s:property value='#st.index+1'/></td>
                    <%--名称--%>
                    <td class="autocut" ></td>
                    <%--数量--%>
                    <td class="autocut" ></td>
                    <%--备注--%>
                    <td class="autocut" ></td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>