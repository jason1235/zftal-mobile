<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
  <div style="overflow:auto;">
  <table class="dateline tablenowrap" width="100%">
    <thead>
      <tr>
        <s:iterator value="query.clazz.viewables" var="p">
        <s:if test="fieldName != 'zp'">
        <td>${p.name }</td>
        </s:if>
        </s:iterator>
      </tr>
    </thead>
    <tbody>
      <s:iterator value="pageList" var="info">
      <tr>
        <s:iterator value="query.clazz.viewables" var="p">
        <s:if test="fieldName != 'zp'">
        <td>${info.viewHtml[p.fieldName]}</td>
        </s:if>
        </s:iterator>
      </tr>
      </s:iterator>
    </tbody>
  </table>
  </div>
  <ct:page pageList="${pageList }" function="changePage"/>
