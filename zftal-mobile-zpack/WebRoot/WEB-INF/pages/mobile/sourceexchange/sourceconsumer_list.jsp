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
	    <div class="toolbox" style="height:49px;">
            <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_lq" onclick="doControl('query.flag=1')">兑换领取</button>
            </div>
        </div>
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    <form id="form_list" action="<%=request.getContextPath()%>/sourcegoods/sourceconsumer_list.html" method="post">
	    <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">工号:</label>
                        <div class="col-xs-7">
                            <s:textfield maxlength="100"  name="query.userid" cssClass="form-control" theme="simple"></s:textfield>
                        </div>
                    </div>
                </div>
                 
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">状态:</label>
                        <div class="col-xs-7">
                            <s:select id="flag" name="query.flag" list="#{'1':'已领取','0':'未领取' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
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
                    <th style="width:10%;">工号</th>
                    <th style="width:10%;">姓名</th>
                    <th style="width:10%;">商品名称</th>
                    <th style="width:10%;">兑换数量</th>
                    <th style="width:10%;">订单编号</th>
                    <th style="width:10%;">创建时间</th>
                    <th style="width:10%;">兑换状态</th>
                    <th style="width:16%;">兑换时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="pageList" var="p" status="st">
                        <td>
                            <input name="idckb" type="checkbox" id="id" value="${p.id}"/>
                        </td>
                        <td width="10%">${p.userid}</td>
                        <td width="10%">${p.username }</td>
                        <td width="10%">${p.goodsname}</td>
                        <td width="10%">${p.amount}</td>
                        <td width="10%">${p.id}</td>
                        <td width="10%"><s:date name="#p.createtime" format="yyyy-MM-dd" /></td>
                        <td width="10%">
                           <s:if test="%{#p.flag==1 }"><span id="tdstatus" style="color:green">${p.status}</span></s:if>
	                       <s:if test="%{#p.flag==0 }"><span id="tdstatus" style="color:red">${p.status}</span></s:if>
                        </td>
                        <td width="10%"><s:date name="#p.exhangetime" format="yyyy-MM-dd" /></td>
                        </tr>
               </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${pageList}" query="${query}" />  
	    </form>
	</div>
	
	<script type="text/javascript">
	$(function(){
		var enableMsg = '${enableMsg}';
		if (enableMsg != '') {
			alert(enableMsg);
		}
		
		$('#list_body tr').find('input').bind('click',function(e){
			e.stopPropagation();
			var $that = $(this);
			showMenu($(this).parent().parent());
			if (!$(this).prop("checked")) {
				$('#btn_lq').css('display','');
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
				$('#btn_lq').css('display','');
			}
			var status = $(this).find("input[type='checkbox']").prop('checked');
			if (status) {
				$(this).find("input[type='checkbox']").prop('checked',false);
				$('#btn_lq').css('display','');
			} else {
				$(this).find("input[type='checkbox']").prop('checked',true);
			}
			$(this).siblings().find('input[id="id"]').prop('checked',false);
		});
		
		function showMenu(obj) {
			var tdstatus = obj.find('#tdstatus').html();
			tdstatus = (tdstatus==null?'':trim(tdstatus));
			if (tdstatus == '已领取') {
				$('#btn_lq').css('display','none');
			} else {
				$('#btn_lq').css('display','');
			}
		}
		
		
	});
	
	
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
		$.post("<%=request.getContextPath() %>/sourcegoods/sourceconsumer_control.html?"+arg+"&query.id="+id, null, function(data){
			processCall(data, function(){
				location.reload();
			});
		}, "json");	
	}
	</script>
	
	</body>
</html>
