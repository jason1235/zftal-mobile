<%@ page language="java" import="java.util.*,com.zfsoft.common.factory.SessionFactory" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        	<div class="index_zjsy show-grid-15">
            <c:set var="yhm" scope="session" value="<%=SessionFactory.getUser().getYhm()%>"/>
            <h3>
            <span>常用入口</span>
            </h3>
	            <c:if test="${yhm != 'hrmadmin'}">
	            	<b><font color="red" size="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;没有常用入口权限</font></b>
	            </c:if>
            	<c:if test="${yhm == 'hrmadmin'}">
					<ul class="list-unstyled" id="region">
						<li class="col-md-6 col-sm-6 col-xs-6">
							<a id="usual_N900102" href="#"><img src="/zfstyle_v5/images/ico/ico_but1.png" alt="资讯管理"><h5>资讯管理</h5></a>
						</li>
						<li class="col-md-6 col-sm-6 col-xs-6">
							<a id="usual_N910101" href="#"><img src="/zfstyle_v5/images/ico/ico_but2.png" alt="服务管理"><h5>服务管理</h5></a>
						</li>
						<li class="col-md-6 col-sm-6 col-xs-6">
							<a id="usual_N929902" href="#"><img src="/zfstyle_v5/images/ico/ico_but3.png" alt="信息类维护"><h5>信息类维护</h5></a>
						</li>
						<li class="col-md-6 col-sm-6 col-xs-6">
							<a id="usual_N010201" href="#"><img src="/zfstyle_v5/images/ico/ico_but4.png" alt="系统管理"><h5>系统管理</h5></a>
						</li>
					</ul>
				</c:if>
						
  			</div>
