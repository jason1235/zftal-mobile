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
			$("#btn_zj").click(function(){//功能条增加按钮
				inputRequest();
			});
			$("#btn_xg").click(function(){//功能条修改按钮
				var id = $("input[id='id']:checked").val();
				editRequest(id);
			});
			$("#btn_sc").click(function(){//功能条删除按钮
				deleteRequest();
			});
			$("#btn_tx").click(function(){//功能条删除按钮
				remindRequest();
			});
			var current = null;
			$("tbody > tr[name^='tr']").click(function(){	//监听单击行
				if(current != null) {
					current.removeClass("current");
					current.find("input[id='id']").removeAttr("checked");
				}
				$(this).attr("class", "current");
				$(this).find("input[id='id']").attr("checked","checked");
				current = $(this);
			});
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var id = $(this).find("input[id='id']").val();
				editRequest(id);
			});
			
			fillRows("20", "", "", false);//填充空行
		});
    
    function editRequest(id){		
		showWindow("编辑",_path+"/contract/categoryConfig_edit.html?categoryConfig.htzldm="+id,700,350);
	}
	
	function inputRequest(){
		showWindow("新增",_path+"/contract/categoryConfig_add.html",700,350);
	}
	
	function deleteRequest(){
		showConfirm("确认要删除吗?");
		var id = $("input[id='id']:checked").val();
		$("#why_cancel").click(function(){
			divClose();
		});
		$("#why_sure").click(function(){
			delRequest(id);
		});
	}  
	
	function delRequest(id){
		$.post(_path+'/contract/categoryConfig_delete.html',"categoryConfig.htzldm="+id,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
	
	function remindRequest(){
		$.post(_path+'/contract/categoryConfig_manualExeByDate.html',null,function(data){
			var callback = function(){
				$("#search").submit();
			};
			processDataCall(data,callback);
		},"json");
	}
    </script>
  </head>
  <body>
  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增加</a>
							<a id="btn_xg" class="btn_xg">修改</a>
							<a id="btn_sc" class="btn_sc">删除</a>
							<a id="btn_tx" class="btn_sc">到期提醒</a>
						</li>
					</ul>
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
<form action="<%=request.getContextPath() %>/contract/categoryConfig_list.html" name="search" id="search" method="post">
<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>合同种类列表<font color="#0457A7" style="font-weight:normal;">（提示：双击一行可以修改信息）</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="5%"><input type="checkbox" disabled/></td>
						<td width="15%">合同种类代码</td>
						<td width="15%">合同种类名称</td>
						<td width="15%">到期提醒天数</td>
						<td width="15%">续聘提醒天数</td>
						<td width="15%">试用期提醒天数</td>
						<td width="20%">管理员</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="s" status="st">
					<tr name="tr">
						<td><input type="checkbox" id="id" value="${s.htzldm }"/></td>
						<td>${s.htzldm }</td>
						<td>${s.htzlmc }</td>
						<td>${s.dqtxts }</td>
						<td>${s.xqtxts }</td>
						<td>${s.syqtxts }</td>
						<td>${s.glry }</td>
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  		<ct:page pageList="${pageList }" query="${categoryConfig }" queryName="categoryConfig"/>
	</div>
	</form>
  </body>
</html>