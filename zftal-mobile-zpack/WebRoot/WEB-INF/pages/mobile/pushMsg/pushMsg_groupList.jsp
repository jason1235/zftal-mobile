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
            	parent.modalClose();
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
                parent.showResult(array,"groupList");
                parent.modalClose();
                //window.returnValue = array;
                //if(window.opener){
                //    window.opener.returnValue = array;
                //}
                //window.close();
                //$(".ymPrompt_close",window.parent.document).click();
            });

            $("tbody > tr").click(function(){
                var cbx = $(this).find("input[name='cbox']");
                if (!$(cbx).attr("checked")  ) {
                    $(this).attr("class", "current");
                    var name = $(this).find("input[name='name']").val()
                    $(cbx).attr("checked", true);
                }else{
                	removeItem($(cbx).val());
                    }
            });
            //监听复选框
            $("input[name='cbox']").click( function() {
                var $tr = $(this).closest("tr");
                
                if (this.checked ) {
                    $tr.attr("class", "current");
                    var name = $( this).closest("tr").find("input[name='name']").val()
                } else {
                	removeItem($(this).val());
                }
                
            });

            //监听全选复选框
            $("#checkAll").click( function() {
                if ( $(this).attr("checked") ) {
                    $("#MyTable_tableData").find("input[name='cbox']").each(function(){
                        var $tr = $(this).closest("tr");
                        $tr.attr("class", "current");
                        var name = $( this).closest("tr").find("input[name='name']").val()
                        $(this).attr("checked", true);
                    });
                } else {
                    $("#MyTable_tableData").find("input[name='cbox']").each(function(){
                        removeItem($(this).val());
                    });
                }
                
            });
            FixTable("MyTable", 0, 443, 340); 
            $("input[name='ghList']").each(function(){
                var tr = $("#MyTable_tableData").find("tr[pk=\"pk_"+$(this).val()+"\"]");
               $(tr).find("input[name='cbox']").attr("checked", true);
               $(tr).attr("class", "current");
           });
            function removeItem(value){
                var tr = $("#MyTable_tableData").
                     find("tr[pk=\"pk_"+value+"\"]");
                $(tr).find("input[name='cbox']").attr("checked", false);
                $(tr).removeClass("current");
            }
        });
    </script>
</head>
  
<body>
<div class="container_ydht sl_all_bg">
    <form action="<%=request.getContextPath() %>/pushMsg/pushMsg_groupList.html" method="post">
        <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">分组名称</label>
                        <div class="col-xs-7">
                            <input name="groupQuery.name" class="form-control" value="${groupQuery.name }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">分组描述</label>
                        <div class="col-xs-7">
                            <input name="groupQuery.detail" class="form-control" value="${groupQuery.detail }"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <div class="toolbox" style="text-align:right;width:100%;">
                    <div class="btn-group" id="but_ancd">
                        <button type="button" class="btn btn-primary" id="btn_cx">查 询</button>
                        <button type="button" class="btn btn-primary" id="btn_qd">确 定</button>
                        <button type="button" class="btn btn-primary" id="btn_qx">取 消</button>
                    </div>
                </div>
            </div>
        </div>

        <table id="MyTable" class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;"><input type="checkbox" id="checkAll"/></th>
                    <th>分组名称</th>
                    <th>分组描述</th>
                    <th>创建时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
            <c:forEach items="${list}" var="p" varStatus="i">
                <tr pk="pk_${p.id}">
                    <td width="5%">
                        <input type="checkbox" name="cbox" value="${p.id}" />
                        <input type="hidden" name="name" value="${p.name}" />
                    </td>
                    <td>${p.name }</td>
                    <td>${p.detail}</td>
                    <td><fmt:formatDate value="${p.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${groupQuery }" />

    </form>
</div>
<%--
    <form action="<%=request.getContextPath() %>/pushMsg/pushMsg_groupList.html" method="post">
      <div class="search_advanced" id="myTbody3">
        <div class="searchtab">
            <table width="100%">
                <tfoot>
                    <tr>
                        <td colspan="6">
                            <div class="bz">
                                <label>
                                </label>
                            </div>
                            <div class="btn">
                                <button id="btn_cx" class="button" type="button" name="查询">查 询</button>
                                <button id="btn_qd" class="button" type="button" name="确定">确 定</button>
                                <button id="btn_qx" class="button" type="button" name="取消">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                
                <tbody>
                    <th width="10%">分组名称</th>
			          <td width="20%">
			            <input name="groupQuery.name" value="${groupQuery.name }"/>
			          </td>
			          <th width="10%">分组描述</th>
			          <td width="20%">
			            <input name="groupQuery.detail" value="${groupQuery.detail }"/>
			          </td>
                    
                </tbody>
            </table>
        </div>
    
    
    <div id="lockTable" style="height:200px;">
        <h3 class="datetitle_01">
            <span>人员信息<font color="red" style="font-weight:normal;">（提示：双击一行可以选定）</font></span>
        </h3>
        <table id="MyTable" cellpadding="0" cellspacing="0" class="dateline nowrap" style="width: 443px;">
            <thead id="list_head">
                <tr>
                    <td>
                        <input type="checkbox" id="checkAll"/>
                    </td>
                        <td width="20%">分组名称</td>
                        <td width="40%">分组描述</td>
                        <td width="20%">创建时间</td>
                    </tr>
            </thead>
            <tbody id="list_body">
                <c:forEach items="${list}" var="p" varStatus="i">
                <tr pk="pk_${p.id}">
                    <td width="5%">
                        <input type="checkbox" name="cbox" value="${p.id}" />
                        <input type="hidden" name="name" value="${p.name}" />
                    </td>
                    <td>${p.name }</td>
                    <td>${p.detail}</td>
                    <td><fmt:formatDate value="${p.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    <ct:page pageList="${list }" queryName="groupQuery"/>
    </div>
    </form> --%>
    <script type="text/javascript">
        //fillRows(11, '', '', false);
    </script>
</body>
</html>
