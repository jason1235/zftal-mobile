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
       //sort('${sortFieldName}','${asc }');
        
        $("#btn_zj").click(function(){
            showWindowV2("增加",_path+"/expertvote/experttask_add.html",360,250);
        });
        $("#btn_xg").click(function(){
        	showConfirm("提交评审结果后将不能再对这些结果进行更改，您真的要提交评审结果吗？");
            $("#why_cancel").click(function(){divClose();});
            $("#why_sure").click(function(){
            	$.post(_path+'/expertvote/expertassess_saveResult.html','query.year=${query.year}'
                        +'&query.groupId=${query.groupId}&query.taskId=${query.taskId}',function(data){
                    if(data.success){
                        $('.btn_cx').click();
                    }else{
                        alert(data.text);
                    }
                    return false;
                },"json");
                $('#windown-close').click();
            });
        });
        $("#btn_ck").click(function(){
            if($("input[id='id']:checked").length!=1){
                alert("请先选中操作行");
                return false;
            }
            var id = $("input[id='id']:checked").val();
            showWindow("申报信息",_path+"/expertvote/expertassess_detail.html?model.id="+id,600,600);
        });
        $("input:checkbox").click(function(e){
            e.stopPropagation();
            if($(this).is(":checked")){
                $(this).closest("tr").click();
            }else{
                $(this).closest("tr").removeClass("current");
            }
        });
        $("#groupId").change(function(){
        	var groupId = $("#groupId").val();
             $.post(_path+'/expertvote/expertassess_findTaskList.html',$("#groupId").serialize(),function(data){
                 if(data.success){
                     var strHtml="";
                     for(var i = 0;i<data.list.length;i++){
                         strHtml += "<option value='"+data.list[i].id+"'>"+data.list[i].name+"</option>";
                     }
                     $("#taskId").html(strHtml);
                 }else{
                     alert(data.text);
                 }
             },"json");
            
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
        $("tbody > tr[name^='tr']").dblclick(
                function(){ //监听单击行
                    var id = $(this).find("input[type='checkbox']").val();
                    showWindow("申报信息",_path+"/expertvote/expertassess_detail.html?model.id="+id,600,400);
                }
            );

        $(".expertResult").click(function(){
        	saveResult(this);
        });

        var list = $(".result");
        for(var i=0;i<list.size();i++){
            var v = $(".result")[i];
            var id=$(v).attr("id").substr(2);
            var val=$(v).val();
            $("#"+id+"_"+val).attr("checked","checked");
        }

        $("#groupId").val("${query.groupId}");
        fillRows("20", "", "", false);//填充空行
    });
    /*
     *排序回调函数
     */
     function callBackForSort(sortFieldName,asc){
         $("#sortFieldName").val(sortFieldName);
         $("#asc").val(asc);
         $("#search").submit();
     }


     function saveResult(e){
         var modelId = $(e).attr("name");
         var val=$(e).val();
         var old=$("#r_"+modelId).val();
         if(val==old) return false;
          $.post(_path+'/expertvote/expertassess_saveTask.html','model.id='+modelId
                  +'&model.result='+val+'&query.year=${query.year}'
                  +'&query.groupId=${query.groupId}&query.taskId=${query.taskId}',function(data){
              if(data.success){
            	  $("#r_"+modelId).val(val);
            	  $("#passNum").html(data.text);
              }else{
	              $("input[name='"+modelId+"']").removeAttr("checked");
	              $("#"+modelId+"_"+old).attr("checked","checked");
	              alert(data.text);
              }
        	  return false;
          },"json");
     }
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_xg" class="btn_xg">提交</a>
                        </li>
                        <li>
                            <a id="btn_ck" class="btn_ck">查 看</a>
                        </li>
                    </ul>
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
 <form action="<%=request.getContextPath()%>/expertvote/expertassess_page.html" name="search" id="search" method="post">
        <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
        <input type="hidden" id="asc" name="asc" value="${asc}"/>
        <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">年度</th>
          <td width="20%">
             <input name="query.year" value="${query.year }" type="text" 
             style="width: 130px;" onfocus="WdatePicker({dateFmt:'yyyy'})" class="Wdate"/>
          </td>
          <th width="10%">专家组</th>
          <td width="20%">
            <select id="groupId" name="query.groupId" style="width:186px">
            <c:forEach items="${groups }" var="g">
            <option value="${g.id }">${g.name }</option>
            </c:forEach>
            </select>
          </td>
          <th width="10%">任务</th>
          <td width="20%">
            <select id="taskId" name="query.taskId" style="width:186px">
            <c:forEach items="${taskList }" var="t">
            <option value="${t.id }">${t.name }</option>
            </c:forEach>
            </select>
          </td>
        </tr>
      </tbody>
      <tfoot>
        <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" type="submit">查 询</button>
            </div>
          </td>
      </tfoot>
    </table>
  </div>
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>用户评审<font color="#0457A7" style="font-weight:normal;">  
        (评审总数：<b id="sum">${sum }</b>条，
                        最大赞成数：<b id="allowNum">${allowNum }</b>条，
                        当前赞成数：<b id="passNum">${passNum }</b>条)
        </font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"><input type="checkbox" disabled/></td>
                        <td width="5%">序号</td>
                        <td class="sort_titlem_m" id="gh">职工号</td>
                        <td class="sort_titlem_m" id="xm">姓名</td>
                        <td class="sort_titlem_m" id="dwh">部门</td>
                        <td class="sort_titlem_m" id="tgbl">评审结果</td>
                    </tr>
                </thead>
                <tbody id="list_body" >
                    <s:iterator value="pageList" var="p" status="st">
                        <tr name="tr">
                        <td><input type="checkbox" id="id" value="${p.id }"/></td>
                        <td>
                        ${st.index + beginIndex }
                        </td>
                        <td>${p.gh }</td>
                        <td>${p.xm }</td>
                        <td><ct:codeParse code="${p.dwm }"
                             catalog="<%=ICodeConstants.DM_DEF_ORG %>" />
                        </td>
                        <td>
                            <c:if test="${p.status == '0'}">
                            <input type="radio" class="expertResult" value="1" name="${p.id }" id="${p.id }_1"/>同意
                            <input type="radio" class="expertResult" value="0" name="${p.id }" id="${p.id }_0"/>反对
                            <input type="hidden" class="result" id='r_${p.id }' value="${p.result }"/>
                            </c:if>
                            <c:if test="${p.status != '0'}">
                                <c:if test="${p.result != '1'}">
                                                                                                 反对
                                </c:if>
                                <c:if test="${p.result == '1'}">
                                                                                                同意
                                </c:if>
                            </c:if>    
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
