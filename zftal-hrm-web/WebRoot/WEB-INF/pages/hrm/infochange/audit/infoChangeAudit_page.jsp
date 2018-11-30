<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
    	  jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/tip/tip.js");
			$("#btn_sh").click(function(){//功能条增加按钮
				detail();
			});
			$("#btn_ck").click(function(){//功能条增加按钮
				view();
			});
			$("#btn_plsh").click(function(){//功能条增加按钮
                batchPass();
            });
			$("input:checkbox").click(function(e){
				e.stopPropagation();
				if($(this).is(":checked")){
					$(this).closest("tr").click();
				}else{
					$(this).closest("tr").removeClass("current");
				}
			});
			$("tbody > tr[name^='tr']").click(
				function(){	//监听单击行
					if($(this).attr("class")!="current"){
						$(this).attr("class", "current");
						$(this).find("input:checkbox").attr("checked","checked");
					}else{
						$(this).removeClass("current");
                        $(this).find("input:checkbox").removeAttr("checked");
					}
					refreshButton();
				}
			);
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
			
				$("div.buttonbox li:visible:first").find("a").click();
			});

			$("tr[name=tr]").mouseover(function(){
				var id=$(this).attr("id");
				var tr=$(this);
				var classId=$(this).attr("classId");
/*				$.post(_path+"/infochange/infochange_getChangeString.html","query.classId="+classId+"&query.id="+id,
						function(data){
					tip(tr,data.message);
				},"json");*/
				$.ajax({type:"post",
					url:"<%=request.getContextPath()%>/infochange/infochange_getChangeString.html?query.classId="+classId+"&query.id="+id,
					success:function(data){
						tip(tr,data.message);
					},
					datatType:"json",
					global:false
				});
			});
			
			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/infochange/audit_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			function refreshButton(){
				var current = $(".current");
				$("div.buttonbox li").hide();
				if(current.length==1){
		    		var status = $(current[0]).find("#status").val();
		    		if(status == "WAIT_AUDITING" || status == "IN_AUDITING"){
		    			$("#btn_sh").parent().show();
		    		}
		    		if(status != ""){
		    			$("#btn_ck").parent().show();
		    		}
				}else if(current.length>1){
					var showPL = true;
				    for(var i = 0 ;i<current.length;i++){
				    	var status = $(current[i]).find("#status").val();
				    	if(status != "WAIT_AUDITING" && status != "IN_AUDITING"){
				    		showPL = false;break;
	                    }
					}
				    if(showPL){
                        $("#btn_plsh").parent().show();
                    }
				}else{
					$("div.buttonbox li").show();
				}
	    	}
			$("select[name='query.status']").val('${query.status}');
			var page=$("input[name=query\\.showCount]").val();
			fillRows(page, "", "", false);//填充空行
			
			function detail(){
	    		if($("input[id='id']:checked").length==0){
	    			alert("请先选中操作行");
	    			return false;
	    		}
	    		if($("input[id='id']:checked").length>1){
                    alert("不能对多行记录同时进行该操作");
                    return false;
                }
	    		var id = $("input[id='id']:checked").val();
	    		var classId= $($("input[id='id']:checked")[0]).closest("tr").attr("classId");
				goUrl(_path+"/infochange/audit_detail.html?query.classId="+classId+"&infoChange.id="+id);
	    	}
	    	function batchPass(){
	    		if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
	    		showConfirm("确认要对选择的数据进行批量审核通过吗?");
	            $("#why_cancel").click(function(){
	                divClose();
	            });
	            $("#why_sure").click(function(){
	            	var param = $("input[id='id']:checked").attr("name","query.id").serialize();
	                $("input[id='id']:checked").removeAttr("name");
	                $.post(_path+'/infochange/audit_passBatch.html',param,function(data){
	                    var callback = function(){
	                        $("#search").submit();
	                    };
	                    processDataCall(data,callback);
	                },"json");
	            });
				
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
     	    goUrl(_path+"/infochange/audit_view.html?query.classId="+classId+"&infoChange.id="+id);
	     }
    	
    	/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#search").submit();
		}
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_sh" class="btn_sh">审 定</a>
						</li>
						<li>
                            <a id="btn_plsh" class="btn_sh">批量审核</a>
                        </li>
						<li>
							<a id="btn_ck" class="btn_ck">查 看</a>
						</li>
					</ul>
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form action="<%=request.getContextPath()%>/infochange/audit_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">职工号</th>
          <td width="20%">
            <input name="query.userId" value="${query.userId }" type="text" style="width: 130px;" />
          </td>
          <th width="10%">姓名</th>
          <td width="20%">
            <input name="query.username" value="${query.username }" type="text" style="width: 130px;" />
          </td>
          <th width="10%">部门</th>
          <td width="20%">
            <ct:codePicker name="query.depId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.depId }" width="130" />
          </td>          
        </tr>
        <tr>
          <th width="10%">信息类</th>
		  <td width="20%">
          	<select name="query.classId" style="width:130px">
          		<option value="" <c:if test="${empty(query.classId) }">selected="true"</c:if>>全部</option>
          		<c:forEach items="${infoClasses }" var="clazz">
          		<option value="${clazz.guid }" <c:if test="${clazz.guid eq query.classId }">selected="true"</c:if>>${clazz.name }</option>
          		</c:forEach>
          	</select>
		  </td>
		  <th width="10%">状态</th>
		  <td width="20%">
          	<select name="query.status" style="width:130px">
          		<c:forEach items="${statusArray }" var="d">
          		<c:if test="${d.key != 'INITAIL'}"><option value="${d.key }">${d.text }</option></c:if>
          		</c:forEach>
          	</select>
		  </td>
		  <td colspan="2">
            <div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
        </tr>
      </tbody>
     
    </table>
  </div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>信息修改审核<font color="#0457A7" style="font-weight:normal;">  (提示：双击待审核、审核中、不通过时审核，其他则查看)</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="5%"><input type="checkbox" disabled/></td>
						<td witth="5%">信息类</td>
						<c:if test="${'userId' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="userId"  width="5%">职工号</td>
						</c:if>
						<c:if test="${'userId' != sortFieldName}">
						<td class="sort_title" id="userId" width="5%">职工号</td>
						</c:if>
						<c:if test="${'userName' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="userName"  width="10%">姓名</td>
						</c:if>
						<c:if test="${'userName' != sortFieldName}">
						<td class="sort_title" id="userName" width="10%">姓名</td>
						</c:if>
						<c:if test="${'depId' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="depId"  width="10%">部门</td>
						</c:if>
						<c:if test="${'depId' != sortFieldName}">
						<td class="sort_title" id="depId" width="10%">部门</td>
						</c:if>
						<c:if test="${'createDate' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="createDate"  width="10%">录入时间</td>
						</c:if>
						<c:if test="${'createDate' != sortFieldName}">
						<td class="sort_title" id="createDate" width="10%">录入时间</td>
						</c:if>
						<c:if test="${'commitDate' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="commitDate"  width="10%">提交时间</td>
						</c:if>
						<c:if test="${'commitDate' != sortFieldName}">
						<td class="sort_title" id="commitDate" width="10%">提交时间</td>
						</c:if>
						<c:if test="${'auditDate' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="auditDate"  width="10%">审核时间</td>
						</c:if>
						<c:if test="${'auditDate' != sortFieldName}">
						<td class="sort_title" id="auditDate" width="10%">审核时间</td>
						</c:if>
						<c:if test="${'status' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="status"  width="10%">审核状态</td>
						</c:if>
						<c:if test="${'status' != sortFieldName}">
						<td class="sort_title" id="status" width="10%">审核状态</td>
						</c:if>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
					<tr name="tr" id="${p.id}" classId="${p.classId}">
						<td><input type="checkbox" id="id" value="${p.id }"/></td>
						<td witth="5%">${p.className }</td>
						<td width="5%">
							<input type="hidden" id="status" name="${p.status}" value="${p.status}"/>
							${p.userId}
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
