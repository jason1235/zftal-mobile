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
    	
     $("#btn_sh").click(function(){//功能条修改按钮
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
     
     //详情 点击只显示问题描述、联系方式
     $("#btn_xq").click(function(){//功能条修改按钮
    	 if($("input[name='id']:checked").length==0){
              openAlert("请先选中操作行！", "e");
              return false;
          }
      	 if($("input[name='id']:checked").length>1){
              openAlert("不能选中多行！", "e");
              return false;
          }	
      	 
      	 var fixId = $("input[name='id']:checked").val();
    	 var url = _path+"/reportFix/reportFix_toDetail.html?fixId="+fixId;
    	 $("#myModal_toji").find(".modal-content").width(800);
         $("#myModal_toji").find(".modal-content").height(520);
         $("#myFrame").height(520);
         $("#myFrame").attr("src", url);
         $("#myModal_toji").modal("show");
     });
     
     
     //分配可以选列表
     $("#btn_fp").click(function(){//功能条修改按钮
    	 if($("input[name='id']:checked").length==0){
              openAlert("请先选中操作行！", "e");
              return false;
          }
      	 if($("input[name='id']:checked").length>1){
              openAlert("不能选中多行！", "e");
              return false;
          }	
      	 
      	 var fixId = $("input[name='id']:checked").val();
    	 var url = _path+"/reportFix/reportFix_personList.html?fixId="+fixId;
    	 $("#myModal_toji").find(".modal-content").width(440);
        $("#myModal_toji").find(".modal-content").height(520);
        $("#myFrame").attr("src", url);
        $("#myFrame").height(520);
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
	    	 $.post(_path+"/reportFix/reportFix_htReportFixRemove.html",{"fixId":fixId}, function(data){
					if(data.msg=='success'){
					    window.location.href=_path+"/reportFix/reportFix_htReportFixList.html";
					}else{
						openAlert("删除失败！", "e");
					}
			 }, "json");
      	}
     });
     
     $("#btn_dc").click(function(){//功能条删除按钮
    	 window.open(_path+"/reportFix/reportFix_htExpReportFixList.html");
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
          	  <button type="button" class="btn btn-default" id="btn_xq">详情</button>
          	  <button type="button" class="btn btn-default" id="btn_sh">审核</button>
          	  <button type="button" class="btn btn-default" id="btn_fp">分配</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <button type="button" class="btn btn-default" id="btn_dc">导出</button>
               <%--<button type="button" class="btn btn-default" id="btn_ty">停用</button>
              <button type="button" class="btn btn-default" id="btn_anwser">查看投票结果</button>
              --%>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath() %>/reportFix/reportFix_htReportFixList.html" name="search" id="form_edit" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
            	
            	<div class="col-xs-11">
	                <div class="col-xs-3">
	                    <div class="form-group">
	                        <label for="" class="col-xs-4 control-label">报修人</label>
	                        <div class="col-xs-8">
	                            <input maxlength="50" placeholder="最多50个字符" class="form-control" name="query.bxr" value="${query.bxr}"/>
	                        </div>
	                    </div>
	                </div>
	                <div class="col-xs-3">
	                    <div class="form-group">
	                        <div class="form-group">
		                        <label for="" class="col-xs-5 control-label">类型</label>
		                        <div class="col-xs-7">
		                            <select  name="query.status" class="form-control" value="${query.status}">
		                           		<option value="" <c:if test="${empty query.status}">selected="selected"</c:if>>全部</option>
		                                <option value="0" <c:if test="${query.status eq '0' }">selected="selected"</c:if>>待报修</option>
		                                <option value="1" <c:if test="${query.status eq '1' }">selected="selected"</c:if>>报修中</option>
		                                <option value="2" <c:if test="${query.status eq '2' }">selected="selected"</c:if>>已报修</option>
		                                <option value="3" <c:if test="${query.status eq '3' }">selected="selected"</c:if>>已评价</option>
		                            </select>
		                        </div>
		                    </div>
	                    </div>
	                </div>
	                <div class="col-xs-3">
	                    <div class="form-group">
	                        <label for="" class="col-xs-4 control-label">维修人</label>
	                        <div class="col-xs-8">
	                            <input maxlength="50" placeholder="最多50个字符" class="form-control" name="query.wxr" value="${query.wxr}"/>
	                        </div>
	                    </div>
	                </div>
	                <div class="col-xs-3">
	                    <div class="form-group">
	                        <label for="" class="col-xs-4 control-label">标题</label>
	                        <div class="col-xs-8">
	                            <input maxlength="50" placeholder="最多50个字符" class="form-control" name="query.bxbt" value="${query.bxbt}"/>
	                        </div>
	                    </div>
	                </div>
                </div>
                
                <div class="col-xs-1">
	                    <div class="form-group">
	                          <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
	                    </div>
                </div>
                
            </div>
          </div>
           <%--<div class="row sl_aff_btn">
               <div class="col-xs-12">
                  <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
              </div>
              
          </div>--%>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:5%;">选择</th>
                    <th style="width:5%;">序号</th>
                    <%-- <th>问题描述</th>
                    <th>联系方式</th>
                    <th>问题描述</th>
                    <th>联系方式</th>
                    <th>状态</th>
                    <th>评价</th>
                    <th>评分</th>
                    <th>创建时间</th>--%>
                    <th>维修人</th>
                    <th>报修标题</th>
                    <th>报修类型</th>
                    <th>报修地址</th>
                    <th>联系方式</th>
                    <th>报修时间</th>
                    <th>评价</th>
                    
               </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${id }"/></td>
                    <td class="autocut" ><s:property value='#st.index+1'/></td>
                    <%--报修人--%>
                    <td class="autocut" >${menu.repairName }</td>
                    <%--报修标题--%>
                    <td class="autocut" >${menu.problem }</td>
                    <td class="autocut" >
						<c:if test="${menu.status eq '0' }">
							<font style="color:red;">待报修</font>
						</c:if>
						<c:if test="${menu.status eq '1' }">
							<font style="color:green;">报修中</font>
						</c:if>
						<c:if test="${menu.status eq '2' }">
							<font style="color:gray;">已报修</font>
						</c:if>
						<c:if test="${menu.status eq '3' }">
							<font style="color:gray;">已评价</font>
						</c:if>
					 </td>
					 <%--报修地址--%> 
					 <td class="autocut" >${menu.address }</td>
                     <td class="autocut" >${menu.telephone}</td>
                     <td class="autocut" ><s:date name="#menu.createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
                      <%--备注--%>
                     <td class="autocut" >${menu.evaluate}</td>
                    <%-- <td class="autocut" >${menu.score}</td>--%>
                    
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>