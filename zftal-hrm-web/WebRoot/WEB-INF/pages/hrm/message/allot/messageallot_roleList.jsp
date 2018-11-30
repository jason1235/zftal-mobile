<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib uri="/WEB-INF/infoclasstag.tld" prefix="clazz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    
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
                window.close();
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

                window.returnValue = array;
                if(window.opener){
                    window.opener.returnValue = array;
                }
                window.close();
            });

            $("tbody > tr").click(function(){
                var cbx = $(this).find("input[name='cbox']");
                if (!$(this).is(".current")  ) {
                    $(this).attr("class", "current");
                    var name = $(this).find("input[name='name']").val()
                    $(cbx).attr("checked", true);
                }else{
                    removeItem($(cbx).val());
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
    <form action="<%=request.getContextPath() %>/message/allot_jsglList.html" method="post">
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
                    <th width="10%">角色名称</th>
                      <td width="20%">
                        <input name="jsglQuery.jsmc" value="${jsglQuery.jsmc }"/>
                      </td>
                      <td></td><td></td>
                </tbody>
            </table>
        </div>
    
    
    <div id="lockTable" style="height:200px;">
        <h3 class="datetitle_01">
            <span>角色信息<font color="red" style="font-weight:normal;">（提示：双击一行可以选定）</font></span>
        </h3>
        <table id="MyTable" cellpadding="0" cellspacing="0" class="dateline nowrap" style="width: 443px;">
            <thead id="list_head">
                <tr>
                    <td  width="5%">
                        <input type="checkbox" id="checkAll"/>
                    </td>
                        <td width="20%">角色名</td>
                        <td width="40%">说明</td>
                    </tr>
            </thead>
            <tbody id="list_body">
                <c:forEach items="${pageList}" var="p" varStatus="i">
                <tr pk="pk_${p.jsdm}">
                    <td>
                        <input type="checkbox" name="cbox" value="${p.jsdm}" />
                        <input type="hidden" name="name" value="${p.jsmc}" />
                    </td>
                    <td>${p.jsmc }</td>
                    <td>${p.jssm}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    <ct:page pageList="${pageList }" queryName="messageAllot"/>
    </div>
    </form>
    <script type="text/javascript">
        fillRows(11, '', '', false);
    </script>
</body>
</html>