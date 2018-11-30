<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<script type="text/javascript">
		$(function(){
			var current = null;

			// 行单击选定事件
			$("#list_body tr").click( function(){
				if(current != null) {
					current.removeClass("current");
				}
				$(this).attr("class", "current");
			});
			initSaveEvent();
		});
		
		function initSaveEvent(){
			$("#windown-content").unbind("ajaxStart");
			$("#save","#configList").click(function(){
				//var checked = $("input[name='propertyId']:checked");
				//var unchecked = $("input[name='propertyId']:not(:checked)");
				//$(checked).removeAttr("checked");
				//$(unchecked).attr("checked","checked");
				var param = $("input[name='classId'],input[name='propertyId']:checked","#configList").serialize();
				//$(checked).attr("checked","checked");
				//$(unchecked).removeAttr("checked");
				$.post(_path+'/infoclass/infoPropertyView_save.html',param,function(data){
					var callback = function(){
						window.location.href = location.href;
					};
					processDataCall(data, callback);
				},"json");
			});
		}
    </script>
</head>

<body>
		<div class="tab" id="configList">
			<table width="100%" class="formlist" id="tiptab" >
				<thead id="list_head">
					<tr>
					<td>序号</td>
					<td>列名</td>
					<td>显示</td>
					<input type="hidden" name="classId" value="${classId }"/>
					</tr>
				</thead>
				<tbody id="list_body">
				<s:iterator value="configList" var="c" status="st">
				<tr name="tr">
					<td>${st.count}</td>
					<td>${c.propertyName}</td>
					<td>
						<s:if test="allow"><input name="propertyId" type="checkbox" checked="checked" value="${c.propertyId }"/></s:if>
						<s:else><input name="propertyId" type="checkbox" value="${c.propertyId }"/></s:else>
					</td>
				</tr>
				</s:iterator>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="3">
							<div class="btn">
								<button id="save">保 存</button>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
</body>
</html>
