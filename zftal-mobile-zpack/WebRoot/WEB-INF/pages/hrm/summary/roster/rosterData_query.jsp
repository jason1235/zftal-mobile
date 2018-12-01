<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
.tab_szcd { display:block;float:none;}
</style>
<div style="width:100%;overflow-x:auto;overflow-y:hidden;_padding-bottom:20px;">
  <table width="100%" class="dateline"  style="white-space:nowrap">
    <thead id="list_head">
      <tr>
        <td style="white-space:nowrap">序 号</td>
        <c:forEach items="${columnCollection }" var="column" varStatus="vs">
        <td style="white-space:nowrap">
        <c:if test="${vs.index<=1 }">
        ${column.name }
        </c:if>
        <c:if test="${vs.index>1 }">
        <div class="tab_szcd">
        	<a href="#" class="ico_sz Sort_down">${column.name }</a>
        	<div class="btn_list" id="menu" style="display:none;height:auto">
              <ul>
                <li><a href="#" class="btn_zy">左移</a></li>
                <li><a href="#" class="btn_yy">右移</a></li>
                <li><a href="#" class="btn_sc">删除</a></li>
              </ul>
            </div>
	        <input type="hidden" value="${column.columnId }"/>
        </div>
        </c:if>
        </td>
        </c:forEach>
      </tr>
    </thead>
    <tbody id="list_body">
      <c:forEach items="${pageList }" var="map" >
      <tr>
      	<input type="hidden" gh="gh" value="${map['GH'] }"/>
        <td style="white-space:nowrap">${map['RN'] }</td>
        <c:forEach items="${keySet }" var="key">
        <td style="white-space:nowrap">${map[key] }</td>
        </c:forEach>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
<ct:page pageList="${pageList }" function="pageCall"/>