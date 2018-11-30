<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
        <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript">
		$(function(){
			var current = null;

			//监听单击行
			$("tbody[name='line_table'] > tr").click(function(){
				var guid = $(this).attr("id");
				
				if( guid == null )
				{
					return;
				}
				
				if(current != null) {
					current.removeClass("current");
				}

				$(this).attr("class", "current");

				current = $(this);
			});
			
			
			$("#line_type").change(function(){
	            lineTypeChange();
	        });
			
	        $("#btn_zj").click(function (){
	        	addLine();
	        });

	        $("#btn_bclc").click(function (){
	        	if(false) return false;
                $.post('<%=request.getContextPath() %>/sp/spline_saveLink.html', $('#link_edit').find('input').serialize(), function(data){
                    tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
                    $("#window-sure").click(function() {
                        divClose();
                        if( data.success ) {
                            location.reload();
                        }
                    });
                }, "json");
                return false;
            });

	        initChangeType();
            updateIndex();
		
		});
		function move_down(obj){
            var tr=$(obj).closest("tr");
            var preTr=tr.nextAll("tr[class='node']:first");
            var trHtml=tr.html();
            tr.html(preTr.html());
            preTr.html(trHtml);
            updateIndex();
        }
		function move_up(obj){
			var tr=$(obj).closest("tr");
			var preTr=tr.prevAll("tr[class='node']:first");
			var trHtml=tr.html();
            tr.html(preTr.html());
            preTr.html(trHtml);
            updateIndex();
		}

		function addLine(){
			var type = $("#line_type").val();
            var check = findByType(type);
            if(check.length==0){
                 alert("请选择该环节所使用的流程节点！");
                 return false;
            }
            var subHtml="";
            for(var i=0;i<check.length;i++){
                var id = $(check[i]).val();
                var nodeName = $("#nodeInfo_"+id).val();
                 subHtml+="<span class='node'><input type='hidden' class='nodeList' value='"+$(check[i]).val()+"' />"+nodeName+"</span>";
                 if(i < check.length-1){subHtml+=", ";}
            }
            var html="<tr class='node' ><td><span class='indexText'></span></td><td>"+subHtml+"</td><td>是</td><td><input class='LineNodeDesc' value='' /></td>";
            html+="<td><a class='opt_sy' onclick='move_up(this)'>上移</a> <a class='opt_xy' onclick='move_down(this)'>下移</a> <a class='opt_sc' onclick='removeLine(this)'>删除</a> </td></tr>";
            $("#list_body_line").append(html);
            $("#list_body_line").find("tr:last").find(".LineNodeDesc").val($("#lineDesc").val());
            lineTypeChange();
            updateIndex();
		}
		function removeLine(obj){
			var tr = $(obj).closest("tr");
			var nodeList = tr.find(".nodeList");
			$("#addLine").show();
			for(var i=0;i<nodeList.length;i++){
                var id =$(nodeList[i]).val();
                var nodeName = $("#nodeInfo_"+id).val();
                var type = $("#nodeInfo_"+id).attr("nodeType");
                var html;
                if(type=="COUNTERSIGN_NODE"){
                	if($("#line_type").find("option[value='node_morecheck']").length==0){
                        $("#line_type").append("<option value='node_morecheck'>会签</option>");
                    }
                    html="<span id='checkbox_"+id+"'><input class='countErsignNode' type='checkbox' value='"+id+"'/>"
                    +nodeName+"</span>";
                    $("#node_morecheck").append(html);
                }else{
                	if($("#line_type").find("option[value='node_onecheck']").length==0){
                        $("#line_type").append("<option value='node_onecheck'>普通</option>");
                    }
                	$("#node_onecheck_select").append("<option value='"+id+"'>"+nodeName+"</option>");
                }
            }
			tr.remove();
			lineTypeChange();
			updateIndex();
	    }
		function updateIndex(){
			var nodeTRs = $("#list_body_line").find("tr");
			for(var index=0;index<nodeTRs.length;index++){
				var tr =$(nodeTRs[index]);
				tr.find(".indexText").html(index+1);
				tr.find(".nodeList").attr("name","spLinks["+index+"].nodesStr");
				tr.find(".LineNodeDesc").attr("name","spLinks["+index+"].lineDesc");
				if(index==0){
					tr.find(".opt_sy").hide();
				}else{
					tr.find(".opt_sy").show();
				}
				if(index==nodeTRs.length-1){
                    tr.find(".opt_xy").hide();
				}else{
                    tr.find(".opt_xy").show();
                }
			}
		}
		
		function lineTypeChange(){
			$("#node_onecheck").hide();
			$("#node_morecheck").hide();
			$("#"+$("#line_type").val()).show();
		}

		function findByType(type){
		    if('node_onecheck' == type){
		    	var res = $("#node_onecheck").find(":selected");
		        $(res).remove();
		        if($("#node_onecheck").find(":selected").length==0){
		        	$("#line_type").find(":selected").remove();
		        	lineTypeChange();
		        }
		        if($("#line_type").find(":selected").length==0)
		            $("#addLine").hide();
		        return res;
		    }
			else{
				var res = $("#node_morecheck").find(":checked");
				for(var i=0;i<res.length;i++){
                    var id = $(res[i]).val();
                    $("#checkbox_"+id).remove();
                }
                if($("#node_morecheck").find(".countErsignNode").length==0){
                	$("#line_type").find(":selected").remove();
                	lineTypeChange();
                }
                if($("#line_type").find(":selected").length==0)
                    $("#addLine").hide();
				return res;
			}
		}

		function initChangeType(){
			var nodeList = $(".nodeList");
            for(var i=0;i<nodeList.length;i++){
                var id =$(nodeList[i]).val();
                var nodeName = $("#nodeInfo_"+id).val();
                var type = $("#nodeInfo_"+id).attr("nodeType");
                if(type=="COUNTERSIGN_NODE"){
                    $("#checkbox_"+id).remove();
                }else{
                	var res = $("#node_onecheck").find("[value='"+id+"']");
                    $(res).remove();
                }
            }
            if($("#node_onecheck").find(":selected").length==0){
                $("#line_type").find("[value='node_onecheck']").remove();
            }
            if($("#node_morecheck").find(".countErsignNode").length==0){
                $("#line_type").find("[value='node_morecheck']").remove();
            }
            if($("#line_type").find(":selected").length==0)
                $("#addLine").hide();
			lineTypeChange();
		}
	</script>
