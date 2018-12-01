<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
            
    	$("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
            }
    	    if(confirm("确认要删除吗?")){
	            var checked = $("input[id='id']:checked");
		        $.post(_path+"/express/express_remove.html",checked.serialize(),function(data){
	                if(data.success){
	                	var callback = function(){
							location.reload();
						};
	                	openAlert("删除成功", "s","auto", callback);
	                }else{
	                    alert(data.text);
	                }
	            },"json");
    	    }
    	 });
    	
    	$('#list_body tr').find('input').bind('click',function(e){
			e.stopPropagation();
			$(this).parent().parent().toggleClass('current');
		});      
		$('#list_body tr').bind('click',function(e){
		 var status = $(this).find('input[name="id"]').prop('checked');
		 if (status) {
			$(this).find('input[name="id"]').removeAttr('checked');
			$(this).removeClass("current");
		 } else {
		    $(this).attr("class", "current");
			$(this).find('input[name="id"]').prop('checked',true);
		 }
		});

    });
    
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="express/express_list.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">标题</label>
                        <div class="col-xs-7">
                            <input id="title" maxlength="30" placeholder="最多30个字符" class="form-control" name="query.title" value="${query.title }"/>
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
          <input name="minIndex" id="minIndex" type="hidden" value=""/>
        <input name="maxIndex" id="maxIndex" type="hidden" value=""/>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th style="width:10%;">标题</th>
                    <th style="width:10%;">文本内容</th>
                    <th style="width:12%;">发布对象</th>
                    <th style="width:15%;">发布人</th>
                    <th style="width:7%;">创建时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data pxtr">
                    <td>
                    	<input type="checkbox" id="id" name="id" value="${menu.expressId }"/>
                    	<input name="ids" type="hidden" value="${menu.expressId }"/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.title }</td>
                    <td class="autocut" >${menu.textContent}</td>
                    <td class="autocut" >
                    	<c:if test="${menu.theType eq '0' }"><span id="tdstatus" style="color:green">帅锅</span></c:if>
	                    <c:if test="${menu.theType eq '1' }"><span id="tdstatus" style="color:red">小仙女</span></c:if>
                    </td>
                    <td class="autocut discription">${menu.xm}</td>
                    <td class="autocut" ><fmt:formatDate value="${menu.createTime }" pattern="yyyy-MM-dd" /></td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>