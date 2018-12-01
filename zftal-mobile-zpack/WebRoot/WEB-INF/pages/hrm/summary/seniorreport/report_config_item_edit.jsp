<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>

	<script type="text/javascript">
	$(function(){
		
		$("#save").click(function(){
			if($("#name").val()==null||$("#name").val()==""){
				showWarning("名称不能为空，请重新输入！");
				return false;
			}
			if($("#condition").val()==null||$("#condition").val()==""){
				showWarning("条件不能为空，请重新输入！");
				return false;
			}
			
			$.post("<%=request.getContextPath() %>/summary/seniorreportconfig_save_${itemType}.html",$("form:last").serialize(),function(data){
				var callback = function(){
					$("form:first").submit();
				};
				processDataCall(data,callback);
			},"json");

			return false;
		});
		$("cancel").click(function(){
			divClose();
			return false;
		});
		$("#viewType").change(function(){
		    show();
		    return false;
		});
		$("#viewType").val("${item.viewType}");
		show();
	});

	function show(){
		var value = $("#viewType").val();
		$("#sql").hide();
		$("#value").hide();
		$("#dataSql").hide();
        $("#joinSql").hide();
		if(value == 'COUNT_TYPE'){
			$("#sql").show();
	    }else if(value == 'VALUE_TYPE'){
	    	$("#value").show();
		}else if(value == 'DATA_TYPE'){
            $("#sql").show();
            $("#dataSql").show();
            $("#joinSql").show();
        }
    }
	</script>
</head>

<body>
	<form>
		<input type="hidden" name="id" value="${id }" />
		<input type="hidden" name="item.itemId" value="${item.itemId }" />
		<input type="hidden" name="pid" value="${pid }" />
		<div class="tab">
			<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th colspan="2">
							<span>报表维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
						</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="2">
							<div class="bz">"<span class="red">*</span>"为必填项</div>
							<div class="btn">
								<button id="save">保 存</button>
								<button id="cancel">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<tr>
						<th><span class="red">*</span>名称</th>
						<td>
							<input type="text" class="text_nor" id="name" name="item.name" size="25" value="${item.name }" />
						</td>
					</tr>
					<tr>
                        <th><span class="red">*</span>类型</th>
                        <td>
                            <select name="item.viewType" id="viewType">
                                <c:forEach items="${viewTypes}" var="v">
                                    <option value="${v.key }">${v.text }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr id='dataSql'>
                        <th><span class="red">*</span>数据语句</th>
                        <td>
                            <textarea style="width:180px;height:60px" id="dataSql" name="item.dataSql" size="25">${item.dataSql }</textarea> 
                        </td>
                    </tr>
                    <tr id='joinSql'>
                        <th><span class="red">*</span>关联语句</th>
                        <td>
                            <input type="text" class="text_nor" id="joinSql" name="item.joinSql" size="25" value="${item.joinSql }" /> 
                        </td>
                    </tr>
					<tr id="sql">
						<th><span class="red">*</span>条件语句</th>
						<td>
							<textarea style="width:180px;height:60px" id="condition" name="item.sql" size="25">${item.sql }</textarea> 
						</td>
					</tr>
					<tr id="value">
                        <th><span class="red">*</span>默认值</th>
                        <td>
                            <textarea style="width:180px;height:60px" id="condition" name="item.defultValue" size="25">${item.defultValue }</textarea> 
                        </td>
                    </tr>
				</tbody>
			</table>
		</div>
	</form>
</body>
</html>