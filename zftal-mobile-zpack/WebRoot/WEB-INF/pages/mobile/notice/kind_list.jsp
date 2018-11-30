<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini" %>
	</head>
	<body>
		<div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增加</a>
						
                            <a id="btn_xg" class="btn_xg">修改</a>
                        
							<a id="btn_sc" onclick="doControl('query.deleted=1')" class="btn_sc">删除</a>
						
							<a id="btn_qy" onclick="doControl('query.status=1')" class="btn_qd">启用</a>
						
							<a id="btn_ty" onclick="doControl('query.status=0')" class="btn_zant">停用</a>
						</li>
					</ul>
				</div>
		  
		</div>
		<form id="form_list" action="<%=request.getContextPath() %>/gggl/kind_list.html" method="post">
			<input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
			<input type="hidden" id="asc" name="asc" value=""/>
    <div class="searchtab">
            		<table width="100%" border="0">
						<tbody>
							<tr>
								
								<td>
									种类名称
								</td>
								<td>
									<s:textfield name="query.name" theme="simple"></s:textfield>									
								</td>
								
								<td>
									使用状态
								</td>
								<td>
									<s:select id="status" name="query.status" list="#{'1':'启用','0':'停用' }" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple" cssStyle="width:100px;"></s:select>
								</td>
								
								<td>
									所属业务系统
								</td>
						          <td >
						           <select id="businessSystem" name="query.businessSystem"  value="${query.businessSystem }" style="width:126px;">
						                                    <option value="" ></option>
						                       			 <c:forEach var="menu" items="${businessList}" varStatus="st">
						                         		    <option value="${menu.classId} ">${menu.classXtmc}</option>
						                                 </c:forEach>
						           </select>
						          </td>
								
								<td width="20%">&nbsp;</td>
								 
							</tr>
						</tbody>
					</table>
					<table width="100%" border="0">
						<tfoot>
				          <tr>
				            <td colspan="8">
				              <div class="btn">
				                <button class="btn_cx" type="submit" id="search_go">查 询</button>
				              </div>
				           </td>
				          </tr>
				       </tfoot>
					</table>
            	</div>
  <div class="formbox">
  	<!--标题start-->
    <h3 class="datetitle_01">
    	<span>通知公告管理<font color="#0457A7" style="font-weight:normal;"> </font></span>
    </h3>
<!--标题end-->
<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td><input type="checkbox" disabled/></td>
						<td width="20%">种类名称</td>
						<td width="20%">创建时间</td>
						<td width="20%">使用状态</td>
						<td width="20%">所属业务系统</td>
						<td width="20%">是否抓取</td>
						
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
					<tr>
						<td>
							<input name="idckb" type="checkbox" id="id" value="${p.id }"/>
						</td>
						<td width="20%">${p.name}</td>
						<td width="20%"><s:date name="#p.createTime" format="yyyy-MM-dd" /></td>
						<td id="tdstatus" width="20%">
						<s:if test="%{#p.status==1 }">启用</s:if>
						<s:if test="%{#p.status==0 }">停用</s:if>
						</td>
						<td width="20%">
	                        <c:forEach items="${businessList}" var="li"> 
									<c:if test="${p.businessSystem eq li.classId }">${li.classXtmc}</c:if>
							</c:forEach> 
                        </td>
                        <td id="grabOrNot" width="20%">
						<s:if test="%{#p.grabOrNot==1 }">是</s:if>
						<s:if test="%{#p.grabOrNot==0 }">否</s:if>
						</td>
				
						</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" query="${query }" />
  </div>
		</form>
		<script type="text/javascript">
		var heightOffset= 200;
			$(function(){
			var enableMsg = '${enableMsg}';
			if (enableMsg != '') {
				alert(enableMsg);
			}
				$('#list_body tr').find('input').bind('click',function(e){
					e.stopPropagation();
					$(this).parent().parent().toggleClass('current');
				});
				$('#list_body tr').bind('click',function(e){
				var status = $(this).find('input').prop('checked');
				if (status) {
					$(this).find('input').removeAttr('checked');
				} else {
					$(this).find('input').attr('checked',true);
				}
				//$(this).find('input').attr('checked','checked');
					$(this).siblings().find('input[id="id"]').removeAttr('checked');
					$(this).toggleClass('current').siblings().removeClass('current');
					var tdstatus = $(this).find('#tdstatus').html();
					tdstatus = (tdstatus==null?'':trim(tdstatus));
					if (tdstatus == '启用') {
						$('#btn_qy').css('display','none');
						$('#btn_ty').css('display','');
						$('#btn_sc').css('display','none');
					} else {
						$('#btn_qy').css('display','');
						$('#btn_ty').css('display','none');
						$('#btn_sc').css('display','');
					}
				});
				$('#list_body tr').bind('dblclick',function(){
					var id = $(this).find('input').val();
					showWindow("修改","<%=request.getContextPath()%>/gggl/kind_modify.html?query.id=" + id, 750,heightOffset);
				});
			});
		    //heightOffset+=230;
		    $("#btn_zj").click(function(){
					showWindow("增加","<%=request.getContextPath()%>/gggl/kind_add.html", 750,heightOffset);
				});
			 $("#btn_xg").click(function(){
			 var checked=$("input[id='id']:checked");
					if(checked.length==0){
						alert("请选择行");
						return false;
						
					}else if(checked.length>1){
                        alert("只能对单行进行操作");
                        return false;
                    }
                    var id = checked.val();
					showWindow("修改","<%=request.getContextPath()%>/gggl/kind_modify.html?query.id=" + id, 750,heightOffset);
				});
				function doUpdate(obj) {
					var id = obj;
					showWindow("修改","<%=request.getContextPath()%>/gggl/kind_modify.html?query.id=" + id, 750,heightOffset);
				}
				
				function doControl(arg) {
				var checked=$("input[id='id']:checked");
                    if(checked.length==0){
                        alert("请选择行");
                        return false;
                    }else if(checked.length>1){
                        alert("只能对单行进行操作");
                        return false;
                    }
                    
					var id = checked.val();
					if (arg == "query.deleted=1") {
						showConfirm("确定要删除吗？");
						$("#why_cancel").click(function(){
						alertDivClose();
					});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/gggl/kind_control.html?query.id="+id + "&" + arg, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							processDataCall(data, callback);
						}, "json");
					});
					} else {
						$.post("<%=request.getContextPath() %>/gggl/kind_control.html?query.id="+id + "&" + arg, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							processDataCall(data, callback);
						}, "json");
					}
					
				}
				
				
				//fillRows("10", "", "", false);//填充空行
		</script>
	</body>
</html>
