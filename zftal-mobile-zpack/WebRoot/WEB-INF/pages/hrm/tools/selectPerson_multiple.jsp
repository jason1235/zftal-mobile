<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib uri="/WEB-INF/infoclasstag.tld" prefix="clazz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
	
	<script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	<script type="text/javascript" src="<%=stylePath %>/js/lockTableTitle.js"></script>
	
	<script type="text/javascript">
		window.returnValue = null;

		$(function(){
			//监听【查询】按钮点击事件
			$("#btn_cx").click( function() {
				$("form").submit();
			});

			//监听【取消】按钮点击事件
			$("#btn_qx").click( function() {
				window.close();
			});

			//监听【确定】按钮点击事件
			$("#btn_qd").click( function() {
				var $checks = $("input[name='ghList']");

				var values = "";
				var titles = "";

				for( var i = 0; i < $checks.length; i++ ) {
					$check = $checks.get(i);
					values += $check.value + ";";
					titles += $("#selected_"+$check.value).find("h5").html() + ";";
				}

				window.returnValue = [values, titles];
				if(window.opener){
				    window.opener.returnValue = [values, titles];
				}
				window.close();
			});
			
			//监听双击行
			$("#list_body tr").dblclick( function() {
				var $checks = $("input[name='ghList']");

                var values = "";
                var titles = "";

                for( var i = 0; i < $checks.length; i++ ) {
                    $check = $checks.get(i);
                    values += $check.value + ";";
                    titles += $("#selected_"+$check.value).find("h5").html() + ";";
                }

				window.returnValue = [values, titles];
				if(window.opener){
				    window.opener.returnValue = [values, titles];
				}
				window.close();
			});

			$("#list_body tr").click(function(){
				var cbx = $(this).find("input[name='cbox']");
				if (!$(cbx).attr("checked")  ) {
                    $(this).attr("class", "current");
                    var name = $(this).find("input[name='name']").val()
                    $(cbx).attr("checked", true);
                    addItem($(cbx).val(),name)
                } else {
                    removeItem($(cbx).val());
                }
			});
			//监听复选框
			$("input[name='cbox']").click( function() {
				var $tr = $(this).closest("tr");
				
				if (this.checked ) {
					$tr.attr("class", "current");
					var name = $( this).closest("tr").find("input[name='name']").val()
					addItem($(this).val(),name)
				} else {
					removeItem($(this).val());
				}
				
			});

			//监听全选复选框
			$("#checkAll").click( function() {
				if ( $(this).attr("checked") ) {
					$("#MyTable_tableData").find("input[name='cbox']").each(function(){
						var $tr = $(this).closest("tr");
		                $tr.attr("class", "current");
		                var name = $( this).closest("tr").find("input[name='name']").val()
		                addItem($(this).val(),name);
		                $(this).attr("checked", true);
					});
				} else {
					$("#MyTable_tableData").find("input[name='cbox']").each(function(){
						removeItem($(this).val());
					});
				}
				
			});
			FixTable("MyTable", 0, 840, 260); 
			$("input[name='ghList']").each(function(){
                var tr = $("#MyTable_tableData").find("tr[pk=\"pk_"+$(this).val()+"\"]");
               $(tr).find("input[name='cbox']").attr("checked", true);
               $(tr).attr("class", "current");
           });
		});

		function addItem(value, title){
			if($("a[id=\"selected_"+value+"\"]").length>0){
				return;
			}
			var content="<dd> <a id=\"selected_"+value+"\" href=\"#\"><h5>"+title+"</h5>("+value
            +")<span class=\"close-icon\" title=\"取消\"></span></a> "
            +"<input type=\"hidden\" name=\"ghList\" value=\""+value+"\"></dd>";
	        content = $(content);
	        $(content).appendTo($(".selected-attr dl"));
	        $(content).find(".close-icon").click(function(){
	        	removeItem(value);
	        });
		}
		function removeItem(value){
			var currentTarget=$("#selected_"+value);
            $(currentTarget).closest("dd").remove();
            var tr = $("#MyTable_tableData").
                 find("tr[pk=\"pk_"+value+"\"]");
            $(tr).find("input[name='cbox']").attr("checked", false);
            $(tr).removeClass("current");
        }
	</script>
</head>
  
<body>
	<form action="<%=request.getContextPath() %>/tools/selectPerson_multiple.html?type=${type}" method="post">
	  <div class="search_advanced" id="myTbody3">  
	    <div class="selected-attr after" style="float:none;min-height:30px;_height:30px">
                <h3>已选人员:</h3>
                <dl><c:forEach items="${ghList}" var="gh">
                            <dd><a id="selected_${gh }" href="#">
                            <h5>${titleMap[gh] }</h5>
                            (${gh} )
                            <span class="close-icon" title="取消" onclick="removeItem('${gh}')"></span></a>
                            <input type="hidden" name="ghList" value="${gh }"> </dd>
                    </c:forEach>
                </dl>
            </div>
        </div>
		<div class="searchtab">
			<table width="100%">
				<tfoot>
					<tr>
						<td colspan="6">
							<div class="bz">
								<label>
								</label>
							</div>
							<div class="btn">
								<button id="btn_cx" class="button" type="button" name="查询">查 询</button>
								<button id="btn_qd" class="button" type="button" name="确定">确 定</button>
								<button id="btn_qx" class="button" type="button" name="取消">取 消</button>
							</div>
						</td>
					</tr>
				</tfoot>
				
				<tbody>
					<tr>
						<c:forEach items="${query.config.conditions}" var="condition" varStatus="i">
						<th>${condition.title }</th>
						<td><clazz:search type="${query.config.type.name}" condition="${condition}" value="${query.values[condition.name]}" /></td>
						<c:if test="${( i.index + 1 ) % 3 == 0 }">
					</tr>
					<tr>
						</c:if>
						</c:forEach>
						<c:forEach begin="1" end="${3-(fn:length(query.config.conditions))%3 }" step="1">
						<th>&nbsp;</th>
						<td>&nbsp;</td>
						</c:forEach>
					</tr>
					
				</tbody>
			</table>
		</div>
	
	
	<div id="lockTable" style="height:200px;">
		<h3 class="datetitle_01">
			<span>人员信息<font color="red" style="font-weight:normal;">（提示：双击一行可以选定）</font></span>
		</h3>
		<table id="MyTable" cellpadding="0" cellspacing="0" class="dateline nowrap" style="width: 823px;">
			<thead id="list_head">
				<tr>
					<td>
						<input type="checkbox" id="checkAll"/>
					</td>
					<c:forEach items="${query.config.propertyInfos}" var="property">
					<td>${property.name }</td>
					</c:forEach>
				</tr>
			</thead>
			<tbody id="list_body">
				<c:forEach items="${beans}" var="bean" varStatus="i">
				<tr pk="pk_${bean.viewHtml[query.config.type.primaryFileName] }">
					<td>
						<input type="checkbox" name="cbox" value="${bean.viewHtml[query.config.type.primaryFileName] }" />
						<input type="hidden" name="name" value="${bean.viewHtml['xm'] }" />
					</td>
					<c:forEach items="${query.config.properties}" var="pName">
					<td>${bean.viewHtml[pName] }</td>
					</c:forEach>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	<ct:page pageList="${beans }" />
	</div>
	</form>
	<script type="text/javascript">
		fillRows(11, '', '', false);
	</script>
</body>
</html>
