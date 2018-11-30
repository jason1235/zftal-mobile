<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
    	   operationList();
    	   operationHover();
            $("#queryType").val("${query.type}");//初始化操作栏目
            fillRows("20", "", "", false);//填充空行
    });
    
    function operationList(){

    	
        $("a[name='download']").click(function(){
        	var id = $(this).closest("tr").find("input[id='guid']").val();
            <c:if test="${managepage}">
            	showWindow("人员选择",_path+"/export/export_choose.html?exportConfig.id="+id, 480, 180);
            </c:if>
            <c:if test="${managepage!=true}">
            var url = _path+"/export/export_export.html?exportConfig.id=" + id;
            window.open( url, "400", "300", true);
            </c:if>
        });
        
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
 <form enctype="application/x-www-form-urlencoded" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">申报表名称</th>
          <td width="20%">
            <input name="query.name" value="${query.name }"/>
          </td>
          <th width="10%">申报表类型</th>
          <td width="20%">
            <select id="queryType" name="query.type" style="width:180px">
                <option value="" >不限</option>
                <c:forEach items="${typeList}" var="t">
                <option value="${t.id }">${t.name }</option>
                </c:forEach>
            </select>
          </td>
        <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" type="submit">查 询</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>文件下载<font color="#0457A7" style="font-weight:normal;"></font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td class="sort_title_current_${asc }" id="NAME" width="30%">申报表名称</td>
                        <td class="sort_title_current_${asc }" id="TYPE" width="20%">申报表类型</td>
                        <td class="sort_title_current_${asc }" id="CREATOR" width="15%">创建者</td>
                        <td class="sort_title" id="LAST_MODIFY" width="20%">更新时间</td>
                        <td width="15%">操作</td>
                    </tr>
                </thead>
                <tbody id="list_body" >
                    <s:iterator value="pageList" var="file">
                        <tr name="tr">
                        <input type="hidden" id="guid" value="${file.id }"/>
                        <td>${file.name }</td>
                        <td>
                            <c:forEach items="${typeList}" var="t">
			                 <c:if test="${t.id == file.type}">${t.name}</c:if>
			                </c:forEach>
                        </td>
                        
                        <td><ct:PersonParse code="${file.creator }"/></td>
                        <td><s:date name="lastModify" format="yyyy-MM-dd" /></td>
                        <td>
                          <div>
                            <div class="current_item">
                                <span class="item_text">下载</span>
                            </div>
                            <div class="select_tools" id="select_tools1" style=" width:80px; display:none">
                                <ul>
                                    <li><a name="download" href="#" class="tools_list">下载</a></li>
                                </ul>
                            </div>
                          </div>
                        </td>
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
