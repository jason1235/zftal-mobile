<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
	</head>
	<body>
	    <div class="tab">
			<table class="formlist">
			    <thead>
			    	<tr>
			        	<th colspan="8"><span>${model.name } 表结构信息<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
			        </tr>
			    </thead>
			    <tbody>
			      <tr>
					<th width="7%">序号</th>
					<th>字段名</th>
					<th>注释</th>
					<th>数据类型</th>
					<th>数据长度</th>
					<th>是否为空</th>
					<th>默认值</th>
				  </tr>
				  <c:forEach items="${list}" var="field" varStatus="st">
				      <tr>
				      	<td>${(st.index+1)}</td>
						<td>${field.fieldName }</td>
						<td>${field.fieldChineseName}</td>
						<td>${field.fieldtype }</td>
						<td>${field.fieldLength }</td>
						<td>${field.vacant.text }</td>
						<td>${field.fieldDefalutValue }</td>
				      </tr>
			      </c:forEach>
			    </tbody>
		    </table>
		    <br />
		    <table class="formlist">
			    <thead>
			    	<tr>
			        	<th colspan="8"><span>${model.name } 主外键信息<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
			        </tr>
			    </thead>
			    <tbody>
			      <tr>
					<th width="7%">序号</th>
					<th>名称</th>
					<th>类型</th>
					<th>字段名</th>
				  </tr>
				  <c:forEach items="${keyList}" var="key" varStatus="st">
				      <tr>
				      	<td>${(st.index+1)}</td>
						<td>${key.keyName }</td>
						<td>${key.keyType }</td>
						<td>${key.columnName }</td>
				      </tr>
			      </c:forEach>
			    </tbody>
		    </table>
		    <br />
		    <table class="formlist">
			    <thead>
			    	<tr>
			        	<th colspan="8"><span>${model.name } 主外键关联关系<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
			        </tr>
			    </thead>
			    <tbody>
			      <tr>
					<th width="7%">序号</th>
					<th>主键名称</th>
					<th>主键表名</th>
					<th>主键字段名</th>
					<th>外键名称</th>
					<th>外键表名</th>
					<th>外键字段名</th>
				  </tr>
				  <c:forEach items="${relationList}" var="pf" varStatus="st">
				      <tr>
				      	<td>${(st.index+1)}</td>
						<td>${pf.pk_constraint_name }</td>
						<td>${pf.pk_table_name }</td>
						<td>${pf.pk_column_name }</td>
						<td>${pf.fk_constraint_name }</td>
						<td>${pf.fk_table_name }</td>
						<td>${pf.fk_column_name }</td>
				      </tr>
			      </c:forEach>
			    </tbody>
		    </table>
		    <br />
		    <table class="formlist">
			    <thead>
			    	<tr>
			        	<th colspan="8"><span>${model.name } 索引信息<font color="#0f5dc2" style="font-weight:normal;"></font></span></th>
			        </tr>
			    </thead>
			    <tbody>
			      <tr>
					<th width="7%">序号</th>
					<th>名称</th>
					<th>类型</th>
					<th>字段名</th>
				  </tr>
				  <c:forEach items="${indexList}" var="index" varStatus="st">
				      <tr>
				      	<td>${(st.index+1)}</td>
						<td>${index.indexName }</td>
						<td>${index.indexType }</td>
						<td>${index.columnName }</td>
				      </tr>
			      </c:forEach>
			    </tbody>
			    <tfoot>
			      <tr>
			        <td colspan="8">
			        	<div class="btn">
				            <button name="cancel" onclick='divClose();'>关 闭</button>
				        </div>
			        </td>
			      </tr>
			    </tfoot>
		    </table>
	    </div>
	</body>
</html>
