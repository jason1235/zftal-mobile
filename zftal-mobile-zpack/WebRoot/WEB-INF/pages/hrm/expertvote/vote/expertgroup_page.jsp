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
        sort('${sortFieldName}','${asc }');
        
        $("#btn_zj").click(function(){
        	showWindowV2("增加",_path+"/expertvote/expertgroup_add.html",360,250);
        });

		$("#btn_xg").click(function(){
			viewDeclare();
		});
		
		$("#btn_sc").click(function(){
			if($("input[id='id']:checked").length==0){
				alert("请选中操作行");
			}
			delDeclare();
		});
		
        $("input:checkbox").click(function(e){
            e.stopPropagation();
            if($(this).is(":checked")){
                $(this).closest("tr").click();
            }else{
                $(this).closest("tr").removeClass("current");
            }
        });

        var groups = $("input:checkbox");
        $.each($("input:checkbox"),function(j,n) {
	            id=$(groups[j]).val();
	            if(id!=null){
	            	getSjfw(id);
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
            viewDeclare();
        });

        fillRows("20", "", "", false);//填充空行
    });

    function getSjfw(groupId){
        $.post( "<%=request.getContextPath() %>/dataprivilege/deptFilter_getOrgsTextForRow.html",{"deptFilter.userId":groupId,"deptFilter.roleId":"expertgroup"},function(data){
            $("#sjfw_"+groupId).html(data.simple);
            $("#sjfw_"+groupId).attr("name",data.whole);
            $("#sjfw_"+groupId).mousemove(function(){
                datatips($(this));
            })
        });
    }
    
    function delDeclare(){
   		showConfirm("确定要删除吗?");
   		$("#why_cancel").click(function(){
   			divClose();
   		});
   		$("#why_sure").click(function(){
   			var id = $("input[id='id']:checked").val();
    		$.post(_path+"/expertvote/expertgroup_delete.html","model.id="+id,function(data){
				var callback = function(){
					$("#search").submit();
				};
				processDataCall(data,callback);
	    	},"json");
   		});
    }
    
    function datatips(obj){
        var x = 0;  //设置偏移量
        var y = 20;
        var padding_right = 0;
        var t = jQuery(obj);
        var l = 100;
        t.mouseover(function(e){
            var datatip = "<div id=\"datatip\" style=\"width:200px;z-index:9999;display:none;position:absolute;padding:10px;border:1px solid #999; color:#0457A7; background: #F2F2F2;\"></div>"; //创建 div 元素
            var tip = jQuery(datatip);
            var data = jQuery(obj).attr("name");
            jQuery(tip).append(data);
            jQuery("body").append(tip); //把它追加到文档中
            l = jQuery(tip).outerWidth();
            jQuery("#datatip")
                .css({
                    "top": (e.pageY+y) + "px",
                    "left": checkX(e.pageX)  + "px"
                }).show("fast");      //设置x坐标和y坐标，并且显示
        });
        t.mouseout(function(){      
            jQuery("#datatip").remove();   //移除 
        });
        t.mousemove(function(e){
            jQuery("#datatip")
                .css({
                    "top": (e.pageY+y) + "px",
                    "left": checkX(e.pageX)  + "px"
                });
        });
        
        function checkX(mouseX){
            var width = jQuery(document).width();
            var border = width-l-x-padding_right;
            if(mouseX+x<border){
                return mouseX+x;
            }else{
                return mouseX-l;
            }
        }
    }
    function viewDeclare(){
        if($("input[id='id']:checked").length==0){
            alert("请先选中操作行");
            return false;
        }
        var id = $("input[id='id']:checked").val();
        var path="/attendance/declare_page.html"
        goUrl(_path+"/expertvote/expertgroup_detail.html?model.id="+id);
}

    function zj_createGroup(pram){
        $.post(_path+"/expertvote/expertgroup_save.html",pram,function(data1){
            if(data1.success){
                var o = $(".ymPrompt_close");
                $(".ymPrompt_close").click();
                goUrl(_path+"/expertvote/expertgroup_detail.html?model.id="+data1.id);
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
                            <a id="btn_xg" class="btn_xg">修 改</a>
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
 <form action="<%=request.getContextPath()%>/expertvote/expertgroup_page.html" name="search" id="search" method="post">
        <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
        <input type="hidden" id="asc" name="asc" value="${asc}"/>
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>专家组管理<font color="#0457A7" style="font-weight:normal;">  (提示：双击查看详情)</font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"><input type="checkbox" disabled/></td>
                        <td width="5%">序号</td>
                        <td class="sort_titlem_m" id="name" width="20%">组名</td>
                        <td class="sort_titlem_m" id="type" width="20%">专业领域</td>
                        <td class="sort_titlem_m" id="audit_level" width="20%">审核级别</td>
                        <td width="30%">审查范围</td>
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
                        <td><ct:codeParse catalog="DM_DEF_ZJLX" code="${p.type }"/></td>
                        <td><ct:codeParse catalog="DM_SYS_ZJPSJB" code="${p.level }"/></td>
                        <td><span id="sjfw_${p.id}"></span></td>
                        
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
