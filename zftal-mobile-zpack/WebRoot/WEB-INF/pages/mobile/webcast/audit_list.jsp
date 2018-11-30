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
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <button type="button" class="btn btn-default" id="btn_sh">审核</button>
            </div>
        </div>
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
        
        <form id="form_list" action="<%=request.getContextPath()%>/webcast/audit_list.html" method="post">
         <div class="form-horizontal sl_all_form">
         	<div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">申请账号:</label>
                        <div class="col-xs-7">
                            <s:textfield maxlength="30" placeholder="最多30个字符" name="query.userid" cssClass="form-control" theme="simple"></s:textfield>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">审核状态:</label>
                        <div class="col-xs-7">
                            <s:select id="status" name="query.isaudit" list="#{'0':'待审','1':'通过','2':'驳回' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
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
                    <th style="width:7%;">申请账号</th>
                    <th style="width:7%;">申请人</th>
                    <th style="width:7%;">状态</th>
                    <th style="width:10%;">申请原因</th>
                    <th style="width:11%;">申请时间</th>
                    <th style="width:10%;">审核理由</th>
                    <th style="width:11%;">审核时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
            	<s:iterator value="pageList" var="p" status="st">
            	  <tr>
                    <input type="hidden" value="${p.appid}" />
                    <td><input name="idckb" type="checkbox" id="id" value="${p.appid}"/></td>
                    <td width="10%">${p.userid}</td>
                    <td width="10%">${p.username}</td>
                    <td width="7">
                      <s:if test="%{#p.isaudit==0}"><span id="tdstatus" style="color:green">待审</span></s:if>
                      <s:if test="%{#p.isaudit==1}"><span id="tdstatus" style="color:green">通过</span></s:if>
                      <s:if test="%{#p.isaudit==2}"><span id="tdstatus" style="color:red">驳回</span></s:if>
                    </td>
                    <td width="10%">${p.appreason}</td>
                    <td width="11%"><s:date name="#p.createTime" format="yyyy-MM-dd" /></td>
                    <td width="15%">${p.auditReason}</td>
                     <td width="11%"><s:date name="#p.auditTime" format="yyyy-MM-dd" /></td>
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
				
				if (!$(this).prop("checked")) {
					$('#btn_sh').css('display','');
					$('#btn_sc').css('display','');
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
				
				if ($(this).find("input[type='checkbox']").length < 1) {
					$('#btn_sh').css('display','');
					$('#btn_sc').css('display','');
				}
				var status = $(this).find("input[type='checkbox']").prop('checked');
				if (status) {
					$(this).find("input[type='checkbox']").prop('checked',false);
					$('#btn_sh').css('display','');
					$('#btn_sc').css('display','');
				} else {
					$(this).find("input[type='checkbox']").prop('checked',true);
				}
				$(this).siblings().find('input[id="id"]').prop('checked',false);
			});
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
		
		
		$("#btn_sh").click(function(){
			var checked=$("input[id='id']:checked");
			if(checked.length==0){
				openAlert("请选择行", "e");
				return false;
			}else if(checked.length>1){
                openAlert("只能对单行进行操作", "e");
                return false;
            }
            var id = checked.val();
            location.href="<%=request.getContextPath()%>/webcast/audit_toModify.html?query.appid=" + id;
		});
			
		function doDelete(obj) {
			var id = obj;
			openAlert("确定要删除吗？", "w");
			$("#why_cancel").click(function(){
				alertDivClose();
			});
			$("#why_sure").click(function(){
				$.post("<%=request.getContextPath() %>/webcast/audit_delete.html?query.appid="+id, null, function(data){
					processCall(data, function(){
						location.reload();
					});
				}, "json");
			});
		}
		/*		
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
			if (arg == "ty") {
			    arg = 'query.isaudit=2';
				openAlert("确定要驳回吗？", "w");
			}else {
				$.post("/webcast/audit_control.html?"+arg+"&query.appid="+id, null, function(data){
					processCall(data, function(){
						location.reload();
					});
				}, "json");
			}

			$("#why_sure").click(function(){
				$.post("/webcast/audit_control.html?"+arg+"&query.appid="+id, null, function(data){
					processCall(data, function(){
						location.reload();
					});
				}, "json");
			});
		}*/
	</script>
  </body>
</html>
