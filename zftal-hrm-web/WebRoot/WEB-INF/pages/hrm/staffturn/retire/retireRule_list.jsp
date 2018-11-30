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
		$("#list_body > tr[name^='tr']").click(function(){//行数据单击事件
			if($(this).is(".current")){
				$("#list_body > tr[name^='tr']").removeClass("current");
			}else{
				$("#list_body > tr[name^='tr']").removeClass("current");
				$(this).attr("class","current");
			}
			//控制checkbox的选中状态
			var trcheckbox= $(this).find(":checkbox").get(0);
			if(trcheckbox.checked){
				$("input[name='sid']").removeAttr("checked");
			}else{
				$("input[name='sid']").removeAttr("checked");
				trcheckbox.checked=true;
			}
		});
		//改变checkbox的事件
		$("input[name='sid']").click(function(e){
			e.stopPropagation();
			if($(this).is(":checked")){
				$("input[name='sid']").removeAttr("checked");
				$(this).attr("checked","checked");
				$("#list_body > tr[name^='tr']").removeClass("current");
				$(this).closest("tr").attr("class","current");
			}else{
				$("#list_body > tr[name^='tr']").removeClass("current");
			}
		});
		
		$("#btn_zj").click(function(){//功能条增加按钮
			//showWindow("新增退休规则","<%=request.getContextPath()%>/retire/retireScan_input.html",760,600);
			showWindowIframe("新增",720,350,"<%=request.getContextPath()%>/retire/retireScan_input.html");
		});
		
		$("#btn_xg").click(function(){//功能条修改按钮
			var ch=$("input[name='sid']:checked");
			if(ch.length==0){
				alert("请选中要修改的记录！");
				return false;
			}
			var id = ch.val();
			queryEntity(id);
		});
		
		$("#btn_sc").click(function(){//功能条删除按钮
			var ch=$("input[name='sid']:checked");
			if(ch.length==0){
				alert("请选中要删除的记录！");
				return false;
			}
			var id = ch.val();
			preDel(id);;
		});
		
		$("#list_body > tr[name^='tr']").dblclick(function(){//行数据双击事件
			$("#list_body > tr[name^='tr']").removeClass("current");
			$("input[name='sid']").removeAttr("checked");
			$(this).attr("class","current");
			$(this).find(":checkbox").attr("checked","checked");
			var id=$(this).find(":checkbox").val();
			queryEntity(id);
		});
		
		operationList();//操作栏初始化
		fillRows("20", "", "", false);//填充空行
	});

	function queryEntity(id){//查询
		showWindow("修改","<%=request.getContextPath()%>/retire/retireScan_query.html?model.id="+id,720,350);
	}
    function preDel(id){//删除前操作
		showConfirm("确定要删除该记录吗？");
		$("#why_cancel").click(function(){
			divClose();
		});
		$("#why_sure").click(function(){
			alertDivClose();
			delEntity(id);
		});
	}
	function delEntity(id){//删除
		$.post('<%=request.getContextPath()%>/retire/retireScan_delete.html',"model.id="+id,function(data){
	        var callback = function(){
	            refreshList();
	        };
	        if(data.success){
	            processDataCall(data, callback);
	        }else{
	            showWarning(data.text);
	        }
		},"json");
	}
	
	function operationList(){
		$(".select_tools a").css("cursor","pointer");
		operationHover();
	}
	function refreshList(){
		$("#rule-div").load("<%=request.getContextPath()%>/retire/retireScan_config.html");
	}
	
	/**
	 * 把原有的弹出窗口转成ymPormpt式弹出
	 * @param title
	 * @param width
	 * @param height
	 * @param url
	 */
	function showWindowIframe(title,width,height,url,handler){
		ymPrompt.win({message:url,
					  width:width,
					  height:height,
					  title:title,
					  maxBtn:true,
					  minBtn:true,
					  iframe:true,
					  showShadow:false,
					  useSlide:true,
					  maskAlphaColor:"#FFFFFF",
					  maskAlpha:0.3,
					  handler:handler
				}
		);
	}
    </script>
  </head>
  <body>
	<div class="toolbox">
		<!-- 按钮 -->
		<div class="buttonbox">
			<ul>
				<li><a id="btn_zj" class="btn_zj">新 增</a></li>
				<li><a id="btn_xg" class="btn_xg">修 改</a></li>
				<li><a id="btn_sc" class="btn_sc">删 除</a></li>
			</ul>
		</div>
		<!-- 按钮 -->
		<p class="toolbox_fot">
			<em></em>
		</p>
	</div>
	<form id="search" method="post" action="">
		<div class="formbox">
			<!--标题start-->
		    <h3 class="datetitle_01">
		    	<span>预退休规则列表</span>
		    </h3>
			<!--标题end-->
			<div style="overflow: auto;">
				<table width="100%" class="dateline" id="tiptab">
					<thead id="list_head">
						<tr>
							<td><input type="checkbox" disabled="disabled"/> </td>
							<td>权重</td>
							<td>条件名称</td>
							<td>条件值</td>
							<td>性别</td>
							<td>退休年龄</td>
							<!-- 20140423 add start -->
							<td>人员身份</td>
							<td>行政职务级别</td>
							<td>通知处理人</td>
							<!-- 20140423 add end -->
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${pageList}" var="obj" varStatus="st">
							<tr name="tr">
								<td><input type="checkbox" name="sid" value="${obj.id }"/> </td>
								<td>${obj.seq }</td>
								<td>${empty obj.cond_col ? "默认规则" : obj.cond_col_name }</td>
								<td>
									<c:if test="${empty obj.cond_col }">
										无
									</c:if>
									<c:if test="${!empty obj.cond_col }">
										<ct:codeParse code="${obj.cond_val }" catalog="${obj.cond_col_code }"/>
									</c:if>
								</td>
								<td>
									<ct:codeParse code="${obj.sex }" catalog="<%=ICodeConstants.SEX %>"/>
								</td>
								<td>${obj.retire_age }</td>
								<!-- 20140423 add start -->
								<td><ct:codeParse catalog="<%=ICodeConstants.DM_GB_RYSFDMB %>" code="${obj.identity}"/></td>
								<td><ct:codeParse catalog="<%=ICodeConstants.ADMIN_DUTY_LEVEL %>" code="${obj.duty_level}"/></td>
								<td>${obj.receiverNm}</td>
								<!-- 20140423 add end -->
							</tr>
						</c:forEach>
					</tbody>
			  	</table>
		  	</div>
		</div>
	</form>
  </body>
</html>
