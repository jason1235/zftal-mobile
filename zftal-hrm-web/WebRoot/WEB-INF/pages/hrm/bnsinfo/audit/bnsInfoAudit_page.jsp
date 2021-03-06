<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
          jsImport_<%=pageIndex%>("<%=request.getContextPath()%>/js/tip/tip.js");
            $("#btn_sh").click(function(){//功能条增加按钮
                detail();
            });
            $("#btn_resh").click(function(){//功能条增加按钮
                redetail();
            });
            $("#btn_ck").click(function(){//功能条增加按钮
                view();
            });
            $("#btn_ck2").click(function(){//功能条增加按钮
            	if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
            	var id = $("input[id='id']:checked").val();
                showWindow("查看",_path+"/sp/spworkflow_detail.html?workId="+id,720,400);
            });
            $("#btn_bj").click(function(){//功能条增加按钮
                edit();
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
            
                $("div.buttonbox li:visible:first").find("a").click();
            });
            
            $("button[name='search']").click(function(e){//搜索按钮
                $("#search").attr("action","<%=request.getContextPath()%>/bnsinfo/infoaudit_page.html");
                $("#search").attr("method","post");
                $("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
            function refreshButton(){
            }
            $("select[name='query.status']").val('${query.status}');
            var page=$("input[name=query\\.showCount]").val();
            fillRows(page, "", "", false);//填充空行

            function redetail(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                var classId= $(current).attr("classId");
                $.post(_path+"/bnsinfo/infoaudit_checkNode.html?businessInfoChange.id="+id,"",function(data){
                    if(data.success){
                        goUrl(_path+"/bnsinfo/infoaudit_detail.html?query.classId="+classId+"&businessInfoChange.id="+id+"&reAudit=true");
                    }else{
                        alert(data.text);
                    }
                },"json");
                
            }
            
            function detail(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                var classId= $(current).attr("classId");
                goUrl(_path+"/bnsinfo/infoaudit_detail.html?query.classId="+classId+"&businessInfoChange.id="+id);
            }

            function edit(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                var classId= $(current).attr("classId");
                goUrl(_path+"/bnsinfo/infoaudit_toEdit.html?query.classId="+classId+"&businessInfoChange.id="+id);
 
            }
            
            function view(){
                if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
                var id = $("input[id='id']:checked").val();
                var classId= $(current).attr("classId");
                goUrl(_path+"/bnsinfo/infoaudit_view.html?query.classId="+classId+"&businessInfoChange.id="+id);
                
                
                
            }
                var xmtrs=$("tr[name=tr]");
            for ( var i=0; i<xmtrs.length; i++){
                var xmtr = xmtrs[i];
                var tds = $(xmtr).closest("tr").find("td");
                var xmtd = tds[2];
                var id = $(xmtd).closest("tr").find("input[id='id']").val();
                var classId= $(xmtd).closest("tr").attr("classId");
                var html="<a style=\"color:#074695\" href=\"#\" onclick=\"on('" + id +"','"+classId+ "')\" >"+$(xmtd).html()+ "</a>";
                $(xmtd).html(html);
            }
            
        });
        function on(id,classId){
            var inputObj = document.getElementsByName(id);
            inputObj.checked = true;
            goUrl(_path+"/bnsinfo/infoaudit_view.html?query.classId="+classId+"&businessInfoChange.id="+id);
         }
        
    </script>
  </head>
  <body>
  <div class="tab_cur">
            </div>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <c:if test="${query.status == 'WAIT_AUDITING' || query.status == 'IN_AUDITING'}">
                        <li>
                            <a id="btn_sh" class="btn_sh">审 定</a>
                        </li>
                        </c:if>
                        <li>
                            <a id="btn_ck" class="btn_ck">查 看</a>
                        </li>
                        <li>
                            <a id="btn_ck2" class="btn_ck">流程跟踪</a>
                        </li>
                    </ul>
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
 <form action="infochange/inputaudit_page.html" name="search" id="search" method="post">
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <%--<th width="10%">信息类</th>
          <td width="20%">
            <select name="query.classId" style="width:186px">
                <option value="" <c:if test="${empty(query.classId) }">selected="true"</c:if>>全部</option>
                <c:forEach items="${infoClasses }" var="clazz">
                <option value="${clazz.guid }" <c:if test="${clazz.guid eq query.classId }">selected="true"</c:if>>${clazz.name }</option>
                </c:forEach>
            </select>
          </td> --%>
          <th width="10%">部门</th>
          <td width="20%">
            <ct:codePicker name="query.depId" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.depId }" width="130" />
          </td>
          <th width="10%">姓名</th>
          <td width="18%">
            <input name="query.username" value="${query.username }" type="text" style="width: 130px;" />
          </td>
          
          <th width="10%">职工号</th>
          <td width="18%">
            <input name="query.userId" value="${query.userId }" type="text" style="width: 130px;" />
          </td>
        </tr>
        <tr>
          <th width="10%">状态</th>
          <td colspan="4">
            <select name="query.status" style="width:130px">
                <c:forEach items="${statusArray }" var="d">
                <c:if test="${d.key != 'INITAIL'}"><option value="${d.key }">${d.text }</option></c:if>
                </c:forEach>
            </select>
          </td>
          <td>
            <div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>【${clazz.name }】审核<font color="#0457A7" style="font-weight:normal;">  (提示：双击待审核、审核中时审核，其他则查看)</font></span>
        <input type="hidden" name="query.classId" value="${query.classId }"/>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"><input type="checkbox" disabled/></td>
                        <td width="5%">职工号</td>
                        <td width="10%">姓名 </td>
                        <td width="10%">部门 </td>
                        <%--<td width="10%">录入时间 </td> --%>
                        <td width="10%">提交时间 </td>
                        <td width="10%">审核时间 </td>
                        <td width="10%">审核状态</td>
                    </tr>
                </thead>
                <tbody id="list_body" >
                    <s:iterator value="pageList" var="p" status="st">
                    <tr name="tr" id="${p.id}" classId="${p.classId}">
                        <td><input type="checkbox" id="id" value="${p.id }"/></td>
                        <td width="5%">
                            <input type="hidden" id="status" name="${p.status}" value="${p.status}"/>
                            ${p.userId}
                        </td>
                        <td width="10%">${p.userName}</td>
                        <td width="10%">${p.deptName}</td>
                        <%--<td width="10%">${p.createDateStr } </td> --%>
                        <td width="10%">${p.commitDateStr } </td>
                        <td width="10%">${p.auditDateStr } </td>
                        <td width="10%">${p.status.text }</td>
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
    </div>
    <ct:page pageList="${pageList }" query="${query }" />
    </div>
      </form>
  </body>
</html>
