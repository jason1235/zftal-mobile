<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/hrm/head_v5.ini" %>
	</head>
	<body>
	<div class="container_ydht sl_all_bg">
	    <div class="toolbox"></div>
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    
	    <form id="form_list" action="<%=request.getContextPath()%>/sourcegoods/sourcesignin_list.html" method="post">
	    <div class="form-horizontal sl_all_form">
            <input type="hidden" id="asc" name="asc" value=""/>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">工号:</label>
                        <div class="col-xs-7">
                            <s:textfield maxlength="100"  name="query.userid" cssClass="form-control" theme="simple"></s:textfield>
                        </div>
                    </div>
                </div> 
        </div>
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
            </div>
        </div>
        
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                   <th style="width:5%;">序号</th>
                    <th style="width:10%;">工号</th>
                    <th style="width:10%;">姓名</th>
                    <th style="width:10%;">签到积分</th>
                    <th style="width:11%;">签到时间</th>
                    <th style="width:10%;">签到终端</th>
                </tr>
            </thead>
            <tbody id="list_body" >
	            <s:iterator value="pageList" var="p" status="st">
	            <tr>
	                <td width="5%">${st.index+1}</td>
	                 <td width="10%">${p.userid}</td>
	                 <td width="10%">${p.username }</td>
	                 <td width="10%">${p.source}</td>
	                 <td width="10%"><s:date name="#p.createtime" format="yyyy-MM-dd" /></td>  
	                 <td width="10%">${p.ast }<!--${p.appsource eq '1'?"移动端签到":(p.appsource eq '2'?"web端签到":"其他设备")}</td>-->
	                 </tr>
	             </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${pageList}" query="${query}" />  
	    </form>
	</div>
	</body>
</html>
