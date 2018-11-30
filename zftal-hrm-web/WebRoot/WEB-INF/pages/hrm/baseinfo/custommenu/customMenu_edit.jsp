<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<script type="text/javascript">
$(function(){
	var id = $("#form1 input[name='custom.menuId']").val();//判断增加还是修改
	if(id==""){
		$("#action").click(function(){
			addEntity();
			});
	}else{
		$("#action").click(function(){
			updateEntity();
			});
	}
	$("#ul input").change(function(){
		var value = $(this).val();
		var i = $(this).attr("seq");
		if($(this).is(":checked")){
			loadCodeSelect(value,i);
		}else{
			$("#td_" + value).empty();
			$("#td_" + value).append('<input type="hidden" value="" name="custom.conditions[' + i + '].value" />');
			$("#th_" + value).css("display","none");
			$("#td_" + value).css("display","none");
		}
	});
});

function addEntity(){//增加
	if(!validate()){
		return false;
	}
	$.post('baseinfo/customMenu_save.html',$("#form1 input").serialize(),function(data){
		var callback = function(){
			window.location.reload();
		};
		processDataCall(data, callback);
	},"json");
}

function updateEntity(){//修改
	if(!validate()){
		return false;
	}
	var param = $("#form1 input").serialize();
	$.post('baseinfo/customMenu_update.html',param,function(data){
		var callback = function(){
			window.location.reload();
		};
		processDataCall(data, callback);
	},"json");
}

</script>
</head>
<body>
<div id="testID" >    
  <div class="tab">
	<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="2">
					<span>分类查询定义维护<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<input type="hidden" name="custom.menuId" value="${custom.menuId }"/>
			<input type="hidden" name="custom.type" value="${custom.type }"/>
			<tr>
				<th width="20%"><span class="red">*</span>分类名称</th>
				<td width="80%"><input type="text" name="custom.name" class="text_nor" style="width:180px" value="${custom.name }"/></td>
			</tr>
			<tr>
				<th width="20%">字段</th>
				<td width="80%">
			        <ul id="ul">
						<c:forEach items="${propertyList }" var="property" varStatus="i">
							<input type="checkbox" value="${property.guid }" seq="${i.index }" name="custom.conditions[${i.index }].name" 
							<c:forEach items="${custom.conditions }" var="condition">
								<c:if test="${property.guid == condition.name}">
									checked="checked"
								</c:if>
							</c:forEach>
							><span>${property.name }</span></input>
						</c:forEach>
			        </ul>
				</td>
			</tr>
			<c:forEach items="${propertyList }" var="property" varStatus="j">
				<c:set var="trVar" value="false"></c:set>
				<c:forEach items="${custom.conditions }" var="condition">
					<c:if test="${property.guid == condition.name}">
						<tr>
							<th id="th_${property.guid }"><span class="red">*</span>${property.name }</th>
							<td id="td_${property.guid }">
							<c:if test="${custom.menuId != null}">
								<c:set var="trVar" value="true"></c:set>
								<ct:codePicker name="custom.conditions[${j.index }].value" catalog="${condition.codeId}" code="${condition.value }"/>
							</c:if>
					</c:if>
				</c:forEach>
				<c:if test="${!trVar }">
					<tr >
						<th style="display: none;" id="th_${property.guid }"><span class="red">*</span>${property.name }</th>
						<td style="display: none;" id="td_${property.guid }">
							<input type="hidden" value="" name="custom.conditions[${j.index }].value" />
				</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
    <tfoot>
      <tr>
        <td colspan="2">
            <div class="bz">"<span class="red">*</span>"为必填项</div>
          	<div class="btn">
            	<button id="action" name="action" >保 存</button>
            	<button name="cancel" onclick='divClose();'>取 消</button>
          	</div>
        </td>
      </tr>
	</tfoot>
	</table>
	</div>
</div>
</body>
</html>
