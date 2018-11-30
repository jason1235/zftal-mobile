<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
  <head>
		<%@ include file="/commons/hrm/head_v5.ini" %>
  </head>
  <body>
    <div class="container_ydht sl_all_bg">
    	<div class="toolbox">
            <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_zj">增加</button>
              <button type="button" class="btn btn-default" id="btn_xg">修改</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <button type="button" class="btn btn-default" id="btn_qy" onclick="doControl('query.isactive=1')">启用</button>
              <button type="button" class="btn btn-default" id="btn_ty" onclick="doControl('ty')">停用</button>
            </div>
        </div>
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
        
        <form id="form_list" action="<%=request.getContextPath()%>/canteenfood/food_list.html" method="post">
         <div class="form-horizontal sl_all_form">
         	<div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-4 control-label">食品名称:</label>
                        <div class="col-xs-8">
                            <s:textfield maxlength="100" placeholder="最多100个字符" name="query.foodName" cssClass="form-control" 
                            theme="simple"></s:textfield>
                        </div>
                    </div>
                </div>
               <!--<div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-3 control-label">库存:</label>
                        <div class="col-xs-7">
                            <s:select id="storage" name="query.storage" list="#{-1:'无库存',-2:'只看有货' }" cssClass="form-control" listKey="key" listValue="value" 
                            	headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>-->
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-3 control-label">状态:</label>
                        <div class="col-xs-7">
                            <s:select id="status" name="query.isactive" list="#{'1':'启用','0':'停用' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>
        	</div>
         </div>
    	 <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
            </div>
         </div>
    	 <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:5%;">选择</th>
                    <th style="width:15%;">食品名称</th>
                    <th style="width:15%;">所属类别</th>
                    <th style="width:15%;">所属商家</th>
                    <th style="width:7%;">单价(￥)</th>
                    <!--<th style="width:5%;">库存</th>-->
                    <th style="width:7%;">状态</th>
                    <th style="width:15%;">食品描述</th>
                    <th style="width:11%;">创建时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
            	<s:iterator value="pageList" var="p" status="st">
            	  <tr>
                    <input type="hidden" value="${p.foodId}" />
                    <td><input name="idckb" type="checkbox" id="id" value="${p.foodId}"/></td>
                    <td width="15%">${p.foodName}</td>
                    <td width="15%"><input type="hidden" id="fcname_${p.foodId}" value="${p.foodCataid}" />${p.cataname}</td>
                    <td width="15%">${p.canname}</td>
                    <td width="7">${p.price}</td>
                    <!--<td width="5%">${p.storage}</td>-->
                    <td width="7">
                      <s:if test="%{#p.isactive==1 }"><span id="tdstatus" style="color:green">启用</span></s:if>
                      <s:if test="%{#p.isactive==0 }"><span id="tdstatus" style="color:red">停用</span></s:if>
                    </td>
                    <td width="15%">${p.description}</td>
                    <td width="11%"><s:date name="#p.createtime" format="yyyy-MM-dd" /></td>
                   </tr>
                 </s:iterator>
            </tbody>
         </table>
         <ct:page pageList="${pageList}" query="${query}" /> 
    	</form>
    </div>
    <script type="text/javascript">
		$(function(){
			$('#list_body tr').find('input').bind('click',function(e){
				e.stopPropagation();
				var $that = $(this);
				showMenu($(this).parent().parent());
				if (!$(this).prop("checked")) {
					$('#btn_qy').css('display','');
					$('#btn_sc').css('display','');
					$('#btn_ty').css('display','');
				}
				$("#list_body").find('input[type="checkbox"]').each(function(){
					if ($that.val() != $(this).val()) {
						$(this).removeAttr('checked');
						$(this).parent().parent().removeClass('current');
					}
				});
				$(this).parent().parent().toggleClass('current');
			});
			 
			$('#list_body tr').bind('click',function(e){
				showMenu($(this));
				if ($(this).find("input[type='checkbox']").length < 1) {
					$('#btn_qy').css('display','');
					$('#btn_sc').css('display','');
					$('#btn_ty').css('display','');
				}
				var status = $(this).find("input[type='checkbox']").prop('checked');
				if (status) {
					$(this).find("input[type='checkbox']").prop('checked',false);
					$('#btn_qy').css('display','');
					$('#btn_sc').css('display','');
					$('#btn_ty').css('display','');
				} else {
					$(this).find("input[type='checkbox']").prop('checked',true);
				}
				$(this).siblings().find('input[id="id"]').prop('checked',false);
			});
				
			function showMenu(obj) {
				var tdstatus = obj.find('#tdstatus').html();
				tdstatus = (tdstatus==null?'':trim(tdstatus));
				if (tdstatus == '启用') {
					$('#btn_qy').css('display','none');
					$('#btn_sc').css('display','none');
					$('#btn_ty').css('display','');
				} else {
					$('#btn_qy').css('display','');
					$('#btn_sc').css('display','');
					$('#btn_ty').css('display','none');
				}
			}
				
			$('#list_body tr').bind('dblclick',function(){
                   var id = $(this).find("input[id='id']").val();
                   location.href="<%=request.getContextPath()%>/canteenfood/food_toModify.html?query.foodId=" + id;
			});
		});
		    
	    $("#btn_zj").click(function(){
	    	location.href="<%=request.getContextPath()%>/canteenfood/food_toAdd.html";
		});
			
		$("#btn_xg").click(function(){
			var checked=$("input[id='id']:checked");
			if(checked.length==0){
				openAlert("请选择行", "e");
				return false;
			}else if(checked.length>1){
                openAlert("只能对单行进行操作", "e");
                return false;
            }
            var id = checked.val();
            location.href="<%=request.getContextPath()%>/canteenfood/food_toModify.html?query.foodId=" + id;
		});
		
		$("#btn_sc").click(function(){//功能条删除按钮
			var checked=$("input[id='id']:checked");
			if(checked.length==0){
				openAlert("请选择行", "e");
				return false;
			}else if(checked.length>1){
                openAlert("只能对单行进行操作", "e");
                return false;
            }
		    doDelete(checked.val());
        });
				
			
		function doDelete(obj) {
			var id = obj;
			openAlert("确定要删除吗？", "w");
			$("#why_cancel").click(function(){
				alertDivClose();
			});
			$("#why_sure").click(function(){
				$.post("<%=request.getContextPath() %>/canteenfood/food_remove.html?query.foodId="+id, null, function(data){
					processCall(data, function(){
						location.reload();
					});
				}, "json");
			});
		}
				
		function doControl(arg) {
			var checked=$("input[id='id']:checked");
            if(checked.length==0){
                openAlert("请选择行", "e");
                return false;
            }else if(checked.length>1){
                openAlert("只能对单行进行操作", "e");
                return false;
            }
                  
			var id = checked.val();
			var name=$("input[id=fcname_"+id+"]").val();
			if (arg == "ty") {
			    arg = 'query.isactive=0';
				openAlert("确定要停用吗？", "w");
			}else {
				$.post("<%=request.getContextPath() %>/canteenfood/food_control.html?"+arg+"&query.foodId="+id+"&query.foodCataid="+name, null, function(data){
					processCall(data, function(){
						location.reload();
					});
				}, "json");
			}

			$("#why_sure").click(function(){
				$.post("<%=request.getContextPath() %>/canteenfood/food_control.html?"+arg+"&query.foodId="+id+"&query.foodCataid="+name, null, function(data){
					processCall(data, function(){
						location.reload();
					});
				}, "json");
			});
		}
	</script>
  </body>
</html>
