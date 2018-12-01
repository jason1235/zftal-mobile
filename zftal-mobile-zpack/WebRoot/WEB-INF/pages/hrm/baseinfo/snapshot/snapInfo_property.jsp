<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
  <div class="mes_list_con">
    <ul>
    <s:iterator value="configList" var="infoPropertyView">
    <li>${infoPropertyView.propertyName}
      <s:if test="allow">
      <input type="checkbox" name="propertyId" onclick="saveProperty(this);" checked="checked" value="${infoPropertyView.propertyId}"/>
      </s:if>
      <s:else>
      <input type="checkbox" name="propertyId" onclick="saveProperty(this);" value="${infoPropertyView.propertyId}"/>
      </s:else>
    </li>
    </s:iterator>
    </ul>
  </div>
  <div class="clear"></div>
