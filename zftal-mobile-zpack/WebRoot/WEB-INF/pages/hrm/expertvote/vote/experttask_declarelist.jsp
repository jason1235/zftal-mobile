<%@ page language="java"
    import="java.util.*,com.zfsoft.hrm.config.ICodeConstants"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <%@ include file="/commons/hrm/head.ini"%>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
        <script type="text/javascript">
    $(function(){
        
        $("tbody > tr[name^='tr']").dblclick(function() { //双击流程跟踪
            var id = $(this).attr("guid");
            //workFlow(id);
        });

        if("${instanceQuery.taskStatus}"!="PASS"){
            $("td[showType='send']").remove();
        }
        fillRows("10", "list_head_declare", "list_body_declare", false);//填充空行
    });

    /*
     *排序回调函数
     */
    function callBackForSort(sortFieldName, asc) {
        $("#sortFieldName").val(sortFieldName);
        $("#asc").val(asc);
        $("#search").submit();
    }
</script>
    </head>
    <body>
        <form
            enctype="application/x-www-form-urlencoded"
            name="search" id="search" method="post">
            <input type="hidden" id="sortFieldName" name="sortFieldName"
                value="${sortFieldName}" />
            <input type="hidden" id="asc" name="asc" value="${asc}" />
            <input type="hidden" name="instanceQuery.taskStatus" value="${instanceQuery.taskStatus}" />

            <div class="formbox">
                <!--标题start-->
                <h3 class="datetitle_01">
                    <span>申报信息列表<font color="#0457A7" style="font-weight:normal;"></font></span>
                </h3>
                <!--标题end-->
                <div class="con_overlfow">
                    <table width="100%" class="dateline tablenowrap" id="tiptab">
                        <thead id="list_head_declare">
                            <tr>
                                <td width="5%">
                                    序号
                                </td>
                                <td class="sort_titlem_m" width="20%">
                                    职工号
                                </td>
                                <td class="sort_titlem_m" width="20%">
                                    姓名
                                </td>
                                <td class="sort_titlem_m" width="20%">
                                    部门
                                </td>
                                <td class="sort_titlem_m" width="20%">
                                   上报时间
                                </td>
                                <td class="sort_titlem_m" width="20%">
                                   审核时间
                                </td>
                                <td class="sort_titlem_m" width="20%" showType="send">
                                   参与投票人数
                                </td>
                                <td class="sort_titlem_m" width="20%" showType="send">
                                   赞成人数
                                </td>
                            </tr>
                        </thead>
                        <tbody id="list_body_declare">
                            <s:iterator value="declareList" var="p" status="st">
                                <tr name="tr" guid="${p.id }">
                                    <td>
                                        ${st.index + 1 }
                                    </td>
                                    <td>
                                        ${p.gh }
                                    </td>
                                    <td>
                                        ${p.name }
                                    </td>
                                    <td>
                                        <ct:codeParse code="${p.department }"
                                            catalog="<%=ICodeConstants.DM_DEF_ORG %>" />
                                    </td>
                                    <td>
                                       <s:date name="declareTime" format="yyyy-MM-dd" />
                                    </td>
                                    <td>
                                        <s:date name="auditTime" format="yyyy-MM-dd" />   
                                    </td>
                                    <td showType="send">
                                        ${p.expertNum }
                                    </td>
                                    <td showType="send">
                                        ${p.allowNum }
                                    </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </div>
                <ct:page pageList="${declareList }" query="${instanceQuery }"
                    queryName="instanceQuery" />
            </div>
        </form>
    </body>
</html>