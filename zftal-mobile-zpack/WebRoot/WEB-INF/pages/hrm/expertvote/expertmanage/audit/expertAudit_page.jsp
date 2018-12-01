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
    	    sort('${sortFieldName}','${asc }');
			$("#btn_sh").click(function(){//功能条增加按钮
				detail();
			});
			$("#btn_ck").click(function(){//功能条增加按钮
				view();
			});

			var current = null;

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
					$("input:checkbox").removeAttr("checked");
					$("tbody > tr[name^='tr']").removeClass("current");
					$(this).attr("class", "current");
					$(this).find("input:checkbox").attr("checked","checked");
					current = $(this);
					refreshButton();
				}
			);
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				$("#btn_ck").click();
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/expertmanage/audit_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			function refreshButton(){
	    		var status = $(current).find("#status").val();
	    		$("div.buttonbox li").hide();
	    		if(status == "WAIT_AUDITING" || status == "IN_AUDITING"){
	    			$("#btn_sh").parent().show();
	    		}
	    		if(status != ""){
	    			$("#btn_ck").parent().show();
	    		}
	    	}
			$("select[name='query.status']").val('${query.status}');
			fillRows("20", "", "", false);//填充空行
		});
    
    	function detail(){
    		if($("input[id='id']:checked").length==0){
    			alert("请先选中操作行");
    			return false;
    		}
    		var id = $("input[id='id']:checked").val();
			goUrl(_path+"/expertmanage/audit_detail.html?expertDeclare.id="+id);
    	}
    	function view(){
    		if($("input[id='id']:checked").length==0){
    			alert("请先选中操作行");
    			return false;
    		}
    		var id = $("input[id='id']:checked").val();
			goUrl(_path+"/expertmanage/audit_view.html?expertDeclare.id="+id);
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
						<c:if test="${query.status == 'WAIT_AUDITING' || query.status == 'IN_AUDITING'}">
							<li>
								<a id="btn_sh" class="btn_sh">审 定</a>
							</li>
						</c:if>
						<li>
							<a id="btn_ck" class="btn_ck">查 看</a>
						</li>
					</ul>
			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
 <form action="<%=request.getContextPath()%>/expertmanage/audit_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">姓名</th>
          <td width="20%">
            <input name="query.name"  style="width: 130px;" value="${query.name }"/>
          </td> 
          <th width="10%">职工号</th>
          <td width="20%">
            <input name="query.gh" style="width: 130px;" value="${query.gh }" />
          </td> 
		  <th width="10%">状态</th>
		  <td width="20%">
          	<select name="query.status" style="width:130px">
          		<c:forEach items="${statusArray }" var="d">
          		<option value="${d.key }">${d.text }</option>
          		</c:forEach>
          	</select>
		  </td>
      	  <td colspan="6">
		  	<div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
<form action="zjk/audit_page.html" name="search" id="search" method="post">
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>职务认定审核<font color="#0457A7" style="font-weight:normal;">  (提示：双击查看选定行)</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="5%"><input type="checkbox" disabled/></td>
						<td width="5%">序号</td>
						<td class="sort_titlem_m" id="gh" width="10%">工号</td>
						<td class="sort_titlem_m" id="xm" width="10%">专家姓名</td>
						<td class="sort_titlem_m" id="type" width="10%">专家类型</td>
						<td class="sort_titlem_m" id="DEDATE" width="20%">上报时间</td>
						<td class="sort_titlem_m" id="ADUDATE" width="20%">审核时间</td>
						<td class="sort_titlem_m" id="STATUS" width="20%">状态</td>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
						<tr name="tr">
							<td><input type="checkbox" id="id" value="${p.id }"/></td>
							<td>
							${st.index + beginIndex }
							<input type="hidden" id="status" value="${p.status }"/>
							</td>
							<td>${p.tjrgh }</td>
							<td><ct:PersonParse code="${p.tjrgh }"/></td>
							<td><ct:codeParse code="${p.type }" catalog="DM_DEF_ZJLX"/></td>
							<td><s:date name="dedate" format="yyyy-MM-dd"/></td>
							<td><s:date name="adudate" format="yyyy-MM-dd"/></td>
							<td>${p.statusText }</td>
						</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" />
	</div>
  	  </form>
  </body>
</html>
