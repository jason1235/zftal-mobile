<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/tip/tip.js");
    $(function(){
			$("#btn_xg").click(function(){//
				modifyDeclare();
			});
			$("#btn_pltj").click(function(){
				var checked=$("input[id='id']:checked");
                if(checked.length==0){
                    alert("请选择行");
                    return false;
                }
                var ids = "id=";
                for(var i=0;i<checked.length;i++){
                    ids += $(checked[i]).val()+",";
                }
                $.post(_path+"/infochange/infochange_commitBatch.html",ids,function(data){
                    alert(data.text);
                    $("#war_sure").click(function(){
                        alertDivClose();
                        $("#search").submit();
                    });
                },"json");
			});
			$("#btn_sc").click(function(){//
				cancelDeclare();
			});
			$("#btn_ck").click(function(){//
				viewDeclare();
			});
			$("#btn_sx").click(function(){//
				reDeclare();
			});
			$("#btn_ck2").click(function(){//
				var id = $("input[id='id']:checked").val();
				showWindow("查看",_path+"/sp/spworkflow_detail.html?workId="+id,720,400);
			});

			var current = null;

			$("input:checkbox").click(function(e){
				e.stopPropagation();
				if($(this).is(":checked")){
					$(this).closest("tr").click();
				}else{
					$(this).closest("tr").removeClass("current");
					refreshButton();
				}
			});
			$("tbody > tr[name^='tr']").click(
				function(){	//监听单击行
					//<c:if test="${query.status != null && query.status != 'INITAIL'}">
					$("input:checkbox").removeAttr("checked");
					$("tbody > tr[name^='tr']").removeClass("current");
					//</c:if>
					$(this).attr("class", "current");
					$(this).find("input:checkbox").attr("checked","checked");
					current = $(this);
					refreshButton();
				}
			);
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
			//alert(11);
				$("#btn_ck").click();
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/infochange/infochange_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			function refreshButton(){
				if($("input[id='id']:checked").length>0){
					$("div.buttonbox li").hide();
					$("#btn_pltj").parent().show();
				}
				if($("input[id='id']:checked").length==0){
                    $("div.buttonbox li").show();
                }
	    		var status = $($("input[id='id']:checked").closest("tr")).find("#status").val();
	    		
	    		$("div.buttonbox li").hide();
	    		if(status == "" || status == "INITAIL" ){
	    			$("#btn_zj").parent().show();
	    			$("#btn_xg").parent().show();
	    		}else{
		    		$("#btn_ck").parent().show();
		    		$("#btn_ck2").parent().show();
	    		}
	    		if(status == "NO_PASS_AUDITING" || status == "" || status == "INITAIL" || status == "WAIT_AUDITING"){
	    			$("#btn_sc").parent().show();
	    		}
	    		if(status == "NO_PASS_AUDITING"){
	    			$("#btn_sx").parent().show();
	    		}
	    	}
			
			$("select[name='query.status']").val('${query.status}');
			
			$("tr[name=tr]").mouseover(function(){
				var id=$(this).attr("id");
				var tr=$(this);
				var classId=$(this).attr("classId");
			/*	$.post(_path+"/infochange/infochange_getChangeString.html","query.classId="+classId+"&query.id="+id,
						function(data){
					tip(tr,data.message);
				},"json");
				*/
				$.ajax({type:"post",
					url:"<%=request.getContextPath()%>/infochange/infochange_getChangeString.html?query.classId="+classId+"&query.id="+id,
					success:function(data){
						tip(tr,data.message);
					},
					datatType:"json",
					global:false
				});
			});
			
			var page=$("input[name=query\\.showCount]").val();
			fillRows(page, "", "", false);//填充空行
			
	    	function createDeclare(){
	    		var classId=$(current).attr("classId");
	    		$.post(_path+"/infochange/infochange_create.html?query.classId="+classId,"",function(data){
	    			if(data.success){
	    				location.href=_path+"/infochange/infochange_detail.html?query.classId="+classId+"&infoChange.id="+data.infoChange.id;
	    			}else{
	    				alert(data.text);
	    			}
	    		},"json");
	    	}
	    	
	    	function modifyDeclare(){
	    		if($("input[id='id']:checked").length==0){
	    			alert("请先选中操作行");
	    			return false;
	    		}
	    		var id = $("input[id='id']:checked").val();
	    		var classId= $(current).attr("classId");
	    		$.post(_path+"/infochange/infochange_modify.html?query.classId="+classId,"infoChange.id="+id,
	    				function(data){
	    			if(data.success){
	    				location.href=_path+"/infochange/infochange_detail.html?query.classId="+classId+"&infoChange.id="+data.infoChange.id;
	    			}else{
	    				alert(data.message);
	    			}
	    		},"json");
	    	}
	    	
	    	function cancelDeclare(){
	    		if($("input[id='id']:checked").length==0){
	    			alert("请先选中操作行");
	    			return false;
	    		}
	    		var classId= $(current).attr("classId");
	    		var id = $("input[id='id']:checked").val();
	    		$.post(_path+"/infochange/infochange_cancel.html?query.classId="+classId,"infoChange.id="+id,function(data){
	    			if(data.success){
	    				$("#search").attr("action","<%=request.getContextPath()%>/infochange/infochange_page.html");
	    				$("#search").attr("method","post");
	    				$("#search").submit();
	    			}else{
	    				alert(data.message);
	    			}
	    		},"json");
	    	}
	    	function reDeclare(){
	    		if($("input[id='id']:checked").length==0){
	    			alert("请先选中操作行");
	    			return false;
	    		}
	    		var classId= $(current).attr("classId");
	    		var id = $("input[id='id']:checked").val();
	    		location.href=_path+"/infochange/infochange_recommit.html?query.classId="+classId+"&infoChange.id="+id;
	    		
	    	}
	    	function viewDeclare(){
	    		if($("input[id='id']:checked").length==0){
	    			alert("请先选中操作行");
	    			return false;
	    		}
	    		var v=$(current);
	    	
	    		var classId= $(current).attr("classId");
	    		var id = $("input[id='id']:checked").val();
				goUrl(_path+"/infochange/infochange_view.html?query.classId="+classId+"&infoChange.id="+id);
	    	}
	    	
	        var xmtrs=$("tr[name=tr]");
			for ( var i=0; i<xmtrs.length; i++){
				var xmtr = xmtrs[i];
				var tds = $(xmtr).closest("tr").find("td");
				var xmtd = tds[3];
			    var id = $(xmtd).closest("tr").find("input[id='id']").val();
			    var classId= $(xmtd).closest("tr").attr("classId");
			    var html="<a style=\"color:#074695\" href=\"#\" onclick=\"on('" + id +"','"+classId+ "')\" >"+$(xmtd).html()+ "</a>";
			    $(xmtd).html(html);
			}
		});
      function on(id,classId){
     	    var inputObj = document.getElementsByName(id);
     	    inputObj.checked = true;
     	    goUrl(_path+"/infochange/infochange_view.html?query.classId="+classId+"&infoChange.id="+id);
	     }
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
<%--						<li>--%>
<%--							<a id="btn_zj" class="btn_zj">新 增</a>--%>
<%--						</li>--%>
						<li>
							<a id="btn_xg" class="btn_xg">修 改</a>
						</li>
						<c:if test="${query.status == null || query.status == 'INITAIL'}">
						<li>
                            <a id="btn_pltj" class="btn_xg">批量提交</a>
                        </li>
                        </c:if>
						<li>
							<a id="btn_sc" class="btn_sc">取 消</a>
						</li>
						<li>
							<a id="btn_ck" class="btn_ck">查 看</a>
						</li>
						<li>
							<a id="btn_sx" class="btn_sx">重新提交</a>
						</li>
						<li>
							<a id="btn_ck2" class="btn_ck">流程跟踪</a>
						</li>
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form name="search" id="search" method="post">
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
         <th width="10%">信息类</th>
		  <td width="20%">
          	<select name="query.classId" style="width:186px">
          		<option value="" <c:if test="${empty(query.classId) }">selected="true"</c:if>>全部</option>
          		<c:forEach items="${infoClasses }" var="clazz">
          		<option value="${clazz.guid }" <c:if test="${clazz.guid eq query.classId }">selected="true"</c:if>>${clazz.name }</option>
          		</c:forEach>
          	</select>
		  </td>
		  <th width="10%">状态</th>
		  <td width="20%">
          	<select name="query.status" style="width:186px">
          		<c:forEach items="${statusArray }" var="d">
          		<option value="${d.key }">${d.text }</option>
          		</c:forEach>
          	</select>
		  </td>
        </tr>
      </tbody>
      <tfoot>
      	<tr>
      	  <td colspan="6">
		  	<div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
      	</tr>
      </tfoot>
    </table>
  </div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>信息修改<font color="#0457A7" style="font-weight:normal;">  (提示：双击查看)</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="5%"><input type="checkbox" disabled/></td>
						<td witth="5%">信息类</td>
						<td width="5%">职工号</td>
						<td width="10%">姓名 </td>
						<td width="10%">部门 </td>
						<td width="10%">创建时间 </td>
						<td width="10%">提交时间 </td>
						<td width="10%">审批时间 </td>
						<td width="10%">状态</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
					<tr name="tr" id="${p.id}"  classId="${p.classId}">
						<td><input type="checkbox" name="${p.id}" id="id" value="${p.id }"/></td>
						<td witth="5%">${p.className }</td>
						<td width="5%">
						${p.userId}
						<input type="hidden" id="status" value="${p.status }"/>
						</td>
						<td width="10%">${p.userName}</td>
						<td width="10%">${p.deptName}</td>
						<td width="10%">${p.createDateStr } </td>
						<td width="10%">${p.commitDateStr } </td>
						<td width="10%">${p.auditDateStr } </td>
						<td width="10%">${p.status.text }</td>
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" query="${query }" />
	</div>
  	  </form>
  </body>
</html>