</head>

<body>
	<div class="toolbox" style="z-index: 10;">
		<div class="buttonbox">
			<ul>
				<li><a id="btn_bclc" href="#" class="btn_ccg">保存</a></li>
			</ul>
		</div>
	</div>
	
	<div class="formbox" id="link_edit">
		<h3 class="datetitle_01">
			<span>流程走向信息（提示：双击可以查看选定行）</span>
			<input type="hidden" name="spLine.pid" value="${spProcedure.pid }"/>
		</h3>
		<table summary="" class="dateline" align="" width="100%">
			<thead id="list_head">
				<tr>
					<td></td>
					<td>节点名称</td>
					<td>是否必经</td>
<%--					<td>条件表达式</td>--%>
					<td>走向描述</td>
					<td width="12%">操作</td>
				</tr>
			</thead>
			<tbody class="line_table_edit" id="start_node">
			<c:forEach items="${startNode}" var="bean" varStatus="i">
			 <tr><td>${bean.nodeTypeStr }</td><td>${bean.nodeName }</td><td>是</td><td></td><td></td></tr>
			</c:forEach>
			</tbody>
			<tbody class="line_table_edit" id="list_body_line">
				<c:forEach items="${spLinks}" var="link" varStatus="i">
				    <tr class='node' >
				        <td><span class='indexText'></span></td>
				        <td>
				        <c:forEach items="${link.nodes}" var="n" varStatus="no">
				            <c:if test="${no.index > 0}">,</c:if>
				            <span class='node'><input type='hidden' class='nodeList' value='${n.nodeId }' />${n.nodeName }</span>
				        </c:forEach>
				        </td>
				        <td>是</td>
				        <td><input class='LineNodeDesc' value='${link.lineDesc }' /></td>
                        <td>
	                        <a class='opt_sy' onclick='move_up(this)'>上移</a> 
	                        <a class='opt_xy' onclick='move_down(this)'>下移</a> 
	                        <a class='opt_sc' onclick='removeLine(this)'>删除</a> 
	                    </td>
                     </tr>
				</c:forEach>
			</tbody>
		    <tbody class="line_table_edit" id="end_node">
             <c:forEach items="${endNode}" var="bean" varStatus="i">
             <tr><td>${bean.nodeTypeStr }</td><td>${bean.nodeName }</td><td>是</td><td></td><td></td></tr>
            </c:forEach>
            </tbody>
			<tbody class="line_table_edit" id="addLine">
				<tr id="demo_addline">
                    <td><select id="line_type">
                        <option value="node_onecheck">普通</option>
                        <option value="node_morecheck">会签</option>
                        </select>
                    </td>
                    <td>
                        <div id="node_onecheck" style="width: 200px">
                        <select id="node_onecheck_select">
                            <c:forEach items="${otherNode}" var="bean" varStatus="i">
                            <option value="${bean.nodeId }">${bean.nodeName }</option>
                            </c:forEach>
                        </select>
                        </div>
                        <div id="node_morecheck" style="width: 200px"> 
                        <c:forEach items="${countNode}" var="bean" varStatus="i">
                            <span id="checkbox_${bean.nodeId }">
                            <input class="countErsignNode" type="checkbox" value="${bean.nodeId }"/>${bean.nodeName }
                            </span>
                        </c:forEach>
                        </div>
                        <c:forEach items="${spProcedure.spNodeList}" var="bean" varStatus="i">
                            <input id="nodeInfo_${bean.nodeId }" value="${bean.nodeName }" nodeType="${bean.nodeType }" type="hidden"/>
                        </c:forEach>
                    </td>
                    <td>是</td>
<%--                    <td>${bean.expression }</td>--%>
                    <td><input id="lineDesc" /></td>
                    <td>
                      <div>
                       <div class="buttonbox">
		                <ul>
		                  <li><a id="btn_zj" href="#" class="btn_zj">增加走向</a></li>
		                </ul>
                      </div>
                    </td>
                </tr>
			</tbody>
		</table>
	</div>
</body>

</html>