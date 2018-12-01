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
    
    
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath() %>/serviceManager/question_answerList.html" name="search" id="form_edit" method="post">
          <div class="form-horizontal sl_all_form">
          <div class="row">
            	<div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label"> 问卷名称</label>
                        <div class="col-xs-7">
                            ${query.papermainname }
                            <input type="hidden" name="examAnwserQuery.papermainid" value="${examAnwserQuery.papermainid }"/>
                        </div>
                    </div>
                </div>
          </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">用户id</label>
                        <div class="col-xs-7">
                            <input maxlength="20" placeholder="最多20个字符" class="form-control" name="examAnwserQuery.yhid" value="${examAnwserQuery.yhid }"/>
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
                    <th style="width:5%;">序号</th>
                    <th >用户id</th>
                    <th>姓名</th>
                    <th>题目标题</th>
                    <th>题型</th>
                    <th >答案</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="examAnwserList" var="menu" status="st">
                <tr name="tr" class="data">
                    <td class="autocut" ><s:property value='#st.index+1'/></td>
                    <td class="autocut" >${menu.yhid }</td>
                    <td class="autocut" >${menu.xm }</td>
                    <td class="autocut" >${menu.title}</td>
                    <td class="autocut" >
						<c:if test="${menu.type eq '0' }">单选</c:if>
						<c:if test="${menu.type eq '1' }">多选</c:if>
						<c:if test="${menu.type eq '2' }">简答</c:if>
						<c:if test="${menu.type eq '3' }">打分</c:if>
					</td>
                    <td class="autocut endtr" >
                    	<c:if test="${menu.type eq '0' }">选择为${menu.itemvalue }项</c:if>
						<c:if test="${menu.type eq '1' }">选择为${menu.itemvalue }项</c:if>
						<c:if test="${menu.type eq '2' }">${menu.itemvalue }</c:if>
						<c:if test="${menu.type eq '3' }">打分为${menu.itemvalue }</c:if>
                    </td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${examAnwserList }" query="${examAnwserQuery }" queryName="examAnwserQuery"/>
      </form>
  </div>
	</body>
</html>