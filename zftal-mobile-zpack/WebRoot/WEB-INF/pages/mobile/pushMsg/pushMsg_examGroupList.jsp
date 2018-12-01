<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/infoclasstag.tld" prefix="clazz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <style>
        .pagination{
            margin-top:0px;
            margin-bottom:0px;
        }
    </style>
    
    
    <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=stylePath %>/js/lockTableTitle.js"></script>
    
    <script type="text/javascript">
        window.returnValue = null;

        $(function(){
            //监听【查询】按钮点击事件
            $("#btn_cx").click( function() {
                $("form").submit();
            });

            //监听【取消】按钮点击事件
            $("#btn_qx").click( function() {
                //window.close();
            	window.parent.$('#myModal_toji').modal('hide');
            	window.parent.$('#index').modal('hide');
                return false;
            });

            //监听【确定】按钮点击事件
            $("#btn_qd").click( function() {
                var $checks = $("input[name='cbox']:checked");

                var array=new Array();

                for( var i = 0; i < $checks.length; i++ ) {
                	array[i]=new Array();
                    $check = $checks.get(i);
                    array[i][0] = $($check).val();
                    array[i][1] = $( $check).closest("tr").find("input[name='name']").val();
                }
                parent.showResult(array);
            });

            $("tbody > tr").click(function(){
            	var status = $(this).find('input[name="cbox"]').prop('checked');
	       		 if (status) {
	       			$(this).find('input[name="cbox"]').removeAttr('checked');
	       			$(this).removeClass("current");
	       			deleteqz($(this).find('input[name="cbox"]'));
	       		 } else {
	       		    $(this).attr("class", "current");
	       			$(this).find('input[name="cbox"]').prop('checked',true);
	                addqz($(this).find('input[name="cbox"]'));
	       		 }
            	
                //var cbx = $(this).find("input[name='cbox']");
                //if (!$(cbx).attr("checked")  ) {
                //    $(this).attr("class", "current");
                //    var name = $(this).find("input[name='name']").val()
                //    $(cbx).attr("checked", true);
                //}else{
                //	removeItem($(cbx).val());
                //}
            });
            //监听复选框
            $("input[name='cbox']").click( function() {
            	//e.stopPropagation();
				$(this).parent().parent().toggleClass('current');
				var status = $(this).prop('checked');
	       		 if (status) {
	       			$(this).removeAttr('checked');
	       			$(this).removeClass("current");
	       			deleteqz(this);
	       		 } else {
	       		    $(this).attr("class", "current");
	       			$(this).prop('checked',true);
	                addqz(this);
	       		 }
                //var $tr = $(this).closest("tr");
                //if (this.checked ) {
                //    $tr.attr("class", "current");
                //    var name = $( this).closest("tr").find("input[name='name']").val()
                //} else {
                //	removeItem($(this).val());
                //}
            });

            //监听全选复选框
            $("#checkAll").click( function() {
                if ( $(this).attr("checked") ) {
                    $("#MyTable").find("input[name='cbox']").each(function(){
                        var $tr = $(this).closest("tr");
                        $tr.attr("class", "current");
                        var name = $( this).closest("tr").find("input[name='name']").val()
                        $(this).attr("checked", true);
		                addqz(this);
                    });
                } else {
                    $("#MyTable").find("input[name='cbox']").each(function(){
                        removeItem($(this).val());
                        deleteqz(this);
                    });
                }
            });
            //FixTable("MyTable", 0, 443, 340); 
            $("input[name='ghList']").each(function(){
                var tr = $("#MyTable").find("tr[pk=\"pk_"+$(this).val()+"\"]");
               $(tr).find("input[name='cbox']").attr("checked", true);
               $(tr).attr("class", "current");
           });
        });
        function removeItem(value){
            var tr = $("#MyTable").
                 find("tr[pk=\"pk_"+value+"\"]");
            $(tr).find("input[name='cbox']").attr("checked", false);
            $(tr).removeClass("current");
        }
        function selectAllOrCancel(obj,name){//全选选择框操作
			var checks = document.getElementsByName(name);
			var body = document.getElementById("list_body");
			var tr = body.getElementsByTagName("tr");
			if(obj.checked){
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='current';
					checks[i].checked = true;
				}
			}else{
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='';
					checks[i].checked = false;
				}
			}
		}
        
        function addqz(obj){
        	//var $checks = $("input[name='cbox']:checked");
            var array=new Array();

            //for( var i = 0; i < $checks.length; i++ ) {
            	array[0]=new Array();
                //$check = $checks.get(i);
                array[0][0] = $(obj).val();
                array[0][1] = $(obj).closest("tr").find("input[name='name']").val();
            //}
            parent.showResult(array);
        }
        function deleteqz(obj){
        	//var $checks = $("input[name='cbox']:checked");
            var array=new Array();

            //for( var i = 0; i < $checks.length; i++ ) {
            	array[0]=new Array();
                //$check = $checks.get(i);
                array[0][0] = $(obj).val();
                array[0][1] = $(obj).closest("tr").find("input[name='name']").val();
            //}
            parent.deleteResult(array);
        }
    </script>
</head>
  
<body>
<div class="container_ydht sl_all_bg" style="padding-bottom: 0px;padding-top: 0px;">
    <form action="<%=request.getContextPath() %>/pushMsg/pushMsg_examGroupList.html" method="post">
	    <input type="hidden" value="${qzidList }" name="qzidList"/>
	    <input type="hidden" value="${papermainid }" name="papermainid"/>
        <div class="form-horizontal sl_all_form" style="padding-bottom: 0px;">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">分组名称</label>
                        <div class="col-xs-7">
                            <input name="groupQuery.name" class="form-control" value="${groupQuery.name }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">分组描述</label>
                        <div class="col-xs-7">
                            <input name="groupQuery.detail" class="form-control" value="${groupQuery.detail }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <div class="toolbox" style="text-align:right;width:100%;">
                        <button type="button" class="btn btn-primary" id="btn_cx" style="margin-right: 20px;">查 询</button>
                        <%--<button type="button" class="btn btn-primary" id="btn_qd">确 定</button>
                        --%><button type="button" class="btn btn-primary" id="btn_qx">关闭页面</button>
                </div>
                    </div>
                </div>
            </div>
        </div>

        <table id="MyTable" class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;"><input type="checkbox" id="checkAll" onclick="selectAllOrCancel(this,'cbox');"/></th>
                    <th>分组名称</th>
                    <th>分组描述</th>
                    <th>创建时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
            <c:forEach items="${list}" var="p" varStatus="i">
           	
                <tr pk="pk_${p.id}">
                    <td width="5%">
                        <input type="checkbox" name="cbox" value="${p.id}" 
                         <c:if test = "${qzid != null && qzid != ''}"> 
	                        <c:forEach items="${qzid}" var="qz" varStatus="i">
	                        <c:if test = "${qz eq p.id}"> 
	                        checked="checked" 
	                        </c:if> 
	                        </c:forEach>
                         </c:if> 
                        />
                        <input type="hidden" name="name" value="${p.name}" />
                    </td>
                    <td>${p.name }</td>
                    <td>${p.detail}</td>
                    <td><fmt:formatDate value="${p.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                </tr>
             
             </c:forEach>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${groupQuery }" queryName="groupQuery"/>

    </form>
</div>
    <script type="text/javascript">
        //fillRows(11, '', '', false);
    </script>
</body>
</html>
