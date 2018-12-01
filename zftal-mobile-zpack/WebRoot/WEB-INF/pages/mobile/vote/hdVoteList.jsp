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
    	
     $("#btn_zj").click(function(){//功能条增加按钮
    	 var url = _path+"/vote/vote_toAdd.html";
    	 $("#myModal_toji").find(".modal-content").width(800);
         $("#myModal_toji").find(".modal-content").height(600);
         $("#myFrame").height(600);
         $("#myFrame").attr("src", url);
         $("#myModal_toji").modal("show");
     });
     
     $("#btn_ck").click(function(){//功能条修改按钮
    	 if($("input[name='id']:checked").length==0){
              openAlert("请先选中操作行！", "e");
              return false;
          }
      	 if($("input[name='id']:checked").length>1){
              openAlert("不能选中多行！", "e");
              return false;
          }	
      	 var voteStatus = $("input[name='id']:checked").parent("td").siblings("td[name='status']").text().trim();
      	 if(voteStatus=='已结束'){
            openAlert("已结束投票不能进行修改！", "e");
            return false;
         }	
      	 
      	 var voteId = $("input[name='id']:checked").val();
    	 var url = _path+"/vote/vote_toUpdate.html?voteId="+voteId;
    	 $("#myModal_toji").find(".modal-content").width(800);
         $("#myModal_toji").find(".modal-content").height(600);
         $("#myFrame").height(600);
         $("#myFrame").attr("src", url);
         $("#myModal_toji").modal("show");
     });
     
     $("#btn_anwser").click(function(){//功能条投票结果按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
    	 var voteId = $("input[name='id']:checked").val();
    	 window.open(_path+"/vote/vote_toResult.html?voteId="+voteId);  
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
    	 var voteId = $("input[name='id']:checked").val();
    	 $.post("<%=request.getContextPath() %>/vote/vote_hdVoteRemove.html",{"voteId":voteId}, function(data){
				if(data.msg=='success'){
				    window.location.href="<%=request.getContextPath() %>/vote/vote_hdVoteList.html";
				}else{
					openAlert("删除失败！", "e");
				}
			}, "json");
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
              <button type="button" class="btn btn-default" id="btn_zj">增加</button>
              <button type="button" class="btn btn-default" id="btn_ck">修改</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <%--<button type="button" class="btn btn-default" id="btn_sh">审核</button>
              <button type="button" class="btn btn-default" id="btn_ty">停用</button>--%>
              <button type="button" class="btn btn-default" id="btn_anwser">查看投票结果</button>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath() %>/vote/vote_hdVoteList.html" name="search" id="form_edit" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">投票标题</label>
                        <div class="col-xs-7">
                            <input id="Papermainname" maxlength="50" placeholder="最多50个字符" class="form-control" name="query.voteTitle" value=""/>
                        </div>
                    </div>
                </div>
                
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">投票类型</label>
                        <div class="col-xs-7">
                            <select  name="query.voteType" class="form-control" value="" >
                                <option value="0" >文字</option>
                                <option value="1" >打分</option>
                            </select>
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
                    <th style="width:5%;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th>投票标题</th>
                    <th>投票描述</th>
                    <th>投票类型</th>
                    <th>发起人</th>
                    <th>结果仅发起人可见</th>
                    <th>是否草稿</th>
                    <th style="width:15%;">创建时间</th>
                    <th style="width:15%;">截止时间</th>
                    <th>当前状态</th>
               </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${voteId }"/></td>
                    <td class="autocut" ><s:property value='#st.index+1'/></td>
                    <td class="autocut" >${menu.voteTitle }</td>
                    <td class="autocut" >${menu.voteDescription}</td>
                    <td class="autocut" >
						<c:if test="${menu.voteType eq '0' }">
							<font>文字</font>
						</c:if>
						<c:if test="${menu.voteType eq '1' }">
							<font>打分</font>
						</c:if>
					 </td>
                     <td class="autocut" >${menu.voteOrganiserId}</td>
                     <td class="autocut" >
                        <c:if test="${menu.voteResultOnlySee eq '0' }">
							<font>否</font>
						</c:if>
						<c:if test="${menu.voteResultOnlySee eq '1' }">
							<font>是</font>
						</c:if>
                      </td>
                    <td class="autocut" >
						<c:if test="${menu.voteIsDraft eq '0' }">
							<font>否</font>
						</c:if>
						<c:if test="${menu.voteIsDraft eq '1' }">
							<font>是</font>
						</c:if>
					</td>
					<td class="autocut" ><s:date name="#menu.voteStartDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                    <td class="autocut" ><s:date name="#menu.voteEndDate" format="yyyy-MM-dd HH:mm:ss" /></td>
                    <td name='status' class="autocut" >
                        <c:if test="${menu.status eq '已结束' }">
							<font style="color:red;">已结束</font>
						</c:if>
						<c:if test="${menu.status eq '进行中' }">
							<font style="color:green;">进行中</font>
						</c:if>
                    </td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>