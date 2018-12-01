<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.dagl.enumobject.ArchiveStatus"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib  
    prefix="fmt"  
    uri="http://java.sun.com/jsp/jstl/fmt"  
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
     <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
       $("#btn_zj").click(function(){//功能条增加按钮
            createData();
        });
        
       $("#btn_xg").click(function(){
            editData();
        });
        
       $("#btn_sc").click(function(){//
        	logView();
        });
        
        $("tbody > tr[name^='tr']").click(
                function(){ //监听单击行
                    var check = $(this).find("input:checkbox").attr("checked");
                    $("input:checkbox").removeAttr("checked");
                    $("tbody > tr[name^='tr']").removeClass("current");
                    if(check == "checked"){
                    	$(this).removeClass("current");
                    	$(this).find("input:checkbox").removeAttr("checked");
                    }else{
                    	$(this).attr("class", "current");
                    	$(this).find("input:checkbox").attr("checked","checked");
                    }
                }
         );
        fillRows("20", "", "", false);//填充空行
    });
    
    function editData(){
    	if($("input[id='id']:checked").length==0){
            alert("请先选中操作行");
            return false;
        }
        var id = $("input[id='id']:checked").val();
        showWindowV1("编辑",_path+"/dagl/materials_toModify.html?materials.classId="+id, 630,180);
    }
    
    function logView(){
    	if($("input[id='id']:checked").length==0){
            alert("请先选中操作行");
            return false;
        }
    	var id = $("input[id='id']:checked").val();
    	$.post("<%=request.getContextPath()%>/dagl/materials_removeData.html",
                "materials.classId="+id,function(data){
                    if(data.success){
                	$("#search").submit();
	                }else{
	                    alert(data.text);
	                }
                            
            },"json");
    }
    
    
    function createData(){
        showWindowV1("增加",_path+"/dagl/materials_toAdd.html", 780,530);
    }
    
    
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_zj" class="btn_zj">增加</a>
                        </li>
                            <li>
	                            <a id="btn_xg" class="btn_xg">归还时间登记</a>
	                        </li>
	                        <li>
	                            <a id="btn_sc" class="btn_sc">删除</a>
	                        </li>
                    </ul>           
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
  
  <form action="<%=request.getContextPath()%>/dagl/materials_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
 
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">借阅人职工号</th>
          <td width="20%">
                <input type="text" style="width:128px" class="text_nor" name="query.classGh" value="${query.classGh }"/>
          </td>
          <th width="10%">借阅人姓名</th>
          <td width="20%">
            <input type="text" style="width:128px" class="text_nor" name="query.classXm" value="${query.classXm }"/>
          </td>
          <th width="10%">借阅材料名称</th>
          <td width="20%">
            <input type="text" style="width:128px" class="text_nor" name="query.classClmc" value="${query.classClmc }"/>
          </td>
        </tr>
        <tr> 
      </tbody>
      <tfoot>
        <tr>
          <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" >查 询</button>
            </div>
          </td>
        </tr>
      </tfoot>
    </table>
  </div>
 
 
 
 
 <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>档案材料借阅登记<font color="#0457A7" style="font-weight:normal;">  (提示：双击可以查看选定行)</font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"></td>
                        <td class="sort_titlem_m" id="t.gh"  width="10%">借阅人职工号</td>
                        <td class="sort_titlem_m" id="o.xm"  width="10%">借阅人姓名</td>
                        <td class="sort_titlem_m" id="t.bh"  width="10%">借阅材料编号</td>
                        <td class="sort_titlem_m" id="t.clmc"  width="10%">借阅材料名称</td>
                        <td class="sort_titlem_m" id="t.jysj"  width="10%">借阅时间</td>
                        <td class="sort_titlem_m" id="t.ghsj"  width="10%">归还时间</td>
                    </tr>
                </thead>
                <tbody id="list_body" >
                    <s:iterator value="list" var="p" status="st">
                        <tr name="tr">
                        <td><input type="checkbox" id="id" value="${p.classId }"/></td>
                        <td>${p.classGh }</td>
                        <td>${p.classXm } </td>
                        <td>${p.classDescribe}</td>
                        <td>${p.classClmc}</td>
                        <td><fmt:formatDate value="${p.classJysj }" type="date"/></td>
                        <td><fmt:formatDate value="${p.classGhsj }" type="date"/></td>
                        <%-- <td>${p.statusText }</td> --%>
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
    </div>
    <ct:page pageList="${list }" />
    </div>
 
 
 
 
 </form>
  </body>
</html>
  