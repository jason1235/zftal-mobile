<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.infochange.action.InfoInputAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/tip/tip.js");
    $(function(){
            $("#btn_xg").click(function(){//
                modifyDeclare();
            });
            $("#btn_qx").click(function(){//
                cancelDeclare();
            });
            $("#btn_sc").click(function(){//
            	deleteDeclare();
            });
            
            $("#btn_ck").click(function(){//
                viewDeclare();
            });
            $("#btn_zj").click(function(){//
            	createDeclare();
            });
            $("#btn_sx").click(function(){//
                reDeclare();
            });
            $("#btn_jump").click(function(){//
            	window.open("<%=InfoInputAction.getPropertiesValue("kyxtlink") %>");
            });
            $("#btn_ck2").click(function(){//
                var id = $("input[id='id']:checked").val();
                showWindow("查看",_path+"/sp/spworkflow_detail.html?workId="+id,720,400);
            });

            var current = null;

            $("input:checkbox").click(function(e){
                e.stopPropagation();
                if($(this).is(":checked")){
                    $(this).closest("tr").click();
                }else{
                    $(this).closest("tr").removeClass("current");
                }
            });
            $("tbody > tr[name^='tr']").click(
                function(){ //监听单击行
                    $("input:checkbox").removeAttr("checked");
                    $("tbody > tr[name^='tr']").removeClass("current");
                    $(this).attr("class", "current");
                    $(this).find("input:checkbox").attr("checked","checked");
                    current = $(this);
                    refreshButton();
                }
            );
            
            $("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
                $("#btn_ck").click();
            });
            
            function refreshButton(){
                var status = $(current).find("#status").val();
                $("div.buttonbox li").hide();
                if(status == "" || status == "INITAIL" ){
                    $("#btn_zj").parent().show();
                    $("#btn_xg").parent().show();
                    $("#btn_sc").parent().show();
                }else{
                    $("#btn_ck").parent().show();
                    $("#btn_ck2").parent().show();
                }
                if(status == "WAIT_AUDITING"){
                    $("#btn_qx").parent().show();
                }
                if(status == "NO_PASS_AUDITING"){
                    $("#btn_sx").parent().show();
                }
                $("#btn_jump").parent().show();
            }

            var classId = $("#classId").val();
            $("#search").attr("action","<%=request.getContextPath()%>/infochange/infoinput_list.html?classId="+classId);

            var perPageSize=$("input[name=query\\.showCount]").val();
            fillRows(perPageSize, "", "", false);//填充空行
            
            function createDeclare(){
                $.post(_path+"/infochange/infoinput_create.html?classId="+classId,"",function(data){
                    if(data.success){
                        location.href=_path+"/infochange/infoinput_detail.html?classId="+classId+"&infoChange.id="+data.infoChange.id;
                    }else{
                        alert(data.text);
                    }
                },"json");
            }

            function deleteDeclare(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                $.post(_path+"/infochange/infoinput_delete.html?classId="+classId,"infoChange.id="+id,function(data){
                    if(data.success){
                        $("#search").attr("action","<%=request.getContextPath()%>/infochange/infoinput_list.html?classId="+classId);
                        $("#search").attr("method","post");
                        $("#search").submit();
                    }else{
                        alert(data.message);
                    }
                },"json");
            }
            
            function modifyDeclare(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                $.post(_path+"/infochange/infoinput_modify.html?query.classId="+classId,"infoChange.id="+id,
                        function(data){
                    if(data.success){
                        location.href=_path+"/infochange/infoinput_detail.html?classId="+classId+"&infoChange.id="+data.infoChange.id;
                    }else{
                        alert(data.message);
                    }
                },"json");
            }
            
            function cancelDeclare(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                $.post(_path+"/infochange/infoinput_cancel.html?classId="+classId,"infoChange.id="+id,function(data){
                    if(data.success){
                        $("#search").attr("action","<%=request.getContextPath()%>/infochange/infoinput_list.html?classId="+classId);
                        $("#search").attr("method","post");
                        $("#search").submit();
                    }else{
                        alert(data.message);
                    }
                },"json");
            }
            function reDeclare(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                location.href=_path+"/infochange/infoinput_recommit.html?classId="+classId+"&infoChange.id="+id;
                
            }
            function viewDeclare(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                goUrl(_path+"/infochange/infoinput_view.html?classId="+classId+"&infoChange.id="+id);
            }
        });
    
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_zj" class="btn_zj">新 增</a>
                        </li>
                        <li>
                            <a id="btn_xg" class="btn_xg">修 改</a>
                        </li>
                        <li>
                            <a id="btn_sc" class="btn_sc">删除</a>
                        </li>
                        <li>
                            <a id="btn_ck" class="btn_ck">查 看</a>
                        </li>
                        <!--
                        <li>
			                <a id="btn_qx" class="btn_qxsh">取消提交</a>
			            </li>
			            -->
                        <li>
                            <a id="btn_sx" class="btn_sx">重新提交</a>
                        </li>
                        <!--
                        <li>
                            <a id="btn_ck2" class="btn_ck">流程跟踪</a>
                        </li>
                         -->
                    </ul>
            
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
     <form name="search" id="search" method="post">
        <div class="formbox">
		<!--标题start-->
		    <h3 class="datetitle_01">
		        <span>${clazz.name }信息录入<font color="#0457A7" style="font-weight:normal;">  (提示：您录入的信息提交后才能被业务部门审核，信息提交后本人不可再进行编辑。)</font></span>
		        <input type="hidden" id=classId value="${classId }"/>
		    </h3>
		<!--标题end-->
		    <div class="con_overlfow">
		        <table summary="" class="dateline tablenowrap" align="" width="100%">
		            <thead id="list_head">
		                <tr>
		                    <td width="5%"><input type="checkbox" disabled/></td>
		                    <c:forEach items="${clazz.viewables}" var="infoProperty">
		                    <c:if test="${infoProperty.fieldName != 'gh'}">
		                        <td style="white-space: normal;">${infoProperty.name}</td>
		                    </c:if>
		                    </c:forEach>
		                    <td>录入时间 </td>
		                    <td>审核状态</td>
		                </tr>
		            </thead>
		            <tbody id="list_body">
			            <s:iterator value="pageList" var="p" status="st">
			                <tr name="tr" id="${p.id}" classId="${p.classId}">
			                    <td><input type="checkbox" id="id" value="${p.id }"/></td>
			                    <c:forEach items="${clazz.viewables}" var="infoProperty">
			                    <c:if test="${infoProperty.fieldName != 'gh'}">
                                 <td style="white-space: normal;">${p.instance[infoProperty.classFiledName]}</td>
                                </c:if>
			                    </c:forEach>
			                    <td><s:date name="createDate" format="yyyy-MM-dd" /></td>
			                    <td>
			                    <c:if test="${p.status==null||p.status.key=='INITAIL'}">
			                                                                 未提交
			                    </c:if>
			                    <c:if test="${p.status.key!='INITAIL'}">
                                    ${p.status.text }
                                </c:if>
			                    <input type="hidden" id="status" value="${p.status }"/></td>
			                </tr>
			            </s:iterator>
		            </tbody>
		        </table>
		     </div>
	         <ct:page pageList="${pageList }" query="${query }"/>
        </div>
      </form>
  </body>
</html>