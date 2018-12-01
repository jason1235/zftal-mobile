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
            showWindowV2("增加",_path+"/expertvote/experttask_add.html",360,300);
        });
        $("#btn_xg").click(function(){
        	if($("input[id='id']:checked").length==0){
                alert("请先选中操作行");
                return false;
            }
        	var id=$("input[id='id']:checked").val();
            showWindowV2("修改",_path+"/expertvote/experttask_add.html?model.id="+id,360,250);
        });
        $("#btn_sc").click(function(){
        	if($("input[id='id']:checked").length==0){
                alert("请先选中操作行");
                return false;
            }
        	var id = $("input[id='id']:checked").val();
            $.post(_path+"/expertvote/experttask_remove.html","model.id="+id,function(data){
                if(data.success){
                	refurbish();
                }else{
                    alert(data.text);
                }
            },"json");
        });
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
            id=$(this).find("input:checkbox").val();
            viewDeclare(id);
        });

        fillRows("20", "", "", false);//填充空行
    });

    function viewDeclare(id)
    {
    	location.href = _path+"/expertvote/experttask_detail.html?model.id="+id;
    }
    function refurbish()
    {
    	location.href = _path+"/expertvote/experttask_page.html";
    }
    function zj_createTask(pram){
        $.post(_path+"/expertvote/experttask_save.html",pram,function(data1){
            if(data1.success){
                var o = $(".ymPrompt_close");
                $(".ymPrompt_close").click();
                refurbish();
            }else{
                   alert(data1.text);
               }
        },"json");
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
                            <a id="btn_zj" class="btn_zj">新 增</a>
                        </li>
                        <li>
                            <a id="btn_xg" class="btn_xg">修改</a>
                        </li>
                        <li>
                            <a id="btn_ck" class="btn_ck">查 看</a>
                        </li>
                        <li>
                            <a id="btn_sc" class="btn_sc">删 除</a>
                        </li>
                    </ul>
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
 <form action="<%=request.getContextPath()%>/expertvote/experttask_page.html" name="search" id="search" method="post">
        <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
        <input type="hidden" id="asc" name="asc" value="${asc}"/>
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>评审任务管理<font color="#0457A7" style="font-weight:normal;">  (提示：双击查看详情)</font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"><input type="checkbox" disabled/></td>
                        <td width="5%">序号</td>
                        <td class="sort_titlem_m" id="mc" width="20%">任务名称</td>
                        <td class="sort_titlem_m" id="belong_to_sys" width="20%">归属模块</td>
                        <td class="sort_titlem_m" id="shjb" width="20%">审核级别</td>
                        <td class="sort_titlem_m" id="tgbl" width="20%">推荐比例</td>
                    </tr>
                </thead>
                <tbody id="list_body" >
                    <s:iterator value="pageList" var="p" status="st">
                        <tr name="tr">
                        <td><input type="checkbox" id="id" value="${p.id }"/></td>
                        <td>
                        ${st.index + beginIndex }
                        </td>
                        <td>${p.name }</td>
                        <td>${p.belongToSysName }</td>
                        <td><ct:codeParse catalog="DM_SYS_ZJPSJB" code="${p.level }"/></td>
                        <td>${p.passPoint }</td>
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
