<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.dagl.enumobject.ArchiveStatus"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
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
        $("#btn_ck").click(function(){//
            viewData();
        });
        $("#btn_jydj").click(function(){//
            logView("借阅登记",'<%=ArchiveStatus.SAVE.getKey()%>');
        });
        $("#btn_dadc").click(function(){//
        	logView("档案调出",'<%=ArchiveStatus.OUT.getKey()%>');
        });
        $("#btn_sc").click(function(){//
        	logView("档案销毁",'<%=ArchiveStatus.DISABLE.getKey()%>');
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
            }
        );
        
        $("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
            $("#btn_ck").click();
        });

        $("button[name='search']").click(function(e){//搜索按钮
            reflashPage();
            e.preventDefault();//阻止默认的按钮事件，防止多次请求
        });
        $("#archivesStatus").val("${query.status}");
        sort('${sortFieldName}','${asc}');
        fillRows("20", "", "", false);//填充空行
    });

    function viewData(){
        if($("input[id='id']:checked").length==0){
            alert("请先选中操作行");
            return false;
        }
        var id = $("input[id='id']:checked").val();
        goUrl(_path+"/dagl/archives_detail.html?archives.id="+id);
    }
    function editData(){
    	if($("input[id='id']:checked").length==0){
            alert("请先选中操作行");
            return false;
        }
        var id = $("input[id='id']:checked").val();
        showWindow("编辑",_path+"/dagl/archives_toModify.html?archives.id="+id, 650,300);
    } 
    
    function createData(){
        showWindow("增加",_path+"/dagl/archives_toAdd.html", 750,500);
    }
    function logView(titel,status){
    	if($("input[id='id']:checked").length==0){
            alert("请先选中操作行");
            return false;
        }
    	var id = $("input[id='id']:checked").val();
    	showWindow(titel,_path+"/dagl/archives_addLog.html?archives.status="+status+"&archives.id="+id, 650,300);
    }
    function reflashPage(){
        $("#search").attr("action","<%=request.getContextPath()%>/dagl/archives_page.html");
        $("#search").attr("method","post");
        $("#search").submit();
    }
    function reload(){
        goUrl(_path+"/dagl/archives_page.html");
        }
    /*
    *排序回调函数
    */
    function callBackForSort(sortFieldName,asc){
        $("#sortFieldName").val(sortFieldName);
        $("#asc").val(asc);
        $("#search").submit();
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
                            <a id="btn_ck" class="btn_ck">查看详情</a>
                        </li>
                        <s:if test="query.status == 'SAVE'">
                            <li>
	                            <a id="btn_xg" class="btn_xg">编辑</a>
	                        </li>
	                        <li>
	                            <a id="btn_jydj" class="btn_ck">借阅登记</a>
	                        </li>
	                        <li>
	                            <a id="btn_dadc" class="btn_dc">档案调出</a>
	                        </li>
	                        <li>
	                            <a id="btn_sc" class="btn_sc">档案销毁</a>
	                        </li>
                        </s:if>
                    </ul>           
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
 <form action="<%=request.getContextPath()%>/dagl/archives_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">职工号</th>
          <td width="20%">
                <input type="text" style="width:128px" class="text_nor" name="query.gh" value="${query.gh }"/>
          </td>
          <th width="10%">姓名</th>
          <td width="20%">
            <input type="text" style="width:128px" class="text_nor" name="query.xm" value="${query.xm }"/>
          </td>
          <th width="10%">存放位置</th>
          <td width="20%">
            <input type="text" style="width:128px" class="text_nor" name="query.savePoint" value="${query.savePoint }"/>
          </td>
        </tr>
        <tr>
          <th width="10%">档案编号</th>
          <td width="20%">
            <input type="text" style="width:128px" class="text_nor" name="query.bh" value="${query.bh }"/>
          </td>
          <th width="10%">档案描述</th>
          <td width="20%">
            <input type="text" style="width:128px" class="text_nor" name="query.detail" value="${query.detail }"/>
          </td>
          <th width="10%">档案状态</th>
          <td width="20%">
            <select id="archivesStatus" name="query.status" style="width:120px">
                <c:forEach items="${statusList}" var="s">
                    <option value="${s.key }">${s.text }</option>
                </c:forEach>
            </select>
            
          </td>
        </tr>
        <tr> 
      </tbody>
      <tfoot>
        <tr>
          <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
        </tr>
      </tfoot>
    </table>
  </div>
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>档案管理<font color="#0457A7" style="font-weight:normal;">  (提示：双击可以查看选定行)</font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"><input type="checkbox" disabled/></td>
                        <td class="sort_titlem_m" id="t.gh"  width="10%">职工号</td>
                        <td class="sort_titlem_m" id="o.xm"  width="10%">姓名</td>
                        <td class="sort_titlem_m" id="t.dabh"  width="10%">档案编号</td>
                        <td class="sort_titlem_m" id="t.detail"  width="10%">档案描述</td>
                        <td class="sort_titlem_m" id="t.status"  width="10%">档案状态</td>
                        <td class="sort_titlem_m" id="t.savePoint"  width="10%">存放位置</td>
                        <td class="sort_titlem_m" id="dataNum"  width="10%">材料数量</td>
                    </tr>
                </thead>
                <tbody id="list_body" >
                    <s:iterator value="pageList" var="p" status="st">
                        <tr name="tr">
                        <td><input type="checkbox" id="id" value="${p.id }"/></td>
                        <td>${p.gh }</td>
                        <td>${p.xm } </td>
                        <td>${p.bh}</td>
                        <td>${p.detail}</td>
                        <td>${p.statusText}</td>
                        <td>${p.savePoint}</td>
                        <td>${p.dataNum}</td>
                        <%-- <td>${p.statusText }</td> --%>
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
    </div>
    <ct:page pageList="${pageList }" />
    </div>
      </form>
  </body>
</html>
