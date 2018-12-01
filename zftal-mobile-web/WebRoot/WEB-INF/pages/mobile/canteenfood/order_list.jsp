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
              <button type="button" class="btn btn-default" id="btn_jd" onclick="doControl('query.flag=1')">接单</button>
              <button type="button" class="btn btn-default" id="btn_jud" onclick="doControl('query.flag=2')">拒单</button>
            </div>
        </div>
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    <form id="form_list" action="<%=request.getContextPath()%>/canteenfood/order_list.html" method="post">
	    <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">下单账户:</label>
                        <div class="col-xs-7">
                            <s:textfield maxlength="100"  name="query.userId" cssClass="form-control" theme="simple"></s:textfield>
                        </div>
                    </div>
                </div>
                 
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">状态:</label>
                        <div class="col-xs-7">
                            <s:select id="flag" name="query.flag" list="#{'2':'拒单','1':'已接单','0':'未接单' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>
        </div>
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
            </div>
        </div>
        <table class="table table-striped sl_all_bg text-left" style="border:1px solid #cccddd;">
            <thead id="list_head">
                <tr>
                    <th style="width:7%;">选择</th>
                    <th style="width:10%;">订单编号</th>
                    <th style="width:10%;">下单账户</th>
                    <th style="width:7%;">姓名</th>
                    <th style="width:10%;">送餐地址</th>
                    <th style="width:10%;">用人人数</th>
                    <!--<th style="width:12%;">送达时间</th>-->
                    <th style="width:12%;">创建时间</th>
                    <th style="width:8%">总价(元)</th>
                    <th style="width:7%;">状态</th>
                    <th style="width:10%;">备注</th>
                   </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="pageList" var="p" status="st">
                     <tr>
                        <td>
                            <input name="idckb" type="checkbox" id="id" value="${p.orderId}">&nbsp;${st.index+1 }</input>
                        </td>
                        <td width="10%">${p.orderId}</td>
                        <td width="10%">${p.userId}</td>
                        <td width="7%">${p.username }</td>
                        <td width="10%">${p.addressname}</td>
                        <td width="10%">${p.personnum}</td>
                        <!--<td width="12%"><s:date name="#p.deliverytime" format="yyyy-MM-dd hh:MM" /></td>-->
                        <td width="12%">${p.createtimeStr}</td>
                        <td width="8%">${p.summation }</td>
                        <td width="7%">
                           <s:if test="%{#p.flag==2 }"><span id="tdstatus" style="color:red">拒单</span></s:if>
                           <s:if test="%{#p.flag==1 }"><span id="tdstatus" style="color:green">接单</span></s:if>
	                       <s:if test="%{#p.flag==0 }"><span id="tdstatus" style="color:blue">未接单</span></s:if>
                        </td>
                        <td width="10%">${p.description}</td>
                      </tr>
                      <s:if test="%{#p.foodlist.size>0}">
                      <tr>
                        <td></td>
                        <td colspan="9">
                          <div class="panel panel-default" 
                               style="top:5px;line-height:30px;margin-bottom: 2px; background: #FC6; border:1px solid #ccc;">
						     <table class="table table-striped sl_all_bg table-bordered text-left">
                               <thead id="list_head">
				                <tr>
				                    <th style="width:5%;">序号</th>
				                    <th style="width:5%;">食品名称</th>
				                    <th style="width:5%;">数量</th>
				                    <th width="5%">单价(元)</th>
				                </tr>
				               </thead>
				               <tbody id="list_body">
					              <s:iterator value="#p.foodlist" var="food" status="status">
					                 <tr>
						               <td>${status.index+1}</td>
						               <td width="10%">${food.foodName}</td>
		                               <td width="5%">${food.famount}</td>
		                               <td width="5%">${food.price}</td>
						              </tr>
					              </s:iterator>
				              </tbody>
                           </table>
						</div>
						</td>
						</tr>
					</s:if>
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
				$('#btn_jd,#btn_jud').css('display','');
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
				$('#btn_jd,#btn_jud').css('display','');
			}
			var status = $(this).find("input[type='checkbox']").prop('checked');
			if (status) {
				$(this).find("input[type='checkbox']").prop('checked',false);
				$('#btn_jd,#btn_jud').css('display','');
			} else {
				$(this).find("input[type='checkbox']").prop('checked',true);
			}
			$(this).siblings().find('input[id="id"]').prop('checked',false);
		});
		
		function showMenu(obj) {
			var tdstatus = obj.find('#tdstatus').html();
			tdstatus = (tdstatus==null?'':trim(tdstatus));
			if (tdstatus == '未接单') {
				$('#btn_jd,#btn_jud').css('display','');
			} else {
				$('#btn_jd,#btn_jud').css('display','none');
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
		$.post("<%=request.getContextPath() %>/canteenfood/order_control.html?"+arg+"&query.orderId="+id, null, function(data){
			processCall(data, function(){
				location.reload();
			});
		}, "json");	
	}
	</script>
	
	</body>
</html>
